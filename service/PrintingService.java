package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.Summary;

import java.util.List;

public interface PrintingService {
    //constants
    String INTEREST_SUM = "INTEREST SUM: ";
    String RATE_NUMBER = "RATE NUMBER: ";
    String YEAR = "YEAR: ";
    String MONTH = " MONTH: ";
    String DATE = "DATE: ";
    String MONTHS = " MONTHS: ";
    String RATE = " RATE: ";
    //odsetki
    String INTEREST = "INTEREST: ";
    String CAPITAL = "CAPITAL: ";
    String LEFT_AMOUNT = "LEFT AMOUNT: ";
    String LEFT_MONTHS = "LEFT MONTHS: ";
    String MORTGAGE_AMOUNT = "MORTGAGE AMOUNT: ";
    String MORTGAGE_PERIOD = "MORTGAGE PERIOD: ";

    String CURRENCY = " ZL ";
    String NEW_LINE = "\n";
    String PERCENT = "% ";

    void printInputDataInfo(final InputData inputData);

    void printRates(List<Rate> rates);

    void printSummary(Summary summary);

}
