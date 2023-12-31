package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.MortgageResidual;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.RateAmounts;

import java.math.BigDecimal;

public class ResidualCalculationServiceImpl implements ResidualCalculationService {
    @Override
    public MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData) {
        //kwota kredytu minus kwota kapitalu jaki bedzie splacany
        BigDecimal residualAmount = calculateResidualAmount(rateAmounts, inputData.getAmount());
        BigDecimal residualDuration = inputData.getMonthsDuration().subtract(BigDecimal.ONE);

        return new MortgageResidual(residualAmount, residualDuration);
    }

    @Override
    public MortgageResidual calculate(RateAmounts rateAmounts, Rate previousRate) {
        //wartosci pozostale po kazdj kolejnej racie

        MortgageResidual residual = previousRate.getMortgageResidual();
        //wartosc z poprzedniej raty, ktroa zostala do splacenia minus kwota kapitalu jaka zostala splacona w tym miesiacu
        BigDecimal residualAmount = calculateResidualAmount(rateAmounts, residual.getAmount());

        //ilosc miesiecy jakie zostaly do splacenia w poprzedniej racie minus 1

        BigDecimal residualDuration = residual.getDuration().subtract(BigDecimal.ONE);
        return new MortgageResidual(residualAmount, residualDuration);
    }

    private BigDecimal calculateResidualAmount(RateAmounts rateAmounts, BigDecimal amount) {
        return amount
                .subtract(rateAmounts.getCapitalAmount())
                .subtract(rateAmounts.getOverpayment().getAmount())
                .max(BigDecimal.ZERO);
    }
}
