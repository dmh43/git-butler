(ns git-butler.auth.core
  (:require [config.core :refer [env]]
            [git-butler.http :as req]))

(def client-id (:client_id (:github-oauth-info env)))
(def client-secret (:client_secret (:github-oauth-info env)))

(defn get-authorize-url []
  (str "https://github.com/login/oauth/authorize?scope=user:email&client_id="
       client-id))

(defn get-oauth-token
  [session-code]
  (let [response (req/POST "https://github.com/login/oauth/access_token"
                           {:client_id client-id
                            :client_secret client-secret
                            :code session-code})]
    (get (:body response) "access_token")))
