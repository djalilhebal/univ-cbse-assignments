import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * <i lang="fr">Médecin</i>.
 */
public class MedecinApp {

    // Host No. 39 ("San Kyu" or "Thank You").
    private static final String THANK_YOU_SERVER = "sankyu.djalil.me";

    public static void main(String[] args) {

        final int refreshInterval = 10; // in seconds

        try {
            System.err.println("[Medecin] Started");

            System.err.println("[Medecin] Locating the Registry and getting the Controleur stub...");
            Registry registry = LocateRegistry.getRegistry(THANK_YOU_SERVER, SmartHospitalThing.RMI_REGISTRY.port());
            IControleur stub = (IControleur) registry.lookup(SmartHospitalThing.CONTROLEUR.name());

            System.err.println(String.format("[Medecin] Updates about every %d seconds", refreshInterval));
            System.err.println("---");

            VitalSigns vitalSigns;
            while (true) {
                vitalSigns = stub.getVitalSigns();
                String output = vitalSigns.getPatient() + " / " + vitalSigns;
                System.out.print(output);
                delaySeconds(refreshInterval);
                eraseCharacters(output.length());
            }

        } catch (Exception e) {
            System.err.println("[Medecin] Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ---

    /**
     * Erase the `count` previous characters from the console output.
     *
     * It's more like, "Replace everything with whitespace and go back to write over them".
     *
     * <strong><pre>
     * - NOTE: Tested on Ubuntu 18.04. It works fine in IntelliJ IDEA v2020.3 and Konsole/Bash v4.4.
     *   In Windows XP's cmd, it works correctly as long as the to-be-"erased" characters do not cause the line to wrap.
     *   Workaround: Increase the cmd's width buffer to be at least equal to the maximum line length (so it won't wrap).
     * </pre></strong>
     */
    private static void eraseCharacters(int count) {
        String backspaces = "";
        String whitespaces = "";
        for (int i = 0; i < count; i++) {
            backspaces += '\b';
            whitespaces += ' ';
        }

        System.out.print(backspaces);
        System.out.print(whitespaces);
        System.out.print(backspaces);

        System.out.flush();
    }

    private static void delaySeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignoredException) {}
    }

}
