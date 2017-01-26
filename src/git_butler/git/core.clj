(ns git-butler.git.core
  (:require [clojure.java.shell :refer [sh]]
            [git-butler.github.url-builder :as gu]
            [git-butler.git.command-builders :as c]))

(defn merge-commit
  [{:keys [repo-owner repo-name base head commit-message] :as params}])

(defn clone-repo
  [path repo-info token]
  (let [command (c/git-clone path repo-info token gu/get-repo-url)]
    (apply sh command)))

(defn checkout-branch
  [path branch-name]
  (apply sh (c/git-checkout path branch-name)))

(defn fetch
  [path]
  (apply sh (c/git-fetch path)))

(defn merge-branch
  [path branch-name]
  (apply sh (c/git-merge path branch-name)))

(defn merge-squash
  [path branch-name commit-message]
  (apply sh (c/git-merge-squash path branch-name commit-message)))

(defn push
  [path]
  (apply sh (c/git-push path)))
