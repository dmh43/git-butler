(ns git-butler.github.core-test
  (:require [git-butler.github.core :as gh]
            [git-butler.http :as http]
            [clojure.test :refer :all]))

(deftest github-comm
  (testing "tests-pass?"
    (with-redefs-fn {#'http/GET (constantly {:body {:state "success"}})}
      (fn []
        (is (gh/tests-pass? {:repo-owner "me"
                             :repo-name "my-code"
                             :commit "master"}))))))
