package ie.gmit.sw;

import java.rmi.Naming;

import ie.gmit.rm.VigenereBreaker;

public class RMICallVigenereBreaker {

	private static String ip = new String("localhost");
	private static String port = new String("1099");
	
	public static VigenereBreaker getVigenereBreaker() throws Exception{
		VigenereBreaker vigenereBreaker = (VigenereBreaker) 
				Naming.lookup("rmi://" + ip + ":" + port + "/VigenereBreakerService");
		
		return vigenereBreaker;
	}

}
