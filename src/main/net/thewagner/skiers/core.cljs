; Example from https://github.com/mbrooker/simulator_example/blob/main/ski_sim.py
(ns net.thewagner.skiers.core
  (:require [goog.string :refer [format]])
  (:import goog.structs.PriorityQueue))

(defn dec0 [x]
  (max (dec x) 0))

(defn clamp [x a b]
  (cond
    (< x a)    a
    (<= a x b) x
    (< b x)    b))

(defmulti handle-event (fn [state event] (:event event)))

(defmethod handle-event :default [state event] state)

(defmethod handle-event :skier/joins-queue [state event]
  (-> state
    (update :skiers/skiing dec0)
    (update :skiers/waiting inc)))

(defmethod handle-event :skier/leaves-lift [{:keys [sim/skiing-time] :as state} {t :t}]
  (-> state
    (update :skiers/riding-lift dec0)
    (update :skiers/skiing inc)
    (update :events conj {:event :skier/joins-queue :t (+ t skiing-time)})))

(defmethod handle-event :lift/leaves [{:keys [lift/ride-time lift/chair-width lift/chair-period] :as state}
                                      {t :t}]
  (let [riders (clamp (:skiers/waiting state) 0 chair-width)]
    (-> state
      (update :skiers/waiting - riders)
      (update :skiers/riding-lift + riders)
      (update :events into (replicate riders {:event :skier/leaves-lift :t (+ t ride-time)}))
      (update :events conj {:event :lift/leaves :t (+ t chair-period)}))))

(def initial-state
  {; simulation parameters
   :sim/end-time 50
   :sim/skiing-time 20
   :lift/ride-time 10
   :lift/chair-width 4
   :lift/chair-period 4

   ; stats
   :skiers/waiting 2
   :skiers/riding-lift 0
   :skiers/skiing 0

   ; future event queue
   :events [{:event :lift/leaves :t 0}]})

(defn events->queue [l]
  (let [q (new PriorityQueue)]
    (doseq [event l]
      (.enqueue q (:t event) event))
    q))

(defn queue->events [queue]
  (loop [res []
         q queue]
    (if (.isEmpty q)
      res
      (let [next-event (.dequeue ^PriorityQueue q)]
        (recur (conj res next-event) q)))))

(defn step [state]
  (let [queue (events->queue (:events state))
        event (.dequeue queue)]
    (if event
      (-> state
        (assoc :events (queue->events queue))
        (handle-event event)
        (assoc :t (event :t)))
      state)))

(defn simulate []
  (loop [res []
         state (assoc initial-state :t 0)]
    (let [next-state (step state)
          t (:t next-state)]
      (if (>= t (:sim/end-time state))
        res
        (do
          (println (format "%5d: %s" t (select-keys state [:skiers/waiting
                                                           :skiers/riding-lift
                                                           :skiers/skiing])))
          (recur (conj res next-state) next-state))))))

(comment
  (handle-event initial-state {:event :skier/joins-queue :t 0})
  (handle-event initial-state {:event :lift/leaves :t 0})
  (handle-event initial-state {:event :skier/leaves-lift :t 0})
  (simulate))
