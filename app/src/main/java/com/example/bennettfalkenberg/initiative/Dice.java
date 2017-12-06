package com.example.bennettfalkenberg.initiative;

import java.util.Random;

/**
 * Created by bennettfalkenberg on 11/28/17.
 */

public class Dice {

    private int numSides;
    private int sideUp;
    private int numDice;

    public Dice() {
        numSides = 0;
        sideUp = 0;
        numDice = 0;
    }

    public Dice(int numSides, int numDice) {
        this.numSides = numSides;
        this.numDice = numDice;
    }

    public int getNumSides() {
        return numSides;
    }

    public void setNumSides(int numSides) {
        this.numSides = numSides;
    }

    public int getNumDice() {
        return numDice;
    }

    public void setNumDice(int numDice) {
        this.numDice = numDice;
    }

    public int rollDice(int modifier) {
        int result = 0;
        Random random = new Random();
        for (int i = 0; i < numDice; i++) {
            sideUp = random.nextInt(numSides) + 1;
            result += sideUp;
        }
        return result + modifier;
    }
}
