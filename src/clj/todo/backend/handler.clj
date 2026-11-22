
(ns todo.backend.handler
  "Este namespace define nossas funções de resposta (Handlers)."
  (:require [todo.backend.db :as db]
            [clojure.string :as str]))

;; Handler para listar todos.
(defn list-todos-handler
  "Handler para a rota GET /api/todos."
  [_request]
  {:status 200
   :body {:todos (db/get-all-todos)}})

;; Handler para criar um todo.
(defn create-todo-handler
  "Handler para a rota POST /api/todos."
  [request]
  (let [todo-data (:body request)
        title (:title todo-data)]
    (if (and title (not (str/blank? title)))
      (let [new-todo (db/create-todo todo-data)]
        {:status 201
         :body new-todo})
      {:status 400
       :body {:error "O 'título' (title) é obrigatório"}})))

;; Converte o ID recebido na URL para inteiro.
(defn- parse-todo-id [request]
  (try
    (Integer/parseInt (get-in request [:path-params :id]))
    (catch NumberFormatException _
      nil)))

;; Handler para alternar o status de conclusão.
(defn toggle-todo-handler
  "Handler para a rota POST /api/todos/:id/toggle."
  [request]
  (if-let [id (parse-todo-id request)]
    (if-let [updated-todo (db/toggle-todo! id)]
      {:status 200
       :body updated-todo}
      {:status 404
       :body {:error "Todo não encontrado"}})
    {:status 400
     :body {:error "ID inválido"}}))

;; Handler para excluir uma tarefa.
(defn delete-todo-handler
  "Handler para a rota DELETE /api/todos/:id."
  [request]
  (if-let [id (parse-todo-id request)]
    (if-let [deleted-todo (db/delete-todo! id)]
      {:status 200
       :body deleted-todo}
      {:status 404
       :body {:error "Todo não encontrado"}})
    {:status 400
     :body {:error "ID inválido"}}))
