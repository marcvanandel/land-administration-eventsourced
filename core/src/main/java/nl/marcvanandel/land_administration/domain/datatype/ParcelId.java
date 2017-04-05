package nl.marcvanandel.land_administration.domain.datatype;

import nl.marcvanandel.land_administration.domain.model.Parcel;

/**
 * An identity of a {@link Parcel}.
 *
 * @author Marc van Andel
 * @since 0.1 on 5-4-2017
 */
public class ParcelId extends Identity {

    public ParcelId(Long id) {
        super(id);
    }

}
