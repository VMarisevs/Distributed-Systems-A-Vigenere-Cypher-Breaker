package ie.gmit.sw;

import java.io.*;
import java.util.*;

public class QuadgramMap extends GramMap{

	private Map<String, Integer> map = new HashMap<String, Integer>();
	
	public QuadgramMap(String filename) throws Exception{
		// parsing file and building the map of quadgrams
		super(filename, 4);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		new QuadgramMap("./WarAndPeace-Tolstoy.txt");
	}
}
