; Example from https://github.com/mbrooker/simulator_example/blob/main/ski_sim.py
(ns net.thewagner.skiers.core
  (:require [clojure.data.priority-map :refer [priority-map]]))

; Skier state :waiting :riding-lift :skiing
(def skier {:entity :skier
            :state :waiting})

(def lift {:entity :lift
           :state :leaving})

(defmulti handle-event (fn [_state _time e] ((juxt :entity :state) e)))

(defmethod handle-event [:skier :waiting] [state t event]
  (let [skiing-time 5]
    (update state :events conj [(assoc event :state :waiting) (+ t skiing-time)])))

(defmethod handle-event [:skier :skiing] [state t event]
  (let [skiing-time 5]
    (update state :events conj [(assoc event :state :waiting) (+ t skiing-time)])))

(defmethod handle-event :lift [_ t event]
  (let [lift-time 10]))

(defn simulate []
  (let [end-time 20]
    (loop [t 0
           state {:lift-queue (clojure.lang.PersistentQueue/EMPTY)
                  :events (priority-map {:entity :skier :id 1 :state :waiting} 0)}]
      (let [{:keys [events]} state]
        (if (and (seq events) (<= t end-time))
          (let [[ev t] (peek events)
                new-state (handle-event (update state :events pop) t ev)]
            (recur t new-state))
          state)))))

(comment
  (handle-event {:events []} 10 skier)
  (handle-event {} 0 (merge skier {:state :skiing}))
  (simulate))
