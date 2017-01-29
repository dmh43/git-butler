(ns git-butler.cli.core-test
  (:require [git-butler.cli.core :as sut]
            [clojure.test :refer :all]
            [config.core :refer [env]]))

(def oauth-token (:github-oauth-token env))
(def testing-repo-info (:test-repo-info env))

(deftest feature-test
  (let [parent-branch "master"
        feature-branch (str (gensym) "feature-branch")]
    (is (= 0 (:exit
              (sut/create-branch oauth-token parent-branch feature-branch testing-repo-info))))
    (is (= 0 (:exit
              (sut/push-empty-commit oauth-token feature-branch testing-repo-info))))
    (is (= 0 (:exit
              (sut/merge-feature-branch oauth-token parent-branch feature-branch testing-repo-info))))))
