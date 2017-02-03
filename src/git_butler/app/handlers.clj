(ns git-butler.app.handlers
  (:require [git-butler.auth.core :as a]
            [hiccup.core :refer :all]
            [ring.util.response :refer [response content-type]]
            [git-butler.http :as req]
            [git-butler.github.url-builder :as u]))

(defn- handler-wrapper
  [html session]
  (-> html
      response
      (content-type "text/html")
      (assoc :session session)))

(defn oauth-callback
  [body params session]
  (let [session-code (:code params)
        token (a/get-oauth-token session-code)
        session (assoc session :token token)]
    (handler-wrapper "Token acquired!" session)))

(defn root
  [body params session]
  (-> (html [:h1 "Welcome to Git Butler!"]
            (if (not (:token session))
              [:a {:href (a/get-authorize-url)} "Get oauth token"]
              [:div
               [:div (str "Your token is: " (:token session))]
               [:div
                [:a {:href "/get-repos"} "Get repos"]
                [:a {:href "/forget-token"} "Forget token"]]]))
      (handler-wrapper session)))

(defn fetch-repos
  [token]
  (req/GET u/list-repos-url {} token))

(defn list-repos
  [repo-infos]
  (html (for [repo-info repo-infos]
          [:div (get repo-info "name")])))

(defn get-repos
  [body params session]
  (-> (fetch-repos (:token session))
      :body
      list-repos
      (handler-wrapper session)))

(defn forget-token
  [body params session]
  (-> "Token forgotten"
      (handler-wrapper (assoc session :token nil))))
