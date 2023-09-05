package MortgageCalculator.model;

import java.math.BigDecimal;

public class Summary {
    private final BigDecimal interestSum;
    private final BigDecimal overpaymentProvisionSum;

    private final  BigDecimal totalLosts;

    public Summary(BigDecimal interestSum, BigDecimal overpaymentProvisionSum, BigDecimal totalLosts) {
        this.interestSum = interestSum;
        this.overpaymentProvisionSum = overpaymentProvisionSum;
        this.totalLosts = totalLosts;
    }

    public BigDecimal getInterestSum() {
        return interestSum;
    }

    public BigDecimal getOverpaymentProvisionSum() {
        return overpaymentProvisionSum;
    }

    public BigDecimal getTotalLosts() {
        return totalLosts;
    }
}
