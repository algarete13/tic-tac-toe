(ns tic-tac-toe.test-core
  (:use [tic-tac-toe.core] :reload)
  (:use [clojure.test]))


(def eboard [-1 1 1 0 0 1 -1 1 0])

(deftest test-move-possible 
  (is (= false (is-move-possible eboard 7))
      "Move 7 is not possible.")
  (is (= true (is-move-possible eboard 8))
      "Move 8 is possible."))
      
(deftest test-possible-moves
  (is (= [3 4 8] (possible-moves eboard))))

(deftest test-what-board-looks-like-after-move 
  (is (=  [0 0 0 1 -1 -1 0 1 1]
          (what-board-looks-like-after-move
              [0 0 0 1 -1 -1 0 0 1]
              7
              1))))