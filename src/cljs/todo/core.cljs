
(ns todo.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]))

;; Estado local das tarefas
(defonce tarefas
  (r/atom
   [{:id 1
     :title "Aprender Clojure"
     :completed false}
    {:id 2
     :title "Criar minha Todo List"
     :completed false}]))

(defonce texto (r/atom ""))
(defonce proximo-id (atom 3))

;; Adicionar tarefa
(defn adicionar-tarefa []
  (let [titulo (.trim @texto)]
    (when (not= titulo "")
      (swap! tarefas conj
             {:id @proximo-id
              :title titulo
              :completed false})
      (swap! proximo-id inc)
      (reset! texto ""))))

;; Marcar ou desmarcar tarefa
(defn alternar-tarefa [id]
  (swap! tarefas
         (fn [lista]
           (mapv
            (fn [tarefa]
              (if (= (:id tarefa) id)
                (update tarefa :completed not)
                tarefa))
            lista))))

;; Excluir tarefa
(defn excluir-tarefa [id]
  (swap! tarefas
         (fn [lista]
           (vec
            (remove #(= (:id %) id) lista)))))

;; Interface principal
(defn app []
  (let [total (count @tarefas)
        concluidas (count (filter :completed @tarefas))
        progresso (if (zero? total)
                    0
                    (* 100 (/ concluidas total)))]

    [:main.container

     ;; Cabeçalho
     [:header.logo
      [:div.logo-icon "✓"]
      [:div
       [:h2 "Minha Todo List"]
       [:p "Seu espaço de produtividade"]]]

     [:h1 "Organize seu dia."]
     [:p.subtitle
      "Pequenos passos, grandes conquistas."]

     ;; Progresso
     [:section.progress-card
      [:div.progress-header
       [:span "Seu progresso"]
       [:span (str concluidas " de " total " tarefas")]]

      [:div.progress-bar
       [:div.progress-fill
        {:style {:width (str progresso "%")}}]]

      [:small
       (cond
         (zero? total)
         "Adicione sua primeira tarefa!"

         (= total concluidas)
         "Parabéns! Todas as tarefas foram concluídas."

         :else
         "Continue assim! Você está avançando.")]]

     ;; Adicionar novas tarefas
     [:div.input-area
      [:input
       {:type "text"
        :placeholder "O que você precisa fazer?"
        :value @texto
        :on-change
        #(reset! texto (.. % -target -value))
        :on-key-down
        #(when (= "Enter" (.-key %))
           (adicionar-tarefa))}]

      [:button.add-button
       {:on-click adicionar-tarefa
        :aria-label "Adicionar tarefa"}
       "+"]]

     ;; Lista de tarefas
     [:div.section-header
      [:h3 "Suas tarefas"]
      [:span (str total " tarefas")]]

     (if (zero? total)

       [:p.empty-message
        "Nenhuma tarefa por aqui. Adicione uma!"]

       [:ul.task-list
        (for [{:keys [id title completed]} @tarefas]
          ^{:key id}
          [:li.task-item

           [:input
            {:type "checkbox"
             :checked completed
             :on-change
             #(alternar-tarefa id)}]

           [:span
            {:class
             (str "task-title"
                  (when completed " completed"))}
            title]

           [:button.delete-button
            {:on-click
             #(excluir-tarefa id)
             :aria-label "Excluir tarefa"}
            "✕"]])])

     ;; Rodapé
     [:footer.footer
      "Feito com ClojureScript + Reagent"]]))

;; Inicialização do React/Reagent
(defonce root (atom nil))

(defn init []
  (when-not @root
    (reset! root
            (rdom/create-root
             (.getElementById js/document "app"))))
  (rdom/render @root [app]))
