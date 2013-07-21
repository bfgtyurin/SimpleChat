package app;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ServerClientHandler implements Runnable {
	Socket clientSocket;
	InputStreamReader inputStreamReader;
	ObjectInputStream objectReader;	
	Message dataMessage;
	String systemContent;
	String content;
	Server serverInstance;	

	public ServerClientHandler(Socket clientSocket, Server serverInstance) {
		this.clientSocket = clientSocket;
		this.serverInstance = serverInstance;
	}
	
	public void handleMessage (Message dataMessage) {
		switch (dataMessage.messageType) {
		case CONNECTED:
			getTimeInMinutesAndSeconds();
			serverInstance.userArrayList.add(dataMessage.getName());								
			dataMessage.setUserList(serverInstance.getStringFromArrayList());
			content = "<b>" + dataMessage.getName() + "</b>" + " entered the chat";
			dataMessage.setContent(getTimeInMinutesAndSeconds() + content);
			serverInstance.objectTellEveryone(dataMessage);
			System.out.println("Server CONNECTED CASE: " + content);
			break;
		case CHANGENAME:
			break;
		case DISCONNECTED:
			serverInstance.userArrayList.remove(dataMessage.getName());						
			dataMessage.setUserList(serverInstance.getStringFromArrayList());
			content = getTimeInMinutesAndSeconds() + "<b>" + dataMessage.getName() + "</b>" + " left the chat";
			dataMessage.setContent(content);
			serverInstance.objectTellEveryone(dataMessage);
			break;
		case NORMAL:
			content = dataMessage.getContent();
			dataMessage.setContent(getTimeInMinutesAndSeconds() + content);
			System.out.println("Message on server is: " + dataMessage.getContent());
			serverInstance.objectTellEveryone(dataMessage);
			break;		
		}
	}
	
	public String getTimeInMinutesAndSeconds() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		String strTime = "[" + simpleDateFormat.format(new Date()) + "] ";
		System.out.println(strTime);
		return strTime;
	}

	public void run() {
		try {
			objectReader = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object messageFromClient = null;
		try {
			while ((messageFromClient = objectReader.readObject()) != null) {
				dataMessage = new Message();
				dataMessage = (Message) messageFromClient;
				handleMessage(dataMessage);
				System.out.println("SERVER" + serverInstance.userArrayList);				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
