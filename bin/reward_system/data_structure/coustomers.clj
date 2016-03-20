(ns reward-system.data-structure.coustomers)

(def coustomers
  "Coustomers list."
  (hash-map))

(defn not-nil?
  "Returns true if x isn't nil, false otherwise."
  [x]
  (not (nil? x)))

(def accepted
  "Default value to accepted."
   false)

(defn load-coustomer
  "Load or creat new coustomer if he doesn't exist on coustomers list."
  [coustomer-name invited-by]
  (if
    (contains? coustomers coustomer-name)
      (let
        (def accepted true)
        (get-coustomer coustomer-name))
      {:name coustomer-name :invited-by invited-by :invitations [], :prize 0}))

(defn get-coustomer
  "Function to get coustomer by coustomer-name on list of coustomers."
  [coustomer-name]
  (coustomers coustomer-name))

(defn add-invitation
  "Add guest to coustomer invitations"
  [coustomer guest-detail]
  (update-in
    coustomer [:invitations]
    merge
    guest-detail))

(defn add
  "Add coustomer with indication or without indication to coustomers list"
  [coustomer-name, guest-name]
  (let
    (def guest (load-coustomer guest-name coustomer-name))
    (def guest-detail {:guest-name guest-name :acepted accepted})
    (def coustomer 
      (add-invitation 
        (load-coustomer coustomer-name nil) 
        guest-detail))
    (def coustomers
      (merge 
        coustomers
        (hash-map coustomer guest)))
    (calculate 0 coustomer)))

(defn calculate
  ""
  [depth, current-coustomer]
  (if
    (not-nil? (current-coustomer :invitedBy))
    (let
      )))

;(def guest-name (get-coustomer guest-name))
;    (if
;      ;;TODO find func to diferent of nil
;      (nil? guest-name) 
;        (let
;          ;;ADD NA LISTA COUSTOMERS
;          (def guest {guest-name {:invitedBy coustomer-name :invitations [], :prize 0}})
;          (def accepted true)
;         ))


;(def coustomer (get-coustomer coustomer-name))
;    (if
;      (nil? coustomer)
;        ;;ADD NA LISTA COUSTOMERS
;        (def coustomer {:coustomer-name coustomer-name :invitations [], :prize 0}))

;;http://macromancy.com/2014/04/09/data-structures-clojure-trees.html