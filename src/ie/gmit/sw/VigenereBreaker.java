package ie.gmit.sw;

import java.rmi.RemoteException;

public interface VigenereBreaker extends java.rmi.Remote{

	public String decrypt(String cypherText, int maxKeyLength) throws RemoteException;
}
