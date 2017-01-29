(ns git-butler.git.core
  (:require [clojure.java.shell :refer [sh]]
            [git-butler.github.url-builder :as gu]
            [git-butler.git.command-builders :as c]))

(defmacro defcommand
  [name args body]
  `(defn ~name ~args (apply sh ~body)))

(defcommand clone-repo
  [repo-info token]
  (c/git-clone repo-info token gu/get-repo-url))

(defcommand checkout-branch
  [branch-name]
  (c/git-checkout branch-name))

(defcommand fetch
  []
  (c/git-fetch))

(defcommand merge-branch
  [branch-name]
  (c/git-merge branch-name))

(defcommand merge-squash
  [branch-name commit-message]
  (c/git-merge-squash branch-name commit-message))

(defcommand push
  [branch-name]
  (c/git-push "--set-upstream" "origin" branch-name))

(defcommand branch
  [branch-name]
  (c/git-branch branch-name))

(defcommand commit
  [commit-message]
  (c/git-commit commit-message))
