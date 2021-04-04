/**
 * Patient.
 */
public class Patient  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String firstname;
    private String lastname;

    // ---

    public Patient(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // ---

    @Override
    public String toString() {
        return String.format("Patient#%d(%s %s)",
                getId(),
                getFirstname(),
                getLastname().toUpperCase());
    }

    // ---

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
