package sateesh.com.pawnbrokercalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button month_wise_btn, day_wise_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        month_wise_btn = (Button) findViewById(R.id.month_wise);
        month_wise_btn.setOnClickListener(this);

        day_wise_btn = (Button) findViewById(R.id.day_wise);
        day_wise_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.month_wise:
                Intent monthWiseDetail = new Intent(MainActivity.this, MonthWiseDetail.class);
                startActivity(monthWiseDetail);
                break;

            case R.id.day_wise:
                break;
        }
    }
}
