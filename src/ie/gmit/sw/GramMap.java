package ie.gmit.sw;

import java.util.*;
import java.io.*;

public class GramMap {

	private Map<String, Integer> map = new HashMap<String, Integer>();
	private int gramSize;
	private int counter = 0;
	
	// constructor
	public GramMap(int _gramSize){
		gramSize = _gramSize;
	}
	
	// calculating the score for passed text
	public float getScore(String text){
		float score = 0f;
		
		for (int i = 0; i < text.length(); i++){
			
			if (i + 4 >= text.length()) break;
			
			String next = text.substring(i, i+4);
			
			if (map.get(next) != null){
				float frequency = (float)map.get(next);
				float total = (float)map.size();
				score += Math.log10((frequency/total));
			}
		}
		
		return -score;
	}
	
	
	/*
	
	public int getTotalScore(){
		int total = 0;
		
		for (String key : map.keySet()){
			total += map.get(key);
		}
		
		return total;
	}*/
	
	// preloading grams from file
	public void preloadGramsFromFile(String filename) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		String line;
		
	    while ((line = br.readLine()) != null) {
	    	
	    	String key = line.substring(0, gramSize);
	    	int value = Integer.parseInt(line.substring(gramSize+1,line.length()));
	    	
	    	map.put(key,value);
	    }
	    br.close();
	}
	
	// parsing the file and populating gram map
	public void parseGramsFromFile(String filename)throws Exception{
		// declaring buffered reader to read from file
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		
		// declaring StringBuilder array 
		StringBuilder sb[] = new StringBuilder[gramSize];
		
		// initializing each String Buffer in Array
		this.initStringBuilderArray(sb);
		
		// each char in text
		int i = 0;
		// A-Z a-z character counter
		int charCounter = 0;
		
		while((i = br.read()) != -1){
			char next = (char)i;
			
			// rejecting all except A-Z a-z characters
			if ((next >= 'A' && next <= 'Z')
				||	
				(next >= 'a' && next <= 'z')){
				
				
				if (charCounter < gramSize){
					/*
					 * If this is a first char, only first sb[] will add char
					 * If this is a second char, only first and second sb[] will add char
					 * If this is a n char (which is less than gramSize), 
					 *  all arrays up to n will add a character
					 * After first loop, all chars will be added into their sb[]
					 * 
					 * char		0|1|2|3|4|5|6|7|8|9|.|.|
					 * text		A|R|E|N|O|W|J|U|S|T|F|A|M|I|L|Y|E|S|T|A|T|E|S|
					 * -> 	[0]
					 * sb[0]	A| | | | | |
					 * sb[1]	 | | | | | |
					 * sb[2]	 | | | | | |
					 * -> 	[1]
					 * sb[0]	A|R| | | | |
					 * sb[1]	 |R| | | | |
					 * sb[2]	 | | | | | |
					 * ->	[2]
					 * sb[0]	A|R|E| | | |
					 * sb[1]	 |R|E| | | |
					 * sb[2]	 | |E| | | |
					 * ->	[3]
					 * sb[0]	A|R|E|N| | |
					 * sb[1]	 |R|E|N| | |
					 * sb[2]	 | |E|N| | |
					 * ->	[4]
					 * sb[0]	 | | | |O| |
					 * sb[1]	 |R|E|N|O| |
					 * sb[2]	 | |E|N|O| |
					 * ->	[5]
					 * sb[0]	 | | | |O|W|
					 * sb[1]	 | | | | |W|
					 * sb[2]	 | |E|N|O|W|
					 */
					int mod = charCounter % gramSize;
					for (int y = 0; y < mod; y++){
						sb[y].append(Character.toUpperCase(next));
						
					}
				} else {
					// if sequence of string builders is set, we can continue normally adding characters
					appendStringBuilderArrays(sb,next);
				}
				
				// populating the map if reached gram size, and clearing the string builder
				populateMapFromBuilder(sb);
				
				
				charCounter++;
			}	
		}
		
		br.close();
	}
	
	// inserting a new gram or populating frequency 
	private void put(String gram){
		int frequency;		
		if (map.containsKey(gram)){
			frequency = map.get(gram);
		
			frequency++;
		} else{
			frequency = 1;
		}
		
		map.put(gram, frequency);
	}
	
	// init string builder array
	private void initStringBuilderArray(StringBuilder sb[]){
		/*
		 * initializing string builders based on passed array
		 */
		for (int i = 0; i < sb.length; i++){
			sb[i] = new StringBuilder();
		}
	}
	
	private void appendStringBuilderArrays(StringBuilder sb[], char next){
		for (int i = 0; i < sb.length; i++){
			/*
			 * Keep adding each char into all string builders and converting into uppercase
			 * string builder length = gramSize
			 * in this case, all variations of grams will be added into map
			 */
			sb[i].append(Character.toUpperCase(next));
		}
	}
	
	private void populateMapFromBuilder(StringBuilder sb[]){
		for (int i = 0; i < sb.length; i++){
			
			if (sb[i].length() == gramSize){
				/*
				 * When reaches gram size -> populates the map with actual gram
				 * and increases the counter (it is not the size of the map, 
				 * it is the count of the grams were found including duplicates)
				 */
				counter++;
				// inserting into map
				put(sb[i].toString());
				// erasing the string
				sb[i].setLength(0);
			}
		}
	}
	
	public int gramsReaded(){
		return counter;
	}
	
	public int gramsHave(){
		return map.size();
	}
	
	// class testing
	public static void main(String[] args) throws Exception {
		GramMap gm = new GramMap(4);
		gm.parseGramsFromFile("./WarAndPeace-Tolstoy.txt");
		
		String cypherText = 
				new String("XNBEAVHOJRCQODUBUJENJBHXDLJUUHXDABNUOKDCQXFMXHXDMXRBNNRQJENOARPQCNWNMHXDBRCMXFWJWMCNUUVNJUUCQNWNFBRCFJBRWSDUHJWMCQNBYNJTNAFJBCQNFNUUTWXFWJWWJYJEUXEWJBLQNANAVJRMXOQXWXAJWMOJEXARCNXOCQNNVYANBBVJAHJONMXAXEWJFRCQCQNBNFXAMBBQNPANNCNMYARWLNEJBRURTDAJPRWJVJWXOQRPQAJWTJWMRVYXACJWLNFQXFJBCQNORABCCXJAARENJCQNAANLNYCRXWJWWJYJEUXEWJQJMQJMJLXDPQOXABXVNMJHBBQNFJBJBBQNBJRMBDOONARWPOAXVUJPARYYNPARYYNKNRWPCQNWJWNFFXAMRWBCYNCNABKDAPDBNMXWUHKHCQNNURCNJUUQNARWERCJCRXWBFRCQXDCNGLNYCRXWFARCCNWRWOANWLQJWMMNURENANMKHJBLJAUNCURENARNMOXXCVJWCQJCVXAWRWPAJWJBOXUUXFBROHXDQJENWXCQRWPKNCCNACXMXLXDWCXAYARWLNJWMROCQNYAXBYNLCXOBYNWMRWPJWNENWRWPFRCQJYXXARWEJURMRBWXCCXXCNAARKUNRBQJUUKNENAHLQJAVNMCXBNNHXDCXWRPQCKNCFNNWJWMJWWNCCNBLQNANAQNJENWBFQJCJERADUNWCJCCJLTANYURNMCQNYARWLNWXCRWCQNUNJBCMRBLXWLNACNMKHCQRBANLNYCRXWQNQJMSDBCNWCNANMFNJARWPJWNVKAXRMNANMLXDACDWROXAVTWNNKANNLQNBJWMBQXNBJWMQJMBCJABXWQRBKANJBCJWMJBNANWNNGYANBBRXWXWQRBOUJCOJLNQNBYXTNRWCQJCANORWNMOANWLQRWFQRLQXDAPAJWMOJCQNABWXCXWUHBYXTNKDCCQXDPQCJWMFRCQCQNPNWCUNYJCAXWRIRWPRWCXWJCRXWWJCDAJUCXJVJWXORVYXACJWLNFQXQJMPAXFWXUMRWBXLRNCHJWMJCLXDACQNFNWCDYCXJWWJYJEUXEWJTRBBNMQNAQJWMYANBNWCRWPCXQNAQRBKJUMBLNWCNMJWMBQRWRWPQNJMJWMLXVYUJLNWCUHBNJCNMQRVBNUOXWCQNBXOJORABCXOJUUMNJAOARNWMCNUUVNQXFHXDJANBNCHXDAOARNWMBVRWMJCANBCBJRMQNFRCQXDCJUCNARWPQRBCXWNKNWNJCQCQNYXURCNWNBBJWMJOONLCNMBHVYJCQHXOFQRLQRWMROONANWLNJWMNENWRAXWH");
				//new String("ASDKJLASDBJBACJBLAWBJASCLAJSNC");
		String plainText = 
				new String("ONGERMYFAITHFULSLAVEASYOUCALLYOURSELFBUTHOWDOYOUDOISEEIHAVEFRIGHTENEDYOUSITDOWNANDTELLMEALLTHENEWSITWASINJULYANDTHESPEAKERWASTHEWELLKNOWNANNAPAVLOVNASCHERERMAIDOFHONORANDFAVORITEOFTHEEMPRESSMARYAFEDOROVNAWITHTHESEWORDSSHEGREETEDPRINCEVASILIKURAGINAMANOFHIGHRANKANDIMPORTANCEWHOWASTHEFIRSTTOARRIVEATHERRECEPTIONANNAPAVLOVNAHADHADACOUGHFORSOMEDAYSSHEWASASSHESAIDSUFFERINGFROMLAGRIPPEGRIPPEBEINGTHENANEWWORDINSTPETERSBURGUSEDONLYBYTHEELITEALLHERINVITATIONSWITHOUTEXCEPTIONWRITTENINFRENCHANDDELIVEREDBYASCARLETLIVERIEDFOOTMANTHATMORNINGRANASFOLLOWSIFYOUHAVENOTHINGBETTERTODOCOUNTORPRINCEANDIFTHEPROSPECTOFSPENDINGANEVENINGWITHAPOORINVALIDISNOTTOOTERRIBLEISHALLBEVERYCHARMEDTOSEEYOUTONIGHTBETWEENANDANNETTESCHERERHEAVENSWHATAVIRULENTATTACKREPLIEDTHEPRINCENOTINTHELEASTDISCONCERTEDBYTHISRECEPTIONHEHADJUSTENTEREDWEARINGANEMBROIDEREDCOURTUNIFORMKNEEBREECHESANDSHOESANDHADSTARSONHISBREASTANDASERENEEXPRESSIONONHISFLATFACEHESPOKEINTHATREFINEDFRENCHINWHICHOURGRANDFATHERSNOTONLYSPOKEBUTTHOUGHTANDWITHTHEGENTLEPATRONIZINGINTONATIONNATURALTOAMANOFIMPORTANCEWHOHADGROWNOLDINSOCIETYANDATCOURTHEWENTUPTOANNAPAVLOVNAKISSEDHERHANDPRESENTINGTOHERHISBALDSCENTEDANDSHININGHEADANDCOMPLACENTLYSEATEDHIMSELFONTHESOFAFIRSTOFALLDEARFRIENDTELLMEHOWYOUARESETYOURFRIENDSMINDATRESTSAIDHEWITHOUTALTERINGHISTONEBENEATHTHEPOLITENESSANDAFFECTEDSYMPATHYOFWHICHINDIFFERENCEANDEVENIRONY");
				//new String("THEQUICKBROWNFOXJUMPSOVERTHELAZYDOG");
		
		float englishScore = gm.getScore(plainText);
		float gibberishScore = gm.getScore(cypherText);
		System.out.println("English score is :" + englishScore);
		System.out.println("Gibberish score is :" + gibberishScore);
	}
}
