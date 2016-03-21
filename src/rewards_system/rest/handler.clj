(ns rewards-system.rest.handler
  (:use [compojure.core]
        [ring.middleware.params]
        [ring.middleware.multipart-params]
        [ring.adapter.jetty]
        [hiccup.core]
        [clojure.java.io])
  (:import [java.io File])
  (:require [rewards-system.data-structure.customer :as customer]
            [clojure.string :as string]))

(defn home-page []
  (html [:form {:action "/file" :method "post" :enctype "multipart/form-data"}
        [:input {:name "file" :type "file" :size "20"}]
        [:input {:type "submit" :name "submit" :value "submit"}]]))

(defn get-indexed-value
  "Return indexed value of string, separated by spaces.
   Example:
     (get-indexed-value '1 2' 1) return 2
     (get-indexed-value '1 2' 0) return 1"
  [x index]
  (get (string/split x #"\s") index))

(defn add-to-customers
  "Add to customers data structure"
  [line]
  (do 
    (def customer-name (get-indexed-value line 0))
    (def guest-name (get-indexed-value  line 1))
    (customer/add customer-name guest-name)))

(defn show-customers
  [customers]
  {:status 200
      :headers {"Content-Type" "text/html"}
      :body (html [:h1 customers])})

(defn read-file
  "Read file and break into lines to each line be added how cusotmer and guest."
  [file]
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)]
      (add-to-customers line))))

(defroutes handler
  (POST "/file" {params :params} 
    (do
      (def customers (read-file ((get params "file") :tempfile)))
      (map (fn [x] (show-customers x)) customers)))

  (GET "/" [] 
    (home-page)))

(def app
  (-> handler
      wrap-params
      wrap-multipart-params))
