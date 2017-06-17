/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Behaviours;
import Agents.Controlador;
import Business.Acidente;
import Business.Meteo;
import com.restfb.types.Event;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author NMVC
 */
public class EnviaEvento extends CyclicBehaviour{
    
    private Controlador cont;
    String mens;
    
    public EnviaEvento (Controlador c, String mensagem)  {
        this.cont = c;
        this.mens = mensagem;
    }
    
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void action() {
        String conteudo="evento:";
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        ACLMessage resp;
        List<Event> eventosLista = new ArrayList<>();
        msg.setConversationId("");
        msg.setContent(this.mens);
        AID eventos = new AID();
        eventos.setLocalName("eventos");
        msg.addReceiver(eventos);
        List<Event>listafinal = new ArrayList<>();
        
        this.cont.send(msg);
 
        resp = this.cont.blockingReceive(3000);
        if (resp != null && resp.getPerformative() == ACLMessage.INFORM) {
            try {
                if(resp.getContentObject() != null) {
                    AID receiver = new AID();
                    receiver.setLocalName("eventos");
                    long time = System.currentTimeMillis();
                    ACLMessage accept = new ACLMessage(ACLMessage.CONFIRM);
                    accept.setContent("recebi");
                    accept.setConversationId(""+time);
                    accept.addReceiver(receiver);
                    this.cont.send(accept);
                    eventosLista = (List<Event>) resp.getContentObject();
                }
            } catch (UnreadableException ex) {
                Logger.getLogger(EnviaEvento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        List<Event> listaEventosTempo = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Event event : eventosLista) {
            System.out.println(event.getName());
            LocalDate eventday = event.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (today.minusDays(1).isBefore(eventday)) {
                if (today.plusDays(10).isAfter(eventday)) {
                    try {
                        ACLMessage msg2 = new ACLMessage(ACLMessage.REQUEST);
                        msg.setConversationId("");
                        msg2.setContentObject( (Serializable) event);
                        AID tempo = new AID();
                        tempo.setLocalName("tempo");
                        msg2.addReceiver(tempo);
                        this.cont.send(msg2);
                        resp = this.cont.blockingReceive(300000);
                        if (resp != null && resp.getPerformative() == ACLMessage.INFORM) {
                            Meteo tempos;
                            tempos = (Meteo) resp.getContentObject();
                            int code = tempos.getCode();
                            if (((tempos.getTempMin() >= (Integer.parseInt(this.cont.getTempMin()))) && tempos.getTempMax() <= (Integer.parseInt(this.cont.getTempMax())))){
                                if(("Sim".equals(this.cont.getChuva()) && code > 7) || ("Não".equals(this.cont.getChuva()) && (code > 18 && code<=34) || code==36 || code==3200)) { 
                                    listaEventosTempo.add(event);
                                }
                            }
                        }
                    } catch (IOException | UnreadableException ex) {
                    Logger.getLogger(EnviaEvento.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else {
                    listafinal.add(event);         
                }
            } 
        }
        
        
        for (Event event : listaEventosTempo) {
            System.out.println(event.getName());
            LocalDate eventday = event.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (eventday.equals(today)) {
                try {
                    ACLMessage msg2 = new ACLMessage(ACLMessage.REQUEST);
                    msg.setConversationId("");
                    msg2.setContentObject( (Serializable) event);
                    AID transito = new AID();
                    transito.setLocalName("transito");
                    msg2.addReceiver(transito);
                    this.cont.send(msg2);
                    resp = this.cont.blockingReceive(900000);
                    if (resp != null && resp.getPerformative() == ACLMessage.INFORM) {
                        List<Acidente> acidentes;
                        acidentes = (List<Acidente>) resp.getContentObject();
                        int total=0;
                        for(Acidente a : acidentes){                     
                            total += a.getSeveridade();                    
                        }
                        if(total <= 10){
                            listafinal.add(event);
                        }
                    }
                } catch (IOException | UnreadableException ex) {
                    Logger.getLogger(EnviaEvento.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                listafinal.add(event);
            }
        }

        ACLMessage nova = new ACLMessage(ACLMessage.INFORM);
        AID inter = new AID();
        inter.setLocalName("inter");
        nova.addReceiver(inter);
        nova.setConversationId("");
        try {
            nova.setContentObject((Serializable) listafinal);
        } catch (IOException ex) {
            Logger.getLogger(EnviaEvento.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.cont.send(nova);
        
    }
}
