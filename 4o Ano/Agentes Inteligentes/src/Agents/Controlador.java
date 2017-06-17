/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
@author AvôCantigas
 */

package Agents;

import Behaviours.RecebePedidosInterface;
import jade.core.Agent;


public class Controlador extends Agent {
    
    private String chuva;
    private String mensagem;
    private String tempMin;
    private String tempMax;

    public String getChuva() {
        return chuva;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setChuva(String chuva) {
        this.chuva = chuva;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }
    
    
    
    @Override
    protected void setup() {
        
        super.setup();
        System.out.println("Controlador a iniciar..");
        this.addBehaviour(new RecebePedidosInterface(this));
    }
    
    @Override
    protected void takeDown(){
        super.takeDown();
        System.out.println(this.getLocalName()+" a morrer...");
    }
}