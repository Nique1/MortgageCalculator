package MortgageCalculator.model;

import java.math.BigDecimal;

public class Summary {
    private final BigDecimal interestSum;

    public Summary(BigDecimal interestSum) {
        this.interestSum = interestSum;
    }

    public BigDecimal getInterestSum() {
        return interestSum;
    }
}
