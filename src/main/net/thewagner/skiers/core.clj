; Example from https://github.com/mbrooker/simulator_example/blob/main/ski_sim.py
(ns net.thewagner.skiers.core
  (:require [shams.priority-queue :as pq]))

(defn dec0 [x]
  (max (dec x) 0))

(defn clamp [x a b]
  (cond
    (< x a)    a
    (<= a x b) x
    (< b x)    b))

(defmulti handle-event (fn [state event] (:event event)))

(defmethod handle-event :default [state event] state)

; Skier
(defmethod handle-event :skier/joined-queue [state event]
  (-> state
    (update :skiers/skiing dec0)
    (update :skiers/waiting inc)))

(defmethod handle-event :skier/left-lift [state event]
  (let [skiing-time 50]
    (-> state
      (update :skiers/riding-lift dec0)
      (update :skiers/skiing inc)
      (update :events conj {:event :skier/joined-queue :t (+ (event :t) skiing-time)}))))

; Lift
(defmethod handle-event :lift/leaving [state event]
  (let [ride-time 10
        chair-width 4
        chair-period 4
        riders (clamp (:skiers/waiting state) 0 chair-width)]
    (-> state
      (update :skiers/waiting - riders)
      (update :skiers/riding-lift + riders)
      (update :events into (replicate riders {:event :skier/left-lift :t (+ (event :t) ride-time)}))
      (update :events conj {:event :lift/leaving :t (+ (event :t) chair-period)}))))

(def initial-state
  {:skiers/waiting 2
   :skiers/riding-lift 0
   :skiers/skiing 0
   :events (pq/priority-queue (fn [e] (- (:t e))) :elements [{:event :lift/leaving :t 0}])})

(defn simulate []
  (let [end-time 20]
    (loop [t 0
           state initial-state]
      (let [{:keys [events]} state]
        (if (and (seq events) (<= t end-time))
          (let [ev (peek events)
                new-state (handle-event (update state :events pop) ev)]
            (println ev state)
            (recur (ev :t) new-state))
          state)))))

(comment
  (handle-event initial-state {:event :skier/joined-queue :t 0})
  (handle-event initial-state {:event :lift/leaving :t 0})
  (handle-event initial-state {:event :skier/left-lift :t 0})
  (simulate))
