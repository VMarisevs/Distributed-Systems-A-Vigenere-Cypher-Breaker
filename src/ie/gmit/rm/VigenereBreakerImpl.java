package ie.gmit.rm;

import java.rmi.*;
import java.rmi.server.*;

import ie.gmit.sw.KeyEnumerator;

public class VigenereBreakerImpl extends UnicastRemoteObject implements VigenereBreaker {
	private static final long serialVersionUID = 1L;
	KeyEnumerator ke;
	
	public VigenereBreakerImpl() throws Exception {
		ke = new KeyEnumerator();
	}
	
	@Override
	public String decrypt(String cypherText, int maxKeyLength) throws RemoteException {
		// delegating decrypt method to crack cypher
		String result = ke.crackCypher(cypherText, maxKeyLength);
		return result;
	}

}
