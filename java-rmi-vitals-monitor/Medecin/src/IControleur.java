import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IControleur extends Remote {
    VitalSigns getVitalSigns() throws RuntimeException, RemoteException;
}
