(ns git-butler.git.command-builders)

(defn wrap-with-cd
  [path command-vector]
  (let [cd-vector [:dir path]]
    (apply conj command-vector cd-vector)))

(defn git-clone
  [path repo-info token get-repo-url]
  (wrap-with-cd
   path
   (let [full-url (get-repo-url (assoc repo-info :token token))]
     ["git" "clone" full-url path])))

(defn git-checkout
  [path branch-name]
  (wrap-with-cd path ["git" "checkout" branch-name]))

(defn git-fetch
  [path]
  (wrap-with-cd path ["git" "fetch"]))

(defn git-merge
  [path branch-name]
  (wrap-with-cd path ["git" "merge" "--no-edit" branch-name]))

(defn git-merge-squash
  [path branch-name commit-message]
  (wrap-with-cd path ["git" "merge" "--squash" branch-name "-m" commit-message]))

(defn git-push
  [path]
  (wrap-with-cd path ["git" "push"]))
