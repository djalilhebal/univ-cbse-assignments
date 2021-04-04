import java.time.OffsetDateTime;
import java.util.Arrays;

/**
 * Vital Signs read-only data object.
 */
public class VitalSigns implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    final private Patient patient;
    final private float bodyTemperature;
    final private int[] bloodPressure;
    final private int heartRate;
    final private int respiratoryRate;

    final private OffsetDateTime timestamp = OffsetDateTime.now();

    // ---

    public VitalSigns(Patient patient, float bodyTemperature, int[] bloodPressure, int heartRate, int respiratoryRate) {
        this.patient = patient;
        this.bodyTemperature = bodyTemperature;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.respiratoryRate = respiratoryRate;
    }

    // ---

    public Patient getPatient() {
        return patient;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Body Temperature. BT.
     */
    public float getBodyTemperature() {
        return bodyTemperature;
    }

    /**
     * Blood Pressure. BP.
     *
     * @return a tuple of {Systolic, Diastolic} blood pressure values.
     */
    public int[] getBloodPressure() {
        return bloodPressure;
    }

    /**
     * Heart Rate. HR.
     */
    public int getHeartRate() {
        return heartRate;
    }

    /**
     * Respiratory Rate. RR.
     */
    public int getRespiratoryRate() {
        return respiratoryRate;
    }

    @Override
    public String toString() {
        return String.format(
                "Vitals: BT(%.1f), BP(%d/%d), HR(%d), RR(%d), timestamp(%s)",
                getBodyTemperature(),
                getBloodPressure()[0], getBloodPressure()[1],
                getHeartRate(),
                getRespiratoryRate(),
                getTimestamp().toString()
        );
    }
}
