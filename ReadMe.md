# DISTRIBUTED SYSTEMS Main Assignment

## Part two
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

##### Job.java
This is a basic class with overloaded contructor that defines all parameters to make a RMI call and receive a result.(job id, plain text as result, cypher text and max key lenght passed from user)

##### RMICallVigenereBreaker
This class has a get method that returns remote object.

##### AsyncRmiRequest
This is a thread implementation that makes an rmi call gets the result and puts it into out queue.

##### Work
This is *ServletContextListener* that stays awake and every 2 seconds it tries to poll from the in queue and run new thread to make asynchronous calls. There is set of static methods available for other classes, to check in and out queue size, add new job, get job result using job id as parameter, check if job is in the out queue, put the result into out map.

## How To
#### Import into Eclipse
Import both projects into Eclipse.
Project Properties -> Project Facets -> Runtimes
Add new TomCat v8.0 put the path to TomCat folder and it will add set of libraries, that will allow you to compile the project.
#### Create WAR file
Right click on the WAR project Export as WAR file. Saving this *.war file in TomCat/webapp folder, and if service is running, it will be compiled automatically.
If we will remove main project library (in *TomCat/webapp/../WEB-INF/lib*), and restart the server. And we would need just an interface (in theory stub as well) to perform transport and marshalling. So in this case we need compiled interface *ie.gmit.rm.VigenereBreaker* to be copied into *TomCat/webapps/../WEB-INF/classes/ie/gmit/rm/*
(and stub as well, it can be compiled using rmic, check Part One readme How To section)