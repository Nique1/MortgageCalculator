package MortgageCalculator.service;

import MortgageCalculator.model.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class RateCalculationServiceImpl implements RateCalculationService {

    //ponizsze pola wynikaja z elementow listy Rate
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
    //rata pierwsza
    private Rate calculateRate(BigDecimal rateNumber, InputData inputData) {
        //metoda calculate w timePoint bedzie potrzebowala parametru rateNumber -> na jego podstawie wykonywane sa obliczenia
        TimePoint timePoint = timePointService.calculate(rateNumber,inputData);

        //rata pierwsza bedzie liczona z inpputData
        RateAmounts rateAmounts = amountsCalculationService.calculate(inputData);
        //kwota pozostalego kredytu z poprzedniego miesiaca minus kwota kapitalu jaka splacono w miesiacu biezacym

        MortgageResidual mortgageResidual = residualCalculationService.calculate(rateAmounts, inputData);

        return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidual);
    }

    //kazda kolejna rata
    // dodajemy kolejny parametr pozwalajacy oblicznac kolejne raty na podstawie poprzednich
    private Rate calculateRate(BigDecimal rateNumber, InputData inputData, Rate previousRate) {

        TimePoint timePoint = timePointService.calculate(rateNumber,inputData);
        //kazda kolejna rata bedzie liczona na podstawie poprzeniej
        RateAmounts rateAmounts = amountsCalculationService.calculate(inputData, previousRate);

        MortgageResidual mortgageResidual = residualCalculationService.calculate(rateAmounts,previousRate);
        return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidual);
    }
}
