package nl.marcvanandel.land_administration.domain.model;

import nl.marcvanandel.land_administration.domain.datatype.SubjectId;

/**
 * // TODO
 *
 * @author Marc van Andel
 * @since 0.1 on 27-2-2017
 */
public class Subject {

    private SubjectId subjectId;

    public SubjectId getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(SubjectId subjectId) {
        this.subjectId = subjectId;
    }
}
