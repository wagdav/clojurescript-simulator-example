(ns net.thewagner.skiers.web
  (:require [cljs.core.async :as async]
            [goog.dom :as gdom]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [goog.string :as gstring]
            [goog.string.format]
            [react-vega]
            [net.thewagner.skiers.core :as skiers]))

(goog-define ^string revision "main")

(def personsSkiing
  {:$schema "https://vega.github.io/schema/vega-lite/v5.json"
   :description "A simple bar chart with embedded data."
   :data {:name :results}
   :mark "point"
   :encoding {:x {:field :t
                  :type "quantitative"
                  :title "time"}
              :y {:field :skiers/skiing
                  :type "quantitative"
                  :title "persons skiing"}}})

(defn main []
  (let [data {:results (skiers/simulate)}]
    [:<>
      [:section.section
        [:h1.title "Skiers simulation"]
        [:div.columns
          [:div.column
            "Graph on the right"]
          [:div.column
            [:> react-vega/VegaLite {:spec personsSkiing
                                     :data data}]]]]]))

(defn mount []
  (rdom/render [main] (gdom/getElement "app")))

(defn ^:dev/after-load on-reload []
  (mount))

(defonce startup (do (mount) true))

(comment
  ; Evaluate these lines to enter into a ClojureScript REPL
  (require '[shadow.cljs.devtools.api :as shadow])
  (shadow/repl :app)
  ; Exit the CLJS session
  :cljs/quit

  (swap! vlSpec update-in [:data :values 5 :b] + 10)
  (swap! vlSpec update-in [:data :values] skiers/simulate)
  (second (@vlSpec :data)))
