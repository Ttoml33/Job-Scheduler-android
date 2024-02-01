package com.leestream.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.util.Log;

public class JobServiceSample extends JobService {
    private static final String TAG = "JobServiceSample";
    private MyAsyncTask myAsyncTask;
    private JobParameters jobParameters;
    @Override
    public boolean onStartJob(JobParameters params) {
        this.jobParameters =params;
        //executes on main thread.To avoid that use threading
        PersistableBundle persistableBundle = params.getExtras();
        int myData=persistableBundle.getInt("myNumber");

        myAsyncTask=new MyAsyncTask();
        myAsyncTask.execute(myData);
        
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: Job Stopped");
        if (null != myAsyncTask){
            if (!myAsyncTask.isCancelled()){
                myAsyncTask.cancel(true);
            }
        }
        return false;
    }

    private class MyAsyncTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i=1;i<integers[0];i++){
                SystemClock.sleep(1000);
                publishProgress(i);
            }
            return "Task complete";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "onProgressUpdate: I was "+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: message "+s);

            jobFinished(jobParameters,true);
        }
    }
}
