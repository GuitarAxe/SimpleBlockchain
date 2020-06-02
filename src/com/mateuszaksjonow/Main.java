package com.mateuszaksjonow;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must starts with:");
        int input = (scanner.nextInt());
        scanner.nextLine();



        Blockchain blockchain = new Blockchain(input);
        for (int i = 0; i < 5; i++) {
            blockchain.addBlock();
            blockchain.printBlockchain(i);
        }
    }
}
