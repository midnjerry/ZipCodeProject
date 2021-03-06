# ZipCode Project

## Problem Statement
Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.

### NOTES
- The ranges above are just examples, your implementation should work for any set of arbitrary ranges
- Ranges may be provided in arbitrary order
- Ranges may or may not overlap
- Your solution will be evaluated on the correctness and the approach taken, and adherence to coding standards and best practices

### EXAMPLES:
#### Example 1
If input = [94133,94133] [94200,94299] [94600,94699]<BR>
Output should be = [94133,94133] [94200,94299] [94600,94699]
#### Example 2
If input = [94133,94133] [94200,94299] [94226,94399]<BR> 
Output should be = [94133,94133] [94200,94399]

## Solution
The solution was written in Eclipse as a MAVEN project.  This solution can be broken down into the following components:
- ZipCodeTracker - This component keeps track of all zipcodes by using a 100000 size boolean array.  An array was chosen for implementation because the max zipcode for this problem is 99999 and thus O[n] time-complexity would still be relatively fast.  For every range: [start,end], ZipCodeTracker iterates from start to end marking each zipcode within the array.  If the problem-statement were to be changed to include 9-digit zip codes then I would consider a different implementation.
- Parser - This component converts serialized strings into integer components for use by the ZipCodeTracker.  All of the validation for the input string is done in this class.  Zipcodes must be 5 digits and must equate to values in range [00001,99999].  The input format described by the Problem Statement must also be strictly followed.    
- ZipCodeException - This exception was written to represent any validation errors found by the Parser or ZipCodeTracker.

### Unit Tests
I included multiple unit-tests for ZipCodeTracker, Parser, as well as for the "Main" Solution class.

### Installation
There are two ways to install.
1. Download the jar from [https://github.com/midnjerry/ZipCodeProject/blob/master/ZipCodeProject.jar](https://github.com/midnjerry/ZipCodeProject/blob/master/ZipCodeProject.jar)
2. Import the project into Eclipse as Maven Project and Run As Maven Build.  The jar will be placed in the project /target directory.

### Usage
java -jar ZipCodeProject.jar [<5-digit zipcode>,<5-digit zipcode>] [<5-digit zipcode>,<5-digit zipcode>] ...
