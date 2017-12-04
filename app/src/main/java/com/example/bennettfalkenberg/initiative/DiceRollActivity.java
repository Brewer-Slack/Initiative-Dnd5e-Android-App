package com.example.bennettfalkenberg.initiative;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.util.ArrayList;

public class DiceRollActivity extends AppCompatActivity implements SensorEventListener {

    private ArrayList<String> diceTypes = new ArrayList<>();
    private Dice dice = new Dice();

    private SensorManager sensorManager;
    private Sensor sensor;

    private long lastUpdate = 0;
    private float lastX, lastY, lastZ;
    private static final int SHAKE_THRESHHOLD = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        diceTypes.add("4");
        diceTypes.add("6");
        diceTypes.add("8");
        diceTypes.add("10");
        diceTypes.add("12");
        diceTypes.add("20");

        Spinner diceTypesSpinner = (Spinner) findViewById(R.id.typeOfDice);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, diceTypes);
        diceTypesSpinner.setAdapter(adapter);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onRollClick(View view) {
        EditText diceText = (EditText) findViewById(R.id.amountOfDice);
        String amountDiceStr = diceText.getText().toString();
        int amountDice = Integer.parseInt(amountDiceStr);

        Spinner diceTypesSpinner = (Spinner) findViewById(R.id.typeOfDice);

        String dieTypeStr = diceTypesSpinner.getSelectedItem().toString();
        int dieType = Integer.parseInt(dieTypeStr);

        dice.setNumDice(amountDice);
        dice.setNumSides(dieType);

        int result = dice.rollDice();
        TextView resultText = (TextView) findViewById(R.id.rollResult);
        resultText.setText(Integer.toString(result));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 100) {
                long diffTime = curTime - lastUpdate;
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;

                if (speed > SHAKE_THRESHHOLD) {
                    onRollClick(new View(this));
                }

                lastX = x;
                lastY = y;
                lastZ = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
