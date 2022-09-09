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
   :sim/end-time 100
   :sim/skiing-time 20
   :lift/ride-time 10
   :lift/chair-width 4
   :lift/chair-period 4

   ; stats
   :skiers/waiting 2
   :skiers/riding-lift 0
   :skiers/skiing 0

   ; future event queue
   :events (pq/priority-queue
             (fn event->priority [e] (- (:t e)))
             :elements [{:event :lift/leaves :t 0}])})

(defn simulate []
  (loop [t 0
         state initial-state]
    (let [{:keys [events sim/end-time]} state]
      (if (and (seq events) (<= t end-time))
        (let [ev (peek events)
              new-state (handle-event (update state :events pop) ev)]
          (println (format "%5d: %s" (ev :t) (select-keys state [:skiers/waiting
                                                                 :skiers/riding-lift
                                                                 :skiers/skiing])))
          (recur (ev :t) new-state))
        state))))

(comment
  (handle-event initial-state {:event :skier/joins-queue :t 0})
  (handle-event initial-state {:event :lift/leaves :t 0})
  (handle-event initial-state {:event :skier/leaves-lift :t 0})
  (simulate))
