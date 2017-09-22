package Check;

import javax.swing.JOptionPane;

public class PopUp
{

	public static void infoBox(String infoMessage)
	{		
	    JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void warningBox(String warningMessage)
	{		
	    JOptionPane.showMessageDialog(null, warningMessage, "Attenzione", JOptionPane.WARNING_MESSAGE);
	}
	
	public static void errorBox(String errorMessage)
	{		
	    JOptionPane.showMessageDialog(null, errorMessage, "Errore", JOptionPane.ERROR_MESSAGE);
	}
		    
	public static boolean confirmBox()
	{
	    if (JOptionPane.showConfirmDialog(null, "Sei sicuro ?", "Richiesta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
	}
}
