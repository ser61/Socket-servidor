package servidorsocket;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import InterfaceServerEvents.VentanaServidorEvent;
import InterfaceServerEvents.VentanaServidorEventListener;
import servidor.Paquete;
import servidor.ServidorSocket;

public class VentanaServidor extends JFrame implements ActionListener{
    JButton btnIniciar;
    JButton btnFinalizar;
    JButton btnSendAll;
    JTextField nroPuerto;
    JTextField txtSend;
    JTextArea textBox;
    JScrollPane scroll;
    JLabel lbEstado;
    JLabel lbMsg;
    JLabel lbConnected;
    JLabel lbSend;
    JList list;
    ModelList client; //Para el JList
    MiServer server;
    VentanaServidorEventListener event;
    int nroConnected;

    public VentanaServidor() {
        super("Servidor Socket");
        this.nroConnected = 0;
        Init();
        this.server = new MiServer(Integer.parseInt(this.nroPuerto.getText()));
        this.server.addEventListener(event);
    }
    
    private void Init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.lbEstado = new JLabel("ESTADO");
        this.lbEstado.setBounds(100, 10, 200, 30);
        this.lbEstado.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        add(this.lbEstado);
        
        this.btnIniciar = new JButton("Iniciar");
        this.btnIniciar.setBounds(10, 40, 150, 30);
        this.btnIniciar.addActionListener(this);
        add(this.btnIniciar);
        
        this.nroPuerto = new JTextField("9090");
        this.nroPuerto.setBounds(170, 40, 150, 30);
        add(this.nroPuerto);
        
        this.btnFinalizar = new JButton("Finalizar");
        this.btnFinalizar.setBounds(10, 80, 310, 30);
        this.btnFinalizar.addActionListener(this);
        this.btnFinalizar.setEnabled(false);
        add(this.btnFinalizar);
        
        this.lbConnected = new JLabel("Conectados: 0");
        this.lbConnected.setBounds(30, 120, 100, 30);
        this.lbConnected.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        add(this.lbConnected);
        
        this.list = new JList();
        this.client = new ModelList();
        this.list.setModel(this.client);
        this.list.setBounds(10, 160, 310, 200);
        this.list.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int index = list.getSelectedIndex();
                new sendMsgClient(server, client.getClient(index));
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        add(this.list);
        
        this.lbMsg = new JLabel("Recepcion de Mensaje:");
        this.lbMsg.setBounds(30, 360, 300, 30);
        this.lbMsg.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        add(this.lbMsg);
        
        this.textBox = new JTextArea();
        DefaultCaret caret = (DefaultCaret) this.textBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.scroll = new JScrollPane(this.textBox);
        this.scroll.setBounds(10, 400, 310, 100);
        add(this.scroll);
        
        this.lbSend = new JLabel("Enviar Mensaje:");
        this.lbSend.setBounds(30, 510, 300, 30);
        this.lbSend.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        add(this.lbSend);
        
        this.txtSend = new JTextField();
        this.txtSend.setBounds(10, 550, 310, 30);
        add(this.txtSend);
        
        this.btnSendAll = new JButton("Enviar Mensaje a Todos");
        this.btnSendAll.setBounds(10, 590, 310, 30);
        this.btnSendAll.addActionListener(this);
        this.btnSendAll.setEnabled(false);
        add(this.btnSendAll);
        
        setLayout(null);
        setSize(350,670);
        setVisible(true);
               
        this.event = new VentanaServidorEventListener() {
            @Override
            public void onConnected(VentanaServidorEvent ev) {
                client.addClient(ev.getClient());
                nroConnected += 1;
                lbConnected.setText("Conectados: " + nroConnected);
            }
            
            @Override
            public void onDesconnected(VentanaServidorEvent ev){
                nroConnected -= 1;
                lbConnected.setText("Conectados: " + nroConnected);
                for (int i = 0; i < client.getSize(); i++) {
                    String name = client.getClient(i).getClientName();
                    if (name.equalsIgnoreCase(ev.getClient().getClientName())) {
                        client.deleteClient(i);
                        break;
                    }
                }
            }
                    
            @Override
            public void onRead(VentanaServidorEvent ev) {
                textBox.append(ev.getClient().getClientName() + ": " + ev.getMsg() + "\n");
            }
        };
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnIniciar) {
            this.server.startServer();
            lbEstado.setText("SERVIDOR INICIADO...");
            lbEstado.setForeground(Color.GREEN);
            btnIniciar.setEnabled(false);
            btnFinalizar.setEnabled(true);
            btnSendAll.setEnabled(true);
        }else if (e.getSource() == this.btnFinalizar) {
            this.server.stopServer();
            lbEstado.setText("SERVIDOR DETENIDO!!!");
            this.nroConnected = 0;
            this.lbConnected.setText("Conectados: 0");
            this.client.clear();
            this.list.clearSelection();
            this.list.removeAll();
            lbEstado.setForeground(Color.RED);
            btnIniciar.setEnabled(true);
            btnFinalizar.setEnabled(false);
            btnSendAll.setEnabled(false);
        }else if (e.getSource() == this.btnSendAll) {
            Paquete msg = new Paquete(this.txtSend.getText());
            this.server.sendMessageAll(msg);
            this.txtSend.setText("");
        }
    }
}
