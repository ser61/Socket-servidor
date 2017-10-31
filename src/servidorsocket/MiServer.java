package servidorsocket;

import InterfaceServerEvents.VentanaServidorEvent;
import InterfaceServerEvents.VentanaServidorEventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import servidor.ClientSocket;
import servidor.ServidorSocket;

/**
 *
 * @author Sergio_W
 */
public class MiServer extends ServidorSocket{
      private static ArrayList listeners;

    public MiServer(int puerto) {
        super(puerto);
        this.listeners = new ArrayList();
    }

    @Override
    public void onServerConnected(ClientSocket clientSocket) {
        this.triggerOnConnected(clientSocket);
    }

    @Override
    public void onServerDesconnected(ClientSocket clientSocket) {
        this.triggerOnDesconnected(clientSocket);
    }

    @Override
    public void getMessage(ClientSocket clientSocket, String text) {
        this.triggerOnRead(clientSocket, text);
    }

    
    
    public void addEventListener(VentanaServidorEventListener listener){
        this.listeners.add(listener);
    }
    
    public void triggerOnConnected(ClientSocket client){
        ListIterator li = this.listeners.listIterator();
        while (li.hasNext()) {
            VentanaServidorEventListener listener = (VentanaServidorEventListener) li.next();
            VentanaServidorEvent clientEvent = new VentanaServidorEvent(this, client);
            (listener).onConnected(clientEvent);
        }
    }
    
    public void triggerOnDesconnected(ClientSocket client){
        ListIterator li = this.listeners.listIterator();
        while (li.hasNext()) {
            VentanaServidorEventListener listener = (VentanaServidorEventListener) li.next();
            VentanaServidorEvent clientEvent = new VentanaServidorEvent(this, client);
            (listener).onDesconnected(clientEvent);
        }
    }
    
    public void triggerOnRead(ClientSocket client, String msg){
        ListIterator li = this.listeners.listIterator();
        while (li.hasNext()) {
            VentanaServidorEventListener listener = (VentanaServidorEventListener) li.next();
            VentanaServidorEvent clientEvent = new VentanaServidorEvent(this, client);
            clientEvent.setMsg(msg);
            (listener).onRead(clientEvent);
        }
    }
}
