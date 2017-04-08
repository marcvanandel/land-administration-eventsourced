package nl.marcvanandel.land_administration.domain.datatype;

/**
 * A general identity.
 *
 * @author Marc van Andel
 * @since 0.1 on 5-4-2017
 */
public class Identity {

    private Long id;

    public Identity(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identity identity = (Identity) o;

        return id != null ? id.equals(identity.id) : identity.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
