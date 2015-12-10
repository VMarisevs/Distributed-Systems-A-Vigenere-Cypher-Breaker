
Run Tom Cat winx:
> $ %CATALINA_HOME%\bin\startup.bat

Browser:
	http://localhost:8080/cracker/

Running Vigenere Breaker RMI Service
U:\Java\Distributed-Systems-A-Vigenere-Cypher-Breaker\bin>

> $ cd /Distributed-Systems-A-Vigenere-Cypher-Breaker/src
/* they already compiled */

> $ javac ie/gmit/rm/*.java

> $ rmic ie.gmit.rm.VigenereBreakerImpl

> $ java ie.gmit.rm.VigenereBreakerServer

> $ java ie.gmit.rm.VigenereBreakerClient
