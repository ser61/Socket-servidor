package InterfaceServerEvents;

import java.util.EventObject;
import servidor.ClientSocket;

/**
 *
 * @author Sergio_W
 */
public class VentanaServidorEvent extends EventObject{
    private ClientSocket client;
    private String msg;

    public VentanaServidorEvent(Object source, ClientSocket _client) {
        super(source);
        this.client = _client;
    }
    
    public void setMsg(String _msg){
        this.msg = _msg;
    }
    
    public ClientSocket getClient(){
        return this.client;
    }
    
    public String getMsg(){
        return this.msg;
    }
}
