import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

public class MonobankAPI {
    private final String TOKEN_MONO = "u9q4pmlANZUadu_qsMwtJwwihPm1m22dDlXrBDQ8eXMU";
    public String getExchangeRateMono(String currencyNameA, String currencyNameB) {
        double rateBuy = 0.0;
        double rateSell = 0.0;
        try {
            URL url = new URL("https://api.monobank.ua/bank/currency");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Token", TOKEN_MONO);
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
                    int currencyCodeA = currency.get("currencyCodeA").getAsInt();
                    int currencyCodeB = currency.get("currencyCodeB").getAsInt();
                    if (currencyCodeA == Integer.parseInt(currencyNameA) && currencyCodeB == Integer.parseInt(currencyNameB)) {
                        rateBuy = currency.get("rateBuy").getAsDouble();
                        rateSell = currency.get("rateSell").getAsDouble();
                    }
                }
                return "Курс в MONOBANK:" + currencyNameA + "/" + currencyNameB + "\nКупівля: " + rateBuy + "\nПродаж: " + rateSell;
            } else {
                System.out.println("Error: " + respCode);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
