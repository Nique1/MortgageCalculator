package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.TimePoint;

import java.math.BigDecimal;

public interface TimePointService {

    TimePoint calculate(BigDecimal rateNumber, InputData inputData);
}
