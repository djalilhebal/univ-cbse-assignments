package me.djalil.modulepoc.devoir1;


/**
 * Names and their ports.
 *
 * <pre>
 * Note: The value can be just the suffix 000
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
public enum SmartStockThing {

    /** RMI Registry */
    RMI_REGISTRY (42000),

    /** StockImpl object */
    STOCK (42001);

    // Other things?

    // ---

    private final int portValue;
    public int port() {return portValue;}
    SmartStockThing(int portValue) {
        this.portValue = portValue;
    }

}
