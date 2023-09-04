package MortgageCalculator.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RateAmounts {
    private final BigDecimal rateAmount;
    private final BigDecimal interestAmount;
    private final BigDecimal capitalAmount;

    public RateAmounts(BigDecimal rateAmount, BigDecimal interestAmount, BigDecimal capitalAmount) {
        this.rateAmount = rateAmount;
        this.interestAmount = interestAmount;
        this.capitalAmount = capitalAmount;
    }

    public BigDecimal getRateAmount() {
        return rateAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getInterestAmount() {
        return interestAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getCapitalAmount() {
        return capitalAmount.setScale(2, RoundingMode.HALF_UP);
    }


    @Override
    public String toString() {
        return "RateAmounts{" +
                "rateAmount=" + rateAmount +
                ", interestAmount=" + interestAmount +
                ", capitalAmount=" + capitalAmount +
                '}';
    }
}
