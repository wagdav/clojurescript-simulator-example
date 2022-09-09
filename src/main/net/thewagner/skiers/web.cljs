(ns net.thewagner.skiers.web
  (:require [cljs.core.async :as async]
            [goog.dom :as gdom]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [goog.string :as gstring]
            [goog.string.format]
            [vega-embed]))

(goog-define ^string revision "main")

(def vlSpec
  {:$schema "https://vega.github.io/schema/vega-lite/v5.json"
   :description "A simple bar chart with embedded data."
   :data {:values [{:a "A"  :b 28}
                   {:a "B"  :b 55}
                   {:a "C"  :b 43}
                   {:a "D"  :b 91}
                   {:a "E"  :b 81}
                   {:a "F"  :b 53}
                   {:a "G"  :b 19}
                   {:a "H"  :b 87}
                   {:a "I"  :b 52}]}
   :mark "bar"
   :encoding {:x {:field "a" :type "ordinal"}
              :y {:field "b" :type "quantitative"}}
   :width "container"})

(defn main []
  [:<>
    [:section.section
      [:h1.title "Skiers simulation"]
      [:div.columns
        [:div.column
          "Graph on the right"]
        [:div#vis.columns.column]]]])

(vega-embed/embed "#vis" (clj->js vlSpec))

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
  :cljs/quit)

