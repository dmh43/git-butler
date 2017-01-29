(ns git-butler.http
  (:require [clj-http.client :as client]
            [slingshot.slingshot :refer [throw+ try+]]))

(defn- request
  [method url params]
  (try+
   (method url (merge-with merge
                           params
                           {:throw-entire-message? true
                            :accept :json
                            :headers {"User-Agent" "git-butler"}}))
   (catch [:status 403] {:keys [request-time headers body]}
     (println "403" request-time headers))
   (catch [:status 404] {:keys [request-time headers body]}
     (println "NOT Found 404" request-time headers body))
   (catch Object _
     (println (:throwable &throw-context) "unexpected error")
     (throw+))))

(defn GET
  ([url] (GET url {}))
  ([url params]
   (request client/get url params)))
