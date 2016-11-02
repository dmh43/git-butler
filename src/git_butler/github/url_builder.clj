(ns git-butler.github.url-builder)

(def url "https://api.github.com/")

(defn get-commit-status-url
  [{:keys [repo-owner repo-name commit]}]
  (str url
       "repos/"
       repo-owner "/"
       repo-name "/"
       "commits/"
       commit "/"
       "status"))

(defn get-merge-url
  [{:keys [repo-owner repo-name commit] :as params}]
  (str url
       "repos/"
       repo-owner "/"
       repo-name "/"
       "merges/"))

(def authorizations-url
  (str url "authorizations"))
