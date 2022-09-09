(ns net.thewagner.skiers.web
  (:require [cljs.core.async :as async]
            [goog.dom :as gdom]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [goog.string :as gstring]
            [goog.string.format]))

(goog-define ^string revision "main")

(defn main []
  [:<>
    [:section.section
      [:h1.title "Skiers simulation"]]])

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

