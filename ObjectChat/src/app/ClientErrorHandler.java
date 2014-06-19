package app;

import javax.swing.JOptionPane;


public class ClientErrorHandler {
    public static final String conErrObj = "Could't connect";
    public static final String conErrTitle = "Bad ip address or server is down";
    public static final String nameErrObj = "Please enter your name";
    public static final String nameErrTitle = "Name field is empty";

    public static void connectionError() {
        JOptionPane.showMessageDialog(Gui.getInstance().getMainFrame(), conErrObj, conErrTitle, JOptionPane.ERROR_MESSAGE);
    }

    public static void nameIsEmptyError () {
        JOptionPane.showMessageDialog(Gui.getInstance().getMainFrame(), nameErrObj, nameErrTitle, JOptionPane.ERROR_MESSAGE);
    }

}
