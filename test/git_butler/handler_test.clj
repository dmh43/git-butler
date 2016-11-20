(ns git-butler.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [git-butler.handler :refer :all]
            [git-butler.core :as gb]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "git-butler"))))

  (testing "merge route"
    (with-redefs-fn {#'gb/process-feature-branch (constantly "merge successful")}
      (fn []
        (let [response (-> (mock/request
                            :post
                            "/me/my-code/merge")
                           (mock/body "{\"commit_message\": \"a feature\", \"base\": \"master\", \"head\": \"feature\"}")
                           (mock/content-type "application/json")
                           app)]
          (is (= (:status response) 200))
          (is (= (:body response) "merge successful"))))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
