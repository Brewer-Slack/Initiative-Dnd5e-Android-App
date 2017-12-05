package com.example.bennettfalkenberg.initiative;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int CREATE_CHARACTER_REQUEST = 1;
    static final int EDIT_CHARACTER_REQUEST = 2;
    private ArrayList<Character> characters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Character character = new Character();
        Character c1 = new Character("Brewer", 11,12,11,12,11,12,"Lawful Good", new ArrayList<String>(),
                "Ranger", "Dwarf");
        characters.add(character);
        characters.add(c1);

        Spinner charSpinner = findViewById(R.id.characterList);
        ArrayAdapter<Character> adapter = new ArrayAdapter<Character>(this,
                android.R.layout.simple_spinner_dropdown_item,
                characters);
        charSpinner.setAdapter(adapter);


        charSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView strengthText = (TextView) findViewById(R.id.strengthText);
                TextView dexterityText = (TextView) findViewById(R.id.dexterityText);
                TextView constitutionText = (TextView) findViewById(R.id.constitutionText);
                TextView intelligenceText = (TextView) findViewById(R.id.intelligenceText);
                TextView wisdomText = (TextView) findViewById(R.id.wisdomText);
                TextView charismaText = (TextView) findViewById(R.id.charismaText);
                TextView classText = (TextView) findViewById(R.id.className);

                classText.setText("Class \n\n" + characters.get(i).getCharClass());
                strengthText.setText("Strength" + "\n\n" + characters.get(i).getStrength());
                dexterityText.setText("Dexterity"+ "\n\n" + characters.get(i).getDexterity());
                constitutionText.setText("Constitution"+ "\n\n" + characters.get(i).getConstitution());
                intelligenceText.setText("Intelligence" + "\n\n" + characters.get(i).getIntelligence());
                wisdomText.setText("Wisdom" + "\n\n" + characters.get(i).getWisdom());
                charismaText.setText("Charisma" + "\n\n" + characters.get(i).getCharisma());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TextView strengthText = (TextView) findViewById(R.id.strengthText);
                TextView dexterityText = (TextView) findViewById(R.id.dexterityText);
                TextView constitutionText = (TextView) findViewById(R.id.constitutionText);
                TextView intelligenceText = (TextView) findViewById(R.id.intelligenceText);
                TextView wisdomText = (TextView) findViewById(R.id.wisdomText);
                TextView charismaText = (TextView) findViewById(R.id.charismaText);
                TextView classText = (TextView) findViewById(R.id.className);

                classText.setText("Class \n\n" + characters.get(0).getCharClass());
                strengthText.setText("Strength" + "\n\n" + characters.get(0).getStrength());
                dexterityText.setText("Dexterity"+ "\n\n" + characters.get(0).getDexterity());
                constitutionText.setText("Constitution"+ "\n\n" + characters.get(0).getConstitution());
                intelligenceText.setText("Intelligence" + "\n\n" + characters.get(0).getIntelligence());
                wisdomText.setText("Wisdom" + "\n\n" + characters.get(0).getWisdom());
                charismaText.setText("Charisma" + "\n\n" + characters.get(0).getCharisma());
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch(menuId){
            case R.id.createNewCharacter:
                startCharacterActivity();
                return true;
            case R.id.editCurrentCharacter:
                startEditCharacterActivity();
                return true;
            case R.id.menuRoll:
                startDiceRollActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void startCharacterActivity(){
        Intent intent = new Intent(this, CharacterActivity.class);
        intent.putExtra("requestCode", CREATE_CHARACTER_REQUEST);
        startActivityForResult(intent, CREATE_CHARACTER_REQUEST);
    }

    private void startEditCharacterActivity(){
        Intent intent = new Intent(this, CharacterActivity.class);
        intent.putExtra("requestCode", EDIT_CHARACTER_REQUEST);
        startActivityForResult(intent, EDIT_CHARACTER_REQUEST);
    }

    private void startDiceRollActivity(){
        Intent intent = new Intent(this, DiceRollActivity.class);
        startActivity(intent);
    }


}
