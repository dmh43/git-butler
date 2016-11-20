(ns git-butler.core-test
  (:require [git-butler.core :as gb]
            [clojure.test :refer :all]))

(deftest butler
  (testing "parent-branch"
    (is (= (gb/parent-branch '("feature") "feature" "master")
           "master"))
    (is (= (gb/parent-branch '("bug-fix" "feature") "feature" "master")
           "bug-fix"))
    (is (= (gb/parent-branch '("bug-fix" "add-docs" "feature") "feature" "master")
           "add-docs"))
    (is (= (gb/parent-branch '("bug-fix" "feature" "add-docs") "feature" "master")
           "bug-fix"))))
