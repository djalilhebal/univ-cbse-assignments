import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * <i lang="fr">Contrôleur<i/>.
 */
public class ControleurApp {

    // Host No. 39 ("San Kyu" or "Thank You") has a static local IP address.
    // sankyu.djalil.me's external IP address is periodically updated via dynamic DNS (DuckDNS).
    static final String THANK_YOU_LOCAL = "192.168.1.39";
    static final String THANK_YOU_EXTERNAL = "sankyu.djalil.me";
    static final String THANK_YOU_SERVER = THANK_YOU_EXTERNAL;

    public static void main(String[] args) {

        System.setProperty("java.rmi.server.hostname", THANK_YOU_SERVER);

        try {
            System.err.println("[Controleur] Started");

            System.err.println("[Controleur] Creating Registry...");
            Registry reg = LocateRegistry.createRegistry(SmartHospitalThing.RMI_REGISTRY.port());

            System.err.println("[Controleur] Starting the dynamic DNS updater...");
            new DuckUpdater()
                    .setDomain( System.getenv("MY_DUCKDNS_DOMAIN") )
                    .setToken( System.getenv("MY_DUCKDNS_TOKEN") )
                    // .setEvery(30) // minutes
                    .setIsSilent(true)
                    .start() // comment this line out to skip the DDNS thing
                    ;

            System.err.println("[Controleur] Instantiating and exporting ControleurImpl...");
            ControleurImpl obj = new ControleurImpl();

            // Object retObj = UnicastRemoteObject.exportObject(obj);
            // >>> "[Controleur][DEBUG] returnedObj is of type: MonitorImpl_Stub"
            Object retObj = UnicastRemoteObject.exportObject(obj, SmartHospitalThing.CONTROLEUR.port());
            // >>> "[Controleur][DEBUG] returnedObj is of type: com.sun.proxy.$Proxy0"
            System.err.println("[Controleur][DEBUG] returnedObj is of type: " + retObj.getClass().getCanonicalName());

            reg.rebind(SmartHospitalThing.CONTROLEUR.name(), obj);

            System.err.println("[Controleur] Ready");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
