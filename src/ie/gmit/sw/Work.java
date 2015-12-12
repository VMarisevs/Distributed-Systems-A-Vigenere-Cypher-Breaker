package ie.gmit.sw;

import java.util.*;
import java.util.concurrent.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Work implements ServletContextListener{

	private static final BlockingQueue<Job> inQueue = new LinkedBlockingQueue<Job>();
	private static final Map<Long,String> outMap = new HashMap<Long,String>();
	private static final List<Job> status = new ArrayList<Job>();
	
	private Thread thread;

	// adding new job into queue
	public static void add(Job job){
		inQueue.add(job);
		status.add(job);
	}
	
	// checking inQueue size
	public static int inQueueSize(){
		return inQueue.size();
	}
	// checking outQueue size
	public static int outQueueSize(){
		return outMap.size();
	}
	
	// getting the decrypted result back
	public static String get(long jobId){
		 if (outMap.containsKey(jobId)){
			 return new String(outMap.get(jobId));
		 }		
		return null;
	}
	
	// checking if this job is done
	public static boolean contains(long jobId){
		return outMap.containsKey(jobId);
	}
	
	// adding result into outqueue
	public static void put(long id,String result){
		outMap.put(id, result);
	}
	
	public static Job[] getStatus(){
		return status.toArray(new Job[status.size()]);
	}
	
	@Override
 	public void contextDestroyed(ServletContextEvent arg0) {
		thread.interrupt();		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	/*
	 *  this thread will be alive while server is running, 
	 *  and checking if there is anything in the inQueue
	 */
		thread = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {
					try{
						Thread.sleep(2000);
						
						/*
						 * This is sync method.
						 * if inQueue is empty, delaying for 2 seconds and checking inQueue again
						 * if there is something to poll from queue and 
						 * making rmi call to break the cypher text
						 * and populates the outMap
						 */
						/*
								Job job;
								while ((job = inQueue.poll()) != null) {
									try {
										job.setPlainText(new String(
												RMICallVigenereBreaker.getVigenereBreaker().decrypt(job.getCypherText(), job.getMaxKeyLength())
												));
										outMap.put(job.getJobId(), job.getPlainText());
										
									}catch (Exception e) {
										throw new RuntimeException(e.getMessage(), e);
									}
								}
						*/
						
								
						/*
						 * This is async method.
						 * if we have something in inQueue, we poll and pass it
						 * to a new thread, that will do RMI call to break cypher text
						 * and populates the outMap
						 */
						
								
								Job job;
								while ((job = inQueue.poll()) != null) {
									try {
										job.setProcessing();
										new Thread(new AsyncRmiRequest(job)).start();
										
									}catch (Exception e) {
										throw new RuntimeException(e.getMessage(), e);
									}
								}
								
						
						
					}catch (InterruptedException e) {
						return;
					}
					
				}
				
			}
			
		});
		thread.start();
		
	}
	
}
