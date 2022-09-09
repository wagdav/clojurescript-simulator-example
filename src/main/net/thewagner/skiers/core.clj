; Example from https://github.com/mbrooker/simulator_example/blob/main/ski_sim.py
(ns net.thewagner.skiers.core
  (:require [clojure.data.priority-map :refer [priority-map]]))

; Skier state :waiting :riding-lift :skiing
(def skier {:entity :skier
            :state :waiting})

(def lift {:entity :lift
           :state :leaving})

(defmulti handle-event (fn [_state _time e] (:event e)))

(defmethod handle-event :default [state t event] state)

; Skier
(defmethod handle-event :skier/waiting [state t event]
  (let [skiing-time 5]
    (update state :lift-queue conj (:skier/id event))))

(defmethod handle-event :skier/skiing [state t event]
  (let [skiing-time 5]
    (update state :events conj [(assoc event :event :skier/waiting) (+ t skiing-time)])))

; Lift
(defmethod handle-event :lift/leaving [state t event]
  (let [lift-time 1
        lift-capacity 4
        skiers (take lift-capacity (:lift-queue state))]
    (-> state
      (update :events into (for [s skiers] [(assoc s :event :skier/skiing) (+ t lift-time)]))
      (update :events conj [event (+ t lift-time lift-time)])
      (update :lift-queue #(drop lift-capacity %)))))

(defn simulate []
  (let [end-time 20]
    (loop [t 0
           state {:lift-queue (clojure.lang.PersistentQueue/EMPTY)
                  :skiers {:a {:state :waiting}
                           :b {:state :waiting}}
                  :events (priority-map {:event :skier/waiting :skier/id :a} 0
                                        {:event :skier/waiting :skier/id :b} 0
                                        {:event :lift/leaving} 0)}]
      (let [{:keys [events]} state]
        (if (and (seq events) (<= t end-time))
          (let [[ev t] (peek events)
                new-state (handle-event (update state :events pop) t ev)]
            (println t ev state)
            (recur t new-state))
          state)))))

(comment
  (handle-event {:events []} 10 {:event :skier/waiting :skier/id "Bob"})
  (handle-event {} 0 {:event :skier/skiing :skier/id "Alice"})
  (handle-event {:lift-queue [{:skier 1} {:skier 2}]
                 :events []}
                0
                {:entity :lift :state :leaving})
  (simulate))
