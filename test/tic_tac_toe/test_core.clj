(ns tic-tac-toe.test-core
  (:use [tic-tac-toe.core] :reload)
  (:use [clojure.test]))


(def eboard [-1 1 1 0 0 1 -1 1 0])

(deftest test_move_possible 
  (is (= false (move-possible eboard 7))
      "Move 7 is not possible.")
  (is (= true (move-possible eboard 8))
      "Move 8 is possible."))
      
		