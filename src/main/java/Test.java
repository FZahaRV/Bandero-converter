import java.util.Currency;

public class Test {
    public static void main(String[] args) {
        MonobankAPI monobankAPI = new MonobankAPI();
        PrivatBankAPI privatBankAPI = new PrivatBankAPI();
        System.out.println(monobankAPI.getExchangeRateMono("840", "980"));
        System.out.println(privatBankAPI.getExchangeRatePrivat("USD", "UAH"));
    }
}
