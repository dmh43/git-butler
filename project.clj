(defproject git-butler "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.395"]
                 [cheshire "5.6.3"]
                 [clj-http "2.3.0"]
                 [compojure "1.5.1"]
                 [crypto-password "0.2.0"]
                 [me.raynes/fs "1.4.6"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.4.0"]
                 [slingshot "0.12.2"]
                 [yogthos/config "0.8"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler git-butler.handler/app}
  :profiles
  {:prod {:resource-paths ["config/prod"]}
   :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]
         :resource-paths ["config/dev"]}})
