package nl.marcvanandel.land_administration.domain.event;

import nl.marcvanandel.land_administration.domain.datatype.Identity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public abstract class LandAdministrationEvent<I extends Identity> implements Serializable {

    private I aggregateIdentifier;
    
    public LandAdministrationEvent(I identity) {
        this.aggregateIdentifier = identity;
    }

    public I getAggregateIdentifier() {
        return aggregateIdentifier;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
