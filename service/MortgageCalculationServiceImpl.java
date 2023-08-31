package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Rate;

import java.util.List;

public class MortgageCalculationServiceImpl implements MortgageCalculationService {

    //dependency injection
    //constructor injection
    //wydzielamy pole w interfejsie na interfejs
    private final PrintingService printingService;

    private final RateCalculationService rateCalculationService;
    //w konstruktorze przekazujemy co ten interfejs bedzie implementowalo
    public MortgageCalculationServiceImpl(
            PrintingService printingService,
            RateCalculationService rateCalculationService
    ) {
        this.printingService = printingService;
        this.rateCalculationService = rateCalculationService;
    }

    @Override
    public void calculate(InputData inputData) {
        //opcja 1 -> drukowanie przed policzeniem
        //tylko z tego miejsca mamy dostep, jakbysmy chcieli zmienic implementacje tego interfejsu to trzeba z palca
//        PrintingService printingService = new PrintingServiceImpl();
        printingService.printInputDataInfo(inputData);

        //oblicznaie kolejnych rat zgromadzonych w liscie
        List<Rate> rates = rateCalculationService.calculate(inputData);

    }
}
