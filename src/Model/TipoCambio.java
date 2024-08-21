/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Franco Coward
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONObject;

public class TipoCambio {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD";

    public static double obtenerTipoCambioColones() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Convertir la respuesta JSON a un objeto JSON
            JSONObject jsonObj = new JSONObject(content.toString());
            double tipoCambio = jsonObj.getJSONObject("rates").getDouble("CRC");

            return tipoCambio;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

}
