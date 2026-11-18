(ns todo.backend.core
  (:require [reitit.ring :as ring]
            [ring.adapter.jetty :as jetty]))

(defn hello-handler [_request]
  {:status 200
   :headers {"Content-Type" "text/plain; charset=utf-8"}
   :body "Hello World! Backend Clojure funcionando."})

(def app
  (ring/ring-handler
   (ring/router
    [["/api/hello" {:get hello-handler}]])))

(defn -main [& _args]
  (println "Iniciando servidor na porta 3000...")
  (jetty/run-jetty app {:port 3000 :join? true}))