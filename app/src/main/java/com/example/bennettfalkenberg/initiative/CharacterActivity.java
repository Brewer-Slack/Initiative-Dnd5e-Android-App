package com.example.bennettfalkenberg.initiative;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        EditText strengthValue = (EditText) findViewById(R.id.strengthValue);
        EditText dexterityValue = (EditText) findViewById(R.id.dexterityValue);
        EditText constitutionValue = (EditText) findViewById(R.id.constitutionValue);
        EditText intelligenceValue = (EditText) findViewById(R.id.intelligenceValue);
        EditText wisdomValue = (EditText) findViewById(R.id.wisdomValue);
        EditText charismaValue = (EditText) findViewById(R.id.charismaValue);

        // we're editing a character
        if(intent.getIntExtra("requestCode", 0) == 2){
            rollStatsButton.setVisibility(View.INVISIBLE);
            strengthValue.setInputType(InputType.TYPE_CLASS_NUMBER);
            dexterityValue.setInputType(InputType.TYPE_CLASS_NUMBER);
            constitutionValue.setInputType(InputType.TYPE_CLASS_NUMBER);
            intelligenceValue.setInputType(InputType.TYPE_CLASS_NUMBER);
            wisdomValue.setInputType(InputType.TYPE_CLASS_NUMBER);
            charismaValue.setInputType(InputType.TYPE_CLASS_NUMBER);

        }
        // we're creating a new character
        else if(intent.getIntExtra("requestCode", 0) == 1){
            rollStatsButton.setVisibility(View.VISIBLE);
            strengthValue.setInputType(InputType.TYPE_NULL);
            dexterityValue.setInputType(InputType.TYPE_NULL);
            constitutionValue.setInputType(InputType.TYPE_NULL);
            intelligenceValue.setInputType(InputType.TYPE_NULL);
            wisdomValue.setInputType(InputType.TYPE_NULL);
            charismaValue.setInputType(InputType.TYPE_NULL);
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
        EditText strengthText = (EditText) findViewById(R.id.strengthValue);
        EditText dexterityText = (EditText) findViewById(R.id.dexterityValue);
        EditText constitutionText = (EditText) findViewById(R.id.constitutionValue);
        EditText intelligenceText = (EditText) findViewById(R.id.intelligenceValue);
        EditText wisdomText = (EditText) findViewById(R.id.wisdomValue);
        EditText charismaText = (EditText) findViewById(R.id.charismaValue);
        Dice dice = new Dice(6,4);

        ArrayList<EditText> textViewArrayList = new ArrayList<>();
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
