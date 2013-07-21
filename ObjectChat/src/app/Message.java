package app;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

	private static final long serialVersionUID = 2010703839412193699L;
	private String name = null;
	private String content = null;	
	private String userList = null;
	MessageType messageType;
	
	enum MessageType {
		CONNECTED, DISCONNECTED, CHANGENAME, NORMAL
	}
	
	public Message() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
