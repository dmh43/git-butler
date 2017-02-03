(ns git-butler.app.core
  (:require [git-butler.middleware :as m]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.session :refer [wrap-session]]
            [git-butler.app.handlers :as handler]))

(defroutes app-routes
  (GET "/" {body :body, params :params, session :session}
       (handler/root body params session))
  (GET "/callback" {body :body params :params session :session}
       (handler/oauth-callback body params session))
  (GET "/forget-token" {body :body params :params session :session}
       (handler/forget-token body params session))
  (GET "/get-repos" {body :body params :params session :session}
       (handler/get-repos body params session))
  (route/not-found "Not Found"))

(def app (-> app-routes
             m/chomp-trailing-slash
             (wrap-json-body {:keywords? true})
             (wrap-json-response {:keywords? true})
             (wrap-defaults (assoc site-defaults :security false))
             wrap-session))
