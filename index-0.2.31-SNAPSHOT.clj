{:namespaces
 ({:doc nil,
   :name "clojure.tools.deps.graph",
   :wiki-url
   "https://clojure.github.io/tools.deps.graph/index.html#clojure.tools.deps.graph",
   :source-url
   "https://github.com/clojure/tools.deps.graph/blob/e0ffcfa30c55c5af48034ba1cdae9771a51a7ce0/src/main/clojure/clojure/tools/deps/graph.clj"}),
 :vars
 ({:raw-source-url
   "https://github.com/clojure/tools.deps.graph/raw/e0ffcfa30c55c5af48034ba1cdae9771a51a7ce0/src/main/clojure/clojure/tools/deps/graph.clj",
   :name "-main",
   :file "src/main/clojure/clojure/tools/deps/graph.clj",
   :source-url
   "https://github.com/clojure/tools.deps.graph/blob/e0ffcfa30c55c5af48034ba1cdae9771a51a7ce0/src/main/clojure/clojure/tools/deps/graph.clj#L216",
   :line 216,
   :var-type "function",
   :arglists ([& args]),
   :doc
   "Create deps graphs. By default, reads deps.edn in current directory, creates deps graph,\nand shows using a viewer. Use ctrl-c to exit.\n\nOptions:\n  -d DEPSFILE - deps.edn file to read, default=deps.edn\n  -t - Trace mode, will output one image per expansion step\n  -f TRACEFILE - Trace file mode - read trace file, don't use deps.edn file\n  -o FILE - Output file, in trace mode required and will create N images\n  -a - Concatenated alias names when reading deps file\n  --trace-omit - Comma-delimited list of libs to skip in trace images\n  --size - Include jar size in dep graph nodes",
   :namespace "clojure.tools.deps.graph",
   :wiki-url
   "https://clojure.github.io/tools.deps.graph//index.html#clojure.tools.deps.graph/-main"}
  {:raw-source-url
   "https://github.com/clojure/tools.deps.graph/raw/e0ffcfa30c55c5af48034ba1cdae9771a51a7ce0/src/main/clojure/clojure/tools/deps/graph.clj",
   :name "parse-opts",
   :file "src/main/clojure/clojure/tools/deps/graph.clj",
   :source-url
   "https://github.com/clojure/tools.deps.graph/blob/e0ffcfa30c55c5af48034ba1cdae9771a51a7ce0/src/main/clojure/clojure/tools/deps/graph.clj#L63",
   :line 63,
   :var-type "function",
   :arglists ([args]),
   :doc "Parse the command line opts to make-classpath",
   :namespace "clojure.tools.deps.graph",
   :wiki-url
   "https://clojure.github.io/tools.deps.graph//index.html#clojure.tools.deps.graph/parse-opts"}
  {:raw-source-url
   "https://github.com/clojure/tools.deps.graph/raw/e0ffcfa30c55c5af48034ba1cdae9771a51a7ce0/src/main/clojure/clojure/tools/deps/graph.clj",
   :name "parse-syms",
   :file "src/main/clojure/clojure/tools/deps/graph.clj",
   :source-url
   "https://github.com/clojure/tools.deps.graph/blob/e0ffcfa30c55c5af48034ba1cdae9771a51a7ce0/src/main/clojure/clojure/tools/deps/graph.clj#L31",
   :line 31,
   :var-type "function",
   :arglists ([s]),
   :doc
   "Parses a concatenated string of libs into a collection of symbols\nEx: (parse-libs \"org.clojure/clojure,org.clojure/test.check\")\nReturns: [org.clojure/clojure org.clojure/test.check]",
   :namespace "clojure.tools.deps.graph",
   :wiki-url
   "https://clojure.github.io/tools.deps.graph//index.html#clojure.tools.deps.graph/parse-syms"})}
