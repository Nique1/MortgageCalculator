package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Overpayment;
import MortgageCalculator.model.Rate;
import MortgageCalculator.model.Summary;
import MortgageCalculator.model.exception.MortgageException;

import java.util.List;
import java.util.Optional;

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

        //opcjonalne wyswietlanie danych o overpaymencie
        Optional.of(inputData.getOverpaymentSchema())
                //jesli schemat nie jest uzupelniony
                .filter(schema -> schema.size() > 0)
                //jesli schemat jest uzupelniony
                .ifPresent(schema -> logOverpayment(msg, inputData));
        printMessage(msg.toString());
    }

    private void logOverpayment(StringBuilder msg, InputData inputData) {
        switch (inputData.getOverpaymentReduceWay()) {
            case Overpayment.REDUCE_PERIOD:
                msg.append(OVERPAYMENT_REDUCE_PERIOD);
                break;
            case Overpayment.REDUCE_RATE:
                msg.append(OVERPAYMENT_REDUCE_RATE);
                break;
            default:
                throw new MortgageException();
        }

        msg.append(NEW_LINE);
        msg.append(OVERPAYMENT_FREQUENCY).append(inputData.getOverpaymentSchema());
        msg.append(NEW_LINE);
    }

        //metoda drukujaca
        private void printMessage(String sb) {
            System.out.println(sb);
        }


    @Override
    @SuppressWarnings("StringBufferReplaceableByString")
    public void printSummary(Summary summary) {
        StringBuilder msg = new StringBuilder(NEW_LINE);
        msg.append(INTEREST_SUM).append(summary.getInterestSum()).append(CURRENCY);
        msg.append(NEW_LINE);
        msg.append(OVERPAYMENT_PROVISION).append(summary.getOverpaymentProvisionSum()).append(CURRENCY);
        msg.append(NEW_LINE);
        msg.append(LOSTS_SUM).append(summary.getTotalLosts()).append(CURRENCY);
        msg.append(NEW_LINE);


        printMessage(msg.toString());
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
                    OVERPAYMENT, rate.getRateAmounts().getOverpayment().getAmount(),
                    LEFT_AMOUNT, rate.getMortgageResidual().getAmount(),
                    LEFT_MONTHS, rate.getMortgageResidual().getDuration()
            );
            printMessage(message);

            //po 12 miesiacach drukowana bedzie przerwa

            if (rate.getRateNumber().intValue() % 12 == 0) {
                System.out.println();
            }
        }
    }
}
