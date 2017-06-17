
package Behaviours;

import Agents.Controlador;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class RecebePedidosInterface extends CyclicBehaviour {
    private Controlador cont;
    
    public RecebePedidosInterface (Controlador c)  {
        this.cont = c;
    }
    
    @Override
    public void action() {
        ACLMessage mensagem = this.cont.receive();
        
        if(mensagem != null){
            System.out.println(mensagem);
            if(mensagem.getPerformative() == ACLMessage.REQUEST){
                String Utilizador = (String) mensagem.getContent();
                String[] parts = Utilizador.split(":");
                this.cont.setMensagem(parts[0]);
                this.cont.setChuva(parts[1]);
                this.cont.setTempMin(parts[2]);
                this.cont.setTempMax(parts[3]);
                
                switch (this.cont.getMensagem()) {
                    case "daAgentes":
                        new DaAgentesBehaviour(this.cont).action();
                        block();
                        break;
                    case "braga":
                        String mens=this.cont.getMensagem();
                        new EnviaEvento(this.cont,mens).action();
                        block();
                        break;
                    default:
                        AID receiver = new AID();
                        receiver.setLocalName("inter");
                        long time = System.currentTimeMillis();
                        ACLMessage accept = new ACLMessage(ACLMessage.NOT_UNDERSTOOD);
                        accept.setContent("Não entendi");
                        accept.setConversationId(""+time);
                        accept.addReceiver(receiver);
                        this.cont.send(accept);
                        break;
                }
            }
        }
    }
}
