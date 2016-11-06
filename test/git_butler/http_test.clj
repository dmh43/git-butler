(ns git-butler.http-test
  (:require [git-butler.http :as http]
            [clj-http.client :as client]
            [clojure.test :refer :all]
            [slingshot.slingshot :refer [throw+]]
            [helpers :refer :all]
            [git-butler.side-effect-helpers :refer :all]))

(def request-params {:throw-entire-message? true
                     :accept :json
                     :headers {"User-Agent" "git-butler"}})

(deftest http-get
  (let [get-response "GET response"
        request-url "google.com"
        get-calls (atom [])]

    (testing "valid response"
      (with-redefs-fn {#'client/get
                       (fn [& args]
                         (swap! get-calls
                                #(conj % args))
                         get-response)}

        (fn []
          (testing "no request params"
            (is (= (http/get request-url {})
                   get-response))
            (is (some (partial = [request-url request-params])
                      @get-calls)))

          (testing "no request params arg"
            (is (= (http/get request-url)
                   get-response))
            (is (some (partial = [request-url request-params])
                      @get-calls)))

          (testing "passes params to http/get"
            (let [additional-args {:headers
                                   {"Authorization" "token 123"}
                                   :repo_name "my-code"}]
              (is (= (http/get request-url additional-args)
                     get-response))
              (is (some (partial = [request-url
                                    {:headers {"Authorization" "token 123"
                                               "User-Agent" "git-butler"}
                                     :repo_name "my-code"
                                     :throw-entire-message? true,
                                     :accept :json}])
                        @get-calls)))))))

    (testing "invalid response"
      (let [get-status (sequentially [403 404 500])]
        (with-redefs-fn
          {#'client/get
           (fn [& args]
             (swap! get-calls
                    #(conj % args))
             (throw+ {:status (get-status)}))}

          (fn []
            (is-not-thrown? Exception
                            (http/get request-url {}))
            (is-not-thrown? Exception
                            (http/get request-url {}))
            (is (thrown? Exception
                         (http/get request-url {})))))))))
