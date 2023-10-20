import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PrivatBankAPI {
    public String getExchangeRatePrivat(String currencyNameA, String currencyNameB) {
        double rateBuy = 0.0;
        double rateSell = 0.0;
        try {
            URL url = new URL("https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5");
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
                    String currencyCodeA = currency.get("ccy").getAsString();
                    String currencyCodeB = currency.get("base_ccy").getAsString();
                    if (currencyCodeA.equals(currencyNameA) && currencyCodeB.equals(currencyNameB)) {
                        rateBuy = currency.get("buy").getAsDouble();
                        rateSell = currency.get("sale").getAsDouble();
                    }
                }
                return "Курс в PrivatBank:" + currencyNameA + "/" + currencyNameB + "\nКупівля: " + rateBuy + "\nПродаж: " + rateSell;
            } else {
                System.out.println("Error: " + respCode);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
