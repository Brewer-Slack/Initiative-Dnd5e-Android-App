package com.example.bennettfalkenberg.initiative;

import java.util.ArrayList;

/**
 * Created by BJayB on 12/4/2017.
 */

public class Character {

    // fields
    private String name;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private String alignment;
    private ArrayList<String> inventory;
    private ArrayList<String> skills;
    private ArrayList<String> savingThrows;
    private String charClass;
    private String race;

    public Character() {
        name = "default name";
        strength = 15;
        dexterity = 14;
        constitution = 13;
        intelligence = 12;
        wisdom = 10;
        charisma = 8;
        alignment = "Lawful Good";
        inventory = new ArrayList<>();
        skills = new ArrayList<>();
        savingThrows = new ArrayList<>();
        inventory.add("bow");
        skills.add("fireball");
        savingThrows.add("Strength");
        charClass = "Ranger";
        race = "Human";
    }

    public Character(String name, int strength, int dexterity, int constitution, int intelligence,
                     int wisdom, int charisma, String alignment, ArrayList<String> inventory,
                     ArrayList<String> skills, ArrayList<String> savingThrows, String charClass, String race) {
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.alignment = alignment;
        this.inventory = inventory;
        this.skills = skills;
        this.savingThrows = savingThrows;
        this.charClass = charClass;
        this.race = race;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getStrength() {return strength;}

    public void setStrength(int strength) {this.strength = strength;}

    public int getDexterity() {return dexterity;}

    public void setDexterity(int dexterity) {this.dexterity = dexterity;}

    public int getConstitution() {return constitution;}

    public void setConstitution(int constitution) {this.constitution = constitution;}

    public int getIntelligence() {return intelligence;}

    public void setIntelligence(int intelligence) {this.intelligence = intelligence;}

    public int getCharisma() {return charisma;}

    public void setCharisma(int charisma) {this.charisma = charisma;}

    public String getAlignment() {return alignment;}

    public void setAlignment(String alignment) {this.alignment = alignment;}

    public ArrayList<String> getInventory() {return inventory;}

    public void setInventory(ArrayList<String> inventory) {this.inventory = inventory;}

    public String getCharClass() {return charClass;}

    public void setCharClass(String charClass) {this.charClass = charClass;}

    public String getRace() {return race;}

    public void setRace(String race) {this.race = race;}

    public int getWisdom() {return wisdom;}

    public void setWisdom(int wisdom) {this.wisdom = wisdom;}

    public ArrayList<String> getSkills() {return skills;}

    public void setSkills(ArrayList<String> skills) {this.skills = skills;}

    public ArrayList<String> getSavingThrows() {return savingThrows;}

    public void setSavingThrows(ArrayList<String> savingThrows) {this.savingThrows = savingThrows;}

    @Override
    public String toString() {
        return this.getName();
    }
}
