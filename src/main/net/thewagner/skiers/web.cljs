(ns net.thewagner.skiers.web
  (:require [goog.dom :as gdom]
            [goog.string :as gstring]
            [goog.string.format]
            [replicant.alias :refer [defalias]]
            [replicant.dom :as r]
            [net.thewagner.skiers.core :as skiers])
  (:refer-global :only [uPlot]))

(goog-define ^string revision "main")

(defalias uplot-chart [attrs _children]
  [:div
    (-> attrs
        (assoc :replicant/on-mount
               (fn [{:replicant/keys [node remember]}]
                 (let [opts (dissoc attrs ::data)
                       data (::data attrs)
                       chart (uPlot. (clj->js opts) (clj->js data) node)]
                   (remember chart))))
        (assoc :replicant/on-update
               (fn [{:replicant/keys [memory]}]
                 (let [chart memory]
                   (.setData chart (clj->js (::data attrs)))))))])

(defonce store
  (atom {:results [skiers/default-initial-state]
         :running true}))

(defn reset-state! []
  (reset! store {:results [skiers/default-initial-state]
                 :running true}))

(defn advance! []
  (swap! store update :results
    (fn [results]
      (let [new-results (conj results (skiers/step (peek results)))]
        (if (> (count new-results) 200)
          (subvec new-results 1)
          new-results)))))

(defn start! []
  (swap! store assoc :running true))

(defn stop! []
  (swap! store assoc :running false))

(defn toggle-button [state]
  (if (:running state)
    [:button.button.is-primary {:on {:click #(stop!)}} "Pause"]
    [:button.button.is-primary {:on {:click #(start!)}} "Resume"]))

(defn reset-button []
  [:button.button.is-danger {:type "reset"
                             :on {:click #(reset-state!)}}
                            "Restart"])

(defn main [state]
  (list
    [:section.section
      [:h1.title "Skiers simulation"]]
    [:section.section
      [:div.buttons.is-centered
        (toggle-button state)
        (reset-button)]]
    [:section.section
      [:center
        [uplot-chart {::width 600
                      ::height 400
                      ::scales {:x {:time false}}
                      ::axes [{}
                              {:label "number of skiers"}]
                      ::series [{}
                                {:stroke "blue"
                                 :label "riding lift"}
                                {:stroke "orange"
                                 :label "skiing"}
                                {:stroke "red"
                                 :label "waiting"}]
                      ::data [(map :t (:results state))
                              (map :skiers/skiing (:results state))
                              (map :skiers/riding-lift (:results state))
                              (map :skiers/waiting (:results state))]}]]]))

(defonce dom-root (gdom/getElement "app"))

(defn render! [state]
  (r/render dom-root (main state)))

; https://code.thheller.com/blog/shadow-cljs/2019/08/25/hot-reload-in-clojurescript.html
(defn ^:dev/after-load start []
  (render! @store))

(defn tick! []
  (when (:running @store)
    (advance!))
  (js/setTimeout tick! 100))

(defn init []
  (add-watch store ::render (fn [_ _ _ state] (render! state)))
  (tick!)
  (start))

(comment
  ; Evaluate these lines to enter into a ClojureScript REPL
  (require '[shadow.cljs.devtools.api :as shadow])
  (shadow/repl :app)
  ; Exit the CLJS session
  :cljs/quit)

(comment
  (last (:results (deref single)))
  (:running @state)
  (reset-state!)
  (start!)
  (stop!)
  (swap! assoc-in [:results])
  (dotimes [_ 10] (advance!)))
