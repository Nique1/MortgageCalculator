package MortgageCalculator.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

public class InputData {

    private static final BigDecimal PERCENT = BigDecimal.valueOf(100);
    //Mapa, ktora umozliwi przypadek nadplacenia rat i skrocenia dlugosci trwania kredytu
    //Integer -> liczba miesiecy
    // Dig Decimal -> kwota wysokosci nadplaty
    //schemat -> najpierw splaca jest rata, potem jest nadplacana
    Map<Integer, BigDecimal> overpaymentSchema = Map.of(
            5, BigDecimal.valueOf(10000),
            6, BigDecimal.valueOf(10000),
            7, BigDecimal.valueOf(10000),
            8, BigDecimal.valueOf(10000)
    );
    private LocalDate repaymentStartDate = LocalDate.of(2020, 1, 6);
    private BigDecimal wiborPercent = new BigDecimal("1.73");
    private BigDecimal amount = new BigDecimal("298000");
    private BigDecimal monthsDuration = BigDecimal.valueOf(180);
    private RateType rateType = RateType.DECREASING;
    //marza banku
    //calkowite oprocentowanie kredytu -> marza banku + stawka wibor
    private BigDecimal bankMarginPercent = new BigDecimal("1.9");
    //zachowanie po nadplacie kredytu
    //1. zminiejszenie raty
    //2. rata ta sama, zmniejszenie dlugosci trwania kredytu.. mozna w obu przypadkach to zrobic enumem. Tutaj zastosujemy Stringa i stale w klasie
    private String overpaymentReduceWay = Overpayment.REDUCE_PERIOD;


    //prowizja dla banku naliczana, kiedy dokona sie nadplaty przed okreslona liczba miesiecy
    private BigDecimal overpaymentProvisionPercent = BigDecimal.valueOf(3);
    private BigDecimal overpaymentProvisionMonths = BigDecimal.valueOf(36);

    //withery

    public InputData withOverpaymentSchema(Map<Integer, BigDecimal> overpaymentSchema) {
        this.overpaymentSchema = overpaymentSchema;
        return this;
    }

    public InputData withRepaymentStartDate(LocalDate repaymentStartDate) {
        this.repaymentStartDate = repaymentStartDate;
        return this;
    }


    public InputData withOverpaymentReduceWay(String overpaymentReduceWay) {
        this.overpaymentReduceWay = overpaymentReduceWay;
        return this;
    }


    public InputData withOverpaymentProvisionPercent(BigDecimal overpaymentProvisionPercent) {
        this.overpaymentProvisionPercent = overpaymentProvisionPercent;
        return this;
    }

    public InputData withOverpaymentProvisionMonths(BigDecimal overpaymentProvisionMonths) {
        this.overpaymentProvisionMonths = overpaymentProvisionMonths;
        return this;
    }

    public InputData withWiborPercent(BigDecimal wiborPercent) {
        this.wiborPercent = wiborPercent;
        return this;
    }

    public InputData withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public InputData withMonthsDuration(BigDecimal monthsDuration) {
        this.monthsDuration = monthsDuration;
        return this;
    }

    public InputData withRateType(RateType rateType) {
        this.rateType = rateType;
        return this;
    }


    public InputData withBankMarginPercent(BigDecimal bankMarginPercent) {
        this.bankMarginPercent = bankMarginPercent;
        return this;
    }


    //gettery
    public LocalDate getRepaymentStartDate() {
        return repaymentStartDate;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getMonthsDuration() {
        return monthsDuration;
    }

    public RateType getRateType() {
        return rateType;
    }

    //oprocentowanie to wibor i marza banku -> z 2 getterow robimy 1
    public BigDecimal getInterestPercent() {
        return wiborPercent.add(bankMarginPercent).divide(PERCENT, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal getInterestDisplay() {
        return wiborPercent.add(bankMarginPercent.setScale(2, RoundingMode.HALF_UP));
    }

    public Map<Integer, BigDecimal> getOverpaymentSchema() {
        return overpaymentSchema;
    }

    public String getOverpaymentReduceWay() {
        return overpaymentReduceWay;
    }

    public BigDecimal getOverpaymentProvisionPercent() {
        return overpaymentProvisionPercent.divide(PERCENT, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal getOverpaymentProvisionMonths() {
        return overpaymentProvisionMonths;
    }
}
