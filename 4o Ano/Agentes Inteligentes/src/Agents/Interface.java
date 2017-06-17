/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
@author AvôCantigas
 */
package Agents;

import Behaviours.RecebeInfoControlador;
import GUI.AfterLogin;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import jade.core.AID;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Miguel/Alexandre
 */





public class Interface extends GuiAgent {
    
    protected AfterLogin myGui;
    
    private FacebookClient fbClient;
     
    public Interface(){
        this.fbClient = new DefaultFacebookClient();
    }
     
    
    @Override
    protected void setup() {
        myGui = new AfterLogin(this);
        System.out.println("Interface a iniciar..");
        myGui.setVisible(true);
        
        super.setup();
        this.addBehaviour(new RecebeInfoControlador(this));
    }
    
    protected void onGuiEvent(GuiEvent ev){
        int comand= ev.getType();
        if(comand==1){
            String content = (String)ev.getSource();
            AID receiver = new AID();
            receiver.setLocalName("controlador");
            long time = System.currentTimeMillis();
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setContent(content);
            
            msg.setConversationId(""+time);
            msg.addReceiver(receiver);
            send(msg);
        }
    }
	
    
    protected void takeDown() {
        super.takeDown(); //To change body of generated methods, choose Tools | Templates.
            System.out.println("Interface a terminar..");
    }
    
    public void setFbClient(FacebookClient client){
        this.fbClient = client;
    }
    
    public FacebookClient getFbClient(){
        return this.fbClient;
    }    
}
