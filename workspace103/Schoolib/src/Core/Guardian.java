package Core;
import Core.Clients;
import Core.Requests.RS;
import connections.Message;
import connections.MessageRealServer;
import connections.Server;
import connections.ServerReal;
 
 
public class Guardian implements Runnable {
	private Server 			meS;
	private Requests 		R;
	private boolean 		Stop=false;
	
	private boolean 		Busy=false;
	private Message 		MesServing;
	
	//-----------------------------------------------------------
	public Guardian (Server Srv,Requests Req){
		meS	=	Srv;
		R	=	Req;
		System.out.println("guardian operativo");
	}
	//-----------------------------------------------------------
	
	public void run() {
		while(!Stop){
			
			while (isBusy()){}	//attesa query in esecuzione...
			
			try {
				//System.out.println("guardian dorme");
				//Thread.sleep(5000);
				//System.out.println("guardian riprende");	

			Val_BL();	// prima valutazione, le altre vengono richiamate se necessario
	
			} 	catch (Exception e) {
				System.out.println("guardian problemi");
				}
		}
	}


//Procedure di Valutazione
	
	//------------------------------------------------------------
	public void Val_BL() throws InterruptedException{	
		int x;
		if (R.getBL().getWr()>0){		//richieste in attesa per BL
			
				if(R.getLastserved()!=Requests.RS.BL){	//ultima richiesta servita diversa da 	BL
						ServeBL(0);				//SERVITA mod PRIMA RICHIESTA BL					
				}else{
						x = R.getBLcs();		//						
						if (x < 5){				//servite continuativamente MENO di 5 BL
								ServeBL(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 5 BL ( provo altro )
								
								R.setBLcs(4);	//decremento conteggio BL
												//al prossimo turno verra servita
								
								Val_BR();		//altre valutazioni a partire da BR
						}		
				}		
		}else{								//nessuna richiesta in attesa per BL
				Val_BR();
		}
		
	}
	//------------------------------------------------------------
	public void Val_BR() throws InterruptedException{	
		int x;
		if (R.getBR().getWr()>0){		//richieste in attesa per BR
			
				if(R.getLastserved()!=Requests.RS.BR){	//ultima richiesta servita diversa da 	BR
						ServeBR(0);				//SERVITA mod PRIMA RICHIESTA BR					
				}else{
						x = R.getBRcs();		//						
						if (x < 5){				//servite continuativamente MENO di 5 BR
								ServeBR(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 5 BR ( provo altro )
								
								R.setBRcs(4);	//decremento conteggio BR
												//al prossimo turno verra servita
								
								Val_AL();		//altre valutazioni a partire da AL
						}		
				}		
		}else{								//nessuna richiesta in attesa per BR
				Val_AL();
		}		
	}
	//------------------------------------------------------------
	public void Val_AL() throws InterruptedException{	
		int x;
		if (R.getAL().getWr()>0){		//richieste in attesa per AL
			
				if(R.getLastserved()!=Requests.RS.AL){	//ultima richiesta servita diversa da 	AL
						ServeAL(0);				//SERVITA mod PRIMA RICHIESTA AL					
				}else{
						x = R.getALcs();		//						
						if (x < 3){				//servite continuativamente MENO di 5 AL
								ServeAL(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 3 AL ( provo altro )
								
								R.setALcs(2);	//decremento conteggio AL
												//al prossimo turno verra servita
								
								Val_AR();		//altre valutazioni a partire da AR
						}		
				}		
		}else{								//nessuna richiesta in attesa per AL
				Val_AR();
		}
	}
	//------------------------------------------------------------
	public void Val_AR() throws InterruptedException{	
		int x;
		if (R.getAR().getWr()>0){				//richieste in attesa per AR
			
				if(R.getLastserved()!=Requests.RS.AR){	//ultima richiesta servita diversa da 	AR
						ServeAR(0);				//SERVITA mod PRIMA RICHIESTA AR					
				}else{
						x = R.getARcs();		//						
						ServeAR(x);				//SERVITA mod CONTINUA...						
				}		
		}else{								//nessuna richiesta in attesa per AR
											// RIPARTE CONTROLLO
		}
	}	
	//-----------------------------------------------------------

//Procedure di Gestione Richieste	
	
	public void ServeBL(int cs) throws InterruptedException{		
		R.incBLcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBRcs(0);
			R.setARcs(0);
			R.setALcs(0);
			R.setLastserved(Requests.RS.BL);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getBL().take();//decrementato BLw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
		
	}
	public void ServeBR(int cs) throws InterruptedException{
		R.incBRcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setARcs(0);
			R.setALcs(0);
			R.setLastserved(Requests.RS.BR);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getBR().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
		
	}
	public void ServeAR(int cs) throws InterruptedException{
		R.incARcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setALcs(0);
			R.setLastserved(Requests.RS.AR);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getAR().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}
	public void ServeAL(int cs) throws InterruptedException{
		R.incALcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setARcs(0);
			R.setLastserved(Requests.RS.AL);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getAL().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}

	public boolean isBusy() {
		return Busy;
	}
	public void setBusy(boolean busy) {
		Busy = busy;
	}
	public Message getMesServing() {
		return MesServing;
	}
	public void setMesServing(Message mesServing) {
		MesServing = mesServing;
	}

}
