package servidorsocket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import servidor.ClientSocket;
import servidor.Paquete;
import servidor.SendMessage;
import servidor.ServidorSocket;

/**
 *
 * @author Sergio_W
 */
public class sendMsgClient extends JFrame{
    private JTextField msg;
    private JButton btnEnviar;
    private ServidorSocket server;
    private ClientSocket client;

    public sendMsgClient(ServidorSocket server, ClientSocket client) {
        super(client.getClientName());
        this.server = server;
        this.client = client;
        Init();
    }
    
    public void Init(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.msg = new JTextField();
        this.msg.setBounds(10, 10, 300, 30);
        add(this.msg);
        
        this.btnEnviar = new JButton("Enviar");
        this.btnEnviar.setBounds(10, 50, 300, 30);
        this.btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendBunttonCliked(e);
            }
        });
        add(this.btnEnviar);
        
        setLayout(null);
        setSize(350, 150);
        setVisible(true);
    }
    
    private void sendBunttonCliked(ActionEvent e) {
        Paquete msg = new Paquete(this.msg.getText());
        SendMessage sendMsg = new SendMessage(client, msg);
        sendMsg.start();
        this.msg.setText("");
    }
}
