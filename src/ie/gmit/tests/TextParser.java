package ie.gmit.tests;

import java.io.*;

public class TextParser {

	public TextParser(String _filename) throws Exception {
		parse(_filename);
	}

	private void parse(String _filename) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./"+_filename)));
		
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		while((i = br.read()) != -1){
			char next = (char)i;
			// rejecting all except A-Z a-z characters
			if ((next >= 'A' && next <= 'Z')
				||	
				(next >= 'a' && next <= 'z')){
				//System.out.println(next);
				sb.append(Character.toUpperCase(next));
			
			}
		}
		
		
		File file = new File("./Parsed-" + _filename);

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(sb.toString());
		bw.close();	
		
		br.close();
		
	}
	
	public static void main(String[] args) throws Exception {
		new TextParser("WarAndPeace-Tolstoy.txt");
	}
	
}
