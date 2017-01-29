(ns git-butler.git.core
  (:require [git-butler.github.url-builder :as gu]
            [git-butler.utils.core :refer [defcommand wrap-with-cd]]))

(defmacro git
  [git-command & args]
  `(wrap-with-cd ~(apply conj ["git" (str git-command)] args)))

(defcommand clone-repo
  [repo-info token]
  (let [full-url (gu/get-repo-url repo-info token)]
    ;; get-repo-url should be generic rather than github specific
    (git clone (gu/get-repo-url repo-info token) "./")))

(defcommand checkout-branch
  [branch-name]
  (git checkout branch-name))

(defcommand fetch
  []
  (git fetch))

(defcommand merge-branch
  [branch-name]
  (git merge "--no-edit" branch-name))

(defcommand merge-squash
  [branch-name commit-message]
  (git merge "--squash" branch-name "-m" commit-message))

(defcommand push
  [branch-name]
  (git push "--set-upstream" "origin" branch-name))

(defcommand branch
  [branch-name]
  (git branch branch-name))

(defcommand commit
  [commit-message]
  (git commit "--allow-empty" "-m" commit-message))
