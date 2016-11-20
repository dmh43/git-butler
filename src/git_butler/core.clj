(ns git-butler.core
  (:require [git-butler.github.core :as gh]
            [git-butler.git.core :as git]
            [git-butler.github.url-builder :as u]))

(defn parent-branch
  [process-queue feature-branch-name base-branch-name]
  (if (= 1 (count process-queue))
    base-branch-name
    (reduce
     (fn [parent next-branch-name]
       (if (= next-branch-name feature-branch-name)
         (reduced parent)
         next-branch-name))
     process-queue)))

(defn process-feature-branch
  [{:keys [commit-info commit-message process-queue token]}]
  (let [{:keys [base head commit-message]} commit-info
        repo-url (u/get-repo-url (assoc commit-info :token token))]
    (when (gh/tests-pass? (assoc commit-info :commit head))
      (git/clone-repo repo-url)
      (git/checkout-branch head)
      (git/merge-branch (parent-branch @process-queue head))
      (if (gh/tests-pass? (assoc commit-info :commit head))
        (do
          (git/checkout-branch base)
          (git/merge-squash head commit-message))
        (drop process-queue head)))))
