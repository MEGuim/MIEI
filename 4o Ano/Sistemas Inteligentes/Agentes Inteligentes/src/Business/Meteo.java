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
    public class Meteo implements java.io.Serializable {
        int tempMin;
        int tempMax;
        String descricao;
        int code;

        public Meteo(int tempMin, int tempMax, String descricao, int code) {
            this.tempMin = tempMin;
            this.tempMax = tempMax;
            this.descricao = descricao;
            this.code = code;
        }

        public int getTempMin() {
            return tempMin;
        }

        public int getTempMax() {
            return tempMax;
        }

        public String getDescricao() {
            return descricao;
        }

        public int getCode() {
            return code;
        }

    @Override
    public String toString() {
        return "Meteo{" + "tempMin=" + tempMin + ", tempMax=" + tempMax + ", descricao=" + descricao + ", code=" + code + '}';
    }
        
    }

