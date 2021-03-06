(ns rewards-system.data-structure.customer-test
  (:require [clojure.test :refer :all]
            [rewards-system.data-structure.customer :as customer]))

(def customer-to-test
  {:name 1 :invited-by nil :invitations [] :prize 0 :inviting-paid false :last-fractional-value 1})

(def guest-detail-to-test
  {:guest-name 2, :acepted true})

(def customer-to-test-with-invitation
  {:name 1 :invited-by nil :invitations [guest-detail-to-test] :prize 0 :inviting-paid false :last-fractional-value 1})

(def customer-to-test-with-inviting-paid
  {:name 1 :invited-by nil :invitations [guest-detail-to-test] :prize 1 :inviting-paid true :last-fractional-value 1})

(def customer-to-test-with-prize
  {:name 1 :invited-by nil :invitations [guest-detail-to-test] :prize 1 :inviting-paid true :last-fractional-value 1})

(def guest-to-test
  {:name 2 :invited-by 1 :invitations [] :prize 0 :inviting-paid false :last-fractional-value 1})


(deftest new-customer-test
  (testing "Verify if customer is created with default values when recive 1 with name and nil how invited-by."
    (is (= (customer/new-customer 1 nil) customer-to-test))))
 
(deftest new-guest-detail-test
  (testing "Verify if guest-detail is created with default values when recive 2 with name and true with acepted."
    (is (= (customer/new-guest-detail 1 true) {:guest-name 1 :acepted true})))
  (testing "Verify if guest-detail is created with default values when recive 2 with name and false with acepted."
    (is (= (customer/new-guest-detail 1 false) {:guest-name 1 :acepted false}))))

(deftest update-list-test
  (testing "Case the value exists on base get it or add new to base case it doesnt exist."
    (testing "Add new value, new value is added to customers"
	     (is (= (customer/update-list customer-to-test) (hash-map (customer-to-test :name) customer-to-test))))
	  (testing "Update value that exits on customers.
	              Example:
	                -Before update-list the key (customer-to-test :name) return customer-to-test
	                -After update-list the key (customer-to-test :name) return customer-to-test-with-invitation 
	              Beacause they using same key and the new value is customer-to-test-with-invitation."
	    (is (= (customer/get-customer (customer-to-test :name)) customer-to-test))
	    (is (= (customer/update-list customer-to-test-with-invitation) (hash-map (customer-to-test :name) customer-to-test-with-invitation))
	    (is (= (customer/get-customer (customer-to-test :name)) customer-to-test-with-invitation))))))

(deftest load-customer-test
  (testing "Case customer exists on base get his or creat new case he doesnt exist."
    (testing "Case he exists"
	     (is (= (customer/update-list customer-to-test) (hash-map (customer-to-test :name) customer-to-test)))
	     (is (= (customer/load-customer (customer-to-test :name) nil) customer-to-test)))
	  (testing "Case he doesn't exist"
	    (is (= (customer/get-customer 2) nil))
	    (is (= (customer/load-customer 2 1) (customer/new-customer 2 1))))))

(deftest add-invitation-test
  (testing "Pass guest detail to be add to customer."
    (is (= (customer/update-list customer-to-test) (hash-map (customer-to-test :name) customer-to-test)))
    (is (= (customer/get-customer 1) customer-to-test))
    (is (= (customer/add-invitation customer-to-test guest-detail-to-test) customer-to-test-with-invitation))))

(deftest inviting-paid?-test
  (testing "Return true if customer payed for inviting otherwise false"
    (is (= (customer/inviting-paid? customer-to-test-with-inviting-paid) true))
    (is (= (customer/inviting-paid? customer-to-test) false))))

(deftest get-prize-test
  "To this logic is used (0.5 ^ level)"
  (testing "Get prize when level is 0 and inviting-paid is false"
    (is (= (customer/get-prize 0 customer-to-test) 1)))
  (testing "Get prize when level is 0 and inviting-paid is true"
    (is (= (customer/get-prize 0 customer-to-test-with-inviting-paid) 0)))
  (testing "Get prize when level is 1"
    (is (= (customer/get-prize 1 customer-to-test) 0.5)))
  (testing "Get prize when level is 2"
    (is (= (customer/get-prize 2 customer-to-test) 0.25)))
  (testing "Get prize when level is 3"
    (is (= (customer/get-prize 3 customer-to-test) 0.125))))

(deftest sum-prize
  (testing "Accumulate customer prize more new prize"
    (is (= ((customer/sum-prize 1 customer-to-test-with-prize) :prize) 2))))

(deftest update-last-fractional-value-test
  (testing "Receive new value to save the :last-fractional-value"
    (is (= ((customer/update-last-fractional-value 0.5 customer-to-test) :last-fractional-value) 0.5))))

(deftest update-prize-test
  (testing "Call method sum-pize and update-last-fractional."
    (is (= (customer-to-test-with-prize :prize) 1))
    (is (= (customer-to-test-with-prize :last-fractional-value) 1))
    (is (= ((customer/update-prize 0.5 customer-to-test-with-prize) :prize) 1.5))
    (is (= ((customer/update-prize 0.5 customer-to-test-with-prize) :last-fractional-value) 0.5))))

(deftest update-inviting-paid-test
  (testing "Update inviting-paid to true"
    (is (= (customer-to-test :inviting-paid) false))
    (is (= ((customer/update-inviting-paid customer-to-test) :inviting-paid) true))))

(deftest get-investing-test
  (testing "Return the customer that has invited"
    (is (= (customer/update-list customer-to-test) (hash-map (customer-to-test :name) customer-to-test)))
    (is (= (customer/get-investing guest-to-test) customer-to-test))))

(deftest add-test
  (testing "Enunciation Exercise
            Example:
              1 2
              1 3 
              3 4
              2 4
              4 5
              4 6
            The score is:
              1 - 2.5 (2 because he invited 2 and 3 plus 0.5 as 3 invited 4)
              3 - 1 (1 as 3 invited 4 and 4 invited someone)
              2 - 0 (even as 2 invited 4, it doesn't count as 4 was invited before by 3)
              4 - 0 (invited 5 and 6, but 5 and 6 didn't invite anyone)"
     (customer/add 1 2)
     (customer/add 1 3)
     (customer/add 3 4)
     (customer/add 2 4)
     (customer/add 4 5)
     (customer/add 4 6)
     (is (= ((customer/get-customer 1) :prize) 2.5))
     (is (= ((customer/get-customer 3) :prize) 1))
     (is (= ((customer/get-customer 2) :prize) 0))
     (is (= ((customer/get-customer 4) :prize) 0))))

