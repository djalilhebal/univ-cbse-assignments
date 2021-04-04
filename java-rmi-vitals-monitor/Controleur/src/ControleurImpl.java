import java.rmi.RemoteException;

public class ControleurImpl implements IControleur {

    public VitalSigns getVitalSigns() throws RemoteException {
        // A random normal adult named Rando NORMALHEART
        Patient patient = new Patient(25001, "Rando", "Normalheart");
        VitalSignsReader vitalsReader = new VitalSignsRandomizer();
        VitalSigns vitals = new VitalSigns(
                patient,
                vitalsReader.getBodyTemperature(),
                vitalsReader.getBloodPressure(),
                vitalsReader.getHeartRate(),
                vitalsReader.getRespiratoryRate()
        );
        return vitals;
    }

}
