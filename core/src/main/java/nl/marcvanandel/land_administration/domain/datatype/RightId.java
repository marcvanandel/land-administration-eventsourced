package nl.marcvanandel.land_administration.domain.datatype;

import nl.marcvanandel.land_administration.domain.model.Right;

/**
 * An identity of a {@link Right}.
 *
 * @author Marc van Andel
 * @since 0.1 on 5-4-2017
 */
public class RightId extends Identity {

    public RightId(Long id) {
        super(id);
    }

}
