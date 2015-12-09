package ie.gmit.rm;

import java.rmi.Naming;
import java.rmi.registry.*;

public class VigenereBreakerServer {
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
