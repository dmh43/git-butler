(ns git-butler.json-to-clj-test
  (:require [git-butler.json-to-clj :as j2c]
            [clojure.test :refer :all]))

(deftest json-to-clj
  (testing "coerce-keys"
    (is (= (j2c/coerce-keys {:dogs 1 :cats 2 :mules 4})
           {:dogs 1 :cats 2 :mules 4}))

    (is (= (j2c/coerce-keys {:commit-status "success"
                             :commit "master"
                             :repo-name "my-code"})
           {:commit_status "success"
            :commit "master"
            :repo_name "my-code"}))

    (is (= (j2c/coerce-keys {:commit_status "success"
                             :commit "master"
                             :repo_name "my-code"})
           {:commit_status "success"
            :commit "master"
            :repo_name "my-code"}))))
