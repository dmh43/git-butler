(ns git-butler.http
  (:require [clj-http.client :as client]
            [slingshot.slingshot :refer [throw+ try+]]))

(defn- request
  [method url params]
  (try+
   (method url params)
   (catch [:status 403] {:keys [request-time headers body]}
     (println "403" request-time headers))
   (catch [:status 404] {:keys [request-time headers body]}
     (println "NOT Found 404" request-time headers body))
   (catch Object _
     (println (:throwable &throw-context) "unexpected error")
     (throw+))))

(defn get
  [url params]
  (request client/get url params))

(defn post
  [url params]
  (request client/post url params))
