(ns git-butler.github.core
  (:require [git-butler.github.url-builder :as u]
            [git-butler.http :as http]))

(defn get-commit-status
  [commit-info]
  (get-in
   (http/get (u/get-commit-status-url commit-info)
             {:as :json})
   [:body :state]))

(defn tests-pass?
  [commit-info]
  (= (get-commit-status commit-info)
     "success"))
