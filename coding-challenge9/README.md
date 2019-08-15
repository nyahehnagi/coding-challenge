# coding-challenge10
 Created new classes Boardlocation, Gameboard and Dice and added code to Player

 BoardLocation keeps track of itself when given a dice roll and a gameboard

 Gameboard is a list of locations - this is initialised by passing in a list of strings which in my code is sourced by a text file as I wanted to dabble with file handling

 Player has been updated to now contain a private property for it's location and a new move function.

 Tests for the new classes have been created in GameboardTest and BoardLocationTest


# coding-challenge9
Started out with this trying to use the factory pattern for the transactions. It didn't quite work and was starting to get fiddly.
I thought if a bit fiddly then it was not the right pattern. So I went with an interface for a transaction and then implemented each of the transaction types from there. The gameledger is simply a list of ITransactions. I've added a function in gameledger to bring back the balance of player

I needed to change how my locations were initially designed. I now use locations which inherit various interfaces e.g IRentable, IPurchaseable, IBuildable

I have also started building a gameboard... its not part of the challenge but I wanted to faff around with some file I/O. It's all a work in a progress and certainly should not be looked at as it's all very noddy with no error handling etc



### Stuff I am not sure about or needs some more work

1. Whether a transaction should check if something can be purchased - should that be something a Location does? - I have since changed this and used an interface named IPurchasable - no need to check now, as you have to pass a Location that can be purchased
2. Should I be testing for transaction return types in my tests? Or more to the point should adding a transaction through the gameledger even return a transaction - perhaps it should just do it.. and that's that
3. Building on a location - I'm still in mixed minds whether a Location should track this or whether 
this is done via the ledger or even elsewhere - currently I do it via Location.
4. Is it OK to raise exceptions when instantiating an object with imcompatible constructor parameters? I have since changed the design and no longer need exceptions due to changes in how I've designed the interfaces. 
5. Whether I should have a bank class, I have treated the bank as a special kind of player. I have been mulling over whether to have a bank at all and then change my transaction design to credit/debit set up against a player. I think this all depends on what the rules are for the bank e.g does it have unlimited funds? do we care how much money the bank has etc

### Things I have learned from this exercise

1. How to create a test for an exception
2. How to add a dependancy to the build.gradle file. I updated this file to include JUnit5 so I could use  assertThrows. 
3. I needed to include an api and jupiter engine for JUnit5
4. I so wanted to use the factory pattern but it wasn't the right pattern, still I learned about that - although I think I will use this pattern for the creation of the gameboard and locations. I'll have a Location Factory I think
5. How to throw an exception
6. Smart casts, use of 'is' and 'as'
7. using interfaces and the useful stuff you can do with 'when'
8. assignments using when
9. even more about in interfaces and how they can be used
