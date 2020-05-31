package Tools.RunTime;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

/**
 * RunTimeCalculator Class, this class calculates the runtime code and prints it.
 *  @author LOG1CRS
 */
public class RunTimeCalculator {

    /**
     * Gets the first time, generally this method is called where you want to start the time count.
     * @return Instant, the time it was when this function was called
     */
    public static Instant start() {
        return Instant.now();
    }

    /**
     * Gets the last time, generally this method is called where you want to finish the time count.
     * @return Instant, the time it was when this function was called
     */
    public static Instant end() {
        return Instant.now();
    }

    /**
     * Prints runtime code, gets the total seconds between the start and end time,
     * if the total seconds is less than 60 just prints the seconds,
     * if the total seconds is more than 60 divides total seconds by 60 splitting the string according to the dot to get minutes and seconds.
     * @param start
     * @param end
     */
    public static void printRuntime(Instant start, Instant end) {

        String time = "The code runtime was: ";
        int totalSeconds = (int) ChronoUnit.SECONDS.between(start, end);

        if(totalSeconds > 60){
            String secondsInMinutes = String.valueOf((double) totalSeconds/60);

            String[] divideTime = secondsInMinutes.split(Pattern.quote("."));
            String minutes = divideTime[0];
            String secondsDivided = divideTime[1].substring(0, 2);

            time = time + minutes + " minutes " + secondsDivided + " seconds";
        }else{
            time = time + totalSeconds + " seconds";
        }


        System.out.println(time);
    }
}
