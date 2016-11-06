(ns git-butler.github.url-builder-test
  (:require [git-butler.github.url-builder :as u]
            [clojure.test :refer :all]))

(deftest building-urls
  (testing "get-commit-status-url"
    (is (= (u/get-commit-status-url {:repo-owner "me"
                                     :repo-name "my-code"
                                     :commit "master"})
           "https://api.github.com/repos/me/my-code/commits/master/status"))))
