(ns git-butler.json-to-clj)

(defn coerce-keys
  [map]
  (reduce-kv
   (fn [new-map key-to-check val]
     (assoc
      new-map
      (-> key-to-check
          name
          (.replace "-" "_")
          keyword)
      val))
   {}
   map))
