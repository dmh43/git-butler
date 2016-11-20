(ns git-butler.git.command-builders)

(defn git-clone
  [{:keys [path token]} dest-path]
  (let [full-url (str "https://" token "@github.com" path)]
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
