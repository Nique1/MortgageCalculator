package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.MortgageResidual;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.RateAmounts;

public interface ResidualCalculationService {
    MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData);
    MortgageResidual calculate(RateAmounts rateAmounts, Rate previousRate);
}
