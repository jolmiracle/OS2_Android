package it_minds.dk.eindberetningmobil_android.service;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.TimeZone;

import it_minds.dk.eindberetningmobil_android.constants.DistanceDisplayer;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;

/**
 * Created by kasper on 25-07-2015.
 * an object, handling the bll (business logic), so that the monitoringservice do not have to work directly with the bll layer.
 */
public class MonitoringServiceReport {


    public static final int MINIMUM_REQURIED_ACC_IN_METERS = 50;
    public static final int MAX_DIST_RESUME_ALLOWED_IN_METERES = 200;
    private MonitoringService monitoringService;


    //<editor-fold desc="report mangement">
    private DrivingReport report;
    private boolean validateOnResume = false;
    private Location lastLocation = null;
    //</editor-fold>

    public MonitoringServiceReport(Intent intent, MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
        if (intent.hasExtra(IntentIndexes.DATA_INDEX)) {
            report = intent.getParcelableExtra(IntentIndexes.DATA_INDEX);
        } else {
            report = new DrivingReport();
        }
        updateDisplay(0, 0, null);
    }


    /**
     * Callback function that gets called with a new location.
     *
     * @param location
     */
    public void addLocation(Location location) {
        if (validateOnResume) {
            handleValidationOnResume(location);
            return;
        }
        if (location.getAccuracy() <= MINIMUM_REQURIED_ACC_IN_METERS) {
            if (!location.hasSpeed() || (location.hasSpeed() && location.getSpeed() > 0)) {
                //yes yes , so lets handle the new location (update the distance, and update the displays)
                handleNewLocation(location);
            } else {
                Log.e("temp", "not moving");
            }
        }
    }

    /**
     * handle the new location (update the distance, and update the displays)
     *
     * @param location
     */
    private void handleNewLocation(Location location) {
        if (report != null && report.getgpsPoints() != null) {
            report.getgpsPoints().add(location);
            if (lastLocation == null) {
                lastLocation = location;
                return;
            }
            updateCurrentDistance(location);
            long offset = TimeZone.getDefault().getOffset(location.getTime());
            updateDisplay(location.getAccuracy(), report.getdistanceInMeters(), new DateTime(offset + location.getTime()));
        }
    }

    /**
     * handles the validation if we are "resuming". it validate that the current location is close enough
     * to the old, otherwise we have encountered an error (the user might have moved further away or alike).
     *
     * @param location
     */
    private synchronized void handleValidationOnResume(Location location) {
        Log.e("temp", "is validating location");

        if (report.getgpsPoints().size() == 0) {
            validateOnResume = false;
            handleNewLocation(location);//resume the function.
            return;
        }

        if (location.getAccuracy() <= MINIMUM_REQURIED_ACC_IN_METERS) {
            Log.e("temp", "validation point is semi precise." + location.getAccuracy());
            Location lastLocation = report.getgpsPoints().get(report.getgpsPoints().size() - 1);
            Log.e("temp", "validation is within: " + lastLocation.distanceTo(location));
            if (lastLocation.distanceTo(location) < MAX_DIST_RESUME_ALLOWED_IN_METERES) {
                //ok to contine.
                validateOnResume = false;
                handleNewLocation(location);//resume the function.
            } else {
                //Not allowed to continue
                if (monitoringService.isListening()) { //stop if listening, so we are "safe" :)
                    MonitoringService.pauseResumeListening(monitoringService);
                }
                monitoringService.sendError();
                validateOnResume = false;
            }

        }
    }

    /***
     * Creates an UI model to send to the listening activity.
     *
     * @param acc
     * @param distance
     * @param time
     */
    private void updateDisplay(float acc, double distance, DateTime time) {
        String s = DistanceDisplayer.formatDistance(distance);
        String distanceText = (s + " Km");//kilometer is an SI unit, so no translations is needed
        String timeText = "";
        if (time != null) {
            timeText = (time.toString("HH:mm:ss"));
        }
        String accText = acc + " m";
        monitoringService.sendUiUpdate(new UiStatusModel(timeText, accText, distanceText));
    }


    /**
     * updates the current distance.
     *
     * @param location
     */
    private void updateCurrentDistance(Location location) {
        if (location.getSpeed() > 0) {
            double currentDistance = report.getdistanceInMeters();
            report.setdistanceInMeters(currentDistance + Math.abs(location.distanceTo(lastLocation)));
            lastLocation = location;
        }
    }

    /**
     * @return the current Km we have moved
     */
    public String getCurrentDistanceInKm() {
        return DistanceDisplayer.formatDistance(report.getdistanceInMeters());
    }

    /**
     * Simple helper to wrap the current report into a bundle.
     *
     * @return
     */
    public Bundle createNotificationBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentIndexes.DATA_INDEX, report);
        return bundle;
    }

    public void pause() {
        validateOnResume = true;
    }
}
