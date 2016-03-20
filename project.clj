(defproject reward-system "0.1.0-SNAPSHOT"
  :description "REST service for documents"
  :url "https://github.com/arturschuch"
  :dependencies [[org.clojure/clojure "1.6.0"]
               [compojure "1.1.1"]
               [ring/ring-json "0.1.2"]
               [c3p0/c3p0 "0.9.1.2"]
               [cheshire "4.0.3"]]
  :plugins [[lein-ring "0.7.3"]]
  :ring {:handler clojure-rest.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
