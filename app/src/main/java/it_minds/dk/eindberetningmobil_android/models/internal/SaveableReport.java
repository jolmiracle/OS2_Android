package it_minds.dk.eindberetningmobil_android.models.internal;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * SaveableReport
 *
  */
public class SaveableReport {
    private String jsonToSend;
    private String purpose;
    private String rateid;
    private double totalDistance;
    private DateTime createdAt;

    public SaveableReport(String jsonToSend, String purpose, String rateid, double totalDistance, DateTime createdAt) {

        this.jsonToSend = jsonToSend;
        this.purpose = purpose;
        this.rateid = rateid;
        this.totalDistance = totalDistance;
        this.createdAt = createdAt;
    }

    public SaveableReport(DrivingReport report) {

    }

    /**
     * parseAllFromJson description here
     *
     * @return List<SaveableReport>
     */
    public static List<SaveableReport> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<SaveableReport> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * parseFromJson description here
     *
     * @return SaveableReport
     */
    public static SaveableReport parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        if (obj == null) {
            return null;
        }
        String jsonToSend = obj.optString("jsonToSend");
        String purpose = obj.optString("purpose");
        String rateid = obj.optString("rateid");
        double totalDistance = obj.optDouble("totalDistance");
        DateTime createdAt = new DateTime(obj.optString("createdAt"));
        return new SaveableReport(jsonToSend, purpose, rateid, totalDistance, createdAt);
    }

    /**
     * @return String
     */
    public String getJsonToSend() {
        return this.jsonToSend;
    }

    /**
     * @return String
     */
    public void setJsonToSend(String newVal) {
        this.jsonToSend = newVal;
    }

    /**
     * @return String
     */
    public String getPurpose() {
        return this.purpose;
    }

    /**
     * @return String
     */
    public void setPurpose(String newVal) {
        this.purpose = newVal;
    }

    /**
     * @return String
     */
    public String getRateid() {
        return this.rateid;
    }

    /**
     * @return String
     */
    public void setRateid(String newVal) {
        this.rateid = newVal;
    }

    /**
     * @return double
     */
    public double getTotalDistance() {
        return this.totalDistance;
    }

    /**
     * @return double
     */
    public void setTotalDistance(double newVal) {
        this.totalDistance = newVal;
    }

    /**
     * @return DateTime
     */
    public DateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * @return DateTime
     */
    public void setCreatedAt(DateTime newVal) {
        this.createdAt = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("jsonToSend", jsonToSend);
        result.put("purpose", purpose);
        result.put("rateid", rateid);
        result.put("totalDistance", totalDistance);
        result.put("createdAt", createdAt.toString());
        return result;

    }


}