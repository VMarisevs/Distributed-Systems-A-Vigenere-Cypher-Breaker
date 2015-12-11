package ie.gmit.sw;

public class AsyncRmiRequest implements Runnable {

	private Job job;
	
	public AsyncRmiRequest(Job _job) {
		this.job = _job;
	}

	@Override
	public void run() {
		
		try{
			job.setPlainText(new String(
						RMICallVigenereBreaker.getVigenereBreaker().decrypt(job.getCypherText(), job.getMaxKeyLength())
					));
			Work.put(job.getJobId(), job.getPlainText());
		} catch(Exception e){}
		
	}

}
