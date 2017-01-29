(ns git-butler.utils.core
  (:require [clojure.java.shell :refer [sh]]))

(def ^:dynamic *path*)

(defmacro defcommand
  [name args body]
  `(defn ~name ~args (apply sh ~body)))

(defn wrap-with-cd
  [command-vector]
  (let [cd-vector [:dir *path*]]
    (apply conj command-vector cd-vector)))

(defn at-path
  [path command-vector]
  (binding [*path* path]
    (wrap-with-cd command-vector)))
