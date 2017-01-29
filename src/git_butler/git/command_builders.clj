(ns git-butler.git.command-builders)

(def ^:dynamic *path*)

(defn wrap-with-cd
  [command-vector]
  (let [cd-vector [:dir *path*]]
    (apply conj command-vector cd-vector)))

(defn at-path
  [path command-vector]
  (binding [*path* path]
    (wrap-with-cd command-vector)))

(defn git-clone
  [repo-info token get-repo-url]
  (wrap-with-cd
   (let [full-url (get-repo-url repo-info token)]
     ["git" "clone" full-url *path*])))

(defn git-checkout
  [branch-name]
  (wrap-with-cd ["git" "checkout" branch-name]))

(defn git-branch
  [branch-name]
  (wrap-with-cd ["git" "branch" branch-name]))

(defn git-fetch
  []
  (wrap-with-cd ["git" "fetch"]))

(defn git-merge
  [branch-name]
  (wrap-with-cd ["git" "merge" "--no-edit" branch-name]))

(defn git-merge-squash
  [branch-name commit-message]
  (wrap-with-cd ["git" "merge" "--squash" branch-name "-m" commit-message]))

(defn git-push
  [& args]
  (wrap-with-cd (apply conj ["git" "push"] args)))

(defn git-commit
  [commit-message]
  (wrap-with-cd ["git" "commit" "--allow-empty" "-m" commit-message]))
