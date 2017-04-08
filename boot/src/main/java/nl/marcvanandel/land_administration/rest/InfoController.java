package nl.marcvanandel.land_administration.rest;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="info", description=" ")
@RestController
@RequestMapping("/info")
public class InfoController implements Serializable {

    @ApiOperation(value="version")
    @RequestMapping(path = "/version", method = RequestMethod.GET)
    public String getVersion() {
        return getClass().getPackage().getImplementationVersion();
    }

}
