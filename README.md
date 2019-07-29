# coding-challenge9
Started out with this trying to use the factory pattern for the transactions. It didn't quite work and was starting to get fiddly.
I thought if a bit fiddly then it was not the right pattern. So I went with an interface for a transaction and then implemented each of the transaction types from there

Within GameLedger, I have created an overloaded function called addTransaction which takes an enumerated type indicating the transaction type and then the various parameters needed to create a transaction. I've added some logic within gameledger to ensure transaction types are managed and within each of the various transaction classes there's additional logic to ensure that each transaction is created properly. If the client tries to create an invalid transaction of any type an exceptiion is raised. Tests have been created to check these exceptions along with succesful creations

### Stuff I am not sure about

Whether a transaction should check if something can be purchased - should that be something a Location does?
Should I be testing for transaction return types in my tests? Or more to the point should adding a transaction through the gameledger even return a transaction - perhaps it should just do it.. and that's that

### Things I have learned from this exercise

1. How to create a test for an exception
2. How to add a dependancy to the the build.gradle file. I updated this file to include JUnit5 so I could use  assertThrows. 
3. I needed to include an api and jupiter engine for JUnit5
4. I so wanted to use the factory pattern but it wasn't the right pattern, still I learned about that - although I think I will use this pattern for the creation of the gameboard and locations. I'll have a Location Factory I think
5. How to throw an exception
6. Smart casts, use of 'is' and 'as'
