# DSP Coding Exercise

##Run all Tests in the exercise - 

Clone the Repository

`git clone https://github.com/jayeshguru/javacodeexercise.git`

Build with Maven(This will build and run all JUnit tests required in the assignment)

`mvn clean install test` in Terminal/Command Line

or 

Eclipse > Rt Click on pom.xml > Run As > Maven Build > Put Goals as clean install test

## Assignments - 

* Exercise 1 Display All Routes with type 0,1
* Exercise 2a Route With Most Stops
* Exercise 2b Route With Least Stops
* Exercise 2c Stops connecting 2 or more routes

## Documents

TestFiles

https://github.com/jayeshguru/javacodeexercise/tree/master/src/test/java/com/java/test/mbta

_Exercise 1_

b) There are two ways to filter results for subway­only routes. Think about the two options below and choose.
Option 1) https://api­v3.mbta.com/routes

Option 2) https://api­v3.mbta.com/routes?filter[type]=0,1

_Answer_ : We should use Option 2 as we always want endpoints to return minimum results to process rather processing huge JSON response.