(defproject reward-system "0.1.0-SNAPSHOT"
  :description "REST service for documents"
  :url "https://github.com/arturschuch"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.1.0"]
                 [ring/ring-devel "1.1.0"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [ring-json-params "0.1.3"]
                 [compojure "1.1.0"]
                 [clj-http "0.5.2"]
                 [org.clojure/tools.nrepl "0.2.0-beta9"]
                 [hiccup "1.0.1"]]
  :dev-dependencies [[lein-ring "0.9.7"]]
  :main com.rewards-system.main)
