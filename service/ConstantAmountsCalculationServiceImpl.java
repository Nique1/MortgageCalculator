package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Overpayment;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.RateAmounts;
import MortgageCalculator.utils.CalculationUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;


//import statyczny
import static MortgageCalculator.utils.CalculationUtils.calculateInterestAmount;

public class ConstantAmountsCalculationServiceImpl implements ConstantAmountsCalculationService{


    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment) {
        //parametr Q potrzebny do wylicznia raty kredytu

        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal q = calculateQ(interestPercent);

        BigDecimal residualAmount = inputData.getAmount();
        BigDecimal referenceAmount = inputData.getAmount();
        BigDecimal referenceDuration = inputData.getMonthsDuration();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal rateAmount = calculateConstantRateAmount(
                q, referenceAmount, referenceDuration, interestAmount, residualAmount);
        BigDecimal capitalAmount = calculateCapitalAmount(rateAmount, interestAmount, residualAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);

    }
    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate) {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal q = calculateQ(interestPercent);

        BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();
        BigDecimal referenceAmount = previousRate.getMortgageReference().getReferenceAmount();
        BigDecimal referenceDuration = previousRate.getMortgageReference().getReferenceDuration();


        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal rateAmount = calculateConstantRateAmount(
                q, referenceAmount, referenceDuration, interestAmount, residualAmount);
        BigDecimal capitalAmount = calculateCapitalAmount(rateAmount, interestAmount, residualAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }


    private BigDecimal calculateQ(BigDecimal interestPercent) {
        return interestPercent.divide(CalculationUtils.YEAR, 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }


    //bug z blednym obliczaniem koncowej raty w porownaniu do pozostalej raty

    private BigDecimal calculateConstantRateAmount(
            BigDecimal q,
            BigDecimal amount,
            BigDecimal monthsDuration,
            BigDecimal interestAmount,
            BigDecimal residualAmount
    ) {
        BigDecimal rateAmount = amount
                .multiply(q.pow(monthsDuration.intValue()))
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(q.pow(monthsDuration.intValue()).subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        return compareWithResidual(rateAmount,interestAmount, residualAmount);

    }
    private BigDecimal compareWithResidual(
            BigDecimal rateAmount,
            BigDecimal interestAmount,
            BigDecimal residualAmount
    ) {
        if (rateAmount.subtract(interestAmount).compareTo(residualAmount) >= 0) {
            return residualAmount.add(interestAmount);
        }
        return rateAmount;

    }

    private BigDecimal calculateCapitalAmount(
            BigDecimal rateAmount,
            BigDecimal interestAmount,
            BigDecimal residualAmount
    ) {
        //kwota raty minus  odsetki
        BigDecimal capitalAmount = rateAmount.subtract(interestAmount);

        if (capitalAmount.compareTo(residualAmount) >= 0){
            return residualAmount;
        }
        return capitalAmount;
    }


}
