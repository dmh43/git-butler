(ns git-butler.core
  (:require [git-butler.github.core :as gh]
            [git-butler.git.core :as g]
            [git-butler.queue :as q]
            [git-butler.github.url-builder :as u]))

(defn process-feature-branch
  [{:keys [commit-info commit-message shipping-queue token]}]
  (let [{:keys [base head commit-message]} commit-info
        repo-url (u/get-repo-url (assoc commit-info :token token))]
    (when (gh/tests-pass? (assoc commit-info :commit head))
      (g/clone-repo repo-url)
      (g/checkout-branch head)
      (g/merge-branch (q/parent-branch @shipping-queue head))
      (if (gh/tests-pass? (assoc commit-info :commit head))
        (do
          (g/checkout-branch base)
          (g/merge-squash head commit-message))
        (q/drop-branch shipping-queue head)))))
