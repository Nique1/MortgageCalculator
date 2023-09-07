package MortgageCalculator;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Overpayment;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.RateType;
import MortgageCalculator.service.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        InputData inputData = new InputData()
                .withMonthsDuration(BigDecimal.valueOf(360))
                .withRateType(RateType.CONSTANT)
                .withOverpaymentReduceWay(Overpayment.REDUCE_RATE);


        //przyklad dependancy injection
        //mamy interfejs i jego implementacje
        PrintingService printingService = new PrintingServiceImpl();
        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
                new TimePointServiceImpl(),
                new AmountsCalculationServiceImpl(
                        new ConstantAmountsCalculationServiceImpl(),
                        new DecreasingAmountsCalculationServiceImpl()
                ),
                new ResidualCalculationServiceImpl(),
                new OverpaymentCalculationServiceImpl(),
                new ReferenceCalculationServiceImpl()
        );

        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
                printingService,
                rateCalculationService,
                //metoda, ktora da implementacje intefejsu w postaci lambdy
                // metoda wywolana na klasie -> z uzyciem statica
                SummaryServiceFactory.create());


        mortgageCalculationService.calculate(inputData);

        //TODO
        //usunac blad z koncowa suma LEFT AMOUNT -> powinno byc 0

    }
}
