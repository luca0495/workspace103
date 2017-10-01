package connections;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Core.Clients;
import Core.Guardian;
import Core.Requests;
import Core.SearchFor;
import gui.SystemClientStub;
import gui.SystemServerSkeleton;

import database.*;

//ver 2017 03 29 v1
public class ServerReal extends ServerSkeleton {
		private 	SystemServerSkeleton 	meS;
		private 	Server					Srv;
		private 	Guardian				GpG;
		private 	Requests				Req;		
		private 	String 					mSg;	
		
		//***************************************************************************
		public 		Boolean					Go;
		//***************************************************************************
		
		private 	MessageBack				mSgB;
		
	//Costruttore----------------------------------------------------------------------
	public ServerReal(Socket socket,Server SrvRif) throws Exception{
		super(socket);
		setSrv(SrvRif);
		super.set_meServer(this);
		// Ottiene i riferimenti di Request e Guardian dal Server
		setGpG(Srv.getG());
		setReq(Srv.getR());	
		
		/*TODO ELIMINA FINESTRE DI SYSTEMA PER TESTAGGIO */
		setMeS(new SystemServerSkeleton(this));
		getMeS().getFrame().setVisible(true);	
		
		Srv.setSrvconnINC();		//	INCrease Server Connections counter
		Srv.getMeG().addMsg(mSg="numero connessioni attive :"+ Srv.getSrvconn());	
	}	
	//Costruttore----------------------------------------------------------------------
	
	//Connections
	@Override
	public MessageBack connection(Message msg) throws RemoteException, InterruptedException {	
				System.out.println("REAL SERVER :> GESTISCO RICHIESTA CONNNECTION");
		MessageBack x = new MessageBack();
		switch (msg.getCommand()) {		
			case ConnTEST:
				//System.out.println(mSg = "REALServer:> TEST connessione richiesto ");
				getMeS().addMsg(mSg);				
				x.setText(new String ("SRV - Connessione OK"));					
				break;												
			case ConnSTOP:			
				//System.out.println(mSg = "REALServer:> CHIUSURA connessione richiesta ");
				getMeS().addMsg(mSg);
				x.setText(new String ("SRV - chiudo socket tra 5 secondi..."));			
				//super._socket.close();
				//Server.setSrvconn(Server.getSrvconn() - 1);	
				System.out.println("REALServer:> attuale numero connessioni : "+ Server.getSrvconn() +"\n"); 	
				System.out.println("REALServer:> chiusura socket...");
				break;		
			default:
				break;
		}	
		return x;
	}
	
	//test
	@Override
	public MessageBack test (Message msg) {	
				System.out.println( "REAL SERVER :> GESTISCO RICHIESTA ... ... ... test "+
									""+msg.getCommand());
		MessageBack x = new MessageBack();
				
		
		
		switch (msg.getCommand()) {		
			case ConnTEST:
				//System.out.println(mSg = "REALServer:> TEST connessione richiesto ");
				getMeS().addMsg(mSg);				
				x.setText(new String ("SRV - Connessione OK"));					
				break;												
			case ConnSTOP:			
				//System.out.println(mSg = "REALServer:> CHIUSURA connessione richiesta ");
				getMeS().addMsg(mSg);
				x.setText(new String ("SRV - chiudo socket tra 5 secondi..."));			
				//super._socket.close();
				//Server.setSrvconn(Server.getSrvconn() - 1);	
				System.out.println("REALServer:> attuale numero connessioni : "+ Server.getSrvconn() +"\n"); 	
				System.out.println("REALServer:> chiusura socket...");
				break;		
			// era mancante...
			case tableExistBook:
				System.out.println("REAL SERVER :> Gestisco RICHIESTA :> tableExistBook ");
				
					try {
						ChkDBandTab.tableExistBook();
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> CHECK TABLE book Exist :> OK"));	
					} catch (SQLException e) {
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> CHECK TABLE book Exist :> NG..."));
						System.out.println("problemi con controllo tabella Book");
						e.printStackTrace();
					}
				
				break;
			// 	
			case tableExistLoans:
				System.out.println("REAL SERVER :> Gestisco RICHIESTA :> tableExistLoans ");
				
					try {
						ChkDBandTab.tableExistLoans();
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> CHECK TABLE Loans Exist :> OK"));	
					} catch (SQLException e) {
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> CHECK TABLE Loans Exist :> NG..."));
						System.out.println("problemi con controllo tabella Loans");
						e.printStackTrace();
					}
				
				break;
				//
			case tableExistPerson:
				System.out.println("REAL SERVER :> Gestisco RICHIESTA :> tableExistPerson ");
				
					try {
						ChkDBandTab.tableExistPerson();
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> CHECK TABLE Person Exist :> OK"));	
					} catch (SQLException e) {
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> CHECK TABLE person Exist :> NG..."));
						System.out.println("problemi con controllo tabella Person");
						e.printStackTrace();
					}
				
				break;
				
				
			default:
				System.out.println("REAL SERVER :> Gestisco RICHIESTA ... ... ... ???? ");
				break;
		}	
		return x;
	}
	
	
	
	
	
	
	
	@Override
	public MessageBack 	visualizza(Message Mes) {
	// chiamata diretta al dbManager
		MessageRealServer M = this.MessageEncapsulation(Mes);
		MessageBack AnswerM = null;
		//Query DIRETTA su dbManager
		
		/*TODO PREPARA Answer*/
		
		return AnswerM;
	}
	
	
	@Override
	public MessageBack 	modifica(Message Mes) {
	// accodamento richiesta su Requests gestita da Guardian 	
		MessageRealServer M = this.MessageEncapsulation(Mes);
		MessageBack Answer = null;
		
		
		switch (M.getMsg().getUType()) {			// estrae tipo di utente da Message 
		
		case Librarian :
			if (M.getMsg().getCommand().getTarget()== SearchFor.Account ){	//AL
				try {
					Req.getAL().put(M);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				while (!Go){	}	//Attesa del turno...
				//TODO
				
				// Query
				// OpenConnection
				// Execute
				
				GpG.setBusy(false);
				return Answer;
				
			}else{
				if (M.getMsg().getCommand().getTarget()== SearchFor.Book ){	//BL
					try {
						Req.getBL().put(M);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
					while (!Go){	}	//Attesa del turno...
					//TODO
					// Query
					
					
					GpG.setBusy(false);
					return Answer;
					
				}else{
					
					// errore
					System.out.println("comando indefinito");
				}
					
					
			}
			break;
			
		case Reader :
			if (M.getMsg().getCommand().getTarget()== SearchFor.Account ){	//AR
				try {
					Req.getAR().put(M);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while (!Go){	}	//Attesa del turno...
				
				// Query
				
				
				GpG.setBusy(false);
				return Answer;
				
			}else{
				if (M.getMsg().getCommand().getTarget()== SearchFor.Book ){	//BR
					try {
						Req.getBR().put(M);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					while (!Go){	}	//Attesa del turno...
					
					// Query
					
					
					GpG.setBusy(false);
					return Answer;
					
				}else{
					
					// errore
					System.out.println("comando indefinito");
				}		
			}
			break;		
		default:
			break;
		}
		return null;
	}

	/*
	@Override
	public MessageBack 	testConnectionSS() {
		MessageBack x = new MessageBack();
			System.out.println(mSg = " Richiesto TEST connessione ");
			getMeS().addMsg(mSg);							
			x.setText(new String ("SRV - Connessione OK"));
			return x;
	}
	*/
	/*
	@Override
	public void	closeConnectionSS() {
		MessageBack x = new MessageBack();		
		try{
			x.setText(new String ("SRV - chiudo socket tra 5 secondi..."));
			Thread.sleep(5000);
			super._socket.close();
			//Server.setSrvconn(Server.getSrvconn() - 1);
			System.out.println("attuale numero connessioni : "+ Server.getSrvconn() +"\n");	
		}catch(IOException | InterruptedException e){	
			x.setText(new String ("SRV - problemi alla chiusura del socket "));
		} 
	}	
	*/
	
	//Test
	@Override
	public Message 	metodotest1(){
			Message x = new Message(Core.Commands.Test1);
			
			System.out.println(mSg = " Ritorno metodo test 1 ...");
			getMeS().addMsg(mSg);		
			String xx = "test1 stringax";
			x.setText(xx);
			
			return x;
		}	
	@Override
	public Message 	metodotest2(){
			Message x = new Message(Core.Commands.Test2);
			
			System.out.println(mSg = " Ritorno metodo test 2 ...");
			getMeS().addMsg(mSg);		
			String xx = "test2 stringax";
			x.setText(xx);
			
			return x;
		}	
	@Override	
	public Message 	metodotest3(){
			Message x = new Message(Core.Commands.Test3);
						
			String xx = null;
			System.out.println(mSg=" Ritorno metodo test 3 ... creazione tabella");
			getMeS().addMsg(mSg);
			
			// test creazione tabella su db
			
			try {
				String url ="jdbc:postgresql://localhost:5432/Dexodb";
				String username="postgres";
				String password="postgres";
				Connection con = DriverManager.getConnection(url,username,password);
				Statement stmt= con.createStatement();		
				
				DBmanager.openConnection();
				System.out.println(mSg = " connessione al db OK ...");
				getMeS().addMsg(mSg);	
			// risultato OK
			xx = "CONNESSIONE DB OK";
				
				// CREA TABELLA UTENTI X TEST
				String Q = "CREATE TABLE Utenti (  id bigint primary key , nome character varying(20) NOT NULL, cognome character varying(20) NOT NULL)";
				
				DBmanager.execute(Q);
				DBmanager.closeConnection();
				
				
				
			} catch (SQLException e) {
				System.out.println(mSg = " problemi connessione al db ...");
				getMeS().addMsg(mSg);
			// risultato NG
			xx = "non connesso al DB";
				e.printStackTrace();
			}
			
			x.setText(xx);
			
			return x;
		}
	//
	public Server getSrv() {
		return Srv;
	}
	public void setSrv(Server srv) {
		Srv = srv;
	}
	public SystemServerSkeleton getMeS() {
		return meS;
	}
	public void setMeS(SystemServerSkeleton meS) {
		this.meS = meS;
	}

	public Guardian getGpG() {
		return GpG;
	}
	public void setGpG(Guardian gpG) {
		GpG = gpG;
	}
	public Requests getReq() {
		return Req;
	}
	public void setReq(Requests req) {
		Req = req;
	}
	
	
	
	/*public MessageBack	DBExist					(){
		MessageBack mb = null;
				
		try {
			mb = ChkDBandTab.DBExist()	;
		} catch (SQLException 	e) {
								e.printStackTrace();
			mb.setText("SRV Problemi...");
		}
		return mb;
	
	};
	*/
	
	// *************************************************************	
	public MessageRealServer MessageEncapsulation (Message msg){
		MessageRealServer mrs=new MessageRealServer(msg, this);
		return mrs;
	}
	// *************************************************************

}
