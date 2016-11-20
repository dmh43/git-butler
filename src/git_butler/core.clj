(ns git-butler.core
  (:require [git-butler.github.core :as gh]
            [git-butler.git.core :as g]
            [git-butler.github.url-builder :as u]))

(defn parent-branch
  [shipping-queue feature-branch-name base-branch-name]
  (if (= 1 (count shipping-queue))
    base-branch-name
    (reduce
     (fn [parent next-branch-name]
       (if (= next-branch-name feature-branch-name)
         (reduced parent)
         next-branch-name))
     shipping-queue)))

(defn process-feature-branch
  [{:keys [commit-info commit-message shipping-queue token]}]
  (let [{:keys [base head commit-message]} commit-info
        repo-url (u/get-repo-url (assoc commit-info :token token))]
    (when (gh/tests-pass? (assoc commit-info :commit head))
      (g/clone-repo repo-url)
      (g/checkout-branch head)
      (g/merge-branch (parent-branch @shipping-queue head))
      (if (gh/tests-pass? (assoc commit-info :commit head))
        (do
          (g/checkout-branch base)
          (g/merge-squash head commit-message))
        (drop shipping-queue head)))))
