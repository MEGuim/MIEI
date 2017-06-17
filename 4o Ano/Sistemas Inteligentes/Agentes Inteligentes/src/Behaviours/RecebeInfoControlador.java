/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Behaviours;
import Agents.Interface;
import GUI.AfterLogin;
import GUI.ListaEventos;
import GUI.YellowPages;
import com.restfb.types.Event;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NMVC
 */


public class RecebeInfoControlador extends CyclicBehaviour{
    
    private Interface inter;
    
    public RecebeInfoControlador(Interface i) {
        this.inter = i;
    }
    
    @Override
    public void action() {
        
        ACLMessage recebida = this.inter.receive();
        
        if (recebida != null) {
            Object eventos = new ArrayList<>();
            if (recebida.getPerformative() == ACLMessage.INFORM) {
                try {
                    eventos =  recebida.getContentObject();
                } catch (UnreadableException ex) {
                    Logger.getLogger(RecebeInfoControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                ListaEventos le = new ListaEventos();
                le.setVisible(true);
                le.mostraEventos((List<Event>) eventos);

                AID receiver = new AID();
                receiver.setLocalName("controlador");
                long time = System.currentTimeMillis();
                ACLMessage accept = new ACLMessage(ACLMessage.CONFIRM);
                accept.setContent("recebi");
                accept.setConversationId(""+time);
                accept.addReceiver(receiver);
                this.inter.send(accept);

                if(recebida.getContent().matches("agentes:.+")){
                    List<String> agentes = new ArrayList<>(Arrays.asList(recebida.getContent().split(":")));
                    YellowPages yp = new YellowPages();
                    yp.setVisible(true);
                    yp.mostraAgentes(agentes);

                    AID receiver2 = new AID();
                    receiver2.setLocalName("controlador");
                    ACLMessage accept2 = new ACLMessage(ACLMessage.CONFIRM);
                    accept.setContent("recebi");
                    accept.setConversationId(""+time);
                    accept.addReceiver(receiver);
                    this.inter.send(accept);
                }
                
            } else if(recebida.getPerformative() == ACLMessage.NOT_UNDERSTOOD){
                AfterLogin af = new AfterLogin();
                af.mostraOptionPane();
            }
        }
        block();
    }
}
