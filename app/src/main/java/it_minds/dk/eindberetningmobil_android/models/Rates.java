package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Rates
 *
 * @license see ..
 */
public class Rates {
    private int Id;
    private String Description;
    private String Year;


    public Rates(int id, String description, String year) {
        Id = id;
        Description = description;
        Year = year;
    }

    /**
     * parseFromJson description here
     *
     * @return Rates
     */
    public static Rates parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        int Id = obj.optInt("Id");
        String Description = obj.optString("Description");
        String Year = obj.optString("Year");
        return new Rates(Id, Description, Year);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<Rates>
     */
    public static ArrayList<Rates> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<Rates> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * @return int
     */
    public int getId() {
        return this.Id;
    }

    /**
     * @return int
     */
    public void setId(int newVal) {
        this.Id = newVal;
    }

    /**
     * @return String
     */
    public String getDescription() {
        return this.Description;
    }

    /**
     * @return String
     */
    public void setDescription(String newVal) {
        this.Description = newVal;
    }

    /**
     * @return String
     */
    public String getYear() {
        return this.Year;
    }

    /**
     * @return String
     */
    public void setYear(String newVal) {
        this.Year = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Id", Id);
        result.put("Description", Description);
        result.put("Year", Year);
        return result;

    }
}