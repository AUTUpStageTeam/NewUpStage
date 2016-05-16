

package org.example.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.inject.Inject;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.example.model.Msg; 
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/actions")
public class MsgWebSocketServer {
    
    @Inject
    private MsgSessionHandler sessionHandler;

    @OnOpen
        public void open(Session session) {
            sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(MsgWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                Msg msg = new Msg();
                msg.setDescription(jsonMessage.getString("description"));
                sessionHandler.addDevice(msg);
            }

        }
    }
} 