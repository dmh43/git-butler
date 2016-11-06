(ns git-butler.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [git-butler.handler :refer :all]
            [git-butler.github.core :as gh]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "git-butler"))))

  (testing "status route"
    (with-redefs-fn {#'gh/get-commit-status (constantly "success")}
      (fn []
        (let [response (app (mock/request :get "/me/my-code/master/status"))]
          (is (= (:status response) 200))
          (is (= (:body response) "success"))))))

  (testing "merge route"
    (with-redefs-fn {#'gh/merge-commit (constantly "merge successful")}
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
