(ns git-butler.http
  (:require [clj-http.client :as client]
            [slingshot.slingshot :refer [throw+ try+]]
            [cheshire.core :as ch]))

(defn- request
  ([method url params]
   (let [default-params {:throw-entire-message? true
                         :accept :json
                         :headers {"User-Agent" "git-butler"}
                         :content-type "application/json"}
         with-json-body (update params :body #(when % (ch/generate-string %)))]
     (try+
      (let [resp (method url (merge-with merge with-json-body default-params))
            content-type (get "Content-Type" (ch/parse-string (:headers resp)))] ;; HEADER MAPP
        (if (= content-type "application/json")
          (update resp :body ch/parse-string)
          resp))
      (catch [:status 403] {:keys [request-time headers body]}
        (println "403" request-time headers))
      (catch [:status 404] {:keys [request-time headers body]}
        (println "NOT Found 404" request-time headers body))
      (catch Object _
        (println (:throwable &throw-context) "unexpected error")
        (throw+)))))
  ([method url params oauth-token]
   (let [with-auth (assoc-in params
                           [:headers "Authorization"]
                           (str "token " oauth-token))]
     (request method url with-auth))))

(defn GET
  ([url] (GET url {}))
  ([url params]
   (request client/get url params))
  ([url params oauth-token]
   (request client/get url params oauth-token)))

(defn POST
  ([url] (POST url {}))
  ([url params]
   (request client/post url params))
  ([url params oauth-token]
   (request client/post url params oauth-token)))
