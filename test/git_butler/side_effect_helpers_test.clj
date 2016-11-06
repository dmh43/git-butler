(ns git-butler.side-effect-helpers-test
  (:require [git-butler.side-effect-helpers :refer :all]
            [clojure.test :refer :all]))

(deftest iterate-sequentially
  (testing "sequentially"

    (is (= (take-while (complement nil?)
                       (repeatedly (sequentially [1 2 3 4])))
           '(1 2 3 4)))

    (is (= ((sequentially [1 2 3 4]))
           1))

    (let [get-next (sequentially [1 2 3 4])]
      (is (= (get-next)
             1))
      (is (= (get-next)
             2))
      (is (= (get-next)
             3))
      (is (= (get-next)
             4))
      (is (= (get-next)
             nil)))))
