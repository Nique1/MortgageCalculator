package MortgageCalculator.service;

import MortgageCalculator.model.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class RateCalculationServiceImpl implements RateCalculationService {

    private final TimePointService timePointService;
    private final AmountsCalculationService amountsCalculationService;
    private final ResidualCalculationService residualCalculationService;

    public RateCalculationServiceImpl(
            TimePointService timePointService,
            AmountsCalculationService amountsCalculationService,
            ResidualCalculationService residualCalculationService
    ) {
        this.timePointService = timePointService;
        this.amountsCalculationService = amountsCalculationService;
        this.residualCalculationService = residualCalculationService;
    }

    @Override
    public List<Rate> calculate(InputData inputData) {
        List<Rate> rates = new LinkedList<>();

        //pierwsza rata liczona inaczej niz kolejne
        BigDecimal rateNumber = BigDecimal.ONE;

        Rate firstRate = calculateRate(rateNumber, inputData);
        rates.add(firstRate);

        Rate previousRate = firstRate;
        //kazda kolejna rata

        for (BigDecimal index = rateNumber.add(BigDecimal.ONE); // tutaj zaczynamy od i = 2
             index.compareTo(inputData.getMonthsDuration()) <= 0;
             index = index.add(BigDecimal.ONE)
        ) { // i++
            Rate nextRate = calculateRate(index, inputData, previousRate);
            //dodanie do listy
            rates.add(nextRate);
            previousRate = nextRate;
        }
        return rates;
    }

    private Rate calculateRate(BigDecimal rateNumber, InputData inputData) {
        TimePoint timePoint = timePointService.calculate();
        RateAmounts rateAmounts = amountsCalculationService.calculate();
        MortgageResidual mortgageResidual = residualCalculationService.calculate();

        return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidual);
    }

    //dodajemy kolejny parametr pozwalajacy oblicznac kolejne raty na podstawie poprzednich
    private Rate calculateRate(BigDecimal rateNumber, InputData inputData, Rate previousRate) {

        TimePoint timePoint = timePointService.calculate();
        RateAmounts rateAmounts = amountsCalculationService.calculate();
        MortgageResidual mortgageResidual = residualCalculationService.calculate();
        return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidual);
    }
}
