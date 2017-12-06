package com.example.bennettfalkenberg.initiative;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BJayB on 12/5/2017.
 */

public class CharacterDatabaseHelper extends SQLiteOpenHelper {

    static final String TAG = "CharacterDatabaseHelper";
    // database fields
    static final String DATABASE_NAME = "databaseCharacters";
    static final int DATABASE_VERSION = 1;

    // table fields
    static final String TABLE_CHARACTERS = "tableCharacters";

    // column fields
    static final String ID = "_id";
    static final String NAME = "name";
    static final String STRENGTH = "strength";
    static final String DEXTERITY = "dexterity";
    static final String CONSTITUTION = "constitution";
    static final String INTELLIGENCE = "intelligence";
    static final String WISDOM = "wisdom";
    static final String CHARISMA = "charisma";
    static final String ALIGNMENT = "alignment";
    static final String INVENTORY = "inventory";
    static final String SAVING_THROWS = "savingThrows";
    static final String SKILLS = "skills";
    static final String CLASS = "class";
    static final String RACE = "race";

    public CharacterDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "CREATE TABLE " + TABLE_CHARACTERS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +   //0
                NAME + " TEXT, " +                              //1
                STRENGTH + " INTEGER, " +                       //2
                DEXTERITY + " INTEGER, " +                      //3
                CONSTITUTION + " INTEGER, " +                   //4
                INTELLIGENCE + " INTEGER, " +                   //5
                WISDOM + " INTEGER, " +                         //6
                CHARISMA + " INTEGER, " +                       //7
                ALIGNMENT + " TEXT, " +                         //8
                INVENTORY + " TEXT, " +                         //9
                SKILLS + " TEXT, " +                            //10
                SAVING_THROWS + " TEXT, " +                     //11
                CLASS + " TEXT, " +                             //12
                RACE + " TEXT)";                                //13

        Log.d(TAG, "onCreate: " + sqlCreate);
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void insertCharacter(Character character) throws JSONException {
        // allow for arrayList in database
        JSONObject json = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONObject json3 = new JSONObject();
        try {
            json.put("inventory", new JSONArray(character.getInventory()));
            json2.put("skills", new JSONArray(character.getSkills()));
            json3.put("savingThrows", new JSONArray(character.getSavingThrows()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String invList = json.toString();
        String skillsList = json2.toString();
        String savingThrowsList = json3.toString();


        // INSERT INTO tableCharacters VALUES(null, 1, 1, 1, 1, 1, 1, 'alignment', 'inventory',
        // 'class', 'race'
        String sqlInsertCharacter = "INSERT INTO " + TABLE_CHARACTERS +
                " VALUES(null, " +
                "'" + character.getName() + "', " +
                "'" + character.getStrength() + "', " +
                "'" + character.getDexterity() + "', " +
                "'" + character.getConstitution() + "', " +
                "'" + character.getIntelligence() + "', " +
                "'" + character.getWisdom() + "', " +
                "'" + character.getCharisma() + "', " +
                "'" + character.getAlignment() + "', " +
                "'" + invList + "', " +
                "'" + skillsList + "', " +
                "'" + savingThrowsList + "', " +
                "'" + character.getCharClass() + "', " +
                "'" + character.getRace() + "')";
        Log.d(TAG, "insertCharacter: " + sqlInsertCharacter);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlInsertCharacter);
    }

    public Cursor getSelectAllCharactersCursor(){
        String sqlSelectAll = "SELECT * FROM " + TABLE_CHARACTERS;
        Log.d(TAG, "getSelectAllCharactersCursor: " + sqlSelectAll);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelectAll, null);

        return cursor;
    }

    public void updateCharacterById(int id, Character newCharacter) {
        // allow for arrayList in database
        JSONObject json = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONObject json3 = new JSONObject();
        try {
            json.put("inventory", new JSONArray(newCharacter.getInventory()));
            json2.put("skills", new JSONArray(newCharacter.getSkills()));
            json3.put("savingThrows", new JSONArray(newCharacter.getSavingThrows()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String invList = json.toString();
        String skillsList = json2.toString();
        String savingThrowsList = json3.toString();

        String sqlUpdate = "Update " + TABLE_CHARACTERS +
                " SET " + NAME + " = '" + newCharacter.getName() +
                "', " + STRENGTH + " = '" + newCharacter.getStrength() +
                "', " + DEXTERITY + " = '" + newCharacter.getDexterity() +
                "', " + CONSTITUTION + " = '" + newCharacter.getConstitution() +
                "', " + INTELLIGENCE + " = '" + newCharacter.getIntelligence() +
                "', " + WISDOM + " = '" + newCharacter.getWisdom() +
                "', " + CHARISMA + " = '" + newCharacter.getCharisma() +
                "', " + ALIGNMENT + " = '" + newCharacter.getAlignment() +
                "', " + INVENTORY + " = '" + invList +
                "', " + SKILLS + " = '" + skillsList +
                "', " + SAVING_THROWS + " = '" + savingThrowsList +
                "', " + CLASS + " = '" + newCharacter.getCharClass() +
                "', " + RACE + " = '" + newCharacter.getRace() + ")";
        Log.d(TAG, "updateCharacterById: " + sqlUpdate);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    public void deleteCharacterById(int id){
        String sqlDeleteCharacter = "DELETE FROM " + TABLE_CHARACTERS + " WHERE " + ID +
                " = " + id;
        Log.d(TAG, "deleteCharacterById: " + sqlDeleteCharacter);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlDeleteCharacter);
        db.close();
    }

    public Cursor selectCharacter(int id){
        String sqlSelectCharacter = "SELECT " + NAME + ", " +
                STRENGTH + ", " +
                DEXTERITY + ", " +
                CONSTITUTION + ", " +
                INTELLIGENCE + ", " +
                WISDOM + ", " +
                CHARISMA + ", " +
                ALIGNMENT + ", " +
                INVENTORY + ", " +
                SKILLS + ", " +
                SAVING_THROWS + ", " +
                CLASS + ", " +
                RACE + " FROM " + TABLE_CHARACTERS +
                " WHERE " + ID + " = " + id;
        Log.d(TAG, "selectCharacter: " + sqlSelectCharacter);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelectCharacter, null);
        return cursor;



    }
}
