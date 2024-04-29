This program is used to find the fake gold bar on the website http://sdetchallenge.fetch.com/

Setup:
1. Install Java JDK
2. Install Eclipse or preferred IDE
3. Clone or import the project files from github:
	https://github.com/tmar00/FetchSDET
4. Open main.java in src/main folder and run program
5. If error occurs, Might need to configure the build path for the project and add all the external jar files in Library/selenium-java-4.20.0

Framework architecture:
- src/main/main.java - Main function to run program
- src/main/CommonUtility.java - Utility library class that contain all commonly used function
- src/main/MainPageObject.java - Page object library class that contains all functions to run action/process on the main page (http://sdetchallenge.fetch.com/)
- Driver/ - Folder containing webdriver
- Library/ - Folder containing all required external jar

Main Algorithm:
1. We start by dividing all the gold bars to weigh into 2 equal amount groups. If we have a odd number of gold bars, we will leave the middle one out. 
(eq. {0,1,2,3,4,5,6,7,8} will be divided into {0,1,2,3} and {5,6,7,8} with {4} being left out.)
2. We populate the first group into the left bowl and the second group into the right bowl and weigh the two bowls.
3. We look at the measurement results
a. If the two bowls are equal, the gold bar that was left out should be the fake one. (eq. in the example in step 1, 4 will be the fake gold bar.)
b. If the left bowl weigh less and contain one gold bar, We found the fake gold bar in the left bowl.
c. If the right bowl weigh less and contain one gold bar, We found the fake gold bar in the right bowl.
d. If the bowl that weigh less still contain more than one gold bars, we go back step 1 with the group that weigh less to divide and repeat until we find the fake gold bar.
