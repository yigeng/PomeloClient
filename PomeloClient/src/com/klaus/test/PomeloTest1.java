package com.klaus.test;

import java.net.URI;
import java.net.URISyntaxException;

import com.zvidia.pomelo.websocket.OnErrorHandler;
import com.zvidia.pomelo.websocket.OnHandshakeSuccessHandler;

import org.json.JSONObject;

import com.zvidia.pomelo.exception.PomeloException;
import com.zvidia.pomelo.protocol.PomeloMessage.Message;
import com.zvidia.pomelo.websocket.OnDataHandler;
import com.zvidia.pomelo.websocket.PomeloClient;

public class PomeloTest1 {
    private String uri = null, key = null;
	private PomeloClient client = null;
    public PomeloTest1(String uri, String key){
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
                entry_json.put("uid", "1");
                entry_json.put("world", "s");
                entry_json.put("team", "b");
                entry_json.put("length", 5);
                entry_json.put("name", "test1");

                final JSONObject msg_chat = new JSONObject();
                msg_chat.put("to", "2");
                msg_chat.put("length", 3);
                msg_chat.put("offset", 2);
                msg_chat.put("msg", "yoo");
                msg_chat.put("type", "sb");
                try {
                    client.request("chat.woc.entry", entry_json.toString(), new OnDataHandler() {
                        @Override
                        public void onData(Message message) throws PomeloException {
                            System.out.println("entry:"+message.getBodyJson().toString());
                            client.request("chat.woc.send", msg_chat.toString(), new OnDataHandler() {
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
                msg.put("uid", "1");

                final JSONObject msg_pair = new JSONObject();
                msg_pair.put("map", "1");

                final JSONObject msg_info = new JSONObject();
                msg_info.put("info", "iinnffoo");
                msg_info.put("index", 0);

                client.listen("onPAIR", new OnDataHandler() {
                    @Override
                    public void onData(Message message) throws PomeloException {
                    	System.out.println("pairing succeeded");
                        String board_id = message.getBodyJson().getString("board_id");
                        msg_info.put("board_id", board_id);

                        client.request("pvp.woc.saveinfo", msg_info.toString(), new OnDataHandler() {
                            @Override
                            public void onData(Message message) throws PomeloException {
                                System.out.println("saveinfo:"+message.getBodyJson().toString());
                                client.request("pvp.woc.getinfo", msg_info.toString(), new OnDataHandler() {
                                    @Override
                                    public void onData(Message message) throws PomeloException {
                                        System.out.println("getinfo:"+message.getBodyJson().toString());
                                    }
                                });
                                client.request("pvp.woc.getinfolength", msg_info.toString(), new OnDataHandler() {
                                    @Override
                                    public void onData(Message message) throws PomeloException {
                                        System.out.println("getinfolength:"+message.getBodyJson().toString());
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

    public void sgtrack() {
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
                msg.put("appid", "woc_ledou_2");
                msg.put("counterid", "test");
                msg.put("metadata", "{}");

                try {
                    for(int i = 0; i < 1; i++) {
                        client.request("sgtrack.v2.log", msg.toString(), new OnDataHandler() {
                            @Override
                            public void onData(Message message) throws PomeloException {
                                System.out.println(message.getBodyJson().toString());
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

    public void admin() {
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
                msg.put("appid", "woc_ledou_2");
                msg.put("counterid", "test");
                msg.put("metadata", "{}");

                try {
                    for(int i = 0; i < 1; i++) {
                        client.request("admin.statics.load", msg.toString(), new OnDataHandler() {
                            @Override
                            public void onData(Message message) throws PomeloException {
                                System.out.println(message.getBodyJson().toString());
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

    public void gate() {
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

                try {
                    for(int i = 0; i < 1; i++) {
                        client.request("admin.gate.hybrid", msg.toString(), new OnDataHandler() {
                            @Override
                            public void onData(Message message) throws PomeloException {
                                System.out.println(message.getBodyJson().toString());
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

    public static void main(String[] args) throws URISyntaxException, PomeloException {
        String uri_dev1 = "http://115.28.21.134:14100";
        String uri_prod1 = "http://115.29.145.79:14100";
        String uri_localhost = "http://127.0.0.1:14100";
        String uri_taiwan = "http://121.40.201.14:14100";

        String ledou1 = "http://203.195.160.29:14100";

        String key = "alfxkwld";
        
    	System.out.println("This is pomelo client 1");
        PomeloTest1 test = new PomeloTest1(uri_localhost, key);
        //test.gate();
         test.pvp();
	}
}