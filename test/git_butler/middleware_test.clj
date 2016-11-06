(ns git-butler.middleware-test
  (:require [git-butler.middleware :as m]
            [ring.mock.request :as mock]
            [clojure.test :refer :all]))

(def handler identity)

(deftest middleware
  (testing "indifferent to trailing slash"
    (let [request (mock/request :get "/")]
      (is (= ((m/chomp-trailing-slash handler) request)
             request)))

    (let [request (mock/request :get "/maps")]
      (is (= ((m/chomp-trailing-slash handler) request)
             request)))

    (let [request (mock/request :get "/maps/")]
      (is (= ((m/chomp-trailing-slash handler) request)
             (assoc request :uri "/maps"))))))
