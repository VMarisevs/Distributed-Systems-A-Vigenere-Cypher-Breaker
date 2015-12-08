package ie.gmit.sw;

import java.util.*;

public class Scores {
	Score[] scoresArray = new Score[1500];
	
	public Scores() {
		for (int i = 0; i < scoresArray.length; i++){
			scoresArray[i] = new Score("",0f);
		}
	}
	
	public int size(){
		return scoresArray.length;
	}
	
	public Score get(int i){
		return scoresArray[i];
	}
	
	public void putScore(Score _score){
		for (int i=0; i<scoresArray.length; i++){
			if (scoresArray[i].getScore() < _score.getScore()){
				shiftScores(i);
				scoresArray[i] = _score;
				break;
			}
		}		
	}
	
	
	private void shiftScores(int place){
		for (int i = scoresArray.length-1; i > place; i--){
			scoresArray[i] = scoresArray[i-1];
		}
	}
	public static void main(String[] args) {
		Scores sc = new Scores();
		Random rn = new Random();
		
		for (int i = 0; i < 20; i++){
			float rfloat = rn.nextFloat();
			char rchar = (char)(rn.nextInt(90 - 65 + 1) + 65);
			System.out.println("key " + rchar + " value " + rfloat);
			sc.putScore(new Score(Character.toString(rchar), rfloat));
		}
		
		for (int i=0; i < sc.size(); i++){
			System.out.println(i+") key " + sc.get(i).getKey() + " score " + sc.get(i).getScore());
		}
	}
}
