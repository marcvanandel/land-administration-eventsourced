package nl.marcvanandel.land_administration.command;

/**
 * Runtime exception in this domain.
 *
 * @author Marc van Andel
 * @since 0.1 on 7-4-2017
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

}
