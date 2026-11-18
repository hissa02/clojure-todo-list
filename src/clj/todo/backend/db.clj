
(ns todo.backend.db
  "Este namespace gerencia os dados dos 'todos' em memória.")

;; Banco de dados em memória
(def todos-db (atom {}))

;; Contador de IDs
(def next-id (atom 0))

;; Retorna todos os todos
(defn get-all-todos
  "Retorna uma lista com todos os 'todos' no banco."
  []
  (vec (vals @todos-db)))

;; Cria um novo todo
(defn create-todo
  "Cria um novo 'todo', salva no banco e o retorna."
  [todo-data]
  (let [id (swap! next-id inc)
        new-todo (assoc todo-data
                        :id id
                        :completed false
                        :created-at (str (java.time.Instant/now)))]
    (swap! todos-db assoc id new-todo)
    new-todo))
