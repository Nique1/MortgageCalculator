package MortgageCalculator;

import MortgageCalculator.model.InputData;
import MortgageCalculator.service.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        InputData inputData = new InputData()
                .withMonthsDuration(BigDecimal.valueOf(180));


        //przyklad dependancy injection
        //mamy interfejs i jego implementacje
        PrintingService printingService = new PrintingServiceImpl();
        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
                new TimePointServiceImpl(),
                new AmountsCalculationServiceImpl(),
                new ResidualCalculationServiceImpl()
        ){

        };


        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
                printingService,
                rateCalculationService,
                //metoda, ktora da implementacje intefejsu w postaci lambdy
                // metoda wywolana na klasie -> z uzyciem statica
                SummaryServiceFactory.create());


        mortgageCalculationService.calculate(inputData);

    }
}
