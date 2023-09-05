package MortgageCalculator.service;

import MortgageCalculator.model.Rate;
import MortgageCalculator.model.Summary;

import java.math.BigDecimal;
import java.util.List;

public class SummaryServiceFactory {
    //typem zwracanym jest tutaj interfejs
    //slowko static -> wywolujemy metode bezposrednio na klasie
    public static SummaryService create() {
        return rates -> {
            BigDecimal interestSum = calculate(rates, rate -> rate.getRateAmounts().getInterestAmount());
            BigDecimal provisions = calculate(rates, rate -> rate.getRateAmounts().getOverpayment().getProvisionAmount());
            BigDecimal totalLosts = interestSum.add(provisions);
            return new Summary(interestSum, provisions, totalLosts);
        };
    }

    //metoda liczaca sume odsetek
    //poprzednio calculateInterestSum
    private static BigDecimal calculate(List<Rate> rates, Function function) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Rate rate : rates) {
            sum = sum.add(function.calculate(rate));

        }
        return sum;
    }
    //metody calculateInterestSum oraz calculateProvisionSum roznia sie tylko sposobem obliczania sumy
    //utworzymy 1 metode i skorzystamy z interfejsu funkcyjnego, oraz lambdy aby zaimplementowac metody powyzej
//    private static BigDecimal calculateProvisionSum(List<Rate> rates) {
//        BigDecimal sum = BigDecimal.ZERO;
//        for (Rate rate : rates) {
//            sum = sum.add(rate.getRateAmounts().getOverpayment().getProvisionAmount());
//        }
//        return sum;
//    }
}
