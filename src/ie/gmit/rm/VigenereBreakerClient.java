package ie.gmit.rm;

import java.rmi.Naming;

public class VigenereBreakerClient {

	public static void main(String[] args) throws Exception {
		/*
		 * Vigenere Breaker Client 
		 * is a tester class, that tests the breaker rmi service
		 */
		VigenereBreaker vigenereBreaker = (VigenereBreaker) 
				Naming.lookup("rmi://localhost:1099/VigenereBreakerService");
		
		String cypherText = new String("CHZQDRLTKAXFWOXGSDVYBXENACQNUJIHMXP");
		String result = vigenereBreaker.decrypt(cypherText, 4);
		System.out.println("decrypted result: " + result);
	}
}
