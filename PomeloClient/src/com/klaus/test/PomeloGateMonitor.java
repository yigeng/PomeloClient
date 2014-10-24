package com.klaus.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONObject;

import com.zvidia.pomelo.exception.PomeloException;
import com.zvidia.pomelo.protocol.PomeloMessage.Message;
import com.zvidia.pomelo.websocket.OnDataHandler;
import com.zvidia.pomelo.websocket.OnErrorHandler;
import com.zvidia.pomelo.websocket.OnHandshakeSuccessHandler;
import com.zvidia.pomelo.websocket.PomeloClient;

public class PomeloGateMonitor {
	
    private static String uri = null, key = "alfxkwld";;
	private static PomeloClient client = null;
	
    public static void main(String[] args) throws URISyntaxException, PomeloException {
    		uri = "http://" + args[0];
        	System.out.println("Requesting gate server at "+ uri);
        	System.out.println("----------------");

        	gate();
    }
	
    public static void gate() {
        try {
            client = new PomeloClient(new URI(uri), key);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        client.setOnErrorHandler(new OnErrorHandler() {
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        client.setOnHandshakeSuccessHandler(new OnHandshakeSuccessHandler() {
            @Override
            public void onSuccess(final PomeloClient client, JSONObject resp) {
                JSONObject msg = new JSONObject();
                System.out.println();
                try {
                    for(int i = 0; i < 1; i++) {
                        client.request("admin.gate.hybrid", msg.toString(), new OnDataHandler() {
                            @Override
                            public void onData(Message message) throws PomeloException {    
                                System.out.println(message.getBodyJson().toString());
                                System.exit(0);
                            }
                        });
                    }
                }
                catch (PomeloException p){
                    p.printStackTrace();
                }
            }
        });
        client.connect();
    };
}
