package MortgageCalculator.service;

import MortgageCalculator.model.Rate;

import java.math.BigDecimal;

public interface Function {

    BigDecimal calculate(Rate rate);
}
