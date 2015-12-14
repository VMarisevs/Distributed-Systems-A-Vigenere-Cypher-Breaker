#### DISTRIBUTED SYSTEMS Main Assignment 2015
##### Vladislavs Marisevs
##### [GitHub](https://github.com/VMarisevs/Distributed-Systems-A-Vigenere-Cypher-Breaker)

This project consists of 2 parts (github project branches). 

One of them is RMI Servant, that breaks Vigenere cypher and returns the decrypted text. Another is web application that process user cypher text request and passes them to RMI Servant to break the message.




### How To

##### Run RMI
Make sure that You have *quadgrams.txt* and *WarAndPeace-Tolstoy.txt* in the same folder
> $ java -cp ./vigenere.jar ie.gmit.sw.Servant

##### Run Web app
Copy cracker.war file into /TomCat/webapps/cracker.war , let Tom Cat deploy the files

##### To get java files
They can be deployed using TomCat/webapp/*.war . Add *.war file (or change type of *.jar). All *.java files are inside in cracker.war and vinegere.jar




### Extras + Features

#### RMI Servant
+ Gram Map can be easy extended and modified to be Quad gram map or any other gram size.
+ When I was trying to figure out what kind of place takes original English text, I have implemented Top Scores that stores all best key scores. And we can display them and see what place takes original key.
+ KeyEnumerator uses recursive function to generate keys and decode the cypher text.

#### TomCat Web Application
+ Added [Bootstrap](http://www.w3schools.com/bootstrap/) styles to .jsp views.
+ Upgraded CrackerHandler so it displays in normal web application view (.jsp)
+ Made Status Servlet, that shows the status of the users job request.
+ Implemented 2 ways of sending RMI requests, sync and async. By default using async, but easy can be changed back in Work class.
+ Work class is a listener, that polls the in queue every 2 sec. And if there are jobs pass them to a new thread, that makes RMI call.




### [RMI Servant](https://github.com/VMarisevs/Distributed-Systems-A-Vigenere-Cypher-Breaker/tree/RMI-Servant)

#### About
This part is a Vigenere RMI Service. In How To section I have described how to compile and run the project. It has an Eclipse structure and can be loaded with it.
This part should be responsible for running RMI Service and generating the stub.

#### Structure
##### GramMap.java
This class is responsible for generating the score of how the passed text is English readable. To implement that I am using map of grams and their occurrence count. It loops through whole string, to get all gram posibilities and generate the score.
I am using 2 types of populating the gram map. One of them is to load the file with gram and frequency in space separated file. Another is to read grams from text and update the frequency in the map. To do that I am using array of string builders that will go one by another to populate the map, I have also described that in small diagram in the class file:
```
Assume that we are looping throught text char is char position. So in the first step StringBuffer that builds quad grams, it adds char[0] and rest of them ignores it. In second step char[1] has been accepted by 2 SB's in 3rd char[2] has been accepted by 3 SB's and so on... In this case we are getting all grams from text.

char	0|1|2|3|4|5|6|7|8|9|.|.|
text	A|R|E|N|O|W|J|U|S|T|F|A|M|I|L|Y|E|S|T|A|T|E|S|
-> 	[0]
sb[0]	A| | | | | |
sb[1]	 | | | | | |
sb[2]	 | | | | | |
sb[3]	 | | | | | |
-> 	[1]
sb[0]	A|R| | | | |
sb[1]	 |R| | | | |
sb[2]	 | | | | | |
sb[3]	 | | | | | |
->	[2]
sb[0]	A|R|E| | | |
sb[1]	 |R|E| | | |
sb[2]	 | |E| | | |
sb[3]	 | | | | | |
->	[3]
sb[0]	A|R|E|N| | |
sb[1]	 |R|E|N| | |
sb[2]	 | |E|N| | |
sb[3]	 | | |N| | |
->	[4]
sb[0]	 | | | |O| |
sb[1]	 |R|E|N|O| |
sb[2]	 | |E|N|O| |
sb[3]	 | | |N|O| |
->	[5]
sb[0]	 | | | |O|W|
sb[1]	 | | | | |W|
sb[2]	 | |E|N|O|W|
sb[3]	 | | |N|O|W|
```

##### QuadgramMap.java
Inherits all grams functionality, but at the constructor it sets gramSize to 4. And it uses Singleton Desgin Pattern.

##### Score.java
This is a basic object class, that stores score and decoded key. I have been using it to test breaking algorithm.

##### Scores.java
This class is resposible for storing TOP 1500 keys. When you try to put a score object into scores, it will be added only if it is in the range of > ScoreArray[Last]. Before it will take a place, rest of the scores that are lower, will be shifted.

##### KeyEnumerator.java
This class have a complete implementation of Vigenere cypher breaker. It has a list of scores (Scores object) and the quad gram map that preloads from *./quadgrams.txt* and *./WarAndPeace-Tolstoy.txt*
To generate all key posibilities I am using recursive function. If it reaches the depth, it will populate the Top Scores.
CrackCypher loops through from minimal minimal key length up to maximum (as depth in the recursive function) and returns the decrypted string with Top key. I was testing this part, and the actual key was always in the top 3000, but not always correct.
I also made few functions to test the functionality of the breaker. We can try to calculate the score for plain English text and display the complete top of keys.

##### Servant.java is an ex ie.gmit.rm.VigenereBreakerServer
Is responsible for creating a registry at the port *1099* and binding the name *VigenereBreakerService* to a *VigenereBreakerImpl*

##### VigenereBreaker.java
This is just a remote interface, that is used for rmi call.

##### VigenereBreakerClient.java
This is a testing class, that calls VigenereBreakerService to decrypt the string.

##### VigenereBreakerImpl.java
This is a remote object implementation which delegates the job to KeyEnumerator. And we can generate stub out of it.

##### Vigenere.java
Is the implementation of Vigenere cypher, that have been given.




### [TomCat Web Application](https://github.com/VMarisevs/Distributed-Systems-A-Vigenere-Cypher-Breaker/tree/TomCat-Web-Application)

#### About
This par is a Web Application for a TomCat. In How To section I have described how to compile and run the project. It has an Eclipse structure and can be loaded with it.
This part should be resposible for making a list of Web Client requests with cypher text and max key length and add them into inQueue and send asynchronous request to RMI Service and get a response and add into out queue. User should constantly refreshing the page (it is automatically done every 10 sec) and wait to receive his decrypted text.

#### Structure
##### WebContent
This folder was given by lecturer. We have there a web.xml file as a project descriptor. And one form that submits the form with key lenght and cypher text.

##### CrackerHandler.java
This class extends HttpServlet and is resposible for accepting the form variables and keep user refreshing same page, but just with initialized task number.
I am using *currentTimeMillis()* as an id for each task. Duplication might occur, but when you are doing on your own, it is very hard to make it :) At the time when job id is initialized I am passing a job object to Work class to implement the work.
Also each user can see in queue and out queue size.

##### StatusHandler.java
This HttpServlet is just displaying the status of jobs, that Web application received from user and passed to RMI Service.

##### Job.java
This is a basic class with overloaded contructor that defines all parameters to make a RMI call and receive a result.(job id, plain text as result, cypher text and max key lenght passed from user)

##### RMICallVigenereBreaker
This class has a get method that returns remote object.

##### AsyncRmiRequest
This is a thread implementation that makes an rmi call gets the result and puts it into out queue.

##### Work
This is *ServletContextListener* that stays awake and every 2 seconds it tries to poll from the in queue and run new thread to make asynchronous calls. There is set of static methods available for other classes, to check in and out queue size, add new job, get job result using job id as parameter, check if job is in the out queue, put the result into out map.
