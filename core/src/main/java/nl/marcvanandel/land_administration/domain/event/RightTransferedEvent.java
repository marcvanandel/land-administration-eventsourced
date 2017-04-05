package nl.marcvanandel.land_administration.domain.event;

import nl.marcvanandel.land_administration.domain.datatype.ParcelId;
import nl.marcvanandel.land_administration.domain.datatype.RightId;
import nl.marcvanandel.land_administration.domain.datatype.SubjectId;

import java.util.Set;

/**
 * // TODO
 *
 * @author Marc van Andel
 * @since 0.1 on 27-2-2017
 */
public class RightTransferedEvent {

    private ParcelId parcelId;
    private RightId rightId;
    private SubjectId sellingSubject;
    private Set<SubjectId> buyingSubjects;

}
