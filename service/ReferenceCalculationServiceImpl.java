package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.MortgageReference;
import MortgageCalculator.model.Rate;

public class ReferenceCalculationServiceImpl implements ReferenceCalculationService {

    //opcja ze skracaniem czasu trwania kredytu w przypadku nadplaty a nie skracania sammej wysokosci raty
    @Override
    public MortgageReference calculate(InputData inputData) {
        return new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
    }


}
