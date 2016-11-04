(ns git-butler.github.auth
  (:require [oauth.client :as oauth]))

#_(def consumer (oauth/make-consumer "46660cc67953536ed1e1"
                                   "2c281e6e7cc1c27edb3f86976e78db5fc69d34f7"
                                   "https://github.com/login/oauth/request_token"
                                   "https://github.com/login/oauth/access_token"
                                   "https://github.com/login/oauth/authorize"
                                   :hmac-sha1))

;(def request-token (oauth/request-token consumer "http://localhost:3000/callback"))

;; (oauth/user-approval-uri consumer
;;                          (:oauth_token request-token))

;; (def access-token-response (oauth/access-token consumer
;;                                                request-token))
