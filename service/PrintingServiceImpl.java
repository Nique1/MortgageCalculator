package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.Summary;

import java.util.List;

public class PrintingServiceImpl implements PrintingService {
    @Override
    //adnotacja do ignorowania warningow
    @SuppressWarnings("StringBufferReplaceableByString")
    public void printInputDataInfo(InputData inputData) {

        StringBuilder msg = new StringBuilder(NEW_LINE);
        //printing input
        //za kazdym razem drukuje w nowej linii

        msg.append(MORTGAGE_AMOUNT).append(inputData.getAmount()).append(CURRENCY);
        msg.append(NEW_LINE);
        msg.append(MORTGAGE_PERIOD).append(inputData.getMonthsDuration()).append(MONTHS);
        msg.append(NEW_LINE);
        msg.append(INTEREST).append(inputData.getInterestDisplay()).append(PERCENT);
        msg.append(NEW_LINE);

        printMessage(msg.toString());
    }


    //metoda drukujaca
    private void printMessage(String sb) {
        System.out.println(sb);
    }

    @Override
    public void printRates(List<Rate> rates) {
        String format = "%s %s " +
                "%s %s " +
                "%s %s " +
                "%s %s " +
                "%s %s " +
                "%s %s " +
                "%s %s " +
                "%s %s " +
                "%s %s " +
                "";


        for (Rate rate : rates) {
            String message = String.format(format,
                    RATE_NUMBER, rate.getRateNumber(),
                    DATE, rate.getTimePoint().getDate(),
                    YEAR, rate.getTimePoint().getYear(),
                    MONTH, rate.getTimePoint().getMonth(),
                    RATE, rate.getRateAmounts().getRateAmount(),
                    INTEREST, rate.getRateAmounts().getInterestAmount(),
                    CAPITAL, rate.getRateAmounts().getCapitalAmount(),
                    LEFT_AMOUNT, rate.getMortgageResidual().getAmount(),
                    LEFT_MONTHS, rate.getMortgageResidual().getDuration()
            );
            printMessage(message);

            //po 12 miesiacach drukowana bedzie przerwa

            if(rate.getRateNumber().intValue() % 12 == 0){
                System.out.println();
            }
        }
    }

    @Override
    @SuppressWarnings("StringBufferReplaceableByString")
    public void printSummary(Summary summary) {
        StringBuilder msg = new StringBuilder(NEW_LINE);
        msg.append(INTEREST_SUM).append(summary.getInterestSum()).append(CURRENCY);
        msg.append(NEW_LINE);

        printMessage(msg.toString());
    }
}
