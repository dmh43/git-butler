(ns git-butler.middleware)

(defn chomp-trailing-slash
  "Removes a single trailing slash from the end of the uri if present"
  [handler]
  (fn [request]
    (let [uri (:uri request)]
      (->
       (assoc request :uri (if (and (not (= "/" uri))
                                    (.endsWith uri "/"))
                             (subs uri 0 (dec (count uri)))
                             uri))
       handler))))
