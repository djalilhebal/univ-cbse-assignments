/**
 * Object names and their ports.
 *
 * <pre>
 * <strong>
 * - NOTE: We have decided to use ports 42000 to 42999 for our system.
 * </strong>
 * </pre>
 *
 * @implNote Although I could use the same identifier for both the attribute and method ("port"),
 *          I have chosen to use different identifiers for the sake of clarity and maybe stability/portability.
 *          See the following page for a possible JavaScript (Java Scripting API) error:
 *          <a href="https://stackoverflow.com/a/39391819">
 *              <b>pcgomes<b/>'s answer to "Java instance variable and method having same name" - Stack Overflow
 *              (#archived/20210119105223)
 *          <a/>
 */
public enum SmartHospitalThing {

    /** RMI Registry */
    RMI_REGISTRY (42000),

    /** ControleurImpl object */
    CONTROLEUR(42001);

    // ... Other things?

    // ---

    SmartHospitalThing(int portValue) {
        this.portValue = portValue;
    }

    private final int portValue;

    public int port() {
        return portValue;
    }

}
