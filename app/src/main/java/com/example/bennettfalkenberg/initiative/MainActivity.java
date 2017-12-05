package com.example.bennettfalkenberg.initiative;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    static final int CREATE_CHARACTER_REQUEST = 1;
    static final int EDIT_CHARACTER_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
