/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Behaviours;

import Agents.Controlador;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author pedro
 */
public class DaAgentesBehaviour extends OneShotBehaviour{
    
    private Controlador control;
    
    public DaAgentesBehaviour(Controlador ctr){
        this.control = ctr;
    }
    
    public void action(){
        DFAgentDescription[] result = null;
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("agentes");
        
        try{
            result = DFService.search(this.control, dfd);
        }catch(FIPAException e){
            e.printStackTrace();
        }
        
        String cont = "agentes:";
        
        int i = 0;
        for(DFAgentDescription dfad: result){
            if(i==result.length-1){
                cont += dfad.getName().getLocalName();
            }
            else{
                cont += dfad.getName().getLocalName() + ";";
            }
            
            i++;
        }
        
        System.out.println("Encontrei estes agentes -> " + cont);
        
        
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        AID aid = new AID();
        aid.setLocalName("inter");
        msg.addReceiver(aid);
        msg.setConversationId("agentesEncontrados" + System.currentTimeMillis());
        msg.setContent(cont);

        this.control.send(msg);
    }
    
    
}
