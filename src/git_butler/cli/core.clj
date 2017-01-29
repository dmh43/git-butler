(ns git-butler.cli.core
  (:require [me.raynes.fs :as fs]
            [git-butler.git.core :as g]
            [git-butler.git.command-builders :as c]
            [git-butler.utils.core :refer [*path*]]))

(defn merge-feature-branch
  [token parent-branch feature-branch {:keys [repo-owner repo-name] :as repo-info}]
  (let [tmp-dir-info (fs/temp-dir (str repo-owner repo-name))]
    (binding [*path* (str tmp-dir-info)]
      (g/clone-repo repo-info token)
      (g/checkout-branch parent-branch)
      (g/merge-branch (str "origin/" feature-branch))
      (g/push parent-branch))))

(defn create-branch
  [token parent-branch feature-branch {:keys [repo-owner repo-name] :as repo-info}]
  (let [tmp-dir-info (fs/temp-dir (str repo-owner repo-name))]
    (binding [*path* (str tmp-dir-info)]
      (g/clone-repo repo-info token)
      (g/checkout-branch parent-branch)
      (g/branch feature-branch)
      (g/checkout-branch feature-branch)
      (g/push feature-branch))))

(defn push-empty-commit
  [token branch-name {:keys [repo-owner repo-name] :as repo-info}]
  (let [tmp-dir-info (fs/temp-dir (str repo-owner repo-name))]
    (binding [*path* (str tmp-dir-info)]
      (g/clone-repo repo-info token)
      (g/checkout-branch branch-name)
      (g/commit "an empty commit")
      (g/push branch-name))))
