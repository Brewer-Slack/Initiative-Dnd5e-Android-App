package com.example.bennettfalkenberg.initiative;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class CharacterActivity extends AppCompatActivity {

    final static String TAG = "CharacterActivity";

    ArrayList<String> classNames = new ArrayList<>();
    ArrayList<String> inventory = new ArrayList<>();
    Character character = new Character();
    ArrayList<String> savingThrows = new ArrayList<>();
    ArrayList<String> skills = new ArrayList<>();

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
        final EditText intelligenceValue = (EditText) findViewById(R.id.intelligenceValue);
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

        // set up alignment spinner
        ArrayList<String> alignmentList = new ArrayList<>();
        alignmentList.add("Alignment");
        alignmentList.add("Lawful Good");
        alignmentList.add("Neutral Good");
        alignmentList.add("Chaotic Good");
        alignmentList.add("Lawful Neutral");
        alignmentList.add("True Neutral");
        alignmentList.add("Chaotic Neutral");
        alignmentList.add("Lawful Evil");
        alignmentList.add("Neutral Evil");
        alignmentList.add("Chaotic Evil");
        ArrayAdapter alignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentList);
        Spinner alignmentSpinner = (Spinner) findViewById(R.id.alignmentSpinner);
        alignmentSpinner.setAdapter(alignmentAdapter);

        // set up race spinner
        ArrayList<String> raceList = new ArrayList<>();
        raceList.add("Race");
        raceList.add("Aarakocra");
        raceList.add("Aasimar");
        raceList.add("Bugbear");
        raceList.add("Dragonborn");
        raceList.add("Dwarf");
        raceList.add("Elf");
        raceList.add("Firbolg");
        raceList.add("Genasi");
        raceList.add("Gnome");
        raceList.add("Goblin");
        raceList.add("Goliath");
        raceList.add("Half-elf");
        raceList.add("Halfling");
        raceList.add("Half-orc");
        raceList.add("Hobgoblin");
        raceList.add("Human");
        raceList.add("Kenku");
        raceList.add("Kobold");
        raceList.add("Lizardfold");
        raceList.add("Orc");
        raceList.add("Tabaxi");
        raceList.add("Tiefling");
        raceList.add("Tortle");
        raceList.add("Triton");
        raceList.add("Yuan-ti");
        ArrayAdapter raceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, raceList);
        Spinner raceSpinner = (Spinner) findViewById(R.id.raceSpinner);
        raceSpinner.setAdapter(raceAdapter);


        for(int i = 1; i <= 12; i++) {
            String urlRequest = "http://dnd5eapi.co/api/classes/" + i;
            DnDClassAPIRequestAsyncTask apiRequestAsyncTask = new DnDClassAPIRequestAsyncTask();
            apiRequestAsyncTask.execute(urlRequest);
        }

        Spinner classSpinner = (Spinner) findViewById(R.id.classSpinner);
        classNames.add("Class");
        ArrayAdapter classAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classNames);
        classSpinner.setAdapter(classAdapter);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // set the character's class
                String item = adapterView.getItemAtPosition(i).toString();
                character.setCharClass(item);

                String inventoryURLRequest;
                String skillsURLRequest;
                String savingThrowsURLRequest;
                switch(i) {
                    case 1:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/158";
                        EquipmentAPIRequestAsyncTask backpack = new EquipmentAPIRequestAsyncTask();
                        backpack.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/5";
                        EquipmentAPIRequestAsyncTask javelin = new EquipmentAPIRequestAsyncTask();
                        javelin.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/18";
                        EquipmentAPIRequestAsyncTask greataxe = new EquipmentAPIRequestAsyncTask();
                        greataxe.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/16";
                        EquipmentAPIRequestAsyncTask flail = new EquipmentAPIRequestAsyncTask();
                        flail.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/1";
                        SkillsAPIRequestAsyncTask lightArmor = new SkillsAPIRequestAsyncTask();
                        lightArmor.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/2";
                        SkillsAPIRequestAsyncTask mediumArmor = new SkillsAPIRequestAsyncTask();
                        mediumArmor.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/18";
                        SkillsAPIRequestAsyncTask shields = new SkillsAPIRequestAsyncTask();
                        shields.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/19";
                        SkillsAPIRequestAsyncTask simpleWeapons = new SkillsAPIRequestAsyncTask();
                        simpleWeapons.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/20";
                        SkillsAPIRequestAsyncTask martialWeapons = new SkillsAPIRequestAsyncTask();
                        martialWeapons.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/1";
                        SavingThrowsAPIRequestAsyncTask str = new SavingThrowsAPIRequestAsyncTask();
                        str.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/3";
                        SavingThrowsAPIRequestAsyncTask con = new SavingThrowsAPIRequestAsyncTask();
                        con.execute(savingThrowsURLRequest);
                        break;
                    case 2:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/39";
                        EquipmentAPIRequestAsyncTask leather = new EquipmentAPIRequestAsyncTask();
                        leather.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/2";
                        EquipmentAPIRequestAsyncTask dagger = new EquipmentAPIRequestAsyncTask();
                        dagger.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/26";
                        EquipmentAPIRequestAsyncTask rapier = new EquipmentAPIRequestAsyncTask();
                        rapier.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/155";
                        EquipmentAPIRequestAsyncTask pack = new EquipmentAPIRequestAsyncTask();
                        pack.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/184";
                        EquipmentAPIRequestAsyncTask lute = new EquipmentAPIRequestAsyncTask();
                        lute.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/1";
                        SkillsAPIRequestAsyncTask lightArmor2 = new SkillsAPIRequestAsyncTask();
                        lightArmor2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/19";
                        SkillsAPIRequestAsyncTask simpleWeapons2 = new SkillsAPIRequestAsyncTask();
                        simpleWeapons2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/42";
                        SkillsAPIRequestAsyncTask longSwords = new SkillsAPIRequestAsyncTask();
                        longSwords.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/46";
                        SkillsAPIRequestAsyncTask rapiers = new SkillsAPIRequestAsyncTask();
                        rapiers.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/48";
                        SkillsAPIRequestAsyncTask shortSwords = new SkillsAPIRequestAsyncTask();
                        shortSwords.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/54";
                        SkillsAPIRequestAsyncTask handCrossbows = new SkillsAPIRequestAsyncTask();
                        handCrossbows.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/2";
                        SavingThrowsAPIRequestAsyncTask dex = new SavingThrowsAPIRequestAsyncTask();
                        dex.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/6";
                        SavingThrowsAPIRequestAsyncTask cha = new SavingThrowsAPIRequestAsyncTask();
                        cha.execute(savingThrowsURLRequest);
                        break;
                    case 3:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/50";
                        EquipmentAPIRequestAsyncTask shield = new EquipmentAPIRequestAsyncTask();
                        shield.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/7";
                        EquipmentAPIRequestAsyncTask mace = new EquipmentAPIRequestAsyncTask();
                        mace.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/43";
                        EquipmentAPIRequestAsyncTask scaleMail = new EquipmentAPIRequestAsyncTask();
                        scaleMail.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/1";
                        EquipmentAPIRequestAsyncTask club = new EquipmentAPIRequestAsyncTask();
                        club.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/159";
                        EquipmentAPIRequestAsyncTask priestsPack = new EquipmentAPIRequestAsyncTask();
                        priestsPack.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/58";
                        EquipmentAPIRequestAsyncTask amulet = new EquipmentAPIRequestAsyncTask();
                        amulet.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/1";
                        SkillsAPIRequestAsyncTask lightArmor3 = new SkillsAPIRequestAsyncTask();
                        lightArmor3.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/2";
                        SkillsAPIRequestAsyncTask mediumArmor2 = new SkillsAPIRequestAsyncTask();
                        mediumArmor2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/18";
                        SkillsAPIRequestAsyncTask shields2 = new SkillsAPIRequestAsyncTask();
                        shields2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/19";
                        SkillsAPIRequestAsyncTask simpleWeapons3 = new SkillsAPIRequestAsyncTask();
                        simpleWeapons3.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/5";
                        SavingThrowsAPIRequestAsyncTask wis = new SavingThrowsAPIRequestAsyncTask();
                        wis.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/6";
                        SavingThrowsAPIRequestAsyncTask cha2 = new SavingThrowsAPIRequestAsyncTask();
                        cha2.execute(savingThrowsURLRequest);
                        break;
                    case 4:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/39";
                        EquipmentAPIRequestAsyncTask leather2 = new EquipmentAPIRequestAsyncTask();
                        leather2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/158";
                        EquipmentAPIRequestAsyncTask explorersPack = new EquipmentAPIRequestAsyncTask();
                        explorersPack.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/8";
                        EquipmentAPIRequestAsyncTask quarterStaff = new EquipmentAPIRequestAsyncTask();
                        quarterStaff.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/10";
                        EquipmentAPIRequestAsyncTask spear = new EquipmentAPIRequestAsyncTask();
                        spear.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/90";
                        EquipmentAPIRequestAsyncTask totem = new EquipmentAPIRequestAsyncTask();
                        totem.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/1";
                        SkillsAPIRequestAsyncTask lightArmor4 = new SkillsAPIRequestAsyncTask();
                        lightArmor4.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/2";
                        SkillsAPIRequestAsyncTask mediumArmor3 = new SkillsAPIRequestAsyncTask();
                        mediumArmor3.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/18";
                        SkillsAPIRequestAsyncTask shields3 = new SkillsAPIRequestAsyncTask();
                        shields3.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/21";
                        SkillsAPIRequestAsyncTask clubs = new SkillsAPIRequestAsyncTask();
                        clubs.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/22";
                        SkillsAPIRequestAsyncTask daggers = new SkillsAPIRequestAsyncTask();
                        daggers.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/25";
                        SkillsAPIRequestAsyncTask javelins = new SkillsAPIRequestAsyncTask();
                        javelins.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/27";
                        SkillsAPIRequestAsyncTask maces = new SkillsAPIRequestAsyncTask();
                        maces.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/28";
                        SkillsAPIRequestAsyncTask quarterStaffs = new SkillsAPIRequestAsyncTask();
                        quarterStaffs.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/29";
                        SkillsAPIRequestAsyncTask sickles = new SkillsAPIRequestAsyncTask();
                        sickles.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/30";
                        SkillsAPIRequestAsyncTask spears = new SkillsAPIRequestAsyncTask();
                        spears.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/32";
                        SkillsAPIRequestAsyncTask darts = new SkillsAPIRequestAsyncTask();
                        darts.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/34";
                        SkillsAPIRequestAsyncTask slings = new SkillsAPIRequestAsyncTask();
                        slings.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/47";
                        SkillsAPIRequestAsyncTask scimitars = new SkillsAPIRequestAsyncTask();
                        scimitars.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/93";
                        SkillsAPIRequestAsyncTask herbalismKit = new SkillsAPIRequestAsyncTask();
                        herbalismKit.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/4";
                        SavingThrowsAPIRequestAsyncTask intel = new SavingThrowsAPIRequestAsyncTask();
                        intel.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/5";
                        SavingThrowsAPIRequestAsyncTask wis2 = new SavingThrowsAPIRequestAsyncTask();
                        wis2.execute(savingThrowsURLRequest);
                        break;
                    case 5:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/47";
                        EquipmentAPIRequestAsyncTask chainMail = new EquipmentAPIRequestAsyncTask();
                        chainMail.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/50";
                        EquipmentAPIRequestAsyncTask shield2 = new EquipmentAPIRequestAsyncTask();
                        shield2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/4";
                        EquipmentAPIRequestAsyncTask handAxe = new EquipmentAPIRequestAsyncTask();
                        handAxe.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/156";
                        EquipmentAPIRequestAsyncTask dungeonPack = new EquipmentAPIRequestAsyncTask();
                        dungeonPack.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/19";
                        EquipmentAPIRequestAsyncTask greatsword = new EquipmentAPIRequestAsyncTask();
                        greatsword.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/4";
                        SkillsAPIRequestAsyncTask allArmor = new SkillsAPIRequestAsyncTask();
                        allArmor.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/18";
                        SkillsAPIRequestAsyncTask shields4 = new SkillsAPIRequestAsyncTask();
                        shields4.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/19";
                        SkillsAPIRequestAsyncTask simpleWeapons4 = new SkillsAPIRequestAsyncTask();
                        simpleWeapons4.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/20";
                        SkillsAPIRequestAsyncTask martialWeapons2 = new SkillsAPIRequestAsyncTask();
                        martialWeapons2.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/1";
                        SavingThrowsAPIRequestAsyncTask str2 = new SavingThrowsAPIRequestAsyncTask();
                        str2.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/3";
                        SavingThrowsAPIRequestAsyncTask con2 = new SavingThrowsAPIRequestAsyncTask();
                        con2.execute(savingThrowsURLRequest);
                        break;
                    case 6:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/12";
                        EquipmentAPIRequestAsyncTask dart = new EquipmentAPIRequestAsyncTask();
                        dart.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/28";
                        EquipmentAPIRequestAsyncTask shortsword = new EquipmentAPIRequestAsyncTask();
                        shortsword.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/158";
                        EquipmentAPIRequestAsyncTask explorersPack2 = new EquipmentAPIRequestAsyncTask();
                        explorersPack2.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/19";
                        SkillsAPIRequestAsyncTask simpleWeapons5 = new SkillsAPIRequestAsyncTask();
                        simpleWeapons5.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/48";
                        SkillsAPIRequestAsyncTask shortSwords2 = new SkillsAPIRequestAsyncTask();
                        shortSwords2.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/1";
                        SavingThrowsAPIRequestAsyncTask str3 = new SavingThrowsAPIRequestAsyncTask();
                        str3.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/2";
                        SavingThrowsAPIRequestAsyncTask dex2 = new SavingThrowsAPIRequestAsyncTask();
                        dex2.execute(savingThrowsURLRequest);
                        break;
                    case 7:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/47";
                        EquipmentAPIRequestAsyncTask chainMail2 = new EquipmentAPIRequestAsyncTask();
                        chainMail2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/50";
                        EquipmentAPIRequestAsyncTask shield3 = new EquipmentAPIRequestAsyncTask();
                        shield3.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/7";
                        EquipmentAPIRequestAsyncTask mace2 = new EquipmentAPIRequestAsyncTask();
                        mace2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/159";
                        EquipmentAPIRequestAsyncTask priestsPack2 = new EquipmentAPIRequestAsyncTask();
                        priestsPack2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/93";
                        EquipmentAPIRequestAsyncTask emblem = new EquipmentAPIRequestAsyncTask();
                        emblem.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/24";
                        EquipmentAPIRequestAsyncTask morningStar = new EquipmentAPIRequestAsyncTask();
                        morningStar.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/4";
                        SkillsAPIRequestAsyncTask allArmor2 = new SkillsAPIRequestAsyncTask();
                        allArmor2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/18";
                        SkillsAPIRequestAsyncTask shields5 = new SkillsAPIRequestAsyncTask();
                        shields5.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/19";
                        SkillsAPIRequestAsyncTask simpleWeapons6 = new SkillsAPIRequestAsyncTask();
                        simpleWeapons6.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/20";
                        SkillsAPIRequestAsyncTask martialWeapons3 = new SkillsAPIRequestAsyncTask();
                        martialWeapons3.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/5";
                        SavingThrowsAPIRequestAsyncTask wis3 = new SavingThrowsAPIRequestAsyncTask();
                        wis3.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/6";
                        SavingThrowsAPIRequestAsyncTask cha3 = new SavingThrowsAPIRequestAsyncTask();
                        cha3.execute(savingThrowsURLRequest);
                        break;
                    case 8:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/36";
                        EquipmentAPIRequestAsyncTask longbow = new EquipmentAPIRequestAsyncTask();
                        longbow.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/54";
                        EquipmentAPIRequestAsyncTask arrow = new EquipmentAPIRequestAsyncTask();
                        arrow.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/43";
                        EquipmentAPIRequestAsyncTask scaleMail2 = new EquipmentAPIRequestAsyncTask();
                        scaleMail2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/2";
                        EquipmentAPIRequestAsyncTask dagger2 = new EquipmentAPIRequestAsyncTask();
                        dagger2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/158";
                        EquipmentAPIRequestAsyncTask explorersPack3 = new EquipmentAPIRequestAsyncTask();
                        explorersPack3.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/1";
                        SkillsAPIRequestAsyncTask lightArmor5 = new SkillsAPIRequestAsyncTask();
                        lightArmor5.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/2";
                        SkillsAPIRequestAsyncTask mediumArmor4 = new SkillsAPIRequestAsyncTask();
                        mediumArmor4.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/18";
                        SkillsAPIRequestAsyncTask shields6 = new SkillsAPIRequestAsyncTask();
                        shields6.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/19";
                        SkillsAPIRequestAsyncTask simpleWeapons7 = new SkillsAPIRequestAsyncTask();
                        simpleWeapons7.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/20";
                        SkillsAPIRequestAsyncTask martialWeapons4 = new SkillsAPIRequestAsyncTask();
                        martialWeapons4.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/1";
                        SavingThrowsAPIRequestAsyncTask str4 = new SavingThrowsAPIRequestAsyncTask();
                        str4.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/2";
                        SavingThrowsAPIRequestAsyncTask dex3 = new SavingThrowsAPIRequestAsyncTask();
                        dex3.execute(savingThrowsURLRequest);
                        break;
                    case 9:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/39";
                        EquipmentAPIRequestAsyncTask leather3 = new EquipmentAPIRequestAsyncTask();
                        leather3.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/2";
                        EquipmentAPIRequestAsyncTask dagger3 = new EquipmentAPIRequestAsyncTask();
                        dagger3.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/28";
                        EquipmentAPIRequestAsyncTask shortsword2 = new EquipmentAPIRequestAsyncTask();
                        shortsword2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/13";
                        EquipmentAPIRequestAsyncTask shortBow = new EquipmentAPIRequestAsyncTask();
                        shortBow.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/54";
                        EquipmentAPIRequestAsyncTask arrow2 = new EquipmentAPIRequestAsyncTask();
                        arrow2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/154";
                        EquipmentAPIRequestAsyncTask burglarsPack = new EquipmentAPIRequestAsyncTask();
                        burglarsPack.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/1";
                        SkillsAPIRequestAsyncTask lightArmor6 = new SkillsAPIRequestAsyncTask();
                        lightArmor6.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/19";
                        SkillsAPIRequestAsyncTask simpleWeapons8 = new SkillsAPIRequestAsyncTask();
                        simpleWeapons8.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/42";
                        SkillsAPIRequestAsyncTask longSwords2 = new SkillsAPIRequestAsyncTask();
                        longSwords2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/46";
                        SkillsAPIRequestAsyncTask rapiers2 = new SkillsAPIRequestAsyncTask();
                        rapiers2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/48";
                        SkillsAPIRequestAsyncTask shortSwords3 = new SkillsAPIRequestAsyncTask();
                        shortSwords3.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/54";
                        SkillsAPIRequestAsyncTask handCrossbows2 = new SkillsAPIRequestAsyncTask();
                        handCrossbows2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/96";
                        SkillsAPIRequestAsyncTask thievesTools = new SkillsAPIRequestAsyncTask();
                        thievesTools.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/2";
                        SavingThrowsAPIRequestAsyncTask dex4 = new SavingThrowsAPIRequestAsyncTask();
                        dex4.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/4";
                        SavingThrowsAPIRequestAsyncTask intel2 = new SavingThrowsAPIRequestAsyncTask();
                        intel2.execute(savingThrowsURLRequest);
                        break;
                    case 10:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/2";
                        EquipmentAPIRequestAsyncTask dagger4 = new EquipmentAPIRequestAsyncTask();
                        dagger4.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/2";
                        EquipmentAPIRequestAsyncTask dagger5 = new EquipmentAPIRequestAsyncTask();
                        dagger5.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/11";
                        EquipmentAPIRequestAsyncTask crossbow = new EquipmentAPIRequestAsyncTask();
                        crossbow.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/56";
                        EquipmentAPIRequestAsyncTask crossbowBolt = new EquipmentAPIRequestAsyncTask();
                        crossbowBolt.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/61";
                        EquipmentAPIRequestAsyncTask orb = new EquipmentAPIRequestAsyncTask();
                        orb.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/156";
                        EquipmentAPIRequestAsyncTask dungeonPack2 = new EquipmentAPIRequestAsyncTask();
                        dungeonPack2.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/22";
                        SkillsAPIRequestAsyncTask daggers2 = new SkillsAPIRequestAsyncTask();
                        daggers2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/28";
                        SkillsAPIRequestAsyncTask quarterStaffs2 = new SkillsAPIRequestAsyncTask();
                        quarterStaffs2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/32";
                        SkillsAPIRequestAsyncTask darts2 = new SkillsAPIRequestAsyncTask();
                        darts2.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/34";
                        SkillsAPIRequestAsyncTask slings2 = new SkillsAPIRequestAsyncTask();
                        slings2.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/3";
                        SavingThrowsAPIRequestAsyncTask con3 = new SavingThrowsAPIRequestAsyncTask();
                        con3.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/6";
                        SavingThrowsAPIRequestAsyncTask cha4 = new SavingThrowsAPIRequestAsyncTask();
                        cha4.execute(savingThrowsURLRequest);
                        break;
                    case 11:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/2";
                        EquipmentAPIRequestAsyncTask dagger6 = new EquipmentAPIRequestAsyncTask();
                        dagger6.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/2";
                        EquipmentAPIRequestAsyncTask dagger7 = new EquipmentAPIRequestAsyncTask();
                        dagger7.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/11";
                        EquipmentAPIRequestAsyncTask crossbowLight = new EquipmentAPIRequestAsyncTask();
                        crossbowLight.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/56";
                        EquipmentAPIRequestAsyncTask crossbowBolt2 = new EquipmentAPIRequestAsyncTask();
                        crossbowBolt2.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/60";
                        EquipmentAPIRequestAsyncTask crystal = new EquipmentAPIRequestAsyncTask();
                        crystal.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/160";
                        EquipmentAPIRequestAsyncTask scholarsPack = new EquipmentAPIRequestAsyncTask();
                        scholarsPack.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/4";
                        EquipmentAPIRequestAsyncTask handAxe2 = new EquipmentAPIRequestAsyncTask();
                        handAxe2.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/1";
                        SkillsAPIRequestAsyncTask lightArmor7 = new SkillsAPIRequestAsyncTask();
                        lightArmor7.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/19";
                        SkillsAPIRequestAsyncTask simpleWeapons9 = new SkillsAPIRequestAsyncTask();
                        simpleWeapons9.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/5";
                        SavingThrowsAPIRequestAsyncTask wis4 = new SavingThrowsAPIRequestAsyncTask();
                        wis4.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/6";
                        SavingThrowsAPIRequestAsyncTask cha5 = new SavingThrowsAPIRequestAsyncTask();
                        cha5.execute(savingThrowsURLRequest);
                        break;
                    case 12:
                        inventory.clear();
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/145";
                        EquipmentAPIRequestAsyncTask spellBook = new EquipmentAPIRequestAsyncTask();
                        spellBook.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/2";
                        EquipmentAPIRequestAsyncTask dagger8 = new EquipmentAPIRequestAsyncTask();
                        dagger8.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/63";
                        EquipmentAPIRequestAsyncTask staff = new EquipmentAPIRequestAsyncTask();
                        staff.execute(inventoryURLRequest);
                        inventoryURLRequest = "http://www.dnd5eapi.co/api/equipment/160";
                        EquipmentAPIRequestAsyncTask scholarsPack2 = new EquipmentAPIRequestAsyncTask();
                        scholarsPack2.execute(inventoryURLRequest);

                        skills.clear();
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/22";
                        SkillsAPIRequestAsyncTask daggers3 = new SkillsAPIRequestAsyncTask();
                        daggers3.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/28";
                        SkillsAPIRequestAsyncTask quarterStaffs3 = new SkillsAPIRequestAsyncTask();
                        quarterStaffs3.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/32";
                        SkillsAPIRequestAsyncTask darts3 = new SkillsAPIRequestAsyncTask();
                        darts3.execute(skillsURLRequest);
                        skillsURLRequest = "http://www.dnd5eapi.co/api/proficiencies/34";
                        SkillsAPIRequestAsyncTask slings3 = new SkillsAPIRequestAsyncTask();
                        slings3.execute(skillsURLRequest);

                        savingThrows.clear();
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/4";
                        SavingThrowsAPIRequestAsyncTask intel3 = new SavingThrowsAPIRequestAsyncTask();
                        intel3.execute(savingThrowsURLRequest);
                        savingThrowsURLRequest = "http://www.dnd5eapi.co/api/ability-scores/5";
                        SavingThrowsAPIRequestAsyncTask wis5 = new SavingThrowsAPIRequestAsyncTask();
                        wis5.execute(savingThrowsURLRequest);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
            textViewArrayList.get(i).setText(Integer.toString(dice.rollDice(0)));
        }
        // set roll button to be invisible
        Button rollStatsButton = (Button) findViewById(R.id.statsButton);
        rollStatsButton.setVisibility(View.INVISIBLE);
    }

    private class DnDClassAPIRequestAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String className) {
            super.onPostExecute(className);
            Log.d(TAG, "onPostExecute: " + className);

            classNames.add(className);
            character.setCharClass(className);
        }

        @Override
        protected String doInBackground(String... strings) {
            // strings[0] holds the url
            Log.d(TAG, "doInBackground: " + strings[0]);
            String name = "";

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                String result = "";
                int data = reader.read();
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }

                JSONObject jsonObject = new JSONObject(result);
                name = jsonObject.getString("name");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return name;
        }
    }

    private class EquipmentAPIRequestAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: " + s);
            if (s.equals("Javelin"))
                s += " (4)";
            else if (s.equals("Dart"))
                s += " (10)";
            else if (s.equals("Arrow"))
                s += " (20)";
            else if (s.equals("Crossbow bolt"))
                s+= " (20)";
            Log.d(TAG, "onPostExecute: " + inventory.size());
            inventory.add(s);
            Log.d(TAG, "onPostExecute: " + inventory.size());

            String result = "";
            for(int i = 0; i < inventory.size(); i++) {
                result += inventory.get(i) + "\n";
            }
            TextView inventoryText = (TextView) findViewById(R.id.equipmentValue);
            inventoryText.setText(result);
            character.setInventory(inventory);
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            // strings[0] holds the url
            Log.d(TAG, "doInBackground: " + strings[0]);
            String name = "";

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                String result = "";
                int data = reader.read();
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }

                JSONObject jsonObject = new JSONObject(result);
                name = jsonObject.getString("name");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return name;
        }
    }

    private class SkillsAPIRequestAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            skills.add(s);
            character.setSkills(skills);

            String result = "";
            for(int i = 0; i < skills.size(); i++) {
                result += skills.get(i) + "\n";
            }
            TextView skillsText = (TextView) findViewById(R.id.skillsValue);
            skillsText.setText(result);
        }

        @Override
        protected String doInBackground(String... strings) {
            // strings[0] holds the url
            Log.d(TAG, "doInBackground: " + strings[0]);
            String name = "";

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                String result = "";
                int data = reader.read();
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }

                JSONObject jsonObject = new JSONObject(result);
                name = jsonObject.getString("name");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return name;
        }
    }

    private class SavingThrowsAPIRequestAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            savingThrows.add(s);
            String result = "";
            for(int i = 0; i < savingThrows.size(); i++) {
                result += savingThrows.get(i) + "\n";
            }
            TextView savingThrowsText = (TextView) findViewById(R.id.savingThrowsValue);
            savingThrowsText.setText(result);
        }

        @Override
        protected String doInBackground(String... strings) {
            // strings[0] holds the url
            Log.d(TAG, "doInBackground: " + strings[0]);
            String name = "";

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                String result = "";
                int data = reader.read();
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }

                JSONObject jsonObject = new JSONObject(result);
                name = jsonObject.getString("name");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return name;
        }
    }
}
