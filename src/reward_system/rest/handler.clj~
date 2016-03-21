(ns reward-system.rest.handler
  (:use [compojure.core]
        [ring.middleware.params]
        [ring.middleware.multipart-params]
        [ring.adapter.jetty]
        [hiccup.core]
        [clojure.java.io])
  (:import [java.io File])
  (:require [com.rewards-system.data-structure.customer :as customer]
            [clojure.string :as string]))

(defn home-page []
  (html [:form {:action "/file" :method "post" :enctype "multipart/form-data"}
        [:input {:name "file" :type "file" :size "20"}]
        [:input {:type "submit" :name "submit" :value "submit"}]]))

(defn read-file
  "Read file and break into lines to each line be added how cusotmer and guest."
  [file]
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)]
      (println (add-to-customers line)))))

(defroutes handler
  (POST "/file" {params :params} 
        (read-file ((get params "file") :tempfile)))

  (GET "/" [] 
       (home-page)))

(def app
  (-> handler
      wrap-params
      wrap-multipart-params)))
