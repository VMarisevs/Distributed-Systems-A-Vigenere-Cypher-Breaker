package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.*;

import ie.gmit.rm.*;

public class Servant {
	public static void main(String[] args) {
		int PORT = 1099;
		
		try {			
			LocateRegistry.createRegistry(PORT);
			
			VigenereBreaker vigenereBreaker = new VigenereBreakerImpl();
			Naming.rebind("VigenereBreakerService", vigenereBreaker);
			
			System.out.println("[INFO] RMI Vigenere Breaker Server is Ready...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
