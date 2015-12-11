package ie.gmit.sw;

public class QuadgramMap extends GramMap{

	/*
	 * It will be a singleton class,
	 * We don't need several instances of quadgram map, 
	 * that hold the breaking methods
	 */
	private static QuadgramMap instance;
	
	protected QuadgramMap(){
		// it is quad = 4 gram
		super(4);
	}
	
	public static QuadgramMap getInstance(){
		
		if (instance == null){
			instance = new QuadgramMap();
		}
		
		return instance;
	}
	
	
	public static void main(String[] args) throws Exception {
		QuadgramMap qm = QuadgramMap.getInstance();
		qm.preloadGramsFromFile("./WarAndPeace-Tolstoy.txt");
	}
}
