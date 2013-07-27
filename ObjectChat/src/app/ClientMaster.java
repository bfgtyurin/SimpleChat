package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import app.Negotiator.ClientStatus;

public class ClientMaster implements Runnable {
	private static ClientMaster instance;
	private Socket chatSocket;
	private ObjectOutputStream objectWriter;
	private Negotiator negotiatorInstance;
	Thread readerThread;
	
	private ClientMaster() {		
	}
	
	@Override
	public void run() {
		this.negotiatorInstance = Negotiator.getInstance();
		setUpConnection();
	}

	public void setUpConnection() {
		try {
			chatSocket = new Socket(negotiatorInstance.getInternetAddress(), 1133);
			if (chatSocket != null) {
				createReaderThread();
				negotiatorInstance.clientStatus = ClientStatus.CONNECTED;
				negotiatorInstance.drawGuiStatus();
			}
		} catch (UnknownHostException e) {
			ClientErrorHandler.connectionError();
			e.printStackTrace();
		} catch (IOException e) {
			ClientErrorHandler.connectionError();
			e.printStackTrace();
		}
	}
	
	public void closeStreams() {
		try {
			chatSocket.close();
			readerThread = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createReaderThread() throws IOException {
		readerThread = new Thread(new Reader());
		readerThread.start();
		objectWriter = new ObjectOutputStream(chatSocket.getOutputStream());
		negotiatorInstance.sendConnectedMessage();		
	}
	
	public void writeObject(Message message) {
		try {
			objectWriter.writeObject(message);
			objectWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class Reader implements Runnable {
		ObjectInputStream objectReader;		
		public void readIncomming() {
			try {
				objectReader = new ObjectInputStream(chatSocket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object objectFromServer;
			try {
				while ((objectFromServer = objectReader.readObject()) != null) {
					Message currentMessage;
					currentMessage = (Message) objectFromServer;
					negotiatorInstance.setCurrentMessage(currentMessage);
					negotiatorInstance.handleMessage(currentMessage);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
				readIncomming();		
		}		
	}
	
	public static ClientMaster getInstance() {
		if (instance == null) instance = new ClientMaster();
		return instance;
	}
}
