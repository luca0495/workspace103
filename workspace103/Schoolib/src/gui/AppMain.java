package gui;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Core.Clients;
import Core.Commands;
import connections.Client;
import connections.Message;
import connections.MessageBack;
import database.ChkDBandTab;

public class AppMain extends SL_JFrame  {
	
	private Client  		me ;
	
	private AppReader 		c;
	private AppLibrarian 	a;
	
	private JFrame 			frame;
	private JTextField 		txtRicerca;
	public 	JPanel 			ac = new JPanel();
	private JTextField 		text;
	
	/**
	 * Create the application.
	 */
	
	public AppMain() {
		initialize();		
		super.SL_Type = AppType.AppMain;	
		super.SL_Client		=null;
		addMsg("inizializzazione completata");	
	}

	public AppMain(Client x) {
		//me = x;
		//me.setActW(this);
		//me.setCliType(Clients.Guest);
		
		super.SL_Client 	= x;
		super.SL_Client.setActW(this);
		super.SL_Client.setCliType(Clients.Guest);
		super.SL_Type	=	AppType.AppMain;
		me=super.SL_Client;
		
		initialize();
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());

		getFrame().setTitle("Schoolib");
		getFrame().setBounds(100, 100, 890, 540);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panelLog = new JPanel();
		getFrame().getContentPane().add(panelLog);
		panelLog.setLayout(null);
		
		ImageIcon backgroundImage0 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Background0.jpg")));
		ImageIcon backgroundImage1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Background1.jpg")));
		
		JButton btnNewButton = new JButton("check DB exist");
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
		
				try {
					
				System.out.println("GUI :> comando inviato dalla gui main");//test tabelle iniziale
	
				// book					
					//ChkDBandTab.tableExistBook();
					MessageBack back = me.Request(Commands.tableExistBook); // me == Client associato alla GUI
					System.out.println("GUI :> risposta dal DB : "+back.getText());					
				// Person	
					// ChkDBandTab.tableExistPerson();
					MessageBack back2 = me.Request(Commands.tableExistPerson);
					System.out.println("GUI :> risposta dal DB : "+back2.getText());	
				//  Loans	
					// ChkDBandTab.tableExistLoans();
					MessageBack back1 = me.Request(Commands.tableExistLoans);    
					System.out.println("GUI :> risposta dal DB : "+back1.getText());

	
				} catch (Exception e) {
					
					e.printStackTrace();	
				}	
			}
		});
		
		JButton btnNewButton_2 = new JButton("connection CLOSE");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				me.closeconn();
				
			}
		});
		btnNewButton_2.setBounds(581, 380, 195, 23);
		panelLog.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("connection TEST");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				me.testconn();
				
			}
		});
		btnNewButton_1.setBounds(581, 346, 195, 23);
		panelLog.add(btnNewButton_1);
		btnNewButton.setBounds(31, 63, 167, 23);
		panelLog.add(btnNewButton);
		
		setText(new JTextField());
		getText().setBounds(208, 54, 628, 41);
		panelLog.add(getText());
		getText().setColumns(10);
		
		
		// PANEL LOG 
		
		txtRicerca = new JTextField();
		txtRicerca.setHorizontalAlignment(SwingConstants.CENTER);
		txtRicerca.setText("Ricerca Libro");
		txtRicerca.setBounds(267, 11, 259, 41);
		panelLog.add(txtRicerca);
		txtRicerca.setColumns(10);
		
		
		
		JButton btnAdmin = new JButton("Admin");
		
		btnAdmin.addMouseListener(new MouseAdapter() {
			 @Override
				public void mousePressed(MouseEvent arg0) {
					
				 
				 		EventQueue.invokeLater(new Runnable() {
							public void run() 
							{
								
							 try 
							{
							 AppLibrarian ak = new AppLibrarian(getFrame());
						    } 
							catch (Exception e) 
							{
							e.printStackTrace();
							}
								
						}	
			
					 });    
				}
		
		 });
		btnAdmin.setBounds(581, 227, 107, 23);
		panelLog.add(btnAdmin);
		
		
		JButton btnRegistrazione = new JButton("Registrazione");
		btnRegistrazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRegistrazione.addMouseListener(new MouseAdapter() {
		 @Override
			public void mousePressed(MouseEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
						public void run() 
						{
						 try 
						{
						 AppReader al = new AppReader(getFrame(),me);
					    } 
						catch (Exception e) 
						{
						e.printStackTrace();
						}
					
					}	
		
				 });    
			}
	 });
		
		btnRegistrazione.setBounds(124, 113, 154, 23);
		panelLog.add(btnRegistrazione);
								
		JButton btnEsci = new JButton("Esci");
		btnEsci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnEsci.setBounds(212, 400, 129, 23);
		panelLog.add(btnEsci);
		
		JLabel lblBackgound1 = new JLabel();
		lblBackgound1.setBounds(0, 0, 884, 511);
		lblBackgound1.setIcon(backgroundImage1);
		lblBackgound1.setBorder(null);
		panelLog.add(lblBackgound1);
	}
	public JTextField getText() {
		return text;
	}
	public void setText(JTextField text) {
		this.text = text;
	}
	
	/**
	 * Launch the application.
	 */
	
	@Override
	public void addMsg(String msg){
		text.setText(msg);	
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				if (me!=null){
				me.closeconn();
				}
			}
		});
	}

	
	
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Client x = new Client();
					
					AppMain window = new AppMain(x);					
					window.frame.setVisible(true);					
					
					new Thread(x).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
