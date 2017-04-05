package nl.marcvanandel.land_administration.domain.datatype;

import nl.marcvanandel.land_administration.domain.model.Subject;

/**
 * An identity of a {@link Subject}.
 *
 * @author Marc van Andel
 * @since 0.1 on 5-4-2017
 */
public class SubjectId extends Identity {

    public SubjectId(Long id) {
        super(id);
    }

}
