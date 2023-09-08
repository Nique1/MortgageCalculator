package MortgageCalculator.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtils {

    //prywatny konstruktor uniemozliwa tworzenie obiektow tej klasy
    private CalculationUtils() {
    }

    public static final BigDecimal YEAR = BigDecimal.valueOf(12);

    //static przy metodzie pozwoli wywolac metode bezposrednio na klasie
    public static BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent) {
        return residualAmount.multiply(interestPercent).divide(YEAR, 10, RoundingMode.HALF_UP);
    }
}
