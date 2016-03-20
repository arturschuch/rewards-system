(ns reward-system.rest.handler
  (:use reward-system.core)
  (:use compojure.core)
  (:use cheshire.core)
  (:use ring.util.response)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]
            [reward-system.core :as reward]))

(defroutes app-routes
  (context "/rewards" [] (defroutes rewards-routes
      (POST "/" {body :body} (println body "<<<-"))))
   (route/not-found "Not Found"))

(def app
	(-> (handler/api app-routes)
	    (middleware/wrap-json-body)
	    (middleware/wrap-json-response)))