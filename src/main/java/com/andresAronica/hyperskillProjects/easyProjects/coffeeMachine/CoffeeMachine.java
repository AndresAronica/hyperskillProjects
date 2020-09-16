package com.andresAronica.hyperskillProjects.easyProjects.coffeeMachine;

import java.util.Scanner;


public class CoffeeMachine {

    // Pos 0 is Water, 1 is Milk, 2 is Coffee Beans, 3 Money
    private static final int[] ESPRESSO = {250, 0, 16, 4};
    private static final int[] LATTE = {350, 75, 20, 7};
    private static final int[] CAPPUCCINO = {200, 100, 12, 6};

    private static int availableWater = 400;
    private static int availableMilk = 540;
    private static int availableCoffeeBeans = 120;
    private static int availableDisposableCups = 9;
    private static int availableMoney = 550;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        recursiveMenu();
    }

    private static void recursiveMenu() {
        String action;
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        action = sc.next();
        switch (action) {
            case "buy":
                buyFromCoffeeMachine();
                recursiveMenu();
                break;
            case "fill":
                fillCoffeeMachine();
                recursiveMenu();
                break;
            case "take":
                takeMoneyFromCoffeeMachine();
                recursiveMenu();
                break;
            case "remaining":
                printCoffeeMachineStock();
                recursiveMenu();
                break;
            case "exit":
                System.exit(0);
                break;
        }

    }

    private static void checkResources(int[] coffee) {
        if (availableWater < coffee[0]) {
            System.out.println("Sorry, not enough water!");
        } else if (availableMilk < coffee[1]) {
            System.out.println("Sorry, not enough milk!");
        } else if (availableCoffeeBeans < coffee[2]) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (availableDisposableCups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            availableWater -= coffee[0];
            availableMilk -= coffee[1];
            availableCoffeeBeans -= coffee[2];
            availableDisposableCups -= 1;
            availableMoney += coffee[3];
        }
    }

    private static void buyFromCoffeeMachine() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String orderedCoffee = sc.next();
        switch (orderedCoffee) {
            case "1":
            case "espresso":
                checkResources(ESPRESSO);
                break;
            case "2":
            case "latte":
                checkResources(LATTE);
                break;
            case "3":
            case "cappuccino":
                checkResources(CAPPUCCINO);
                break;
            case "back":
                break;
        }
        System.out.println();
    }

    private static void printCoffeeMachineStock() {
        System.out.printf("The coffee machine has:\n" +
                        "%d of water\n" +
                        "%d of milk\n" +
                        "%d of coffee beans\n" +
                        "%d of disposable cups\n" +
                        "%d of money\n",
                availableWater, availableMilk, availableCoffeeBeans, availableDisposableCups, availableMoney);
    }

    private static void takeMoneyFromCoffeeMachine() {
        System.out.printf("I gave you $%d", availableMoney);
        System.out.println();
        availableMoney = 0;
        System.out.println();
    }

    private static void fillCoffeeMachine() {
        int[] stuffToAdd = new int[4];

        System.out.println("Write how many ml of water do you want to add:");
        stuffToAdd[0] = sc.nextInt();
        System.out.println("Write how many ml of milk do you want to add:");
        stuffToAdd[1] = sc.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add:");
        stuffToAdd[2] = sc.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        stuffToAdd[3] = sc.nextInt();

        availableWater += stuffToAdd[0];
        availableMilk += stuffToAdd[1];
        availableCoffeeBeans += stuffToAdd[2];
        availableDisposableCups += stuffToAdd[3];
        System.out.println();
    }
}