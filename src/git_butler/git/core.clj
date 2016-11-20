(ns git-butler.git.core
  (:require [me.raynes.fs :as fs]
            [clojure.java.shell :refer [sh]]
            [git-butler.git.command-builders :as gc]))

(defn merge-commit
  [{:keys [repo-owner repo-name base head commit-message] :as params}])

(defn clone-repo
  [repo-info token]
  (let [tmp-dir-info (fs/temp-dir "butler-repo-tmp")
        dest-path (str tmp-dir-info)
        command (gc/git-clone repo-info token dest-path)]
    (apply sh command)))

(defn checkout-branch
  [branch-name]
  (apply sh (gc/git-checkout branch-name)))

(defn merge-branch
  [branch-name]
  (apply sh (gc/git-merge branch-name)))

(defn merge-squash
  [branch-name commit-message]
  (apply sh (gc/git-merge-squash branch-name commit-message)))
