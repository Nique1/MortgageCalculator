package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Rate;

import java.util.List;

public interface RateCalculationService {
    //lista rat
    List<Rate> calculate(final InputData inputData);
}
