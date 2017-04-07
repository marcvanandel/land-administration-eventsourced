package nl.marcvanandel.land_administration.ws;

import nl.marcvanandel.land_administration.api.CommandRequest;
import nl.marcvanandel.land_administration.api.CommandResponse;
import nl.marcvanandel.land_administration.command.DomainException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller containing all command endpoints
 *
 * @author Marc van Andel
 * @since 0.1 on 7-4-2017
 */
@RestController
@RequestMapping("api")
public class CommandController {

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(value = "verwerken",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_XML_VALUE,
        consumes = MediaType.APPLICATION_XML_VALUE)
    public CommandResponse verwerken(@RequestBody CommandRequest registratieOpdracht) {
        try {
            final CommandResponse registratieAntwoord = commandGateway.sendAndWait(registratieOpdracht);
            return registratieAntwoord;
        } catch (DomainException e) {
            final CommandResponse registratieAntwoord =
                new CommandResponse(CommandResponse.ResponseCode.ERROR, e.getMessage());
            return registratieAntwoord;
        } catch (RuntimeException exception) {
//            logger.logException(registratieOpdracht, exception);
            throw exception;
        }
    }

}
