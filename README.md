# reward-system

System designed to reward customers for inviting their friends.

#### Respecting the rules:

 * Give customer points for each confirmed invitation he played a part into. The definition of a confirmed invitation is when the invitee invited someone.

 * He gets (1/2)^k where k is the level of the confirmed invitation: level 0 (people he invited) he gets 1 point, level 1 (people invited by a person he invited) he gets 1/2 points and level 2 invitations (people invited by a person on level 1) awards him 1/4 and so on. Only the first invitation counts: so if someone gets invited by someone that was already invited, it doesn't award any points.

 * Also, to count as a valid invitation, the invited customer must have invited someone (so customers that didn't invite anyone don't count as points for the customer that invited them)

#### Dependencies

* To run the system, is necessary has been installed leiningen _http://leiningen.org/#install_

#### Tests

* Use the command: 
  ```
    lein test
  ```
  
#### Start system 

* Use the command: 
  ```
    lein run
  ```
  
_System will be started on port 8080_

#### How to Use

* Visit the link localhost:8080/

* Upload the file with format txt by the button, following the patern ("customer's id" "space" "guest's id")
```  
  Example:
    1 2
    2 3
    2 4
```
* Will be returned JSON with prize of customers. (explanation about json response are on topic "Logic Used").

#### Logic Used

_The solution taken was to create a data structure inspired by a graph where each customer (node) is connected to the customer who invited him (parent node), but the customer who invited also having the connection with the guest customer (child node) the two have a connection, all being saved in a HashMap with id as the key to the structure is as follows (as has the ID to map hash key is very easy to get to each customer):_

_Line Reading Example: 1 2_

_it Will first be created one structure to 1 and added to the list where:_

```
1 {(id reference to the customer being created)
  :Name = 1 (id reference to the customer being created)
  :Invited-by = nill (because no one added it)
  :Invitations [
    {
      :Guest-name guest-name = 2 (id is the reference of the customer being added)
      :Acepted accepted (Who defines it was accepted or not is the same already standing on the base or not, as is already the basis                          is for that has already been invited by someone else then I was to receive accpted to false)
    }
]
  :0 prize (starts with zero, but remained with the total as defined in the statement logic)
  :Paid false (boolean to indicate whether already paid for whom invitou the base, following the only rule pass when the CURRENT add                someone passes one to whom the added.
              Defaul = false because when you enter the base ngn paid for the invitou)
  :Last-fractional-value (attribute to indicate whether the new value that is being passed as part fractional already been calculated                           or if it coming from lower down the hierarquial.
                          Default = 1, because the greatest fractional number is 0.5 with 1 as the initial value will always be                                 overwritten a validation check if the last number is smaller than the old one)
}
```
_then structure will be created for 2, but without invitiations, because at the time it ta be invited so also will have the invited-by pointing to the one that already created and your invitante._

#### License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
