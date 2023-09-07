package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.MortgageReference;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.RateAmounts;

public interface ReferenceCalculationService {

    MortgageReference calculate(InputData inputData);

    MortgageReference calculate(InputData inputData, RateAmounts rateAmounts, Rate previousRate);
}
