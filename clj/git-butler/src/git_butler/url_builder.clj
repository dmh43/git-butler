(ns git-butler.url-builder)

(defn get-commit-status-url
  [{:keys [repo-owner repo-name commit]}]
  (str "https://api.github.com/repos/"
       repo-owner "/"
       repo-name "/"
       "commits/"
       commit "/"
       "status"))

(defn get-merge-url
  [{:keys [repo-owner repo-name commit] :as params}]
  (str "https://api.github.com/repos/"
       repo-owner "/"
       repo-name "/"
       "merges/"))
