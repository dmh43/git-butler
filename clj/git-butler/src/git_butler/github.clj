(ns git-butler.github
  (:require [git-butler.payload-builder :as p]
            [git-butler.url-builder :as u]
            [git-butler.http :as http]))


(defn get-commit-status
  [{:keys [repo-owner repo-name commit] :as params}]
  (get-in
   (http/get (u/get-commit-status-url params)
             {:as :json
              :accept :json
              :headers {"User-Agent" "git-butler"}})
   [:body :state]))

(defn merge-commit
  [{:keys [repo-owner repo-name commit base head commit-message] :as params}]
  (http/post (u/get-merge-url params)
             {:throw-entire-message? true
              :form-params (p/get-merge-payload params)}))
