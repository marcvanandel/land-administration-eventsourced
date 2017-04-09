package nl.marcvanandel.land_administration.domain.event;

import nl.marcvanandel.land_administration.domain.datatype.ParcelId;
import nl.marcvanandel.land_administration.domain.datatype.RightId;
import nl.marcvanandel.land_administration.domain.datatype.SubjectId;
import nl.marcvanandel.land_administration.domain.model.Subject;

import java.util.Collections;
import java.util.Set;

/**
 * TODO:
 *
 * @author Marc van Andel
 * @since 0.1 on 27-2-2017
 */
public class RightTransferedEvent extends LandAdministrationEvent<ParcelId>{

    private RightId rightId;
    private SubjectId sellingSubject;
    private Set<Subject> buyingSubjects;

    public RightTransferedEvent(ParcelId parcelId, RightId rightId, SubjectId sellingSubjectId) {
        super(parcelId);
        this.rightId = rightId;
        this.sellingSubject = sellingSubjectId;
    }
    
    public ParcelId getParcelId() {
        return getAggregateIdentifier();
    }

    public RightId getRightId() {
        return rightId;
    }

    public SubjectId getSellingSubject() {
        return sellingSubject;
    }

    public Set<Subject> getBuyingSubjects() {
        return Collections.unmodifiableSet(buyingSubjects);
    }

    public RightTransferedEvent setBuyingSubjects(Set<Subject> buyingSubjects) {
        this.buyingSubjects = buyingSubjects;
        return this;
    }
}
