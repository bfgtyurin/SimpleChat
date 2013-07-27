package app;

import java.util.ArrayList;

import app.Message.MessageType;

public class Negotiator {
	private static Negotiator instance;
	private Gui gui;
	private Message message;
	private ArrayList <String> userArrayList;
	private ClientMaster clientMaster;
	private Thread serverThread;
	private Thread clientThread;
	private Server serverInstance;
	ClientStatus clientStatus;
	ConfigBuilder config;

    private static final String OFFLINE_IMAGE = "/resources/offline.png";
    private static final String ONLINE_IMAGE = "/resources/online.png";

	enum ClientStatus {
		CONNECTED, DISCONNECTED
	}
	
	private Negotiator() {
		this.gui = Gui.getInstance();		
		userArrayList = new ArrayList<String>();
		clientStatus = ClientStatus.DISCONNECTED;
        drawGuiStatus();
		config = new ConfigBuilder();
		setNameFromConfigFile();
		setAddressFromConfigFile();
	}
	
	public void setNameFromConfigFile() {
		String nameFromConfig = config.nameFromConfig();
		gui.getNameField().setText(nameFromConfig);
	}
	
	public void setAddressFromConfigFile() {
		String addressFromConfig = config.addressFromConfig();
		gui.getAddressField().setText(addressFromConfig);
	}
	
	public static Negotiator getInstance() {
		if (instance == null) instance = new Negotiator();
		return instance;
	}

	public String getInternetAddress() {
        return gui.getAddress();
	}
	
	public void startServer() {
		serverThread = new Thread(serverInstance = new Server());
		serverThread.start();
	}
	
	public void stopServer() {
		serverInstance.closeConnection();
		serverThread = null;
	}
	
	public void startClient() {
		message = new Message();
		this.clientMaster = ClientMaster.getInstance();
		clientThread = new Thread(clientMaster);
		clientThread.start();
		config.makeConfigFile(gui.getUserName(), gui.getAddress());
	}
	
	public void stopClient() {
		message = null;
		ClientMaster.getInstance().closeStreams();
		clientThread = null;
		clientStatus = ClientStatus.DISCONNECTED;
		drawGuiStatus();
	}
	
	public void drawGuiStatus() {
		switch(clientStatus) {
		case CONNECTED:
			gui.setStatusIcon(ONLINE_IMAGE);
			break;
		case DISCONNECTED:
			gui.setStatusIcon(OFFLINE_IMAGE);
			break;
		}
	}
	
	public void setCurrentMessage(Message currentDataMessage) {
		message = currentDataMessage;
	}
	
	public String getCurrentContent() {
		return message.getContent();
	}
	
	public String getCurrentUserList() {
		return message.getUserList();
	}

	public void handleMessage(Message message) {
		switch (message.messageType) {
		case CONNECTED:
			gui.isLink(getCurrentContent() + "\n");
			userArrayList.clear();
			makeUserArrayList(getCurrentUserList());
			gui.listModel.removeAllElements();					
			gui.setCurrentUserList(userArrayList);			
			break;
		case CHANGENAME:
			break;
		case DISCONNECTED:
			gui.isLink(getCurrentContent() + "\n");
			userArrayList.clear();
			makeUserArrayList(getCurrentUserList());
			gui.listModel.removeAllElements();					
			gui.setCurrentUserList(userArrayList);
			break;
		case NORMAL:
			gui.isLink(getCurrentContent() + "\n");
			break;	
		}		
	}
	
	public void makeUserArrayList(String userList) {
		userList = userList.replace('[', ' ');
		userList = userList.replace(']', ',');		
		int beginIndex = 1;
		int fromIndex = 0;
		int endIndex = userList.indexOf(",", fromIndex);
		while (userList.length() >= beginIndex) {			
			String element = userList.substring(beginIndex, endIndex);
			userArrayList.add(element);
			beginIndex = endIndex + 2;
			endIndex = userList.indexOf(",", beginIndex);
			System.out.println(element);
		}		
	}
	
	public void sendMessage(String messageFromGui) {
		message.setContent("<b>" + gui.getUserName() + "</b>" + ": " + messageFromGui);
		message.messageType = MessageType.NORMAL;
		clientMaster.writeObject(message);
	}
	
	public void sendConnectedMessage() {		
		message.setName(gui.getUserName());
		message.messageType = MessageType.CONNECTED;
		clientMaster.writeObject(message);
	}
	
	public void sendDisconnectedMessage() {
		if (ClientStatus.CONNECTED.toString().equals("CONNECTED")) {
			message.messageType = MessageType.DISCONNECTED;
			clientMaster.writeObject(message); 
		}
		
	}
}
