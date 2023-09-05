package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Overpayment;

import java.math.BigDecimal;

public interface OverpaymentCalculationService {
    //serwis do liczenia nadplaty w miesiacu
    Overpayment calculate(BigDecimal rateNumber, InputData inputData);
}
