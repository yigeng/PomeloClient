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

class PomeloAgent {
    private String uri = null, key = null;
	private PomeloClient client = null;
	private String uid = "";
	private int score = 0;
    public PomeloAgent(String uri, String key, String uid, int score){
        this.uri = uri;
        this.key = key;
        this.uid = uid;
        this.score = score;
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
                msg.put("uid", uid);

                final JSONObject msg_pair = new JSONObject();
                msg_pair.put("map", "kanglai");

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
                                    client.request("pvp.woc.unpair", msg_pair.toString(), new OnDataHandler() {
                                        @Override
                                        public void onData(Message message) throws PomeloException {
                                            System.out.println("unpair:"+message.getBodyJson().toString());
                                        }
                                    });
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
    
    public void pve(){
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
                msg.put("uid", uid);

                final JSONObject msg_pair = new JSONObject();
                msg_pair.put("map", "1");
                msg_pair.put("score", score);
                msg_pair.put("challenge", "-1");

                final JSONObject msg_info = new JSONObject();
                msg_info.put("info", "iinnffoo");
                msg_info.put("index", 0);

                client.listen("onPAIR", new OnDataHandler() {
                    @Override
                    public void onData(Message message) throws PomeloException {
                    	System.out.println("pairing succeeded");
                        String board_id = message.getBodyJson().getString("board_id");
                        msg_info.put("board_id", board_id);

                        client.request("pve.woc.saveinfo", msg_info.toString(), new OnDataHandler() {
                            @Override
                            public void onData(Message message) throws PomeloException {
                                System.out.println("saveinfo:"+message.getBodyJson().toString());
                                client.request("pve.woc.getinfo", msg_info.toString(), new OnDataHandler() {
                                    @Override
                                    public void onData(Message message) throws PomeloException {
                                        System.out.println("getinfo:"+message.getBodyJson().toString());
                                    }
                                });
                                client.request("pve.woc.getinfolength", msg_info.toString(), new OnDataHandler() {
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
                    client.request("pve.woc.entry", msg.toString(), new OnDataHandler() {
                        @Override
                        public void onData(Message message) throws PomeloException {
                            System.out.println("entry:"+message.getBodyJson().toString());
                            client.request("pve.woc.pair", msg_pair.toString(), new OnDataHandler() {
                                @Override
                                public void onData(Message message) throws PomeloException {
                                    System.out.println("pair:"+message.getBodyJson().toString());
//                                	client.request("pve.woc.unpair", msg_pair.toString(), new OnDataHandler() {
//
//										@Override
//										public void onData(Message message)
//												throws PomeloException {
//											// TODO Auto-generated method stub
//											
//										}});
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

        String ledou1 = "http://203.195.160.29:14100";

        String key = "alfxkwld";
        String uid = args[0];
        int score = Integer.parseInt(args[1]);
    	System.out.println("This is pomelo pve agent 1");
    	PomeloAgent test = new PomeloAgent(uri_localhost, key, uid, score);
        //test.gate();
         test.pve();
	}
}

