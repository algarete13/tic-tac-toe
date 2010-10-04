(ns tic-tac-toe.core
  (:require [clojure.set :as set]))


(def all-moves (range 9))

(defn is-move-possible [board move]
  (= 0 (nth board move))) 

(defn possible-moves [board]
  (filter (fn [move]
	    (is-move-possible board move))
	  all-moves))

(defn board-after-move [board move player]
  (assoc board move player))

(def winning-combinations 
     [#{0 1 2} #{3 4 5} 
      #{6 7 8} #{0 3 6} 
      #{1 4 7} #{2 5 8} 
      #{0 4 8} #{2 4 6}])
 
(defn remaining-combinations [board]
  (set (filter (fn [combo]
		 (not (let [contents
			    (set (map (fn [move]
					(nth board move))
				      combo))]
			(and (contains? contents 1)
			     (contains? contents -1)))))
	       winning-combinations)))

(defn one-move-from-win [board]
  (let [remaining-combinations (remaining-combinations board)
	one-space-remaining (filter (fn [combo]
				      (= 1
					 (count (filter (fn [move]
							  (is-move-possible board move))
							combo))))
				    remaining-combinations)]
    (set (filter (fn [move]
		   (is-move-possible board move))
		 (apply set/union one-space-remaining)))))

	    

;(defn combo-value [board

;(defn reasonable-moves [possible-moves]
;  (let [

(defn is-board-full [board]
  (empty? (possible-moves board)))

(defn won-by-method? [board method player]
  (every? (fn [position]
	    (= player
	       (nth board position)))
	  method))

(defn player-won? [board player]
  (some (fn [winning-combination]
	  (won-by-method? board winning-combination player))
	winning-combinations))

(defn who-won [board] 
  (cond
   (player-won? board 1) 1 
   (player-won? board -1) -1 
   true 0))

(defn max-key2
  "Returns the x for which (k x), a number, is greatest."
  ([k x] x)
  ([k x y] (if (> (k x) (k y)) x y))
  ([k x y & more]
     (second (reduce (fn [x y] (if (> (first x) (first y)) x y))
                     (map #(vector (k %) %) (cons x (cons y more)))))))

(defn max-value [score-fn & keys]
  (let [scores (map score-fn keys)]
      (reduce max scores)))    

(defn expected-result [board player]
  (let [winner (who-won board)]
    (cond (not (= 0 winner)) winner
	  (is-board-full board) 0
	  true (apply max-value (fn [move] (* player
					      (expected-result 
					       (board-after-move board move player)
					       (* -1 player)))) 
		      (possible-moves board)))))
				
(defn best-move [board player]
  (max-key2 (fn [move](* player
			 (expected-result 
				    (board-after-move board move player)
				      (* -1 player)))) 
	    (possible-moves board)))