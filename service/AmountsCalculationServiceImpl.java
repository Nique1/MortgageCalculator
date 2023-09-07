package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Overpayment;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.RateAmounts;
import MortgageCalculator.model.exception.RateCalculateException;

public class AmountsCalculationServiceImpl implements AmountsCalculationService {

    //pola zawierajace klasy, na ktore rozbito ponizsza klase

    private final ConstantAmountsCalculationService constantAmountsCalculationService;
    private final DecreasingAmountsCalculationService decreasingAmountsCalculationService;

    public AmountsCalculationServiceImpl(
            ConstantAmountsCalculationService constantAmountsCalculationService,
            DecreasingAmountsCalculationService decreasingAmountsCalculationService
    )
    {
        this.constantAmountsCalculationService = constantAmountsCalculationService;
        this.decreasingAmountsCalculationService = decreasingAmountsCalculationService;
    }

    //obliczanie raty 1
    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment) {
        //do wyboru kredyt o ratach stalych i zmiennych
        switch (inputData.getRateType()) {
            case CONSTANT:
                return constantAmountsCalculationService.calculate(inputData, overpayment);
            case DECREASING:
                return decreasingAmountsCalculationService.calculate(inputData, overpayment);

            //mimo, ze sa tylko 2 przypadki, wyrzuci nam blad, poniewaz ,musi byc default w razie wystapienia np nulla
            default:
                throw new RateCalculateException();
        }

    }

    //obliczanie kazdej kolejnej raty
    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpaymentRate, Rate previousRate) {
        //do wyboru kredyt o ratach stalych i zmiennych
        switch (inputData.getRateType()) {
            case CONSTANT:
                return constantAmountsCalculationService.calculate(inputData, overpaymentRate, previousRate);
            case DECREASING:
                return decreasingAmountsCalculationService.calculate(inputData, overpaymentRate, previousRate);

            //mimo, ze sa tylko 2 przypadki, wyrzuci nam blad, poniewaz musi byc default w razie wystapienia np nulla
            default:
                throw new RateCalculateException();
        }
    }




}
