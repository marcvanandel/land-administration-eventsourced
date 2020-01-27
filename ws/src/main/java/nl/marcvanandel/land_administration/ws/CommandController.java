package nl.marcvanandel.land_administration.ws;

import nl.marcvanandel.land_administration.api.CommandRequest;
import nl.marcvanandel.land_administration.api.CommandResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final CommandGateway commandGateway;

    private final Logger logger = LoggerFactory.getLogger(CommandController.class);

    @Autowired
    public CommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(value = "process",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_XML_VALUE,
        consumes = MediaType.APPLICATION_XML_VALUE)
    public CommandResponse verwerken(@RequestBody CommandRequest request) {
        try {
            logger.debug("request : {}", request);
            final CommandResponse response = commandGateway.sendAndWait(request);
            logger.debug("response: {}", response);
            return response;
        } catch (DomainException e) {
            final CommandResponse response = new CommandResponse(CommandResponse.ResponseCode.ERROR, e.getMessage());
            logger.warn("response: {}", response);
            return response;
        } catch (RuntimeException exception) {
            logger.error("error: " + request, exception);
            throw exception;
        }
    }

}
