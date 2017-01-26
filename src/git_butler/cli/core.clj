(ns git-butler.cli.core
  (:require [me.raynes.fs :as fs]
            [git-butler.git.core :as g]))

(let [tmp-dir-info (fs/temp-dir "butler-repo-tmp")
      path (str tmp-dir-info)] (println path)
     (g/clone-repo path
                   {:repo-owner "dmh43" :repo-name "special-train"}
                   "312d6558666412d229d6eebc4fa15a21f8863b32")
     (g/checkout-branch path "master")
     (g/merge-branch path "origin/for-merge")
     (g/push path))
