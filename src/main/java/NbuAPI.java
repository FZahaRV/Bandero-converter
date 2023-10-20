import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NbuAPI {
    public String getExchangeRateNbu(String currencyNameA, String currencyNameB) {
        double rateBuy = 0.0;
        try {
            URL url = new URL("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int respCode = connection.getResponseCode();
            if (respCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder resp = new StringBuilder();
                String input;
                while ((input = in.readLine()) != null) {
                    resp.append(input);
                }
                in.close();
                String respBody = resp.toString();
                JsonArray currencyData = JsonParser.parseString(respBody).getAsJsonArray();
                for (JsonElement element : currencyData) {
                    JsonObject currency = element.getAsJsonObject();
                    int currencyCodeA = currency.get("r030").getAsInt();
                    if (currencyCodeA == Integer.parseInt(currencyNameA)) {
                        rateBuy = currency.get("rate").getAsDouble();
                    }
                }
                return "Курс Національного банку України:" + currencyNameA + "/" + currencyNameB + "\nНБУ: " + rateBuy;
            } else {
                System.out.println("Error: " + respCode);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
