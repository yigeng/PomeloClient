package com.zvidia.pomelo.websocket;

import com.zvidia.pomelo.exception.PomeloException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: jiangzm
 * Date: 13-8-11
 * Time: 下午7:00
 * To change this template use File | Settings | File Templates.
 */
public interface OnHandshakeSuccessHandler {
    public void onSuccess(PomeloClient client, JSONObject resp);

}
