package it_minds.dk.eindberetningmobil_android.settings;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.models.Tokens;

/**
 * Created by kasper on 28-06-2015.
 * handles all storage on the device, though sharedPrefs (a private one).
 */
public class MainSettings {


    //<editor-fold desc="Constants / indexes">
    private static final String PREF_NAME = "settings";
    private static final String PROVIDER_INDEX = "PROVIDER_INDEX";
    private static final String TOKEN_INDEX = "TOKEN_INDEX";
    private static final String RATES_INDEX = "RATES_INDEX";
    private static final String PROFILES_INDEX = "PROFILES_INDEX";
    private static final String SERVICE_INDEX = "SERVICE_INDEX";
    //</editor-fold>

    //<editor-fold desc="singleton">

    private static MainSettings instance;

    private final Context context;

    public MainSettings(Context context) {
        this.context = context;
    }

    public static MainSettings getInstance(Context context) {
        if (instance == null) {
            instance = new MainSettings(context);
        }
        return instance;
    }

    //</editor-fold>

    //<editor-fold desc="Helper methods">
    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    //</editor-fold>

    //<editor-fold desc="Provider">

    /**
     * haveProvider
     *
     * @return boolean
     */
    public boolean haveProvider() {
        return getPrefs().getString(PROVIDER_INDEX, null) != null;
    }

    /**
     * getProvider
     *
     * @return Provider
     */
    public Provider getProvider() {
        String val = getPrefs().getString(PROVIDER_INDEX, null);
        if (val == null) {
            return null;
        }
        try {
            return Provider.parseFromJson(new JSONObject(val));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * setProvider
     *
     * @return void
     */
    public void setProvider(Provider newVal) {
        String json = null;
        if (newVal != null) {
            json = newVal.saveToJson().toString();
        }
        getPrefs().edit().putString(PROVIDER_INDEX, json).commit();
    }
    //</editor-fold>


    //<editor-fold desc="token">

    /**
     * haveToken
     *
     * @return boolean
     */
    public boolean haveToken() {
        return getPrefs().getString(TOKEN_INDEX, null) != null;
    }

    /**
     * getToken
     *
     * @return Tokens
     */
    public Tokens getToken() {
        String val = getPrefs().getString(TOKEN_INDEX, null);
        if (val != null) {
            try {
                return Tokens.parseFromJson(new JSONObject(val));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * setToken
     *
     * @return void
     */
    public void setToken(Tokens newVal) {
        getPrefs().edit().putString(TOKEN_INDEX, newVal.saveToJson().toString()).commit();
    }


    //</editor-fold>

    //<editor-fold desc="rates">
    public void setRates(ArrayList<Rates> rates) {
        JSONArray arr = Rates.saveAllToJson(rates);
        getPrefs().edit().putString(RATES_INDEX, arr.toString()).commit();
    }

    public ArrayList<Rates> getRates() {
        String val = getPrefs().getString(RATES_INDEX, null);
        if (val != null) {
            try {
                return Rates.parseAllFromJson(new JSONArray(val));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Profile getProfile() {
        String val = getPrefs().getString(PROFILES_INDEX, null);
        if (val != null) {
            try {
                return Profile.parseFromJson(new JSONObject(val));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void setProfile(Profile profile) {
        getPrefs().edit().putString(PROFILES_INDEX, profile.saveToJson().toString()).commit();
    }

    public void clear() {
        getPrefs().edit().clear().commit();
    }

    public void setServiceClosed(boolean serviceClosed) {
        getPrefs().edit().putBoolean(SERVICE_INDEX, serviceClosed).commit();
    }

    public boolean getServiceClosed() {
        return getPrefs().getBoolean(SERVICE_INDEX, true);
    }
    //</editor-fold>
}
