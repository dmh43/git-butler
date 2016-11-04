(ns git-butler.github
  (:require [git-butler.github.url-builder :as u]
            [git-butler.http :as http]
            [git-butler.json-to-clj :as j2c]
            [buddy.sign.jwt :as jwt]
            [buddy.core.keys :as auth-keys]
            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :as t]))

(def privkey (private-key "git-butler.2016-11-02.private-key.pem"))

(def payload {:exp (t/plus (t/now) (t/minutes 40))
              :iat (t/now)
              :iss 556})

(def my-jwt (-> payload jwt (sign :RS256 privkey) to-str))


(defn get-commit-status
  [{:keys [repo-owner
           repo-name
           commit] :as params}]
  (get-in
   (http/get (u/get-commit-status-url params)
             {:as :json})
   [:body :state]))

(defn merge-commit
  [{:keys [repo-owner
           repo-name
           base
           head
           commit-message] :as params}]
  (let [form-params (-> params
                        (select-keys [:base :head :commit-message])
                        j2c/coerce-keys)]
    (http/post (u/get-merge-url params)
               {:form-params form-params
                :headers {"Authorization" (str "token " "203280b13d8611d83a57b7f92eec2b240fd3ab77")}})))

(defn- create-authorization*
  [{:keys [scopes
           note
           note-url
           client-id
           client-secret
           fingerprint] :as options}]
  (let [form-params (-> options
                        j2c/coerce-keys)]
    (http/post u/authorizations-url
               {:form-params form-params})))

(defn authenticate [token]
  (http/get u/url
            {:query-params {:access_token token}}))

(defn create-authorization []
  (create-authorization*
   {:client-id "46660cc67953536ed1e1"
    :client-secret "2c281e6e7cc1c27edb3f86976e78db5fc69d34f7"
    :note-url "localhost:3000"
    :note "git-butler"
    :scopes "repo"}))
