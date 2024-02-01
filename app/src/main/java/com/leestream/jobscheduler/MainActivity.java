package com.leestream.jobscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int EXAMPLEONE_JOB_KEY = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Check LogCat for results", Toast.LENGTH_SHORT).show();
        
        initJobScheduler();
    }

    private void initJobScheduler() {
        Log.d(TAG, "initJobScheduler: started");
        ComponentName componentName = new ComponentName(this,JobServiceSample.class);
        PersistableBundle bundle=new PersistableBundle();
        bundle.putInt("myNumber",10);
        JobInfo.Builder builder = new JobInfo.Builder(EXAMPLEONE_JOB_KEY,componentName)
                .setExtras(bundle)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setPeriodic(15*60*1000,30*60*1000);

        JobScheduler scheduler =(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(builder.build());
    }
}