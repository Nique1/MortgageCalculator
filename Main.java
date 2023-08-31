package MortgageCalculator;

import MortgageCalculator.model.InputData;
import MortgageCalculator.service.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        InputData inputData = new InputData()
                .withAmount(new BigDecimal("29000"))
                .withMonthsDuration(BigDecimal.valueOf(150));


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
                rateCalculationService
        );


        mortgageCalculationService.calculate(inputData);

    }
}
