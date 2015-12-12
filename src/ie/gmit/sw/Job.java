package ie.gmit.sw;

public class Job {

	private long jobId;
	private String plainText;
	private String cypherText;
	private int maxKeyLength;
	private char status;

	
	
	public Job(long jobId, String plainText, String cypherText, int maxKeyLength) {
		super();
		this.jobId = jobId;
		this.plainText = plainText;
		this.cypherText = cypherText;
		this.maxKeyLength = maxKeyLength;
		this.status = 'p'; // p - for pending; o - operation or processing; r - ready
	}
	public int getMaxKeyLength() {
		return maxKeyLength;
	}
	public void setMaxKeyLength(int maxKeyLength) {
		this.maxKeyLength = maxKeyLength;
	}
	public long getJobId() {
		return jobId;
	}
	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
	public String getPlainText() {
		return plainText;
	}
	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	public String getCypherText() {
		return cypherText;
	}
	public void setCypherText(String cypherText) {
		this.cypherText = cypherText;
	}
	
	public void setProcessing(){
		this.status = 'o';
	}
	
	public void setReady(){
		this.status = 'r';
	}
	
	public void setPending(){
		this.status = 'p';
	}
	
	public char getStatus(){
		return this.status;
	}
}
