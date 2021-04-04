public interface VitalSignsReader {

    /**
     * Body Temperature. BT.
     *
     * @return body temperature in °C.
     */
    float getBodyTemperature();

    /**
     * Blood Pressure. BP.
     *
     * @return a tuple of {Systolic, Diastolic} blood pressure values in mm Hg.
     */
    int[] getBloodPressure();

    /**
     * Heart Rate. HR.
     *
     * @return number of beats per minute.
     */
    int getHeartRate();

    /**
     * Respiratory Rate. RR.
     *
     * @return number of breaths per minute.
     */
    int getRespiratoryRate();

}
