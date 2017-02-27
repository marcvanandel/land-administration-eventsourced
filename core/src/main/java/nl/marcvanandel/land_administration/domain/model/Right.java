package nl.marcvanandel.land_administration.domain.model;

import nl.marcvanandel.land_administration.domain.datatype.RightType;
import nl.marcvanandel.land_administration.domain.datatype.Share;

import java.util.Set;

/**
 * A Right is a formal or informal entitlement to own, to do something, or to refrain from doing something. Examples
 * are: ownership right, tenancy right, possession, customary right, or informal right. A right can be an (informal)
 * use right. Rights may be overlapping, or may be in disagreement. A ‘restriction’ is a formal or informal
 * entitlement to refrain from doing something; e.g. it is not allowed to build within 50 meters from a road. A
 * ‘responsibility’ is a formal or informal obligation to do something; e.g. the responsibility to clean a water
 * canal or to maintain a road.
 *
 * @author Marc van Andel
 * @since 0.1 on 27-2-2017
 */
public class Right {

    private RightType type;
    private Share share;
    private Set<Subject> subjects;

}
