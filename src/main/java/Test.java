import java.util.Currency;

public class Test {
    public static void main(String[] args) {
        MonobankAPI monobankAPI = new MonobankAPI();
        System.out.println(monobankAPI.getExchangeRateMono("840", "980"));

    }
}
