(ns git-butler.handler
  (:require [git-butler.github :as gh]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn get-status-handler
  [{:keys [repo-owner repo-name commit] :as params}]
  (gh/get-commit-status params))

(defn merge-handler
  [{:keys [repo-owner repo-name commit base head commit-message] :as params}]
  (gh/merge-commit params))

(defroutes app-routes
  (GET "/:repo-owner/:repo-name/:commit/status" {params :params}
       (get-status-handler params))
  (GET "/:repo-owner/:repo-name/:commit/merge/:base/:head/:commit-message" {params :params}
        (merge-handler params))
  (route/not-found "Not Found"))

(def app (wrap-defaults app-routes (assoc site-defaults :security false)))
