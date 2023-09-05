package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Overpayment;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.RateAmounts;
import MortgageCalculator.model.exception.RateCalculateException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountsCalculationServiceImpl implements AmountsCalculationService {


    private static final BigDecimal YEAR = BigDecimal.valueOf(12);

    //obliczanie raty 1
    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment) {
        //do wyboru kredyt o ratach stalych i zmiennych
        switch (inputData.getRateType()) {
            case CONSTANT:
                return calculateConstantRate(inputData, overpayment);
            case DECREASING:
                return calculateDecreasingRate(inputData, overpayment);

            //mimo, ze sa tylko 2 przypadki, wyrzuci nam blad, poniewaz ,musi byc default w razie wystapienia np nulla
            default:
                throw new RateCalculateException();
        }

    }

    //obliczanie kazdej kolejnej raty
    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpaymentRate, Rate previousRate) {
        //do wyboru kredyt o ratach stalych i zmiennych
        switch (inputData.getRateType()) {
            case CONSTANT:
                return calculateConstantRate(inputData, overpaymentRate, previousRate);
            case DECREASING:
                return calculateDecreasingRate(inputData, overpaymentRate, previousRate);

            //mimo, ze sa tylko 2 przypadki, wyrzuci nam blad, poniewaz ,musi byc default w razie wystapienia np nulla
            default:
                throw new RateCalculateException();
        }
    }

    private BigDecimal calculateQ(BigDecimal interestPercent) {
        return interestPercent.divide(YEAR, 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }

    private BigDecimal calculateConstantCapitalAmount(BigDecimal rateAmount, BigDecimal interestAmount) {
        //kwota raty minus  odsetki
        return rateAmount.subtract(interestAmount);
    }

    private BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent) {
        return residualAmount.multiply(interestPercent).divide(YEAR, 10, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateConstantRateAmount(BigDecimal q, BigDecimal amount, BigDecimal monthsDuration) {
        return amount
                .multiply(q.pow(monthsDuration.intValue()))
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(q.pow(monthsDuration.intValue()).subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDecreasingCapitalAmount(BigDecimal amount, BigDecimal monthsDuration) {
        return amount.divide(monthsDuration, 10, RoundingMode.HALF_UP);
    }

    private RateAmounts calculateConstantRate(InputData inputData, Overpayment overpayment) {
        //parametr Q potrzebny do wylicznia raty kredytu

        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = inputData.getAmount();

        BigDecimal q = calculateQ(interestPercent);

        BigDecimal rateAmount = calculateConstantRateAmount(q, inputData.getAmount(), inputData.getMonthsDuration());
        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);

    }

    private RateAmounts calculateConstantRate(InputData inputData, Overpayment overpayment, Rate previousRate) {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();

        BigDecimal q = calculateQ(interestPercent);

        BigDecimal rateAmount = calculateConstantRateAmount(q, inputData.getAmount(), inputData.getMonthsDuration());
        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private RateAmounts calculateDecreasingRate(InputData inputData, Overpayment overpayment) {

        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = inputData.getAmount();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateDecreasingCapitalAmount(residualAmount, inputData.getMonthsDuration());
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private RateAmounts calculateDecreasingRate(InputData inputData, Overpayment overpayment, Rate previousRate) {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();


        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateDecreasingCapitalAmount(inputData.getAmount(), inputData.getMonthsDuration());
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }
}
