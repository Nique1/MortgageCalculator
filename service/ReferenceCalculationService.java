package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.MortgageReference;
import MortgageCalculator.model.Rate;

public interface ReferenceCalculationService {
    MortgageReference calculate(InputData inputData);

}
