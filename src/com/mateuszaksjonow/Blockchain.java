package com.mateuszaksjonow;

import java.util.ArrayList;

public class Blockchain {

    private ArrayList<Block> blockchain = new ArrayList<>();
    private int zeroes;

    public Blockchain(int zeroes) {
        this.zeroes = zeroes;
    }

    public void addBlock() {
        Block block;
        if (blockchain.size() == 0) {
            block = new Block("0", zeroes);
//            block.mineBlock(zeroes);
            blockchain.add(block);
        }else {
            block = new Block(blockchain.get(blockchain.size()-1).getHash(), zeroes);
//            block.mineBlock(zeroes);
            blockchain.add(block);
        }
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

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public void printBlockchain(int i) {
       Block block = blockchain.get(i);
            System.out.println("Block:");
            System.out.println("Id: " + block.getId());
            System.out.println("Timestamp: " + block.getTimeStamp());
            System.out.println("Magic number: " + block.getMagicNumber());
            System.out.println("Hash of the previous block:");
            System.out.println(block.getPreviousHash());
            System.out.println("Hash of the block:");
            System.out.println(block.getHash());
            System.out.println("Block generated!");
            System.out.println();

    }
}
