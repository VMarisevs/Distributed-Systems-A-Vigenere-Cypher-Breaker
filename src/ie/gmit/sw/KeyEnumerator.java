package ie.gmit.sw;

public class KeyEnumerator {
	
	// minimal key length -> 3 characters long
	public static int MIN_KEY_LENGTH = 3;
	
	
	private GramMap map;
	
	// sorted list of best scores
	private Scores bestScores = new Scores();
	
	
	public KeyEnumerator() throws Exception{
		// loading all quadgrams into map
		map = QuadgramMap.getInstance();
		/*
		 * Now we can populate the map with quad grams generated from text file
		 * or simply passing tab separated file with quad gram list
		 */
		
		map.parseGramsFromFile("./WarAndPeace-Tolstoy.txt");
		map.preloadGramsFromFile("./quadgrams.txt");
	
	}	
	
	// this function generates keys and decrypts the string and populates the scores table
	private void permutation(String prefix, int depth, String cypherText) {
		String alphabet = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		/*
		 * Recursive function that, generates A-Z all Permutations using depth
		 */		
		
		if (prefix.length() >= depth){
			
			// decrypting with each key that have been generated
			String result = new Vigenere(new String(prefix)).doCypher(cypherText, false);
			
			// calculating the score, and checking how English readable it is.
			float score = map.getScore(result);
			
			// populating the map with decoded key and score
			bestScores.putScore(new Score(prefix, score));
			
		} else{
			for (int i = 0; i < alphabet.length(); i++){
				permutation(prefix + alphabet.charAt(i), depth, cypherText);
			}
		}
		
	}
	
	// this function is for testing the original English string, based on calculated algorithm
	public float calcScore(String plainText){
		
		float score = 0f;
		
		score = map.getScore(plainText);
		
		System.out.println("Original score:" + score);
		return score;
	}
	
	// this function runs the decrypter, based on max size of the key
	public String crackCypher(String cypherText, int maxKeyLength){
		
		for (int i = KeyEnumerator.MIN_KEY_LENGTH; i <= maxKeyLength; i++){
			permutation("", i, cypherText);
		}	
		
		// getting the top key
		Vigenere v = new Vigenere(bestScores.get(0).getKey());
		// decrypting
		String result = v.doCypher(cypherText, false);
		
		return result;
	}
	
	// displays top scores in the console
	public void displayTopScores(){
		for (int i=0; i<bestScores.size(); i++){
			System.out.println(i + ") Best scores: " + bestScores.get(i).getScore() + " key:" + bestScores.get(i).getKey());
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		/*
		 * 4 english strings for testing
		 */
		
		String plainText = 				
				//new String("ARENOWJUSTFAMILYESTATES");
				new String("THEQUICKBROWNFOXJUMPSOVERTHELAZYDOG");
				//new String("ONGERMYFAITHFULSLAVEASYOUCALLYOURSELFBUTHOWDOYOUDOISEEIHAVEFRIGHTENEDYOUSITDOWNANDTELLMEALLTHENEWSITWASINJULYANDTHE");
				//new String("ONGERMYFAITHFULSLAVEASYOUCALLYOURSELFBUTHOWDOYOUDOISEEIHAVEFRIGHTENEDYOUSITDOWNANDTELLMEALLTHENEWSITWASINJULYANDTHESPEAKERWASTHEWELLKNOWNANNAPAVLOVNASCHERERMAIDOFHONORANDFAVORITEOFTHEEMPRESSMARYAFEDOROVNAWITHTHESEWORDSSHEGREETEDPRINCEVASILIKURAGINAMANOFHIGHRANKANDIMPORTANCEWHOWASTHEFIRSTTOARRIVEATHERRECEPTIONANNAPAVLOVNAHADHADACOUGHFORSOMEDAYSSHEWASASSHESAIDSUFFERINGFROMLAGRIPPEGRIPPEBEINGTHENANEWWORDINSTPETERSBURGUSEDONLYBYTHEELITEALLHERINVITATIONSWITHOUTEXCEPTIONWRITTENINFRENCHANDDELIVEREDBYASCARLETLIVERIEDFOOTMANTHATMORNINGRANASFOLLOWSIFYOUHAVENOTHINGBETTERTODOCOUNTORPRINCEANDIFTHEPROSPECTOFSPENDINGANEVENINGWITHAPOORINVALIDISNOTTOOTERRIBLEISHALLBEVERYCHARMEDTOSEEYOUTONIGHTBETWEENANDANNETTESCHERERHEAVENSWHATAVIRULENTATTACKREPLIEDTHEPRINCENOTINTHELEASTDISCONCERTEDBYTHISRECEPTIONHEHADJUSTENTEREDWEARINGANEMBROIDEREDCOURTUNIFORMKNEEBREECHESANDSHOESANDHADSTARSONHISBREASTANDASERENEEXPRESSIONONHISFLATFACEHESPOKEINTHATREFINEDFRENCHINWHICHOURGRANDFATHERSNOTONLYSPOKEBUTTHOUGHTANDWITHTHEGENTLEPATRONIZINGINTONATIONNATURALTOAMANOFIMPORTANCEWHOHADGROWNOLDINSOCIETYANDATCOURTHEWENTUPTOANNAPAVLOVNAKISSEDHERHANDPRESENTINGTOHERHISBALDSCENTEDANDSHININGHEADANDCOMPLACENTLYSEATEDHIMSELFONTHESOFAFIRSTOFALLDEARFRIENDTELLMEHOWYOUARESETYOURFRIENDSMINDATRESTSAIDHEWITHOUTALTERINGHISTONEBENEATHTHEPOLITENESSANDAFFECTEDSYMPATHYOFWHICHINDIFFERENCEANDEVENIRONY");
	
		
		Vigenere v = new Vigenere("JAVA");
		String cypherText = v.doCypher(plainText, true);
		System.out.println("Plain text: " + plainText);
		System.out.println("Cypher text: " + cypherText);
		KeyEnumerator ke = new KeyEnumerator();
		System.out.println("Cracking Started:  " + System.currentTimeMillis());
		String bestResult = ke.crackCypher(cypherText, 4);
		System.out.println("Cracking Finished: " + System.currentTimeMillis() + "\n " + bestResult);
		//ke.displayTopScores();
		
	}
}
