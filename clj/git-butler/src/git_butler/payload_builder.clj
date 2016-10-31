(ns git-butler.payload-builder)

(defn get-merge-payload
  [{:keys [base head commit-message] :as params}]
  {:base base
   :head head
   :commit_message commit-message})
