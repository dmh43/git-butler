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

  (testing "push-branch"
    (is (= (gb/push-branch [] "feature")
           ["feature"]))
    (is (= (gb/push-branch ["bug-fix" "feature"] "feature")
           ["bug-fix" "feature"])))

  (testing "drop-branch"
    (is (= (gb/drop-branch [] "feature")
           []))
    (is (= (gb/drop-branch ["feature"] "feature")
           []))
    (is (= (gb/drop-branch ["bug-fix" "feature"] "feature")
           ["bug-fix"]))))
