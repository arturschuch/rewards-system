(ns rewards-system.data-structure.customer 
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

(defn calculate
  "It calculate (1/2)^k where k is the level of the confirmed invitation
   Only the first invitation counts: 
    so if someone gets invited by someone that was already invited, it doesn't award any points.
   
  Example:
    level 0 (people he invited) he gets 1 point,
    level 1 (people invited by a person he invited) he gets 1/2 points and 
    level 2 invitations (people invited by a person on level 1) awards him 1/4 and so on."
  [level customer]
  (loop [level level customer customer]
    (if-not (nil? (customer :invited-by))
	    (do
	      (def prize (get-prize level customer))
	      (def inviting (get-investing customer))
	      (if-not (inviting-paid? customer)
          (do
            (update-list (update-inviting-paid customer))
            (update-list (update-prize prize inviting))))
        (if (> (inviting :last-fractional-value) prize) 
          (update-list (update-prize prize inviting)))
        (recur (+ level 1) (get-customer (inviting :name))))
      @customers)))

(defn add
  "Add customer with indication or without indication to customers list."
  [customer-name guest-name]
  (do  
    (def guest (load-customer guest-name customer-name))
    (def customer 
      (add-invitation 
        (load-customer customer-name nil) 
        (new-guest-detail 
          guest-name 
          (not (contains? @customers guest-name)))))
    (update-list guest)
    (update-list customer) 
    (calculate 0 customer)))
