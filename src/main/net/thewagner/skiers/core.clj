; Example from https://github.com/mbrooker/simulator_example/blob/main/ski_sim.py
(ns net.thewagner.skiers.core
  (:require [clojure.data.priority-map :refer [priority-map]]))

; Skier state :waiting :riding-lift :skiing
(def skier {:state :waiting})

(defn handle [t {state :state :as event}]
  (println "@" t event)
  (let [skiing-time 5
        waiting-time 10]
    (case state
      :waiting [(assoc event :state :skiing) (+ t waiting-time)]
      :skiing  [(assoc event :state :waiting) (+ t skiing-time)])))

(defn simulate []
  (let [end-time 20]
    (loop [t 0
           events (priority-map {:skier 1 :state :waiting} t)]
      (if (and (seq events) (<= t end-time))
        (let [[ev t] (peek events)
              new-events (partition 2 (handle t ev))]
          (recur t (if (seq new-events)
                     (into (pop events) new-events)
                     (pop events))))
        events))))

(comment
  (handle 0 {:state :waiting})
  (handle 0 {:state :skiing})
  (simulate))
