(ns git-butler.cli.core
  (:require [me.raynes.fs :as fs]
            [git-butler.git.core :as g]))

(defn merge-feature-branch
  [token parent-branch feature-branch {:keys [repo-owner repo-name] :as repo-info}]
  (let [tmp-dir-info (fs/temp-dir (str repo-owner repo-name))
        path (str tmp-dir-info)]
    (g/clone-repo path repo-info token)
    (g/checkout-branch path parent-branch)
    (g/merge-branch path (str "origin/" feature-branch))
    (g/push path parent-branch)))

(defn create-branch
  [token parent-branch feature-branch {:keys [repo-owner repo-name] :as repo-info}]
  (let [tmp-dir-info (fs/temp-dir (str repo-owner repo-name))
        path (str tmp-dir-info)]
    (g/clone-repo path repo-info token)
    (g/checkout-branch path parent-branch)
    (g/branch path feature-branch)
    (g/checkout-branch path feature-branch)
    (g/push path feature-branch)))

(defn push-empty-commit
  [token branch-name {:keys [repo-owner repo-name] :as repo-info}]
  (let [tmp-dir-info (fs/temp-dir (str repo-owner repo-name))
        path (str tmp-dir-info)]
    (g/clone-repo path repo-info token)
    (g/checkout-branch path branch-name)
    (g/commit path "an empty commit")
    (g/push path branch-name)))
