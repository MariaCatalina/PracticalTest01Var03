package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import practicaltest01var03.eim.systems.cs.pub.ro.practicaltest01var03.R;
import ro.pub.cs.systems.eim.practicaltest01var03.util.Constants;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    private Button Ok, Cancel;
    private EditText firstET, secondET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        firstET = (EditText) findViewById(R.id.firstET);
        secondET = (EditText) findViewById(R.id.secondET);

        Bundle bundle = getIntent().getExtras();
        if( bundle != null){
            firstET.setText(bundle.getString(Constants.FIRST_ET));
            secondET.setText(bundle.getString(Constants.SECOND_ET));
        }

        Ok = (Button) findViewById(R.id.ok);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intentToParent = new Intent();
                setResult(RESULT_OK, intentToParent);
                finish();
            }
        });

        Cancel = (Button) findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToParent = new Intent();
                setResult(RESULT_CANCELED, intentToParent);
                finish();
            }
        });
    }
}
