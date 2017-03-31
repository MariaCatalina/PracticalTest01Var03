package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import ro.pub.cs.systems.eim.practicaltest01var03.util.Constants;

import static android.content.ContentValues.TAG;

/**
 * Created by Cata on 3/27/2017.
 */

public class ProcessingThread extends Thread {
    private Context context;
    private String nume, grupa;

    public ProcessingThread(Context context, String nume, String grupa) {

        this.context = context;
        this.nume = nume;
        this.grupa = grupa;
    }

    @Override
    public void run() {
        while(true){
            sendMessage(Constants.MESSAGE_NAME);
            Log.i(TAG, "run: send name");
            sleep();

            sendMessage(Constants.MESSAGE_GRUPA);
            Log.i(TAG, "run: send grupa");
            sleep();
        }
    }

    private void sendMessage(int messageType) {
        Intent intent = new Intent();
        switch (messageType){
            case Constants.MESSAGE_NAME:
                intent.setAction(Constants.ACTION_NUME);
                intent.putExtra(Constants.DATA, nume);
                break;

            case Constants.MESSAGE_GRUPA:
                intent.setAction(Constants.ACTION_GRUPA);
                intent.putExtra(Constants.DATA, grupa);
                break;
        }

        context.sendBroadcast(intent);
    }

    private void sleep(){
        try {
            Thread.sleep(Constants.SLEEP_TIME);
        } catch (InterruptedException e) {
            Log.i(TAG, "sleep: " + e.getMessage());
        }
    }
}
