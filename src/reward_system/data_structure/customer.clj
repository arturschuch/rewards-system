(ns com.rewards-system.data-structure.customer 
  (:require [clojure.contrib.math :as math]))

(def customers
  "customers list."
  (atom (hash-map)))

(defn new-customer
  "Structure to customer."
  [customer-name invited-by]
  {:name customer-name :invited-by invited-by :invitations [] :prize 0 :inviting-paid false :last-fractional-value 1})

(defn new-guest-detail
  "Structure to detail guest."
  [guest-name accepted]
  {:guest-name guest-name :acepted accepted})

(defn get-customer
  "Function to get customer by customer-name on list of customers."
  [customer-name]
  (@customers customer-name))

(defn update-list
  "Update/Add to customers list new value."
  [customer]
  (swap! customers assoc-in [(customer :name)] customer))

(defn load-customer
  "Load or creat new customer if he doesn't exist on customers list."
  [customer-name invited-by]
  (if (contains? @customers customer-name)
      (get-customer customer-name)
      (new-customer customer-name invited-by)))

(defn add-invitation
  "Add guest to customer invitations."
  [customer guest-detail]
  (update-in
      customer [:invitations]
      merge
      guest-detail))

(defn inviting-paid?
  "Return true if customer payed for inviting."
  [customer]
  (customer :inviting-paid))

(defn get-prize
  "Return (1/2)^k where k is the level if level > 0
   Or return 1 if customer didn't pay for your inviter
   Or return 0 like default value."
  [level, customer]
  (if (> level 0) 
    (math/expt 0.5 level)
    (if-not (inviting-paid? customer) 1 0)))

(defn sum-prize
  "Update value of prize to sum the old value with new value."
  [prize customer]
  (update-in customer [:prize] + prize))

(defn update-last-fractional-value
  "Update value of last-fractional-value to new prize."
  [prize customer]
  (assoc customer :last-fractional-value prize))

(defn update-prize
  "Update value of prize 
   But when customer receive new prize,
   need update last-fractional-value too."
  [prize customer]
  (sum-prize prize (update-last-fractional-value prize customer)))

(defn update-inviting-paid
  "Update value of inviting-paid to true."
  [customer]
  (assoc customer :inviting-paid true))

(defn get-investing
  "Return the customer that has invited this custumer."
  [customer]
  (get-customer (customer :invited-by)))

