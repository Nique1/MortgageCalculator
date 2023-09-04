package MortgageCalculator.service;

import MortgageCalculator.model.Rate;
import MortgageCalculator.model.Summary;

import java.util.List;

public interface SummaryService {
    Summary calculate(List<Rate> rates);
}
