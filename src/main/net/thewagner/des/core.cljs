; Discreet-event simulation (DES)
(ns net.thewagner.des.core
  (:import goog.structs.PriorityQueue))

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

(defn step [state handle-event]
  (let [queue (events->queue (:events state))
        event (.dequeue queue)]
    (if event
      (-> state
        (assoc :events (queue->events queue))
        (handle-event event)
        (assoc :t (event :t)))
      state)))

(defn simulate [initial-state handle-event]
  (loop [res []
         state (assoc initial-state :t 0)]
    (let [next-state (step state handle-event)
          t (:t next-state)]
      (if (>= t (:sim/end-time state))
        res
        (recur (conj res next-state) next-state)))))
