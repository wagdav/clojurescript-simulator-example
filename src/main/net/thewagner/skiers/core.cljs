; Example from https://github.com/mbrooker/simulator_example/blob/main/ski_sim.py
(ns net.thewagner.skiers.core
  (:require [net.thewagner.des.core :as des]))

(defn dec0 [x]
  (max (dec x) 0))

(defn clamp [x a b]
  (cond
    (< x a)    a
    (<= a x b) x
    (< b x)    b))

;https://stackoverflow.com/questions/25582882/javascript-math-random-normal-distribution-gaussian-bell-curve
(defn standard_normal_bm []
  (let [u (- 1 (js/Math.random))
        v (js/Math.random)]
    (*
      (js/Math.sqrt (* -2.0 (js/Math.log u)))
      (js/Math.cos (* 2.0 js/Math.PI v)))))

(defn normalvariate [mu sigma]
  (+
    (* sigma (standard_normal_bm))
    mu))

(defmulti handle-event (fn [state event] (:event event)))

(defmethod handle-event :default [state event] state)

(defmethod handle-event :skier/joins-queue [state event]
  (-> state
    (update :skiers/skiing dec0)
    (update :skiers/waiting inc)))

(defmethod handle-event :skier/leaves-lift [{:keys [skiers/skiing-time] :as state} {t :t}]
  (-> state
    (update :skiers/riding-lift dec0)
    (update :skiers/skiing inc)
    (update :events conj {:event :skier/joins-queue :t (+ t skiing-time)})))

(defmethod handle-event :lift/leaves [{:keys [lift/ride-time
                                              lift/ride-time-sigma
                                              lift/chair-width
                                              lift/chair-period] :as state}
                                      {t :t}]
  (let [ride-time (normalvariate ride-time ride-time-sigma)
        riders (clamp (:skiers/waiting state) 0 chair-width)]
    (-> state
      (update :skiers/waiting - riders)
      (update :skiers/riding-lift + riders)
      (update :events into (replicate riders {:event :skier/leaves-lift :t (+ t ride-time)}))
      (update :events conj {:event :lift/leaves :t (+ t chair-period)}))))

(def default-initial-state
  {; simulation parameters
   :sim/end-time 50
   :lift/ride-time 10
   :lift/ride-time-sigma 2
   :lift/chair-width 4
   :lift/chair-period 4
   :skiers/skiing-time 20

   ; stats
   :skiers/waiting 2
   :skiers/riding-lift 0
   :skiers/skiing 0

   ; future event queue
   :events [{:event :lift/leaves :t 0}]})

(defn simulate
  ([]
   (simulate default-initial-state))

  ([initial-state]
   (loop [res []
          state (assoc initial-state :t 0)]
     (let [next-state (des/step state handle-event)
           t (:t next-state)]
       (if (>= t (:sim/end-time state))
         res
         (recur (conj res next-state) next-state))))))

(defn skiing-percentage [results]
  (let [{:keys [current-time skiing total-skiers] :as res} (reduce
                                                            (fn [state e] state
                                                              (let [dt (- (e :t) (state :current-time))
                                                                    skiing (* dt (e :skiers/skiing))
                                                                    total-skiers (* dt (+ (e :skiers/skiing) (e :skiers/riding-lift) (e :skiiers/waiting)))]
                                                                (-> state
                                                                  (update :current-time + dt)
                                                                  (update :skiing + skiing)
                                                                  (update :total-skiers + total-skiers))))
                                                            {:current-time 0 :skiing 0}
                                                            results)]
    (assoc res
      :skiing-time-percentage (/ skiing (* current-time total-skiers))
      :total-skiers (/ total-skiers current-time))))

(defn run-sims []
  (let [end-time 5000]
    (for [n-skiers (range 25 1250 200)]
      (-> default-initial-state
          (assoc :skiers/waiting n-skiers
                 :sim/end-time end-time)
          simulate
          skiing-percentage))))

(comment
  (handle-event default-initial-state {:event :skier/joins-queue :t 0})
  (handle-event default-initial-state {:event :lift/leaves :t 0})
  (handle-event default-initial-state {:event :skier/leaves-lift :t 0})
  (skiing-percentage (simulate (assoc default-initial-state :skiers/waiting 50)))
  (run-sims))
