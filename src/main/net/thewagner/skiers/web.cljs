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

(def vlSpec
  (r/atom
    {:$schema "https://vega.github.io/schema/vega-lite/v5.json"
     :description "A simple bar chart with embedded data."
     :data {:values [{:t 1  :skiers/riding-lift 28}
                     {:t 2  :skiers/riding-lift 55}
                     {:t 3  :skiers/riding-lift 43}
                     {:t 4  :skiers/riding-lift 91}
                     {:t 5  :skiers/riding-lift 81}
                     {:t 6  :skiers/riding-lift 53}
                     {:t 7  :skiers/riding-lift 19}
                     {:t 8  :skiers/riding-lift 87}
                     {:t 9  :skiers/riding-lift 52}]}
     :mark "line"
     :encoding {:x {:field :t
                    :type "quantitative"
                    :title "time"}
                :y {:field :skiers/skiing
                    :type "quantitative"
                    :title "number of skiers"}}}))

(defn main []
  [:<>
    [:section.section
      [:h1.title "Skiers simulation"]
      [:div.columns
        [:div.column
          "Graph on the right"]
        [:div.column
          [:> react-vega/VegaLite {:spec @vlSpec}]]]]])

(defn mount []
  (rdom/render [main] (gdom/getElement "app")))

(defn ^:dev/after-load on-reload []
  (mount))

(defonce startup (do (mount) true))

(swap! vlSpec update-in [:data :values] skiers/simulate)

(comment
  ; Evaluate these lines to enter into a ClojureScript REPL
  (require '[shadow.cljs.devtools.api :as shadow])
  (shadow/repl :app)
  ; Exit the CLJS session
  :cljs/quit

  (swap! vlSpec update-in [:data :values 5 :b] + 10)
  (swap! vlSpec update-in [:data :values] skiers/simulate)
  (second (@vlSpec :data)))
