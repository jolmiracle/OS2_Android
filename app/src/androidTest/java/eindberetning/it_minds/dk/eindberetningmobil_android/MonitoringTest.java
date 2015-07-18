package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.content.Intent;

import org.joda.time.DateTime;
import org.junit.Test;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.views.AfterTripActivity;
import it_minds.dk.eindberetningmobil_android.views.MonitoringActivity;

/**
 * Created by kasper on 18-07-2015.
 */
public class MonitoringTest extends BaseTest<MonitoringActivity> {
    @Override
    public void runBeforeGetActivity() {
        Intent i = new Intent();
        DrivingReport report = new DrivingReport("", "", "", "", false, false, false, new DateTime(), new DateTime(), 0);
        i.putExtra(IntentIndexes.DATA_INDEX, report);
        setActivityIntent(i);
    }

    public MonitoringTest() {
        super(MonitoringActivity.class);
    }

    @Test
    public void testDialog() throws InterruptedException {
        solo.clickOnView(solo.getView(R.id.monitoring_view_stop_btn));
        solo.waitForDialogToOpen();
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_no));
        solo.clickOnView(solo.getView(R.id.monitoring_view_pause_resume_btn));
        solo.clickOnView(solo.getView(R.id.monitoring_view_pause_resume_btn));
        solo.clickOnView(solo.getView(R.id.monitoring_view_stop_btn));
        solo.waitForDialogToOpen();
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_ok));
        solo.waitForActivity(AfterTripActivity.class);

    }
}
