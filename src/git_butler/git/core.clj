(ns git-butler.git.core
  (:require [clojure.java.shell :refer [sh]]
            [git-butler.github.url-builder :as gu]
            [git-butler.git.command-builders :as c]))

(defmacro defcommand
  [name args body]
  `(defn ~name ~args (apply sh ~body)))

(defcommand clone-repo
  [path repo-info token]
  (c/git-clone path repo-info token gu/get-repo-url))

(defcommand checkout-branch
  [path branch-name]
  (c/git-checkout path branch-name))

(defcommand fetch
  [path]
  (c/git-fetch path))

(defcommand merge-branch
  [path branch-name]
  (c/git-merge path branch-name))

(defcommand merge-squash
  [path branch-name commit-message]
  (c/git-merge-squash path branch-name commit-message))

(defcommand push
  [path branch-name]
  (c/git-push path "--set-upstream" "origin" branch-name))

(defcommand branch
  [path branch-name]
  (c/git-branch path branch-name))

(defcommand commit
  [path commit-message]
  (c/git-commit path commit-message))

;; (defn clone-repo
;;   [path repo-info token]
;;   (let [command (c/git-clone path repo-info token gu/get-repo-url)]
;;     (apply sh command)))

;; (defn checkout-branch
;;   [path branch-name]
;;   (apply sh (c/git-checkout path branch-name)))

;; (defn fetch
;;   [path]
;;   (apply sh (c/git-fetch path)))

;; (defn merge-branch
;;   [path branch-name]
;;   (apply sh (c/git-merge path branch-name)))

;; (defn merge-squash
;;   [path branch-name commit-message]
;;   (apply sh (c/git-merge-squash path branch-name commit-message)))

;; (defn push
;;   [path]
;;   (apply sh (c/git-push path)))
