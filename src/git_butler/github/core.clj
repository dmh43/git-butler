(ns git-butler.github.core
  (:require [git-butler.github.url-builder :as u]
            [git-butler.http :as http]
            [git-butler.json-to-clj :as j2c]))


(defn get-commit-status
  [{:keys [repo-owner
           repo-name
           commit] :as params}]
  (get-in
   (http/get (u/get-commit-status-url params)
             {:as :json})
   [:body :state]))

(defn merge-commit
  [{:keys [repo-owner
           repo-name
           commit
           base
           head
           commit-message] :as params}]
  (let [form-params (-> params
                        (select-keys [:commit-message :base :head])
                        j2c/coerce-keys)]
    (http/post (u/get-merge-url params)
               {:form-params form-params})))
