
(ns todo.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]
            [todo.api :as api]))

;; Estado da interface
(defonce tarefas (r/atom []))
(defonce texto (r/atom ""))
(defonce erro (r/atom nil))

;; Tratamento de erros
(defn mostrar-erro [mensagem]
  (fn [falha]
    (js/console.error mensagem falha)
    (reset! erro mensagem)))

;; Buscar tarefas no backend
(defn carregar-tarefas []
  (api/buscar-tarefas
   (fn [resposta]
     (let [dados (js->clj resposta :keywordize-keys true)]
       (reset! tarefas (vec (:todos dados)))
       (reset! erro nil)))
   (mostrar-erro "Não foi possível carregar as tarefas.")))

;; Adicionar tarefa
(defn adicionar-tarefa []
  (let [titulo (.trim @texto)]
    (when (not= titulo "")
      (api/adicionar-tarefa
       titulo
       (fn [_]
         (reset! texto "")
         (carregar-tarefas))
       (mostrar-erro "Não foi possível adicionar a tarefa.")))))

;; Marcar ou desmarcar tarefa
(defn alternar-tarefa [id]
  (api/alternar-tarefa
   id
   (fn []
     (carregar-tarefas))
   (mostrar-erro "Não foi possível atualizar a tarefa.")))

;; Excluir tarefa
(defn excluir-tarefa [id]
  (api/excluir-tarefa
   id
   (fn []
     (carregar-tarefas))
   (mostrar-erro "Não foi possível excluir a tarefa.")))

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

     ;; Adicionar tarefas
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

     ;; Mensagem de erro
     (when @erro
       [:p {:style {:color "#c0392b"}}
        @erro])

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
            {:on-click #(excluir-tarefa id)
             :aria-label "Excluir tarefa"}
            "✕"]])])

     ;; Rodapé
     [:footer.footer
      "Feito com ClojureScript + Reagent"]]))

;; Inicialização
(defonce root (atom nil))

(defn init []
  (when-not @root
    (reset! root
            (rdom/create-root
             (.getElementById js/document "app"))))
  (rdom/render @root [app])
  (carregar-tarefas))
