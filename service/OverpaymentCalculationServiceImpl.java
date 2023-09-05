package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.Overpayment;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class OverpaymentCalculationServiceImpl implements OverpaymentCalculationService{


    @Override
    public Overpayment calculate(BigDecimal rateNumber, InputData inputData) {
        BigDecimal overpaymentAmount = calculateAmount(rateNumber,inputData.getOverpaymentSchema())
                //jesli Optional bedzie empty to metoda zwroci zero
                .orElse(BigDecimal.ZERO);
        BigDecimal overpaymentProvision = calculateProvision(rateNumber, overpaymentAmount, inputData);

        return new Overpayment(overpaymentAmount,overpaymentProvision);
    }
    //korzystamy z klasy Optional, bo liczona kwota zalezy od tego czy mamy wgl nadplate
    private Optional<BigDecimal> calculateAmount(BigDecimal rateNumber, Map<Integer, BigDecimal> overpaymentSchema) {
        //iterowanie po kazdej racie w schemacie nadplat
        for (Map.Entry<Integer, BigDecimal> entry : overpaymentSchema.entrySet()) {
            //jesli numer raty pokryje sie z numerem wystapienia w entry -> zwracana jest dana nadplata
            if(rateNumber.equals(BigDecimal.valueOf(entry.getKey()))){
                return Optional.of(entry.getValue());
            }

        }

        return Optional.empty();
    }

    private BigDecimal calculateProvision(BigDecimal rateNumber, BigDecimal overpaymentAmount, InputData inputData) {
        //jesli nadplata nie wystapila -> zwrocone zero
        if(BigDecimal.ZERO.equals(overpaymentAmount)){
            return BigDecimal.ZERO;
        }

        //jesli numer raty wiekszy od ilosci miesiecy w ktorych nastapila nadplata -> zwrocone zero
        if(rateNumber.compareTo(inputData.getOverpaymentProvisionMonths())>0){
            return BigDecimal.ZERO;
        }

        //prowizja naliczana przez bank w momencie nadplaty
        return overpaymentAmount.multiply(inputData.getOverpaymentProvisionPercent());
    }
}
