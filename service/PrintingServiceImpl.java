package MortgageCalculator.service;

import MortgageCalculator.model.InputData;

public class PrintingServiceImpl implements PrintingService{
    @Override
    public void printInputDataInfo(InputData inputData) {
        //printing input
        //za kazdym razem drukuje w nowej linii
        StringBuilder msg = new StringBuilder(NEW_LINE);
        msg.append(MORTGAGE_AMOUNT).append(inputData.getAmount()).append(CURRENCY);
        msg.append(NEW_LINE);
        msg.append(MORTGAGE_PERIOD).append(inputData.getMonthsDuration()).append(MONTHS);
        msg.append(NEW_LINE);
        msg.append(INTEREST).append(inputData.getInterestDisplay()).append(PERCENT);
        msg.append(NEW_LINE);

        printMessage(msg);
    }

    //metoda drukujaca

    private void printMessage(StringBuilder sb){
        System.out.println(sb);
    }
}
