(ns git-butler.cli.core
  (:require [me.raynes.fs :as fs]
            [git-butler.git.core :as g]))

(defn merge-feature-branch
  [token parent-branch remote-name {:keys [repo-owner repo-name] :as repo-info}]
  (let [tmp-dir-info (fs/temp-dir (str repo-owner repo-name))
        path (str tmp-dir-info)]
    (g/clone-repo path repo-info token)
    (g/checkout-branch path parent-branch)
    (g/merge-branch path (str repo-owner "/" repo-name))
    (g/push path)))
