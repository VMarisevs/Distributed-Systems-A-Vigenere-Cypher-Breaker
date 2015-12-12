package ie.gmit.rm;

import java.rmi.*;

public interface VigenereBreaker extends Remote{

	public String decrypt(String cypherText, int maxKeyLength) throws RemoteException;
}
