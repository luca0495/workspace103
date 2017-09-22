package Core;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import connections.Message;
import connections.MessageRealServer;

	public class RequestsList {
		
		
		private Queue<MessageRealServer>ReqList = new LinkedList<MessageRealServer>();
		private int maxRequests;
		private int wr=0;						// wr = Waiting Requests
		
	
		public RequestsList(int maxR){
			this.maxRequests=maxR;			
		}
		public RequestsList(){
		}
	
		public synchronized MessageRealServer take() throws InterruptedException{			
			
			while(ReqList.size()<1){
				wait();
			}	// forse inutile - Guardian non resta in wait su nessuna RL
			
			MessageRealServer obj=ReqList.remove();
			decWr();
			
			notifyAll();
			return obj;
		}
		
		public synchronized void put(MessageRealServer objName) throws InterruptedException{
			incWr();
				
			while(ReqList.size()>=maxRequests){
				System.out.println(" limit reached , wait... ");
				wait();
			}
			ReqList.add(objName);
				System.out.println(" Added request : "+ objName );
				notifyAll();	// forse inutile - Guardian non resta in wait su nessuna RL		
		}
		
		//operazioni su wr
		public void incWr() {
			wr++;
		}		
		public void decWr() {
			wr--;
		}
		public int getWr() {
			return wr;
		}
		public void setWr(int wr) {
			this.wr = wr;
		}

	}
