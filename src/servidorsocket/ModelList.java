package servidorsocket;

import java.util.LinkedList;
import javax.swing.AbstractListModel;
import servidor.ClientSocket;

/**
 *
 * @author Sergio_W
 */
public class ModelList extends AbstractListModel{

    private LinkedList<ClientSocket> lista = new LinkedList();
    
    @Override
    public int getSize() {
        return this.lista.size();
    }

    @Override
    public Object getElementAt(int index) {
        ClientSocket client = this.lista.get(index);
        return client.getClientName();
    }
    
    public void addClient(ClientSocket client){
        this.lista.add(client);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void deleteClient(int index){
        this.lista.remove(index);
        this.fireIntervalRemoved(this, getSize(), getSize()+1);
    }
    
    public ClientSocket getClient(int index){
        return this.lista.get(index);
    }
    
    public void clear(){
        this.lista.clear();
    }
}
