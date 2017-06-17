/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Forecast;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import com.restfb.types.Event;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.bind.JAXBException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 *
 * @author pedro
 */
public class UserWeather {
    
    YahooWeatherService service;
    Channel result;
    Meteo tempo;
    
    // 737514 é o codigo da yahoo de braga
    // 44418 é o código de londres
    // SUBSTITUIR ESTE CONSTRUTOR PELO SEGUINTE PARA USAR A API DIREITO
    /*
    public UserWeather(Event e) throws JAXBException, IOException {
        this.service = new YahooWeatherService();
        this.result = service.getForecast("737514", DegreeUnit.CELSIUS);
        LocalDate eventday = e.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (Forecast dia : this.result.getItem().getForecasts()) {
            LocalDate fore = dia.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (eventday.equals(fore)) {
                this.tempo = new Meteo(dia.getLow(), dia.getHigh(), dia.getText(), dia.getCode());
                System.out.println(dia.getLow() + "\n" + dia.getHigh() + "\n" + dia.getText());
                break;
            }   
        }
    }
    */
    
    //CONSTRUTOR PARA DADOS INVENTADOS
    
    public UserWeather(Event e) throws JAXBException, IOException {
        LocalDate eventday = e.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int coiso = eventday.getDayOfWeek().getValue();
        if (coiso %2 == 0) {
            this.tempo = new Meteo(0, 14, "Showers",11);
            System.out.println(tempo.getTempMin() + "\n" + tempo.getTempMax() + "\n"+ tempo.getDescricao());
        } else {
            this.tempo = new Meteo(19,34, "Sunny",32);
            System.out.println(tempo.getTempMin() + "\n" + tempo.getTempMax() + "\n"+ tempo.getDescricao());
        }
    }
    
    //Para eventualmente procurar em qualquer cidade
    private String getWOEID(String cidade) throws IOException {
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.places%20where%20text%3D%" + cidade + "%22%20AND%20placeTypeName.code%20%3D%207&format=json&diagnostics=true&callback=";
        URL requestUrl;
        requestUrl = new URL(url);
        
        // Make the web request
        URLConnection conn = requestUrl.openConnection();
        InputStream inStream = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        String inputLine, output = "";
        
        while((inputLine = br.readLine()) != null)
        {
            output += inputLine;
        }
        
        br.close();
        inStream.close();
        JSONObject obj = new JSONObject(output);
        JSONArray arrRes = obj.getJSONObject("query").getJSONObject("results").getJSONArray("place");
        Integer woeid = arrRes.getJSONObject(0).getInt("woeid");
        output = woeid.toString();       
        
        return output;        
    }

    private YahooWeatherService getService() {
        return this.service;
    }

    public Channel getResult() {
        return this.result;
    }

    public int getTempMin() {
        return this.tempo.tempMin;
    }

    public int getTempMax() {
        return this.tempo.tempMax;
    }

    public String getDescricao() {
        return this.tempo.descricao;
    }

    public Meteo getTempo() {
        return tempo;
    }
    

    @Override
    public String toString() {
        return "UserWeather{" + "tempMin=" + tempo.tempMin + ", tempMax=" + tempo.tempMax + ", descricao=" + tempo.descricao + '}';
    }    
}
