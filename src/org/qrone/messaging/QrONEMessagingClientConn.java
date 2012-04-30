package org.qrone.messaging;

import java.util.Iterator;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.qrone.xmlsocket.XMLSocket;
import org.qrone.xmlsocket.event.XMLSocketListener;
import org.w3c.dom.Document;

public class QrONEMessagingClientConn implements XMLSocketListener{
	
	private QrONEMessagingServer server;
	private XMLSocket xmlsocket;
	
	public QrONEMessagingClientConn(QrONEMessagingServer server, XMLSocket xmlsocket) {
		this.server = server;
		this.xmlsocket = xmlsocket;
	}

	@Override
	public void onConnect(boolean success) {
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

	@Override
	public void onTimeout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onData(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			
			String join = obj.getString("join");
			String ticket = obj.getString("ticket");
			
			Set<QrONEMessagingClientConn> set = server.getTargets(join);
			if(set != null){
				for (Iterator<QrONEMessagingClientConn> iter = set.iterator(); iter.hasNext();) {
					iter.next().xmlsocket.send(data);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
