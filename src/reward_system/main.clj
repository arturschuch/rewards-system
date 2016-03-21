(ns com.rewards-system.main
  (:require [com.rewards-system.rest.handler :as handler])
  (:use [ring.adapter.jetty]))

(def port 8080)  
  
(defn -main []
  "Exposing port to jetty run"
  (run-jetty handler/app {:port port}))
