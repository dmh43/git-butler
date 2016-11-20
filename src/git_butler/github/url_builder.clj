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

(defn get-repo-url
  [{:keys [token repo-owner repo-name]}]
  (str "https://" token "@github.com/" repo-owner "/" repo-name ".git"))
