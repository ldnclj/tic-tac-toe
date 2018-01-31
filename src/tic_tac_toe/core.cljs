(ns tic-tac-toe.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(def starting-position
  {:board [[0 0 0] [0 0 0] [0 0 0]] :player true})

(defonce app-state (atom starting-position))

(defn player-piece [p]
  (case p
    true "X"
    false "O"))

(defn gen-board [row col]
  (update-in (:board @app-state) [row col] (fn [] (player-piece (:player @app-state)))))

(defn make-move [row col]
  (do
    (swap! app-state assoc :board (gen-board row col))
    (swap! app-state assoc :player (not (:player @app-state)))))

(defn handle-square [row col]
  (.log js/console (str row col)))

(defn get-symbol [row col c]
  (case c
    0 [:div {:class "board-cell" :on-click (fn [] (make-move row col))} ""]
    "X" [:div {:class "board-cell"} "x"]
    "O" [:div {:class "board-cell"} "o"]))

(defn row [row-idx row]
  [:div {:class "board-row"} (map-indexed (partial get-symbol row-idx) row)])

(defn board [board]
   [:div {:class "board"}
     (map-indexed row board)])

(defn tic-tac-toe []
  [:div
   [:h1 "Tic-tac-toe"]
   (board (:board @app-state))
   [:button {:on-click #(reset! app-state starting-position) :class "btn btn-danger mt10"} "Reset!"]])

(reagent/render-component [tic-tac-toe]
                          (. js/document (getElementById "app")))

(defn on-js-reload [])
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
