/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import com.restfb.types.Event;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Miguel
 */
public class UserTraffic {
    
    private final String bingMapsKey = "ApanNzfcCujm2CegUMWN20gHr78rAwt6x-hz70tjCJ9dtoBnALS4NSupUIqeO_qv";
    private final String RestUrlBase = "http://dev.virtualearth.net/REST/V1/Traffic/Incidents";
    
    public List<Acidente> acidentes;    
    
    public UserTraffic (Event event) throws IOException {
        JSONObject obj;
        
        //PARA FAZER O PEDIDO COM AS COORDENADAS REAIS DO EVENTO TROCAR A PROXIMA INSTRUÇÃO PELA 
        // obj = new JSONObject(this.requestJSON(event.getPlace().getLocation().getLatitude(),  event.getPlace().getLocation().getLongitude()));
        obj = new JSONObject(this.requestJSON(51.513415, -0.074765));
        
        
        
        JSONArray res = obj.getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources");
        this.acidentes = new ArrayList<>();
        
        for (int i = 0; i < res.length(); i++) {
            Acidente ac = new Acidente(res.getJSONObject(i).getInt("severity"), res.getJSONObject(i).getString("description"));
            System.out.println(ac.toString());
            acidentes.add(ac);
        }
    }
    
    public ArrayList<Acidente> getAcidentes() {
        return (ArrayList<Acidente>) acidentes;
    }
     
    private String getBoundingBox(final double pLatitude, final double pLongitude, final int pDistanceInMeters) {

               final double[] boundingBox = new double[4];

               final double latRadian = Math.toRadians(pLatitude);

               final double degLatKm = 110.574235;
               final double degLongKm = 110.572833 * Math.cos(latRadian);
               final double deltaLat = pDistanceInMeters / 1000.0 / degLatKm;
               final double deltaLong = pDistanceInMeters / 1000.0 / degLongKm;

               final double minLat = pLatitude - deltaLat;
               final double minLong = pLongitude - deltaLong;
               final double maxLat = pLatitude + deltaLat;
               final double maxLong = pLongitude + deltaLong;

               boundingBox[0] = minLat;
               boundingBox[1] = minLong;
               boundingBox[2] = maxLat;
               boundingBox[3] = maxLong;
               
               StringBuilder sb = new StringBuilder();
               sb.append(boundingBox[0]);
               sb.append(",");
               sb.append(boundingBox[1]);
               sb.append(",");
               sb.append(boundingBox[2]);
               sb.append(",");
               sb.append(boundingBox[3]);
               
               
               String ret = boundingBox[0] + "," + boundingBox[1] + "," + boundingBox[2] + "," + boundingBox[3];

               return sb.toString();
       }
    
    private String requestJSON(double lat, double lon) throws MalformedURLException, IOException {
        URL requestUrl;
        String url = RestUrlBase;
        
        url += "/" + getBoundingBox(lat, lon, 2500);
        url += "?key=" + bingMapsKey;
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
        return output;
    }

    @Override
    public String toString() {
        return "UserTraffic{, acidentes=" + acidentes.toString() + '}';
    }
    
    
}
