# DISTRIBUTED SYSTEMS Main Assignment

## Part one
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

## How To
#### Manually compiling the project
Open cmd, and cd into ../src/ folder
*Note that You might need to move* quadgrams.txt *and* WarAndPeace-Tolstoy.txt *to make it work, because ie.gmit.sw.KeyEnumerator class contructor is preloading them into quad gram map, to decode the text.*

Compile the sw folder (this will allow us to run servant, originally servant was in rm package and called VigenereBreakerServer, in that case I could compile just rm package):
> $ javac ie/gmit/sw/*.java

Client is a testing class, to test rmi connection
> $ javac ie/gmit/rm/VigenereBreakerClient.java

Generating the stub
> $ rmic ie.gmit.rm.VigenereBreakerImpl

Starting the servant, that listens for requests
> $ java ie.gmit.sw.Servant

Run the client to prove that rmi calls are working
> $ java ie.gmit.rm.VigenereBreakerClient

## For submission
A Java Archive containing the Vigenere RMI Service and a servant class with a main() method.
> $ java –cp ./vigenere.jar ie.gmit.sw.Servant