package ie.gmit.sw;

public class Score {

	private float score;
	private String key;
	
	public Score(String _key, float _score) {
		this.score =_score;
		this.key = _key;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public float getScore(){
		return this.score;
	}
}
