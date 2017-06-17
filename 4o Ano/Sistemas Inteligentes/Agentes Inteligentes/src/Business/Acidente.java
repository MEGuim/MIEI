/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author Miguel
 */
public class Acidente implements java.io.Serializable{
        int severidade;
        String descricao; 
        
        public Acidente(int severidade, String descricao) {
            this.severidade = severidade;
            this.descricao = descricao;
        }

        public int getSeveridade() {
            return severidade;
        }

        public String getDescricao() {
            return descricao;
        }

        @Override
        public String toString() {
            return "acidente{" + "severidade=" + severidade + ", descricao=" + descricao + '}';
        }
    }