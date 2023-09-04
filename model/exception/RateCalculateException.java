package MortgageCalculator.model.exception;

public class RateCalculateException extends RuntimeException{
    public RateCalculateException() {
        super("Rate calculate cas not supported");
    }

    public RateCalculateException(String message) {
        super(message);
    }
}
