import java.util.Random;

/**
 * A class that represents vital signs readings of a <strong>random normal/healthy adult</strong>
 * (say, <i>Rando NORMALHEART</i>).
 *
 * <blockquote>
 *     Normal vital sign ranges for the average healthy adult while resting are:
 *
 *     <ul>
 *         <li> Blood pressure: 90/60 mm Hg to 120/80 mm Hg
 *         <li> Breathing: 12 to 18 breaths per minute
 *         <li> Pulse: 60 to 100 beats per minute
 *         <li> Temperature: 97.8°F to 99.1°F (36.5°C to 37.3°C); average 98.6°F (37°C)
 *     </ul>
 *
 *     <cite>
 *      -- <a href="https://medlineplus.gov/ency/article/002341.htm">Vital signs: MedlinePlus Medical Encyclopedia</a>
 *         (`#archived/20201114001924`)
 *     </cite>
 * </blockquote>
 */
public class VitalSignsRandomizer implements VitalSignsReader {

    /** Random BT */
    @Override
    public float getBodyTemperature() {
        return randomInclusive(36.5f, 37.3f);
    }

    /** Random BP */
    @Override
    public int[] getBloodPressure() {
        // Systolic
        int sys = randomInclusive(90, 120);
        // Diastolic
        int dias = randomInclusive(60, 80);
        int[] bloodPressure = {sys, dias};

        // "Pulse pressure is the difference between systolic and diastolic blood pressure." -- Wikipedia
        // int pulsePressure = sys - dias;

        return bloodPressure;
    }

    /** Random HR */
    @Override
    public int getHeartRate() {
        return randomInclusive(60, 100);
    }

    /** Random RR */
    @Override
    public int getRespiratoryRate() {
        return randomInclusive(12, 18);
    }

    // ---

    static private int randomInclusive(int min, int max) {
        int randomInt = min + (int) (new Random().nextFloat() * (max - min));
        return randomInt;
    }

    static private float randomInclusive(float min, float max) {
        float randomFloat = min + new Random().nextFloat() * (max - min);
        return randomFloat;
    }

}
