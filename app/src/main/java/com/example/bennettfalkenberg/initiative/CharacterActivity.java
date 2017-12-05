package com.example.bennettfalkenberg.initiative;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class CharacterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Button rollStatsButton = (Button) findViewById(R.id.statsButton);
        if(intent.getIntExtra("requestCode", 0) == 2){
            rollStatsButton.setVisibility(View.INVISIBLE);
        }
        else if(intent.getIntExtra("requestCode", 0) == 1){
            rollStatsButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuId = item.getItemId();
        switch(menuId){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRollStatsClicked(View view){
        // get references to all 6 data fields
        TextView strengthText = (TextView) findViewById(R.id.strengthValue);
        TextView dexterityText = (TextView) findViewById(R.id.dexterityValue);
        TextView constitutionText = (TextView) findViewById(R.id.constitutionValue);
        TextView intelligenceText = (TextView) findViewById(R.id.intelligenceValue);
        TextView wisdomText = (TextView) findViewById(R.id.wisdomValue);
        TextView charismaText = (TextView) findViewById(R.id.charismaValue);
        Dice dice = new Dice(6,4);

        ArrayList<TextView> textViewArrayList = new ArrayList<>();
        textViewArrayList.add(strengthText);
        textViewArrayList.add(dexterityText);
        textViewArrayList.add(constitutionText);
        textViewArrayList.add(intelligenceText);
        textViewArrayList.add(wisdomText);
        textViewArrayList.add(charismaText);

        for (int i = 0; i < 6; i++){
            // set each individual data value
            textViewArrayList.get(i).setText(Integer.toString(dice.rollDice()));
        }
        // set roll button to be invisible
        Button rollStatsButton = (Button) findViewById(R.id.statsButton);
        rollStatsButton.setVisibility(View.INVISIBLE);
    }
}
