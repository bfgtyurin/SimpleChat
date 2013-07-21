package app;

import javax.swing.JOptionPane;


public class ClientErrorHandler {
	
	public static void connectionError() {
		JOptionPane.showMessageDialog(Gui.getInstance().getMainFrame(), "Could't connect", "Bad ip address or server is down", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void nameIsEmptyError () {
		JOptionPane.showMessageDialog(Gui.getInstance().getMainFrame(), "Please enter your name", "Name filed is empty", JOptionPane.ERROR_MESSAGE);
	}

}
