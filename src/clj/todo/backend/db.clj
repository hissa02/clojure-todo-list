
(ns todo.backend.db
  "Este namespace gerencia os dados dos 'todos' usando SQLite."
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]))

;; Configuração da conexão com o banco SQLite.
;; O arquivo será criado na raiz do projeto.
(def db-spec
  {:dbtype "sqlite"
   :dbname "todos.db"})

;; Cria a tabela automaticamente, caso ainda não exista.
(defn init-db!
  "Inicializa a tabela de tarefas no banco SQLite."
  []
  (jdbc/execute!
   db-spec
   ["
    CREATE TABLE IF NOT EXISTS todos (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      title TEXT NOT NULL,
      completed BOOLEAN NOT NULL DEFAULT 0,
      created_at TEXT NOT NULL
    )
   "]))

;; Converte o resultado do SQLite para o formato usado pela API.
(defn- format-todo [todo]
  {:id (:id todo)
   :title (:title todo)
   :completed (= 1 (:completed todo))
   :created-at (:created_at todo)})

;; Retorna todas as tarefas.
(defn get-all-todos
  "Retorna uma lista com todos os 'todos' no banco."
  []
  (let [todos (jdbc/execute!
               db-spec
               ["SELECT id, title, completed, created_at
                 FROM todos
                 ORDER BY id"]
               {:builder-fn rs/as-unqualified-lower-maps})]
    (mapv format-todo todos)))

;; Cria uma nova tarefa.
(defn create-todo
  "Cria um novo 'todo', salva no banco e o retorna."
  [todo-data]
  (let [created-at (str (java.time.Instant/now))
        todo (jdbc/execute-one!
              db-spec
              ["INSERT INTO todos (title, completed, created_at)
                VALUES (?, ?, ?)
                RETURNING id, title, completed, created_at"
               (:title todo-data)
               false
               created-at]
              {:builder-fn rs/as-unqualified-lower-maps})]
    (format-todo todo)))
