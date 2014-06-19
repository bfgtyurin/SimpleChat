package app;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server implements Runnable {	
    private static Server instance;
    ServerSocket serverSock;
    Socket clientSocket;
    //InputStreamReader inputStreamReader;

    ArrayList <ObjectOutputStream> objectOutputStreams;
    ObjectOutputStream objectWriter;
    ArrayList <String> userArrayList;

    Thread clientHandlerThread;

    public Server() {
        userArrayList = new ArrayList<String>();
    }

    public void objectGo() {
        objectOutputStreams = new ArrayList<ObjectOutputStream>();
        try {
            serverSock = new ServerSocket(1133);
            while(true) {
                clientSocket = serverSock.accept();
                objectWriter = new ObjectOutputStream(clientSocket.getOutputStream());
                objectOutputStreams.add(objectWriter);
                clientHandlerThread = new Thread(new ServerClientHandler(clientSocket, this));
                clientHandlerThread.start();
                System.out.println("got a connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void objectTellEveryone(Object dataMessage) {
        Iterator<ObjectOutputStream> iterator = objectOutputStreams.iterator();
        while (iterator.hasNext()) {
            objectWriter = (ObjectOutputStream) iterator.next();
            try {
                objectWriter.writeObject(dataMessage);
                objectWriter.flush();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        objectGo();
    }

    public void closeConnection() {
        try {
            serverSock.close();
            clientSocket.close();
            objectWriter.close();
            clientHandlerThread = null;
            userArrayList.clear();
            objectOutputStreams.clear();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Server getInstance() {
        if (instance == null) instance = new Server();
        return instance;
    }

    public String getStringFromArrayList() {
        return userArrayList.toString();
    }


}
