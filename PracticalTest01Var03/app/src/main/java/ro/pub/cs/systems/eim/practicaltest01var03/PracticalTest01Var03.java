package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import practicaltest01var03.eim.systems.cs.pub.ro.practicaltest01var03.R;
import ro.pub.cs.systems.eim.practicaltest01var03.util.Constants;

import static android.content.ContentValues.TAG;


public class PracticalTest01Var03 extends AppCompatActivity {

    private static final int START_SECOND_ACTIVITY = 100;
    private EditText firstET, secondET, showInfo;
    private CheckBox firstCB, secondCB;
    private Button displayInfo, nextActivity;

    private ButtonsListener listener = new ButtonsListener();

    public class ButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.displyInformations:
                    Log.i("act", "onClick: disply");
                    StringBuffer result  = new StringBuffer();

                    if (firstCB.isChecked()){
                        result.append(firstET.getText().toString()).append("\n");
                    }

                    if (secondCB.isChecked()){
                        result.append(secondET.getText().toString());
                    }

                    showInfo.setText(result);

                    //start service
                    if (firstCB.isChecked() && secondCB.isChecked()){
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                        intent.putExtra(Constants.FIRST_ET, firstET.getText().toString());
                        intent.putExtra(Constants.SECOND_ET, secondET.getText().toString());

                        getApplicationContext().startService(intent);
                    }


                    break;
                case R.id.secondActivity:

                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.FIRST_ET, firstET.getText().toString());
                    bundle.putString(Constants.SECOND_ET, secondET.getText().toString());

                    intent.putExtras(bundle);
                    startActivityForResult(intent, START_SECOND_ACTIVITY);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        firstET = (EditText) findViewById(R.id.firstET);
        secondET = (EditText) findViewById(R.id.secondET);
        showInfo = (EditText) findViewById(R.id.showInfo);

        firstCB = (CheckBox) findViewById(R.id.firstChechBox);
        secondCB = (CheckBox) findViewById(R.id.seconChechBox);

        displayInfo = (Button) findViewById(R.id.displyInformations);
        displayInfo.setOnClickListener(listener);

        nextActivity = (Button) findViewById(R.id.secondActivity);
        nextActivity.setOnClickListener(listener);

        if (savedInstanceState != null){


            if (savedInstanceState.containsKey(Constants.FIRST_ET)){
                firstET.setText(savedInstanceState.getString(Constants.FIRST_ET));
            }

            if (savedInstanceState.containsKey(Constants.SECOND_ET)){
                secondET.setText(savedInstanceState.getString(Constants.SECOND_ET));
            }

            if (savedInstanceState.containsKey(Constants.SHOW_ET)){
                showInfo.setText(savedInstanceState.getString(Constants.SHOW_ET));
            }
        }

       messageBroadcastReceiver = new MessageBroadcastReceiver();

        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction(Constants.ACTION_GRUPA);
        startedServiceIntentFilter.addAction(Constants.ACTION_NUME);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putBoolean(Constants.FIRST_CB, firstCB.isChecked());
        outState.putBoolean(Constants.SECOND_CB, secondCB.isChecked());

        outState.putString(Constants.FIRST_ET, firstET.getText().toString());
        outState.putString(Constants.SECOND_ET, secondET.getText().toString());
        outState.putString(Constants.SHOW_ET, showInfo.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.FIRST_CB)){
            firstCB.setClickable(savedInstanceState.getBoolean(Constants.FIRST_CB));
        }
        if (savedInstanceState.containsKey(Constants.SECOND_CB)){
            secondCB.setClickable(savedInstanceState.getBoolean(Constants.SECOND_CB));
        }

        if (savedInstanceState.containsKey(Constants.FIRST_ET)){
            firstET.setText(savedInstanceState.getString(Constants.FIRST_ET));
        }

        if (savedInstanceState.containsKey(Constants.SECOND_ET)){
            secondET.setText(savedInstanceState.getString(Constants.SECOND_ET));
        }

        if (savedInstanceState.containsKey(Constants.SHOW_ET)){
            showInfo.setText(savedInstanceState.getString(Constants.SHOW_ET));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case START_SECOND_ACTIVITY:
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                }
                else if ( resultCode == RESULT_CANCELED ){
                    Toast.makeText(this, "CANCEL", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, startedServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    private MessageBroadcastReceiver messageBroadcastReceiver;
    private IntentFilter startedServiceIntentFilter;

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            String data = null;

            if(Constants.ACTION_NUME.equals(action)){
                data = intent.getStringExtra(Constants.DATA);
                Toast.makeText(getBaseContext(), data, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onReceive: pe grupa" + data);
            }

            if(Constants.ACTION_GRUPA.equals(action)){
                data = intent.getStringExtra(Constants.DATA);
                Log.i(TAG, "onReceive: pe nume" + data);
                Toast.makeText(getBaseContext(), data, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
