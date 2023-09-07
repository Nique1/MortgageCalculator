package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Overpayment;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecreasingAmountsCalculationServiceImpl extends CalculateInterestAmountMethod implements DecreasingAmountsCalculationService {


    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment) {

        BigDecimal interestPercent = inputData.getInterestPercent();

        BigDecimal residualAmount = inputData.getAmount();
        BigDecimal referenceAmount = inputData.getAmount();
        BigDecimal referenceDuration = inputData.getMonthsDuration();


        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateCapitalAmount(referenceAmount, referenceDuration, residualAmount);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate) {
        BigDecimal interestPercent = inputData.getInterestPercent();

        BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();
        BigDecimal referenceAmount = previousRate.getMortgageReference().getReferenceAmount();
        BigDecimal referenceDuration = previousRate.getMortgageReference().getReferenceDuration();


        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateCapitalAmount(referenceAmount, referenceDuration, residualAmount);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }


    private BigDecimal calculateCapitalAmount(
            BigDecimal amount,
            BigDecimal monthsDuration,
            BigDecimal residualAmount
    ) {
        //kwota raty minus  odsetki
        BigDecimal capitalAmount = amount.divide(monthsDuration, 10, RoundingMode.HALF_UP);

        if (capitalAmount.compareTo(residualAmount) >= 0){
            return residualAmount;
        }
        return capitalAmount;

    }

}
