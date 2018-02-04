(ns tic-tac-toe.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(def starting-position
  {:board [[0 0 0] [0 0 0] [0 0 0]] :player true :winner false})

(defonce app-state (atom starting-position))

(defn player-piece [p]
  (case p
    true "X"
    false "O"))

(defn get-row [board idx]
  (board idx))

(defn get-column [board idx]
  (mapv #(%1 idx) board))

(defn get-diagonal [board dir]
  (let [b (if (= dir :right)
              (mapv #(vec (reverse %1)) board)
              board)]
    (vec
      (map-indexed #(%2 %1) b))))

(defn get-winning-routes [board]
  (concat (mapv (partial get-row board) (range 3))
          (mapv (partial get-column board) (range 3))
          (mapv (partial get-diagonal board) [:left :right])))

(defn player-has-won? [p board]
  (some #(= %1 (vec (repeat 3 (player-piece p))))
         (get-winning-routes board)))

(defn gen-board [row col]
  (update-in (:board @app-state) [row col] #(player-piece (:player @app-state))))

(defn make-move [row col]
  (do
    (swap! app-state assoc :board (gen-board row col))
    (if (player-has-won? (:player @app-state) (:board @app-state))
      (swap! app-state assoc :winner true)
      (swap! app-state assoc :player (not (:player @app-state))))))

(defn get-symbol [row col c]
  (let [move-handler (if (not (:winner @app-state))
                         #(make-move row col))]
    (case c
      0 [:div {:class "board-cell" :key (str row col) :on-click move-handler} ""]
      "X" [:div {:class "board-cell" :key (str row col)} "x"]
      "O" [:div {:class "board-cell" :key (str row col)} "o"])))

(defn row [row-idx row]
  [:div {:class "board-row"} (map-indexed (partial get-symbol row-idx) row)])

(defn board [board]
   [:div {:class "board"}
     (map-indexed row board)])

(defn player-name [p]
  (let [piece (player-piece p)
        player-num (if p 1 2)]
    (str "Player " player-num " (" piece ")")))

(defn tic-tac-toe []
  [:div
   [:h1 "Tic-tac-toe"]
   (if (:winner @app-state)
     [:h1 (str (player-name (:player @app-state)) " has won!")])
   (board (:board @app-state))
   [:button {:on-click #(reset! app-state starting-position) :class "btn btn-danger mt10"} "Reset!"]])

(reagent/render-component [tic-tac-toe]
                          (. js/document (getElementById "app")))

(defn on-js-reload [])
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
