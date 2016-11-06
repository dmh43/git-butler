(ns git-butler.github.core
  (:require [git-butler.github.url-builder :as u]
            [git-butler.http :as http]))


(defn get-commit-status
  [{:keys [repo-owner
           repo-name
           commit] :as params}]
  (get-in
   (http/get (u/get-commit-status-url params)
             {:as :json})
   [:body :state]))

(defn merge-commit
  [{:keys [repo-owner repo-name base head commit-message] :as params}])
