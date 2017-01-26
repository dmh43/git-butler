(ns git-butler.queue-test
  (:require [git-butler.queue :as gb]
            [clojure.test :refer :all]))

(deftest butler
  (testing "parent-branch"
    (is (= (gb/parent-branch ["feature"] "feature" "master")
           "master"))
    (is (= (gb/parent-branch ["bug-fix" "feature"] "feature" "master")
           "bug-fix"))
    (is (= (gb/parent-branch ["bug-fix" "add-docs" "feature"] "feature" "master")
           "add-docs"))
    (is (= (gb/parent-branch ["bug-fix" "feature" "add-docs"] "feature" "master")
           "bug-fix")))

  (testing "push-merge"
    (is (= (gb/push-merge [] "feature")
           ["feature"]))
    (is (= (gb/push-merge ["bug-fix" "feature"] "feature")
           ["bug-fix" "feature"])))

  (testing "drop-merge"
    (is (= (gb/drop-merge [] "feature")
           []))
    (is (= (gb/drop-merge ["feature"] "feature")
           []))
    (is (= (gb/drop-merge ["bug-fix" "feature"] "feature")
           ["bug-fix"]))))
