(ns clojure.tools.deps.graph
  (:require
    [clojure.java.io :as jio]
    [clojure.string :as str]
    [clojure.tools.cli :as cli]
    [clojure.tools.deps.alpha :as deps]
    [clojure.tools.deps.alpha.reader :as reader]
    [clojure.tools.deps.alpha.script.parse :as parse]
    [clojure.tools.deps.alpha.script.make-classpath2 :as makecp]
    [clojure.tools.deps.alpha.util.session :as session]
    [clojure.tools.deps.alpha.util.io :as io :refer [printerrln]]
    [dorothy.core :as dot]
    [dorothy.jvm :as dotjvm]
    [clojure.string :as str])
  (:import
    [clojure.lang IExceptionInfo]))

(def ^:private opts
  [;; aliases
   ["-R" "--resolve-aliases ALIASES" "Concatenated resolve-deps alias names" :parse-fn parse/parse-kws]
   ["-A" "--aliases ALIASES" "Concatenated generic alias names" :parse-fn parse/parse-kws]])

(defn parse-opts
  "Parse the command line opts to make-classpath"
  [args]
  (cli/parse-opts args opts))

(defn node-id
  [lib]
  (keyword lib)
  (if (= (ns lib) (name lib))
    (keyword (ns lib))
    (keyword lib)))

(defn make-node
  [lib {:keys [mvn/version git/url sha deps/manifest local/root] :as coord}]
  (let [id (node-id lib)
        node-name (if (= (ns lib) (name lib)) (str (ns lib)) (str lib))
        label (str/join "|" (into [node-name]
                              (cond
                                version [version]
                                url [url (subs sha 0 7)]
                                root [root]
                                :else [])))
        node [id {:label label
                  :style :filled
                  :color :black
                  :fillcolor :lightgrey
                  :shape :record}]]
    node))

(defn make-edges
  [lib {:keys [dependents] :as coord}]
  (if (seq dependents)
    (map (fn [dlib] [(node-id dlib) (node-id lib)]) dependents)
    [[:root (node-id lib)]]))

(defn make-graph
  [lib-map]
  (let [statements (mapcat
                     (fn [[lib coord]]
                       (into [[:root {:label "deps.edn"
                                      :shape :box
                                      :fillcolor :cadetblue1
                                      :style :filled}]
                              (make-node lib coord)]
                         (make-edges lib coord)))
                     lib-map)]
    ;(clojure.pprint/pprint statements)
    (-> (dot/digraph (concat [{:rankdir :LR
                               :splines :polyline}]
                       statements))
      dot/dot
      dotjvm/show!
      ;(dotjvm/save! "out.png" {:format :png})
      )))

(defn run
  [{:keys [resolve-aliases aliases] :as opts}]
  (let [install-deps (reader/install-deps)
        user-dep-loc (jio/file (reader/user-deps-location))
        user-deps (when (.exists user-dep-loc) (reader/slurp-deps user-dep-loc))
        project-dep-loc (jio/file "deps.edn")
        project-deps (when (.exists project-dep-loc) (reader/slurp-deps project-dep-loc))
        deps-map (->> [install-deps user-deps project-deps] (remove nil?) reader/merge-deps)
        active-aliases (concat resolve-aliases aliases)]
    (makecp/check-aliases deps-map active-aliases)
    (let [deps-map' (if-let [replace-deps (get (deps/combine-aliases deps-map aliases) :deps)]
                      (->> [install-deps user-deps project-deps {:deps replace-deps}] (remove nil?) reader/merge-deps)
                      deps-map)
          resolve-args (deps/combine-aliases deps-map' active-aliases)
          lib-map (session/with-session (deps/resolve-deps deps-map' resolve-args))]
      (make-graph lib-map))))

(defn -main
  "Main entry point for make-classpath script.

  Options:
    -Rresolve-aliases - concatenated resolve-deps alias names
    -Aaliases - concatenated generic alias names

  Resolves the dependencies, creates the lib map, then produces a graphviz diagram."
  [& args]
  (try
    (let [{:keys [options errors]} (parse-opts args)]
      (when (seq errors)
        (run! println errors)
        (System/exit 1))
      (run options))
    (catch Throwable t
      (printerrln "Error building classpath." (.getMessage t))
      (when-not (instance? IExceptionInfo t)
        (.printStackTrace t))
      (System/exit 1))))

