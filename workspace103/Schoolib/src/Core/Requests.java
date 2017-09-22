package Core;

public class Requests {
	
	private static int Max;
	private RequestsList BL;
	private RequestsList BR;
	private RequestsList AL;
	private RequestsList AR;
	
	// richieste servite continuativamente
	// cs = Countinuosly Served	
	private int BLcs=0;
	private int BRcs=0;
	private int ALcs=0;
	private int ARcs=0;
	
	private	Requests.RS lastserved;
	
	public enum RS {	//	Requests Served
		
		//COSTANTI ENUMERATIVE
		BL		("modify Book 	 by Librarian"	,	1,	0),
		BR		("modify Book 	 by Reader"		,	2,	0),
		AL		("modify Account by Librarian"	,	3,	0),
		AR 		("modify Account by Reader" 	,	4,	0) 
		;
		//campi
				public String Den;
				public int Priority;
				public int Records;				
		//costruttori
				private RS(String Den, int pri,int rec){
				this.Den = Den;	
				this.Priority = pri;
				this.Records=rec;
				}
		};	
		
	public Requests (int max){
		Max=max;
		
		setBL(new RequestsList());
		setBR(new RequestsList());
		setAL(new RequestsList());
		setAR(new RequestsList());
		
	}

	
public Requests.RS getLastserved() {
		return lastserved;
	}
	public void setLastserved(Requests.RS lastserved) {
		this.lastserved = lastserved;
	}

// Requests LISTS	
	public RequestsList getBL() {
		return BL;
	}
	public void setBL(RequestsList bL) {
		BL = bL;
	}
	public RequestsList getBR() {
		return BR;
	}
	public void setBR(RequestsList bR) {
		BR = bR;
	}
	public RequestsList getAL() {
		return AL;
	}
	public void setAL(RequestsList aL) {
		AL = aL;
	}
	public RequestsList getAR() {
		return AR;
	}
	public void setAR(RequestsList aR) {
		AR = aR;
	}

// richieste servite continuativamente
// cs = Countinuosly Served
	public int getBLcs() {
		return BLcs;
	}
	public void setBLcs(int bLser) {
		BLcs = bLser;
	}
	public int getBRcs() {
		return BRcs;
	}
	public void setBRcs(int bRser) {
		BRcs = bRser;
	}
	public int getALcs() {
		return ALcs;
	}
	public void setALcs(int aLser) {
		ALcs = aLser;
	}
	public int getARcs() {
		return ARcs;
	}
	public void setARcs(int aRser) {
		ARcs = aRser;
	}

	//incrementi
	public void incBLcs() {
		BLcs++;
	}
	public void incBRcs() {
		BRcs++;
	}
	public void incALcs() {
		ALcs++;
	}
	public void incARcs() {
		ARcs++;
	}	
}
