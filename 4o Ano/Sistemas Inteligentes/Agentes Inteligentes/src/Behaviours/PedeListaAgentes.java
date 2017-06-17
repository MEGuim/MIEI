/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Behaviours;

import Agents.Interface;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author pedro
 */
public class PedeListaAgentes extends OneShotBehaviour{
    
    private Interface inter;
    
    public PedeListaAgentes(Interface i){
        this.inter = i;
    }
    
    @Override
    public void action(){
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        AID cord = new AID();
        cord.setLocalName("controlador");
        
        msg.addReceiver(cord);
        msg.setConversationId("" + System.currentTimeMillis());
        
        msg.setContent("daAgentes");
        this.inter.send(msg);
        
    }
}
