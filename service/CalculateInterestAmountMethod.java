package MortgageCalculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateInterestAmountMethod {

    public static final BigDecimal YEAR = BigDecimal.valueOf(12);

    public BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent) {
        return residualAmount.multiply(interestPercent).divide(YEAR, 10, RoundingMode.HALF_UP);
    }
}
