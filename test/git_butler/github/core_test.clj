(ns git-butler.github.core-test
  (:require [git-butler.github.core :as gh]
            [git-butler.http :as http]
            [clojure.test :refer :all]))

(deftest github-comm
  (testing "get-commit-status"
    (with-redefs-fn {#'http/get (constantly {:body {:state "success"}})}
      #(gh/get-commit-status {:repo-owner "me"
                              :repo-name "my-code"
                              :commit "master"}))))
