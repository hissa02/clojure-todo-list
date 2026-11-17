
(ns todo.api)

;; Endereco do backend
(def api-url "http://localhost:3000/api/todos")

;; Buscar todas as tarefas
(defn buscar-tarefas [sucesso erro]
  (-> (js/fetch api-url)
      (.then (fn [resposta]
               (if (.-ok resposta)
                 (.json resposta)
                 (throw (js/Error. "Erro ao buscar tarefas")))))
      (.then sucesso)
      (.catch erro)))

;; Adicionar uma tarefa
(defn adicionar-tarefa [titulo sucesso erro]
  (-> (js/fetch
       api-url
       (clj->js
        {:method "POST"
         :headers {"Content-Type" "application/json"}
         :body (js/JSON.stringify
                (clj->js {:title titulo}))}))
      (.then (fn [resposta]
               (if (.-ok resposta)
                 (.json resposta)
                 (throw (js/Error. "Erro ao adicionar tarefa")))))
      (.then sucesso)
      (.catch erro)))

;; Alternar entre concluida e pendente
(defn alternar-tarefa [id sucesso erro]
  (-> (js/fetch
       (str api-url "/" id "/toggle")
       (clj->js {:method "PATCH"}))
      (.then (fn [resposta]
               (if (.-ok resposta)
                 (sucesso)
                 (throw (js/Error. "Erro ao atualizar tarefa")))))
      (.catch erro)))

;; Excluir uma tarefa
(defn excluir-tarefa [id sucesso erro]
  (-> (js/fetch
       (str api-url "/" id)
       (clj->js {:method "DELETE"}))
      (.then (fn [resposta]
               (if (.-ok resposta)
                 (sucesso)
                 (throw (js/Error. "Erro ao excluir tarefa")))))
      (.catch erro)))
