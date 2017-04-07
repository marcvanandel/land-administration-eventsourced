package nl.marcvanandel.land_administration.api;

/**
 * Response of the {@link CommandRequest}.
 *
 * @author Marc van Andel
 * @since 0.1 on 7-4-2017
 */
public class CommandResponse {

    private final ResponseCode responseCode;
    private final String message;

    private CommandResponse(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.message = null;
    }

    public CommandResponse(ResponseCode responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public enum ResponseCode {
        SUCCESS,
        ERROR
    }

}
