package org.qrone.messaging;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.qrone.util.Token;
import org.qrone.xmlsocket.XMLSocket;
import org.qrone.xmlsocket.XMLSocketServer;
import org.qrone.xmlsocket.event.XMLSocketListener;
import org.qrone.xmlsocket.event.XMLSocketServerListener;
import org.w3c.dom.Document;

public class QrONEMessagingServer implements XMLSocketServerListener{

	public static final int SERVER_PORT = 9645;
	
	private XMLSocketServer socketServer;
	private Map<String, Token> signmap
		= new HashMap<String, Token>();
	private Map<String, Set<QrONEMessagingClientConn>> map
		= new HashMap<String, Set<QrONEMessagingClientConn>>();
	
	public QrONEMessagingServer(){
		
		try {
			socketServer = new XMLSocketServer();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		socketServer.setEncoding("UTF-8");
		socketServer.addXMLSocketServerListener(this);
		socketServer.open(SERVER_PORT);
	}
	
	public void listen(int port){
		socketServer.open(SERVER_PORT);
	}

	@Override
	public void onOpen(boolean success) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onError(Exception e) {
		// TODO Auto-generated method stub
	}
	
	public Set<QrONEMessagingClientConn> getTargets(String groupId){
		return map.get(groupId);
	}
	
	public void setTarget(String groupId, QrONEMessagingClientConn conn){
		Set<QrONEMessagingClientConn> set = map.get(groupId);
		if(set == null){
			set = new HashSet<QrONEMessagingClientConn>();
			map.put(groupId, set);
		}
		
		set.add(conn);
	}

	@Override
	public void onNewClient(XMLSocket xmlsocket) {
		QrONEMessagingClientConn conn = new QrONEMessagingClientConn(this, xmlsocket);
	}
		
	public static void main(String[] args){
		QrONEMessagingServer server = new QrONEMessagingServer();
		server.listen(SERVER_PORT);
	}

}
