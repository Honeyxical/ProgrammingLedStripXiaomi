package by.benikov.programming.led.strip.xiaomi.exception;

/**
 * Thrown when a socket error occurs
 */
public class YeelightSocketException extends Exception {
    /**
     * Constructor for the exception
     * @param cause Exception cause
     */
    public YeelightSocketException(Throwable cause) {
        super(cause);
    }
}
