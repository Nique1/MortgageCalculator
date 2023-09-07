package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Overpayment;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.RateAmounts;

public interface ConstantAmountsCalculationService {


    RateAmounts calculate(InputData inputData, Overpayment overpayment);

    RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate);


}
