(ns git-butler.handler
  (:require [git-butler.core :as gb]
            [git-butler.middleware :as m]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

(defn merge-handler
  [data]
  (-> data
      (select-keys [:repo-id :feature-branch :base-branch :commit-message])
      gb/process-feature-branch))

(defroutes app-routes
  (GET "/" request "git-butler")
  (POST "/:repo-owner/:repo-name/merge" {body :body
                                         params :params}
        (let [data (merge body params)]
          (merge-handler data)))
  (route/not-found "Not Found"))

(def app (-> app-routes
             m/chomp-trailing-slash
             (wrap-json-body {:keywords? true})
             (wrap-json-response {:keywords? true})
             (wrap-defaults (assoc site-defaults :security false))))
