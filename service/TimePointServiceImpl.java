package MortgageCalculator.service;

import MortgageCalculator.model.InputData;
import MortgageCalculator.model.TimePoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TimePointServiceImpl implements TimePointService {
    private static final BigDecimal YEAR = BigDecimal.valueOf(12);
    @Override
    public TimePoint calculate(BigDecimal rateNumber, InputData inputData) {
        //data 1 to moment rozpoczecia kredytu
        //kazda kolejna data to bumer raty - 1, bo rate indeksujemy od 1

        LocalDate date = calculateDate(rateNumber, inputData);
        //zwracamy TimePoint(), ktory jako argument przyjmuje date, rok, miesiac
        //nalezy utworzyc metody, ktore na podstawie numeru raty nam to policza
        BigDecimal year = calculateYear(rateNumber);
        BigDecimal month = calculateMonth(rateNumber);


        return new TimePoint(date,year,month);
    }


    //tutaj w debuggerze korzystamy z watchera by za kazdym wywolaniem metody sprawdzac kolejne wartosci, F9 by korzystac z tego watchera
    private LocalDate calculateDate(BigDecimal rateNumber, InputData inputData) {
        return inputData.getRepaymentStartDate()
                .plus(rateNumber.subtract(BigDecimal.ONE).intValue(), ChronoUnit.MONTHS);
    }

    private BigDecimal calculateYear(final BigDecimal rateNumber){
       //rok musi byc co najmniej 1, jesli bedzie mniejszy niz 1 to wyjdzie nam 0
        //dokladamy metode max(), ktora dziala troche jak if -> jesli rok jest mniejszy niz 1 to bierze to co jest przed meotda max(), jesli mniejsze niz 1 to bierze 1
        //trzeba tez zmienic RoundingMode z HALF_UP NA UP
        //ALT+F8 -> TESTOWANIE FRAGMENTU KODU W DEBUGU
        return rateNumber.divide(YEAR, RoundingMode.UP).max(BigDecimal.ONE);
    }

    //miesiace to reszta z dzielenia przez 12
    private BigDecimal calculateMonth(final BigDecimal rateNumber){
        return BigDecimal.ZERO.equals(rateNumber.remainder(YEAR)) ? YEAR : rateNumber.remainder(YEAR);
    }
}
