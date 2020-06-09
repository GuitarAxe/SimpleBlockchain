package com.mateuszaksjonow;

import java.util.Date;
import java.util.Random;

public class Block {

    private long id;
    private static long counter = 1;
    private String hash;
    private String previousHash;
    private long timeStamp;
    private int magicNumber;

    public Block(String previousHash, int zeroes) {
        this.id = counter;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash(zeroes);
        counter++;
    }

    public String calculateHash(int zeroes) {
        StringBuilder start = new StringBuilder();
        //adds more zeroes to hash code if creating block is too quick
        for (int i = 0; i < zeroes; i++) {
            start.append("0");
        }
        String calculatedHash = "";
        boolean quit = false;
        while (!quit) {
            Random random = new Random();
            magicNumber = random.nextInt();
            calculatedHash = StringUtil.applySha256(
                    Integer.toString(magicNumber) + previousHash + Long.toString(timeStamp));
            if (calculatedHash.startsWith(start.toString())) quit = true;
        }
        return calculatedHash;
    }

    public long getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getMagicNumber() {
        return magicNumber;
    }
}
