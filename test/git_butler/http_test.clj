(ns git-butler.http-test
  (:require [git-butler.http :as http]
            [clj-http.client :as client]
            [clojure.test :refer :all]
            [slingshot.slingshot :refer [throw+]]
            [helpers :refer :all]
            [git-butler.side-effect-helpers :refer :all]))

(def request-params {:body nil
                     :throw-entire-message? true
                     :accept :json
                     :headers {"User-Agent" "git-butler"}
                     :content-type "application/json"})

(deftest http-get
  (let [get-response {:body "{\"text\": \"GET response\"}"}
        request-url "google.com"
        get-calls (atom [])]

    (testing "valid response"
      (with-redefs-fn {#'client/get
                       (fn [& args]
                         (swap! get-calls #(conj % args))
                         get-response)}

        (fn []
          (testing "no request params"
            (is (= (http/GET request-url)
                   get-response))
            (is (some (partial = [request-url request-params])
                      @get-calls)))

          (testing "passes params to the request method"
            (let [params {:body {:repo_name "my-code"}}
                  token "123"]
              (is (= (http/GET request-url params token)
                     get-response))
              (is (some (partial = [request-url
                                    {:headers {"Authorization" "token 123"
                                               "User-Agent" "git-butler"}
                                     :body "{\"repo_name\":\"my-code\"}"
                                     :throw-entire-message? true,
                                     :accept :json
                                     :content-type "application/json"}])
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
                            (http/GET request-url {}))
            (is-not-thrown? Exception
                            (http/GET request-url {}))
            (is (thrown? Exception
                         (http/GET request-url {})))))))))
