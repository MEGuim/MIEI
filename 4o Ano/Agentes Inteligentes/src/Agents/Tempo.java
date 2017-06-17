/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agents;

import Business.UserWeather;
import com.restfb.types.Event;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Miguel
 */
public class Tempo extends Agent {
    
    protected void setup() {
        super.setup();
        
        DFAgentDescription dfs = new DFAgentDescription();
        dfs.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        
        sd.setType("agentes");
        dfs.addServices(sd);
        
        try{
            DFService.register(this, dfs);
        } catch(FIPAException e){
            e.printStackTrace();
        }
        
        System.out.println("Agente " + this.getLocalName() + " a iniciar...");
        this.addBehaviour(new ReceiveBehaviour());
    }
    
     protected void takeDown(){
        super.takeDown();
        
        try{
            DFService.deregister(this);
        }catch(FIPAException e){
            e.printStackTrace();
        }
        System.out.println(this.getLocalName()+" a morrer...");
    }
    
      private class ReceiveBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                
                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    try {
                        if (msg.getContentObject() != null){
                            Event e = (Event) msg.getContentObject();
                            UserWeather weather = new UserWeather(e);                
                            reply.setContentObject((Serializable) weather.getTempo());
                            reply.setPerformative(ACLMessage.INFORM);
                            myAgent.send(reply);
                        }
                    } catch (UnreadableException | JAXBException | IOException ex) {
                        Logger.getLogger(Tempo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                    myAgent.send(reply);
                }
            }
            block();
        }
    }
}
