package com.klaus.test;

import com.zvidia.pomelo.exception.PomeloException;
import com.zvidia.pomelo.protocol.PomeloMessage.Message;
import com.zvidia.pomelo.websocket.OnDataHandler;
import com.zvidia.pomelo.websocket.OnErrorHandler;
import com.zvidia.pomelo.websocket.OnHandshakeSuccessHandler;
import com.zvidia.pomelo.websocket.PomeloClient;

import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class PomeloTest2 {
    private String uri = null, key = null;
	private PomeloClient client = null;
    public PomeloTest2(String uri, String key){
        this.uri = uri;
        this.key = key;
    }

    public void chat(){
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
            public void onSuccess(final PomeloClient client, JSONObject resp){
                final JSONObject entry_json = new JSONObject();
                entry_json.put("uid", "2");
                entry_json.put("world", "s");
                entry_json.put("team", "b");
                entry_json.put("length", 5);
                entry_json.put("name", "test2");

                final JSONObject msg_chat = new JSONObject();
                msg_chat.put("length", 3);
                msg_chat.put("msg", "yooooooo");
                try {
                    client.request("chat.woc.entry", entry_json.toString(), new OnDataHandler() {
                        @Override
                        public void onData(Message message) throws PomeloException {
                            System.out.println(message.getBodyJson().toString());
                            client.request("chat.woc.worldchat", msg_chat.toString(), new OnDataHandler() {
                                @Override
                                public void onData(Message message) throws PomeloException {
                                    System.out.println("chat:"+message.getBodyJson().toString());
                                }
                            });
                        }
                    });
                }
                catch (PomeloException p){
                    p.printStackTrace();
                }
            }
        });
        client.connect();
    }

    public void pvp(){
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
            	System.out.println("handshake successed!");
                JSONObject msg = new JSONObject();
                msg.put("uid", "3");

                final JSONObject msg_pair = new JSONObject();
                msg_pair.put("map", "1");

                final JSONObject msg_move = new JSONObject();
                msg_move.put("move", "mmoovvee");
                msg_move.put("index", 0);

                client.listen("onPAIR", new OnDataHandler() {
                    @Override
                    public void onData(Message message) throws PomeloException {
                    	System.out.println("pairing succeeded");
                        String board_id = message.getBodyJson().getString("board_id");
                        msg_move.put("board_id", board_id);

                        client.request("pvp.woc.savemove", msg_move.toString(), new OnDataHandler() {
                            @Override
                            public void onData(Message message) throws PomeloException {
                                System.out.println("savemove:"+message.getBodyJson().toString());
                                client.request("pvp.woc.getmove", msg_move.toString(), new OnDataHandler() {
                                    @Override
                                    public void onData(Message message) throws PomeloException {
                                        System.out.println("getmove:"+message.getBodyJson().toString());
                                    }
                                });
                                client.request("pvp.woc.getlength", msg_move.toString(), new OnDataHandler() {
                                    @Override
                                    public void onData(Message message) throws PomeloException {
                                        System.out.println("getlength:"+message.getBodyJson().toString());
                                    }
                                });
                            }
                        });
                    }
                });

                try {
                    client.request("pvp.woc.entry", msg.toString(), new OnDataHandler() {
                        @Override
                        public void onData(Message message) throws PomeloException {
                            System.out.println("entry:"+message.getBodyJson().toString());
                            client.request("pvp.woc.pair", msg_pair.toString(), new OnDataHandler() {
                                @Override
                                public void onData(Message message) throws PomeloException {
                                    System.out.println("pair:"+message.getBodyJson().toString());
                                }
                            });
                        }
                    });
                }
                catch (PomeloException p){
                    p.printStackTrace();
                }
            }
        });
        client.connect();
	}

	public static void main(String[] args) throws URISyntaxException, PomeloException {
        String uri_dev1 = "http://115.28.21.134:14100";
        String uri_prod1 = "http://115.29.145.79:14100";
        String uri_localhost = "http://127.0.0.1:14100";
        String uri_taiwan = "http://121.40.201.14:14100";

        String key = "alfxkwld";
        
    	System.out.println("This is pomelo client 2");
    	
        new PomeloTest2(uri_localhost, key).pvp();
	}
}