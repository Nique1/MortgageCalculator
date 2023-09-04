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
            BigDecimal interestSum = calculateInterestSum(rates);
            return new Summary(interestSum);
        };
    }
    //metoda liczaca sume odsetek
    private static BigDecimal calculateInterestSum(List<Rate> rates) {
    BigDecimal sum = BigDecimal.ZERO;
        for (Rate rate : rates) {
            sum = sum.add(rate.getRateAmounts().getInterestAmount());
        }
        return sum;
    }
}
