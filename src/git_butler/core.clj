(ns git-butler.core
  (:require [git-butler.github.core :as gh]
            [git-butler.git.core :as g]
            [git-butler.queue :as q]
            [git-butler.github.url-builder :as u]))

(def init (constantly {}))

(defn get-repo-info
  [repos repo-id]
  (get repos repo-id))

(defn init-repo
  [repos repo-id repo-url]
  (assoc repos repo-id {:queues {}
                        :repo-url repo-url}))

(defn create-queue
  [repos repo-id base-branch]
  (assoc-in repos [repo-id :queues base-branch] []))

(defn add-to-queue
  [repos {:keys [repo-id feature-branch base-branch commit-message]}]
  (update-in repos
             [repo-id :queues base-branch]
             #(q/push-merge % {:feature-branch feature-branch
                               :commit-message commit-message})))

(defn drop-from-queue
  [repos {:keys [repo-id feature-branch base-branch commit-message]}]
  (update-in repos
             [repo-id :queues base-branch]
             #(q/drop-merge % {:feature-branch feature-branch
                               :commit-message commit-message})))

(defn process-feature-branch
  [{:keys [commit-info commit-message shipping-queue token]}]
  #_(let [{:keys [base head commit-message]} commit-info
        repo-url (u/get-repo-url (assoc commit-info :token token))]
    (when (gh/tests-pass? (assoc commit-info :commit head))
      (g/clone-repo repo-url)
      (g/checkout-branch head)
      (g/merge-branch (q/parent-branch @shipping-queue head))
      (if (gh/tests-pass? (assoc commit-info :commit head))
        (do
          (g/checkout-branch base)
          (g/merge-squash head commit-message))
        (drop-from-queue shipping-queue head)))))
