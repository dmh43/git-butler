(ns git-butler.handler
  (:require [git-butler.github.core :as gh]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

(defn get-status-handler
  [params]
  (gh/get-commit-status params))

(defn merge-handler
  [data]
  (-> data
      (select-keys [:repo-owner :repo-name :base :head :commit-message])
      gh/merge-commit))

(defroutes app-routes
  (GET "/" request "git-butler")
  (GET "/:repo-owner/:repo-name/:commit/status" {params :params}
       (get-status-handler params))
  (POST "/:repo-owner/:repo-name/merge" {body :body
                                          params :params}
        (let [data (merge body params)]
          (merge-handler data)))
  (route/not-found "Not Found"))

(def app (-> app-routes
             (wrap-json-body {:keywords? true})
             (wrap-json-response {:keywords? true})
             (wrap-defaults (assoc site-defaults :security false))))
