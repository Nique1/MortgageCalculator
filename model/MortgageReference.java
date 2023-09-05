package MortgageCalculator.model;

import java.math.BigDecimal;

public class MortgageReference {
    //klasa ktora odnosi sie do wielkosci raty po nadplacie kredytu
    private final BigDecimal referenceAmount;
    private final BigDecimal referenceDuration;


    public MortgageReference(BigDecimal referenceAmount, BigDecimal referenceDuration) {
        this.referenceAmount = referenceAmount;
        this.referenceDuration = referenceDuration;
    }

    public BigDecimal getReferenceAmount() {
        return referenceAmount;
    }

    public BigDecimal getReferenceDuration() {
        return referenceDuration;
    }
}
