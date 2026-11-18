
(ns todo.backend.core
  (:require [ring.adapter.jetty :as jetty]
            [reitit.ring :as ring]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.cors :refer [wrap-cors]]
            [todo.backend.handler :as handler]))

;; Definição das rotas
(def app-routes
  (ring/router
   ["/api"
    ["/hello"
     {:get {:handler
            (fn [_request]
              {:status 200
               :headers {"Content-Type" "text/plain; charset=utf-8"}
               :body "Hello World! Backend Clojure funcionando."})}}]

    ["/todos"
     {:get  {:handler handler/list-todos-handler}
      :post {:handler handler/create-todo-handler}}]]))

;; Aplicação com middlewares
(def app
  (ring/ring-handler
   app-routes
   (ring/create-default-handler)
   {:middleware
    [[wrap-cors
      :access-control-allow-origin [#"http://localhost:8000"]
      :access-control-allow-methods [:get :post :put :patch :delete]
      :access-control-allow-headers ["Content-Type"]]

     wrap-json-response
     [wrap-json-body {:keywords? true}]
     wrap-params
     wrap-keyword-params]}))

;; Inicialização do servidor
(defn -main [& _args]
  (println "Servidor iniciado na porta 3000")
  (jetty/run-jetty app {:port 3000 :join? true}))
