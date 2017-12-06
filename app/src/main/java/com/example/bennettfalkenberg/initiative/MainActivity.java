package com.example.bennettfalkenberg.initiative;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int CREATE_CHARACTER_REQUEST = 1;
    static final int EDIT_CHARACTER_REQUEST = 2;
    private ArrayList<Character> characters = new ArrayList<>();
    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//         ArrayList<String> inventory = new ArrayList<>();
//        inventory.add("knife");
//         ArrayList<String> skills= new ArrayList<>();
//         skills.add("lightning");
//         ArrayList<Boolean> savingThrows = new ArrayList<>();
//         savingThrows.add(true);
//
//        Character character = new Character("brewer", 12,12,12,12,12,12, "Lawful Good",
//                inventory, skills,
//                savingThrows, "Druid", "Dwarf");
//        Character c1 = new Character();
//        characters.add(character);
//        characters.add(c1);

        final Spinner charSpinner = findViewById(R.id.characterList);

        final CharacterDatabaseHelper databaseHelper = new CharacterDatabaseHelper(this);
//        try {
//            databaseHelper.insertCharacter(character);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        final Cursor cursor = databaseHelper.getSelectAllCharactersCursor();

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, cursor,
                new String[] {CharacterDatabaseHelper.NAME}, new int[] {android.R.id.text1},0);

        charSpinner.setAdapter(cursorAdapter);

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
                TextView invText = (TextView) findViewById(R.id.inventory);
                TextView skillsText = (TextView) findViewById(R.id.currCharacterSkills);



                Cursor selectedNameCursor = (Cursor)charSpinner.getSelectedItem();
                String selectedName = selectedNameCursor.getString(selectedNameCursor.getColumnIndex(
                        databaseHelper.NAME));


//                String selectedName = charSpinner.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemSelected: " + selectedName);
                Cursor newCursor = databaseHelper.getSelectAllCharactersCursor();
                while(newCursor.moveToNext()){
                    if(newCursor.getString(1).equals(selectedName)){

                        classText.setText("Class \n\n" + newCursor.getString(12));
                        strengthText.setText("Strength" + "\n\n" + newCursor.getString(2));
                        dexterityText.setText("Dexterity"+ "\n\n" + newCursor.getString(3));
                        constitutionText.setText("Constitution"+ "\n\n" + newCursor.getString(4));
                        intelligenceText.setText("Intelligence" + "\n\n" + newCursor.getString(5));
                        wisdomText.setText("Wisdom" + "\n\n" + newCursor.getString(6));
                        charismaText.setText("Charisma" + "\n\n" + newCursor.getString(7));
                        String readInv = newCursor.getString(9);
                        String readSkills = newCursor.getString(10);

                        ArrayList<String> invList = new ArrayList<>();
                        ArrayList<String> skillList = new ArrayList<>();

                        try {
                            JSONObject json1 = new JSONObject(readInv);
                            JSONObject json2 = new JSONObject(readSkills);

                            JSONArray jsonInventoryList = json1.optJSONArray("inventory");
                            JSONArray jsonSkillList = json2.optJSONArray("skills");
                            for(int j = 0; j < jsonInventoryList.length(); j++){
                                invList.add(jsonInventoryList.getString(j));
                            }
                            for(int x = 0; x < jsonSkillList.length(); x++){
                                skillList.add(jsonSkillList.getString(x));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String inventory = "";
                        String skills = "";
                        for(int k = 0; k < invList.size(); k++) {
                            inventory += invList.get(k) + "\n";
                        }
                        invText.setText("Inventory \n\n" + inventory);
                        for(int y = 0; y < skillList.size(); y++){
                            skills += skillList.get(y) + "\n";
                        }
                        skillsText.setText("Skills \n\n" + skills);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
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
