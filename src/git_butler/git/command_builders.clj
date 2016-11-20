(ns git-butler.git.command-builders)

(defn git-clone
  [repo-info token get-repo-url dest-path]
  (let [full-url (get-repo-url (assoc repo-info :token token))]
    ["git" "clone" full-url dest-path]))

(defn git-checkout
  [branch-name]
  ["git" "checkout" branch-name])

(defn git-merge
  [branch-name]
  ["git" "merge" "--no-edit" branch-name])

(defn git-merge-squash
  [branch-name commit-message]
  ["git" "merge" "--squash" branch-name "-m" commit-message])
