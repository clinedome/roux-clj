(ns sidekick-extras.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.file :refer [wrap-file]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes (GET "/" [] "Hello W1rld") (route/not-found "Not Found"))

(defn app
  []
  (-> app-routes
      (wrap-file "resources/public")
      (wrap-file-info)))

(defonce server (atom nil))

(defn start
  []
  (reset! server (jetty/run-jetty (app) {:port 3000, :join? false})))

(defn stop [] (.stop @server) (reset! server nil))

(defn restart [] (try (stop) (catch Exception e)) (start))

(comment
  (restart)
  (/ 1 0))
