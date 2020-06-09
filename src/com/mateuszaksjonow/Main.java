package com.mateuszaksjonow;

public class Main {

    private final static int POOL_SIZE = 10;
    private final static int NUMBER_OF_TASKS = 10;
    private final static int BLOCKCHAIN_LENGTH = 5;

    public static void main(String[] args) {

        Blockchain blockchain = Blockchain.getInstance();

        blockchain.createBlockchain(POOL_SIZE, NUMBER_OF_TASKS, BLOCKCHAIN_LENGTH);
    }
}
