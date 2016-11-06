(ns helpers
  (:require  [clojure.test :refer :all]))

(defmacro is-not-thrown? [e expr]
  `(is (not ('thrown? ~e ~expr))))
