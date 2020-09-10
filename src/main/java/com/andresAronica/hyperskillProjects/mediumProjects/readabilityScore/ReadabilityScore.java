package com.andresAronica.hyperskillProjects.mediumProjects.readabilityScore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadabilityScore {

    private static int characters = 0;
    private static int words= 0;
    private static int sentences = 0;
    private static int syllables = 0;
    private static int polysyllables= 0;
    private static double avgWordLength = 0.0d;
    private static double avgSentenceLength = 0.0d;
    private static double avgAge = 0.0d;

    // Sin este char en particular, fallaba los tests automaticos con los nombres de metodologias Flesch-Kincaid
    // y el de Coleman-Liau. Era la unica solucion al problema, por mas que haga esa parte del codigo mucho mas ilegible
    // Si se prueba por consola, se reemplaza el '-' con un '?'.
    // private static char guion= 8211;

    // args[0] is the input text, args[1] indexes to calculate
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File(args[0]);
        Scanner sc = new Scanner(inputFile);

        String[] newInputText = new String[1];
        int i = 0;
        while (sc.hasNextLine()) {
            newInputText[i] = sc.nextLine();
        }

        sc.close();

        String inputText = newInputText[0];

        printInputText(inputText);
        divideInputString(inputText);
        printGeneralInformation();
        calculateIndexes(args[1]);
    }

    private static void printInputText(String inputText) {
        System.out.printf("The text is:%n%s", inputText);
        System.out.println();
    }

    private static void divideInputString(String inputText) {
        characters = inputText.replaceAll("\\s","").split("").length;

        String[] totalWords = inputText.split("\\s");
        words = totalWords.length;

        for (String word : totalWords) {
            if (countWithRegex(word) > 2) {
                polysyllables++;
            }
            syllables += countWithRegex(word);
        }

        sentences = inputText.split("[.!?]+\\s*").length;

        avgWordLength = (double) characters / words;
        avgSentenceLength = (double) words / sentences;
    }

    private static void printGeneralInformation() {
        System.out.println();
        System.out.printf("Words: %d%n" +
                        "Sentences: %d%n" +
                        "Characters: %d%n" +
                        "Syllables: %d%n" +
                        "Polysyllables: %d%n",
                words,
                sentences,
                characters,
                syllables,
                polysyllables);
    }

    private static void calculateIndexes(String indexToCalculate) {
        System.out.println();
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): " + indexToCalculate);
        System.out.println();

        switch (indexToCalculate) {
            case "ARI":
                calculateAutomatedReadabilityIndex();
                break;
            case "FK":
                calculateFleschKincaidReadabilityIndex();
                break;
            case "SMOG":
                calculateSMOGReadabilityIndex();
                break;
            case "CL":
                calculateColemanLiauReadabilityIndex();
                break;
            case "all":
                calculateAutomatedReadabilityIndex();
                calculateFleschKincaidReadabilityIndex();
                calculateSMOGReadabilityIndex();
                calculateColemanLiauReadabilityIndex();
                System.out.println();
                System.out.format("This text should be understood in average by %.2f year olds.", (avgAge / 4));
                break;
        }
    }

    // One method per index.
    private static void calculateAutomatedReadabilityIndex() {
        // Formula to calculate this index.
        double score = ((4.71d * avgWordLength) + (0.5d * avgSentenceLength)) - 21.43d;

        long roundedReadabilityScore = Math.round(score);
        double avgAgeTemp = calculateAvgAge(roundedReadabilityScore);

        if (avgAgeTemp == 25) {
            System.out.printf("Automated Readability Index: %.2f (about 24+ year olds).", score);
        } else {
            System.out.printf("Automated Readability Index: %.2f (about %.0f year olds).", score, avgAgeTemp);
        }
        System.out.println();
        avgAge += avgAgeTemp;
    }

    private static void calculateFleschKincaidReadabilityIndex() {
        // Formula to calculate this index.
        double score = 0.39d * words / sentences + 11.8d * syllables / words - 15.59d;

        long roundedReadabilityScore = Math.round(score);

        double avgAgeTemp =calculateAvgAge(roundedReadabilityScore);

        if (avgAgeTemp == 25) {
            // Esta es la forma mas legible, pero los tests automaticos requerian que fuera feo como el de abajo
            System.out.printf("Flesch-Kincaid readability tests: %.2f (about 24+ year olds).", score);
            //System.out.print("Flesch" + guion + "Kincaid readability tests: " + score + "(about 24+ year olds).");
        } else {
            // Esta es la forma mas legible, pero los tests automaticos requerian que fuera feo como el de abajo
            System.out.printf("Flesch-Kincaid readability tests: %.2f (about %.0f year olds).", score, avgAgeTemp);
            //System.out.print("Flesch" + guion + "Kincaid readability tests: " + score + "(about " + avgAgeTemp + " year olds.");
        }
        System.out.println();
        avgAge += avgAgeTemp;
    }

    private static void calculateSMOGReadabilityIndex() {
        // Formula to calculate this index.
        double score = 1.043d * Math.sqrt((double) polysyllables * 30 / sentences) + 3.1291;

        long roundedReadabilityScore = Math.round(score);
        double avgAgeTemp = calculateAvgAge(roundedReadabilityScore);
        if (avgAgeTemp == 25) {
            System.out.printf("Simple Measure of Gobbledygook: %.2f (about 24+ year olds).", score);
        } else {
            System.out.printf("Simple Measure of Gobbledygook: %.2f (about %.0f year olds).", score, avgAgeTemp);
        }
        System.out.println();
        avgAge += avgAgeTemp;
    }

    private static void calculateColemanLiauReadabilityIndex() {
        // Formula to calculate this index.
        double score = 0.0588 * (((double) characters / words) * 100) - 0.296 * (( (double)sentences / words) * 100) - 15.8;

        long roundedReadabilityScore = Math.round(score);
        double avgAgeTemp = calculateAvgAge(roundedReadabilityScore);

        if (avgAgeTemp == 25) {
            // Esta es la forma mas legible, pero los tests automaticos requerian que fuera feo como el de abajo
            System.out.printf("Coleman-Liau index: %.2f (about 24+ year olds).", score);
            //System.out.print("Coleman" + guion + "Liau readability tests: " + score + "(about 24+ year olds).");
        } else {
            // Esta es la forma mas legible, pero los tests automaticos requerian que fuera feo como el de abajo
            System.out.printf("Coleman-Liau index: %.2f (about %.0f year olds).", score, avgAgeTemp);
            //System.out.print("Coleman" + guion + "Liau readability tests: " + score + "(about " + avgAgeTemp + " year olds.");
        }
        System.out.println();
        avgAge += avgAgeTemp;
    }

    // Helper method to count the ammount of syllables (in total) and the ammount of multi-syllable words
    private static int countWithRegex(String word) {
        String regexPattern = "(?i)[aiouy][aeiouy]*|e[aeiouy]*(?!d?\\b)";
        Matcher m = Pattern.compile(regexPattern).matcher(word);
        int count = 0;
        while(m.find()) {
            count++;
        }
        // In case that the word doesn't have any vowel, it has 1 syllable, that's why it always returns at least 1.
        return Math.max(count, 1);
    }

    // Gives you the average age based on the Automated Readability Index table (in this case, it uses the same table
    // for all the indexes.
    private static int calculateAvgAge(long roundedReadabilityScore) {
        int averageAge;
        switch ((int) roundedReadabilityScore) {
            case 1:
                averageAge = 6;
                break;
            case 2:
                averageAge = 7;
                break;
            case 3:
                averageAge = 9;
                break;
            case 4:
                averageAge = 10;
                break;
            case 5:
                averageAge = 11;
                break;
            case 6:
                averageAge = 12;
                break;
            case 7:
                averageAge = 13;
                break;
            case 8:
                averageAge = 14;
                break;
            case 9:
                averageAge = 15;
                break;
            case 10:
                averageAge = 16;
                break;
            case 11:
                averageAge = 17;
                break;
            case 12:
                averageAge = 18;
                break;
            case 13:
                averageAge = 24;
                break;
            default:
                averageAge = 25;
                break;
        }
        return averageAge;
    }
}
