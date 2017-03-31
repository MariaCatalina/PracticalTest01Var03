package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import ro.pub.cs.systems.eim.practicaltest01var03.util.Constants;

/**
 * Created by cata on 31.03.2017.
 */

public class PracticalTest01Var03Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String nume = "default", grupa = "default";

        if (intent != null){
            nume = intent.getStringExtra(Constants.FIRST_ET);
            grupa = intent.getStringExtra(Constants.SECOND_ET);
        }

        ProcessingThread processingThread = new ProcessingThread(getApplicationContext(), nume, grupa);
        processingThread.start();

        return START_REDELIVER_INTENT;
    }
}
