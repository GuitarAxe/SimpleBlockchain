package com.mateuszaksjonow;

import com.mateuszaksjonow.Block;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Blockchain {

    private ArrayList<Block> blockchain = new ArrayList<>();
    private int zeroes = 0;
    static int count = 1;

    private static Blockchain instance = new Blockchain();

    Set<Callable<Block>> callables = new HashSet<Callable<Block>>();

    private Blockchain() {
    }

    //start blockchain or adds new block
    public Block addBlock() {
        Block block;
        if (blockchain.size() == 0) {
            block = new Block("0", zeroes);
        }else {
            block = new Block(blockchain.get(blockchain.size()-1).getHash(), zeroes);
        }
        return block;
    }

    public Boolean isChainValid(ArrayList<Block> blockchain) {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[zeroes]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.getHash().equals(currentBlock.calculateHash(zeroes)) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if(!currentBlock.getHash().substring( 0, zeroes).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    public void incrementZeroes() {
        zeroes++;
    }

    public void decrementZeroes() {
        zeroes--;
    }

    public int getZeroes() {
        return zeroes;
    }

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public static Blockchain getInstance() {
        if (instance == null) {
            instance = new Blockchain();
        }
        return instance;
    }

    public int size() {
        return blockchain.size();
    }

    public void printBlockchain(int i) {
        Block block = blockchain.get(i);
        System.out.println("Block:");
        System.out.println("Id: " + count);
        System.out.println("Timestamp: " + block.getTimeStamp());
        System.out.println("Magic number: " + block.getMagicNumber());
        System.out.println("Hash of the previous block:");
        System.out.println(block.getPreviousHash());
        System.out.println("Hash of the block:");
        System.out.println(block.getHash());
        System.out.println("Block was generating for " );
        System.out.println("N changed to " + zeroes);
//            System.out.println("Block generated!");
        System.out.println();
        count++;
    }

    public void createBlockchain(int poolSize, int numberOfTasks, int blockchainLength) {
        //create multiple threads simulating mining computers
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        for (int i = 0; i < numberOfTasks; i++) {
            callables.add(new Callable<Block>() {
                @Override
                public Block call() throws Exception {
                    return instance.addBlock();
                }
            });
        }
        for (int y = 0; y < blockchainLength; y++) {
            Block newBlock = null;
            //invokes threads to add block and stops after first thread finds hash code.
            // Decreases number of zeroes if creating takes too long
            try {
                newBlock = executor.invokeAny(callables, 3, TimeUnit.SECONDS);
            }catch (Exception e) {
                instance.decrementZeroes();
                y--;
            }
            if (newBlock != null) {
                instance.getBlockchain().add(newBlock);
                instance.printBlockchain(blockchain.size()-1);
                instance.incrementZeroes();
            }
        }
        executor.shutdown();
    }
}
