# Todo List — Aplicação Full-Stack em Clojure e ClojureScript

## 1. Sobre o projeto

O **Todo List** é uma aplicação web desenvolvida em Clojure e ClojureScript para gerenciar tarefas, permitindo cadastrar, visualizar, concluir e excluir atividades.

O projeto foi desenvolvido como atividade avaliativa da Unidade 3 da disciplina **Tópicos em Engenharia da Computação II — Programação Funcional**, da Universidade Federal do Maranhão (UFMA).

A aplicação utiliza uma arquitetura full-stack, com **Clojure** no backend, **ClojureScript e Reagent** no frontend e **SQLite** para persistência dos dados.

A comunicação entre as camadas ocorre por meio de uma API REST, utilizando requisições HTTP e troca de dados no formato JSON.

## 2. Integrantes

- **Hissa Bárbara Oliveira**
- **Yuram Almeida**

## 3. Funcionalidades

O projeto oferece:

- Cadastro de novas tarefas.
- Visualização das tarefas armazenadas.
- Marcação e desmarcação de tarefas concluídas.
- Exclusão de tarefas.
- Atualização reativa da interface.
- Contagem de tarefas cadastradas e concluídas.
- Barra de progresso das atividades.
- Comunicação entre frontend e backend por API REST.
- Persistência dos dados em SQLite, mesmo após reiniciar o servidor.

## 4. Estrutura do projeto

```text
clojure-todo-list/
├── public/
│   ├── index.html
│   └── style.css
├── src/
│   ├── clj/
│   │   └── todo/
│   │       └── backend/
│   │           ├── core.clj
│   │           ├── db.clj
│   │           └── handler.clj
│   └── cljs/
│       └── todo/
│           ├── api.cljs
│           └── core.cljs
├── .gitignore
├── deps.edn
├── package.json
├── package-lock.json
├── shadow-cljs.edn
└── README.md
```

### Descrição dos módulos

**Backend**

**core.clj:** configura o servidor HTTP com Jetty, define as rotas utilizando Reitit e aplica os middlewares de comunicação.

**handler.clj:** implementa os manipuladores das requisições HTTP, responsáveis por listar, cadastrar, atualizar e excluir tarefas.

**db.clj:** gerencia a conexão com SQLite, a criação da tabela e as operações de persistência utilizando next.jdbc.

**Frontend**

**core.cljs:** implementa a interface gráfica utilizando Reagent, gerencia o estado reativo e apresenta as tarefas e os indicadores de progresso.

**api.cljs:** realiza as requisições HTTP ao backend utilizando a API `fetch` do JavaScript.

**index.html:** contém a estrutura HTML principal da aplicação.

**style.css:** define a aparência visual da interface.

**Arquivos de configuração**

**deps.edn:** define as dependências Clojure e os comandos de execução do backend.

**shadow-cljs.edn:** configura a compilação do ClojureScript e o servidor de desenvolvimento.

**package.json:** define as dependências JavaScript e o comando de inicialização do frontend.

## 5. Requisitos

Para compilar e executar o projeto, é necessário ter instalados:

- Java JDK 17 ou superior.
- Clojure CLI.
- Node.js 18 ou superior.
- npm.
- Git.

O desenvolvimento foi realizado utilizando Java JDK 21, Clojure CLI 1.12.6 e Node.js 22.

### Tecnologias utilizadas

| Camada | Tecnologias |
|---|---|
| Backend | Clojure, Ring, Jetty e Reitit |
| Frontend | ClojureScript, Reagent e React |
| Compilação | Shadow-CLJS |
| Banco de dados | SQLite e next.jdbc |
| Comunicação | API REST, HTTP e JSON |
| Versionamento | Git e GitHub |

## 6. Instalação e execução

### 6.1. Clonar o repositório

```bash
git clone https://github.com/hissa02/clojure-todo-list.git
```

Acesse a pasta do projeto:

```bash
cd clojure-todo-list
```

### 6.2. Instalar as dependências

Na pasta principal do projeto, execute:

```bash
npm install
```

As dependências do Clojure são resolvidas automaticamente pelo Clojure CLI durante a execução.

### 6.3. Iniciar o backend

Abra um terminal na pasta principal do projeto e execute:

```bash
clojure -M:run
```

Em ambientes onde o comando `clj` está disponível, também é possível utilizar:

```bash
clj -M:run
```

O resultado esperado é:

```text
Banco SQLite inicializado.
Servidor iniciado na porta 3000
```

O backend ficará disponível em:

http://localhost:3000

O arquivo `todos.db` e a tabela de tarefas são criados automaticamente, caso ainda não existam.

### 6.4. Iniciar o frontend

Mantenha o terminal do backend aberto.

Em um segundo terminal, na mesma pasta do projeto, execute:

```bash
npm run dev
```

O Shadow-CLJS realizará a compilação da aplicação.

Após a compilação, acesse:

http://localhost:8000

### 6.5. Utilização

Com os dois servidores em execução, é possível cadastrar, visualizar, concluir e excluir tarefas diretamente pela interface.

As alterações são enviadas ao backend e armazenadas no banco SQLite.

Para encerrar os servidores, pressione `Ctrl + C` em cada terminal.

## 7. API REST

A aplicação disponibiliza os seguintes endpoints:

| Método | Endpoint | Funcionalidade |
|---|---|---|
| GET | `/api/todos` | Lista todas as tarefas |
| POST | `/api/todos` | Cadastra uma nova tarefa |
| POST | `/api/todos/:id/toggle` | Alterna o estado de conclusão |
| DELETE | `/api/todos/:id` | Exclui uma tarefa |

### Exemplo de funcionamento

Para cadastrar uma tarefa, o frontend envia uma requisição:

```http
POST /api/todos
Content-Type: application/json
```

Com o seguinte corpo JSON:

```json
{
  "title": "Estudar Clojure"
}
```

O backend registra a tarefa no SQLite e retorna os dados cadastrados.

Para consultar as tarefas, utiliza-se:

```http
GET /api/todos
```

Exemplo de resposta:

```json
{
  "todos": [
    {
      "id": 1,
      "title": "Estudar Clojure",
      "completed": false,
      "created-at": "2026-11-22T22:48:08Z"
    }
  ]
}
```

O campo `completed` indica se a tarefa está concluída. O campo `created-at` registra sua data e hora de criação.

## 8. Persistência e testes

O projeto utiliza SQLite para armazenar as tarefas de forma persistente.

A tabela `todos` contém os seguintes campos:

| Campo | Descrição |
|---|---|
| `id` | Identificador único da tarefa |
| `title` | Descrição da tarefa |
| `completed` | Estado de conclusão |
| `created_at` | Data e hora do cadastro |

Durante a validação da aplicação, foram realizados testes de cadastro, listagem, conclusão e exclusão de tarefas.

Também foi verificado que os dados permanecem armazenados após atualizar a página e reiniciar o servidor backend.

O armazenamento é realizado no arquivo `todos.db`, localizado na pasta principal do projeto.

## 9. Vídeo de apresentação

**Link do vídeo:** Não disponibilizado.

## 10. Referências

- [Tutorial de Clojure/ClojureScript — LambdaGeo](https://lambdageo-edu.github.io/lambdageo-tutorials/clojure/)
- [Clojure — Documentação oficial](https://clojure.org/)
- [ClojureScript — Documentação oficial](https://clojurescript.org/)
- [Reagent — GitHub](https://github.com/reagent-project/reagent)
- [Shadow-CLJS — Documentação oficial](https://shadow-cljs.github.io/docs/UsersGuide.html)
- [SQLite — Documentação oficial](https://www.sqlite.org/docs.html)

## 11. Repositório

Código-fonte e histórico de desenvolvimento disponíveis em:

- [GitHub — clojure-todo-list](https://github.com/hissa02/clojure-todo-list)
