(ns git-butler.side-effect-helpers)

(defn- counter []
  (let [cnt (atom 0)]
    {:inc! (fn [] (swap! cnt inc))
     :get (fn [] @cnt)} ))

(defn sequentially
  [elems]
  (let [n (counter)]
    #(get elems (let [v ((:get n))]
                 ((:inc! n))
                 v))))
