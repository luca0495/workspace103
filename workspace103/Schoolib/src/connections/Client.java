package connections;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


import Core.Clients;
import Core.Commands;
import Core.CommandsType;
import Core.SearchFor;

import java.awt.Font;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import gui.AppLibrarian;
import gui.AppReader;
import gui.AppMain;
import gui.SL_JFrame;
import gui.SystemClientStub;

public class Client implements Serializable, Runnable  {
	
	private 			SL_JFrame			ActW			=null;	//Active Window
	private 			Clients				ClientType;
	private 			ServerStub			srv;	
	
	private 			boolean 			stubok			=false;
	private 			boolean 			repeatconn		=true;
	private 			int 				repeatconnCount = 1;
	
	static private 		int 				conn;
	
	private 			String 				tc  ; 
	private 			Message				mess;
	private 			MessageBack			mSgBack;
	private 			String				mSg;
	
	public Client() throws Exception {		
		this.setCliType(Clients.Default);
		System.out.println("Creato 		 Client");
		System.out.println("CType:		"+getCliType());		
	}

//------------------------------------------------------------------------	
	@Override
	public void run() {		
		try {
			Logica();
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}
//------------------------------------------------------------------------
	public static void main(String[] args) throws Exception {			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client x  = new Client();				
					AppMain StartWindow = new AppMain(x);
					StartWindow.getFrame().setVisible(true);	
						
					System.out.println("creato start windows");
					StartWindow.addMsg("test");	
					new Thread(x).start();	//parte > Logica					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}	
//------------------------------------------------------------------------
	 void Logica() throws Exception {	
		
				while (isRepeatconn()) {		//---- Controllo continuo della connessione con il Server	
							try{									
										System.err.println(mSg = "CLI:> CType:"	+ getCliType());
										//System.err.println(mSg = "ActW:"	+ getActW());						
										//getActW().addMsg(mSg);			
								
										if (isStubok())  {	
											try {
											//	if ((mSg = srv.testConnectionSS().getText()).equals(new String ("SRV - Connessione OK"))){
									
													this.mSgBack = 	Request(Commands.ConnTEST);
													
													//TODO ELIMINA CONTROLLO ERRORE
													try {
														this.mSg 	= 	mSgBack.getText();														
													} catch (Exception e) {				
														e.printStackTrace();
														System.out.println("messaggio non letto");
													}
													//TODO ELIMINA CONTROLLO ERRORE							
													
													
													if (mSg.equals(new String ("SRV - Connessione OK"))){									
															getActW().addMsg(new String ("CLI:> Connection Test result"+mSg));	
												}else{
													setStubok(false);
															getActW().addMsg(new String ("CLI:> Connection Test result : NG"));	
												}											
											
											} catch (Exception e) {
			
												e.printStackTrace();
												System.out.println(mSg = "CLI :> srv stub ERROR ");	
												getActW().addMsg(mSg);		
												setStubok(false);
											}

											Thread.sleep(5000);// test conn every 10 sec
										}
										else {
											
											for (int i = 5;i>0;i--){
												try {Thread.sleep(1000);
												System.out.println(mSg = "CLI :> Verifica Stub NG \nTentativo "+getRepeatconnCount()+ " \nnuovo tentativo tra "+i+" secondi ");
												getActW().addMsg(mSg);
												} catch (Exception e) {
												}	
											}
											incRepeatconnCount();	
											srv  = new ServerStub(this);	
											}
							}catch (Exception e) {e.printStackTrace();} finally {}//server.closeConnection();}

				}	//while					
			}		//Logica
//------------------------------------------------------------------------
		public MessageBack Request(Commands cmd)throws IOException{			
			MessageBack Mb = new MessageBack();				
			System.out.println("CLI:> Request ricevuto :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI:>  nessuna connessione attiva , riprovare ");
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
	
	// **** Client crea Message	
			Message MsgSend = new Message(	
			cmd,						// Comando richiesto
			this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
			this.toString()				// id Client 
										);		
	// ****
					/*TODO CANCELLA MESSAGGI SYSTEMA */
					System.out.println("CLI :> Ricevuta richiesta invio comando per STUB");
					System.out.println("CLI :> comando: "+MsgSend.getCmd());
			try {	
					Mb = this.getSrv().SendRequest(MsgSend);	// SPEDISCE AL SRV [STUB] MESSAGE contenente COMMAND
			} 
			catch (IOException io){	
				io.printStackTrace();
				Mb.setText("CLI:>  collegamento mancante");						
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
				Mb.setText("CLI:>  class not found");	
			}	
		}
		
			//	***	gestione risultati	[Mb]		
															
			switch (cmd.getCommandType()) {				//   		Core\CommandsType	***   									
		//	****************************************************	Connections	   		***		
			case CONNECTION:										
						switch (cmd) {							
						case ConnSTOP: 					//   		Core\Commands		***
						
							break;
						case ConnTEST: 
							
							break;
						}
			break;
		//	****************************************************	Test		   		***	
			case TEST:
						switch (cmd) {							
						case DBExist:							
						
							break;
						case tableExistBook: 
							
							break;
						case tableExistLoans: 
							
							break;
						case tableExistPerson: 
							
							break;
						default:
							
							break;
						}
			break;
		//	****************************************************	Read		   		***			
			case READ:
						switch (cmd) {							
				//	book
						case BookREAD:							
						
							break;
				//	Person
						case UserREAD: 
									
							break;
				//	Loans
						case PrenotationREAD: 
							
							break;
						default:
							
							break;
						}				
			break;
		//	****************************************************	Change		   		***			
			case CHANGE:
						switch (cmd) {							
				//	book
						case BookUPDATE:							
						
							break;
						case BookDELETE: 
							
							break;
				//	Person
						case UserUPDATE: 
									
							break;
						case UserDELETE: 
									
							break;	
						
				//	Loans
						case Prenotation: 
							
							break;
						case BookGet: 
							
							break;
						case BookGiveback: 
							
							break;
						default:
							
							break;
						}
			break;
		//	****************************************************	Default		   		***			
			default:
				
			break;
		}									
		//************************************************************
		
			
			// RITORNA UN MESSAGE-BACK
			return Mb;
			
			
			
		}		//************************************************************
		
//------------------------------------------------------------------------		 
	// sys

		public Clients getCliType() {
			return ClientType;
		}
		public void setCliType(Clients cliType) {
			ClientType = cliType;
		}		
		boolean isStubok() {
		return stubok;
	}
		public void setStubok(boolean stubok) {
		this.stubok = stubok;
	}
		public boolean isRepeatconn() {
		return repeatconn;
	}
		public void setRepeatconn(boolean repeatconn) {
		this.repeatconn = repeatconn;
	}
		public int getRepeatconnCount() {
		return repeatconnCount;
	}
		public void setRepeatconnCount(int repeatconnCount) {
		this.repeatconnCount = repeatconnCount;
	}
		public void incRepeatconnCount(){
		this.repeatconnCount++;
	}
		public ServerStub getSrv() {
		return srv;
	}
		public void setSrv(ServerStub srv) {
		this.srv = srv;
	}	
		
	// comandi
		
	/*
	public String testconn (){
		String testcon1=null;
		testcon1 = srv.testConnectionSS().getText();
		return testcon1;
	}
	*/
		
	public MessageBack testconn(){
		//testcon1 = srv.testConnectionSS().getText();
		try {
			return this.mSgBack = 	Request(Commands.ConnTEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.mSgBack;
		
	}
	
	public MessageBack closeconn(){	
		//srv.closeConnectionSS();
		try {
			return this.mSgBack = 	Request(Commands.ConnSTOP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.mSgBack;
	}	
	
	
	
	
	/*
	
	// test
	public void metodo1 () throws IOException, ClassNotFoundException{		
			for (int i = 0 ; i< 10; i++){	
				System.out.print("CLIENT : cmd no " + i + " Richiedo test 1");				
			
			String M1 = this.getSrv().metodotest1().getText();
				getActW().addMsg(M1);
				System.out.println("CLIENT : Elemento ritornato: " + M1);	
			}
	}
	public void metodo2 () throws IOException, ClassNotFoundException{		
		
			System.out.print("CLIENT : cmd no  Richiedo test 2");				
			
			String M1 = this.getSrv().metodotest2().getText();
				getActW().addMsg(M1);
				System.out.println("CLIENT : Elemento ritornato: " + M1);	
		
	}	
	public void metodo3 () throws IOException, ClassNotFoundException{		
		
			System.out.print("CLIENT : cmd no  Richiedo test 3");				
			
			String M1 = this.getSrv().metodotest3().getText();
				getActW().addMsg(M1);
				System.out.println("CLIENT : Elemento ritornato: " + M1);	
	
	}
*/

	
/*	
	public SystemClientStub getMeG_Sys() {
		return meG_Sys;
	}
	public void setMeG_Sys(SystemClientStub meG_Sys) {
		this.meG_Sys = meG_Sys;
	}
	public Main getMeG_Main() {
		return meG_Main;
	}
	public void setMeG_Main(Main meG_Main) {
		this.meG_Main = meG_Main;
	}
	public AppLibrarian getMeG_Lib() {
		return meG_Lib;
	}
	public void setMeG_Lib(AppLibrarian meG_Lib) {
		this.meG_Lib = meG_Lib;
	}
	public AppReader getMeG_Rd() {
		return meG_Rd;
	}
	public void setMeG_Rd(AppReader meG_Rd) {
		this.meG_Rd = meG_Rd;
	}
	
*/	
	public SL_JFrame getActW() {
		return ActW;
	}
	public void setActW(SL_JFrame actW) {
		ActW = actW;
	}

}
