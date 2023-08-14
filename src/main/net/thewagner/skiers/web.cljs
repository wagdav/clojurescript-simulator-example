(ns net.thewagner.skiers.web
  (:require [cljs.core.async :as async]
            [goog.dom :as gdom]
            [reagent.core :as r]
            [reagent.dom.client :as rclient]
            [goog.string :as gstring]
            [goog.string.format]
            [react-vega]
            [net.thewagner.skiers.core :as skiers]))

(goog-define ^string revision "main")

(def skiers-absolute
  {:$schema "https://vega.github.io/schema/vega-lite/v5.json"
   :description "A simple bar chart with embedded data."
   :data {:name :results}
   :transform [{:fold [:skiers/skiing :skiers/waiting :skiers/riding-lift]
                :as ["state" "number-of-skiers"]}]
   :mark "line"
   :encoding {:x {:field :t
                  :type "quantitative"
                  :title "time"
                  :scale {:zero false}}
              :y {:field "number-of-skiers"
                  :type "quantitative"
                  :title "Number of skiers"}
              :color {:field "state"}}})

(def skiers-percentage
  {:$schema "https://vega.github.io/schema/vega-lite/v5.json"
   :description "A simple bar chart with embedded data."
   :data {:name :results}
   :transform [{:fold [:skiers/skiing :skiers/waiting :skiers/riding-lift]
                :as ["state" "number-of-skiers"]}]
   :mark "bar"
   :encoding {:x {:field :t
                  :type "quantitative"
                  :title "time"
                  :scale {:zero false}}
              :y {:field "number-of-skiers"
                  :type "quantitative"
                  :stack "normalize"
                  :title "Percentage of skiers"}
              :color {:field "state"}}})

(defonce single (r/atom {:results [skiers/default-initial-state]}))
(defonce state (r/atom {:running true}))

(defn reset-state! []
  (reset! single {:results [skiers/default-initial-state]})
  (reset! state {:running true}))

(defn advance! []
  (swap! single (fn [s]
                  (->> (last (:results s))
                       skiers/step
                       (conj (:results s))
                       (take-last 200)
                       vec
                       (assoc s :results)))))

(defn tick! []
  (when (:running @state)
    (advance!))
  (js/setTimeout tick! 100))

(defn start! []
  (swap! state assoc :running true))

(defn stop! []
  (swap! state assoc :running false))

(defn toggle-button []
  (if (:running @state)
    [:button.button.is-primary {:on-click #(stop!)} "Pause"]
    [:button.button.is-primary {:on-click #(start!)} "Resume"]))

(defn reset-button []
  [:button.button.is-danger {:type "reset" :on-click #(reset-state!)} "Restart"])

(defn main []
  [:<>
    [:section.section
      [:h1.title "Skiers simulation"]]
    [:section.section
      [:div.buttons.is-centered
        [toggle-button]
        [reset-button]]]
    [:section.section
      [:div.columns
        [:div.column
          [:> react-vega/VegaLite {:spec skiers-absolute
                                   :data @single
                                   :actions false}]]
       [:div.column
         [:> react-vega/VegaLite {:spec skiers-percentage
                                  :data @single
                                  :actions false}]]]]])

(defonce dom-root
   (rclient/create-root (gdom/getElement "app")))

; https://code.thheller.com/blog/shadow-cljs/2019/08/25/hot-reload-in-clojurescript.html
(defn ^:dev/after-load start []
  (rclient/render dom-root [main]))

(defn init []
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
