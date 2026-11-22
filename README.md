# clojure-todo-list



\# clojure-todo-list



Aplicação de lista de tarefas (Todo List), desenvolvida como projeto acadêmico utilizando Clojure no backend e ClojureScript no frontend.



\## Tecnologias utilizadas



\### Backend

\- Clojure

\- Ring

\- Jetty

\- Reitit

\- SQLite

\- next.jdbc

\- Ring CORS



\### Frontend

\- ClojureScript

\- Reagent

\- Shadow-CLJS

\- React



\## Pré-requisitos



Antes de executar o projeto, instale:



\- Java JDK 17 ou superior

\- Clojure CLI

\- Node.js 18 ou superior

\- npm



\## Como executar o projeto



\### 1. Clonar o repositório



```bash

git clone https://github.com/hissa02/clojure-todo-list.git

cd clojure-todo-list

```



\### 2. Instalar as dependências do frontend



Na pasta raiz do projeto, execute:



```bash

npm install

```



\### 3. Iniciar o backend



Em um terminal, na pasta raiz do projeto, execute:



```bash

clj -M:run

```



O servidor backend será iniciado na porta `3000`.



O banco de dados SQLite (`todos.db`) e a tabela de tarefas são criados automaticamente na primeira inicialização.



\### 4. Iniciar o frontend



Abra outro terminal, também na pasta raiz do projeto, e execute:



```bash

npm run dev

```



O frontend será disponibilizado em:



http://localhost:8000



Mantenha os dois terminais abertos enquanto estiver utilizando a aplicação.



\## API REST



O backend disponibiliza os seguintes endpoints:



| Método | Endpoint | Descrição |

|---|---|---|

| GET | `/api/todos` | Lista todas as tarefas |

| POST | `/api/todos` | Cria uma nova tarefa |

| POST | `/api/todos/:id/toggle` | Alterna o estado de conclusão |

| DELETE | `/api/todos/:id` | Exclui uma tarefa |



\### Exemplo de criação de tarefa



Requisição:



```http

POST /api/todos

Content-Type: application/json

```



Corpo:



```json

{

&#x20; "title": "Estudar Clojure"

}

```



A API retorna a tarefa criada com seu identificador, estado de conclusão e data de criação.



\## Banco de dados



O projeto utiliza SQLite para armazenar as tarefas de forma persistente.



O arquivo `todos.db` é criado automaticamente na raiz do projeto quando o backend é iniciado. Os dados permanecem armazenados mesmo após o encerramento do servidor.



\## Repositório



https://github.com/hissa02/clojure-todo-list

