package it_minds.dk.eindberetningmobil_android.constants;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by kasper on 12-07-2015.
 * A helper to convert meters (As a double value) to km String, and only use 1 decimals precission
 */
public class DistanceDisplayer {
    private static DecimalFormat decimalFormat = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));

    /**
     * Converts the distance in meters to km prettified string.
     * @param distance the distance in meters
     * @return Prettified String to be shown to the user
     */
    public static String formatDistance(double distance) {
        return decimalFormat.format(distance / 1000.0d).replace('.', ',');//danish style
    }

    /**
     * Converts the distance in meters to a string (With format "X.X") for upload to database
     * (Needs to be '.' seperator for database)
     * @param distance the distance in meters
     * @return A string representing the distance in km, usable for database upload
     */
    public static String formatDisanceForUpload(double distance){
        return decimalFormat.format(distance / 1000.0d);//english style (With '.' as seperator)
    }

    /**
     * Converts the accuracy in meters to a comma separated string
     * @param acc The accuracy in meters
     * @return Prettified string to shown to the user
     */
    public static String formatAccuracy(double acc){
        return decimalFormat.format(acc).replace('.', ',');//danish style
    }
}
