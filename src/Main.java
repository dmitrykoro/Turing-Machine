import java.io.*;
import java.util.Scanner;

public class Main {
    private static String[][] statesMatrix;
    private static StringBuilder alphabet = new StringBuilder();
    private static int numberOfStates;
    private static char[] inputtedTape;
    private static String operatedTape = "";

    private static void operateFiles() {
        Scanner inputTape = null;
        Scanner states = null;
        Scanner description = null;
        try {
            inputTape = new Scanner(new FileReader("data//tape.txt"));
            states = new Scanner(new FileReader("data//states.txt"));
            description = new Scanner(new FileReader("data//description.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("The input file(s) doesn't exist\n");
        }

        while (description.hasNextLine()) {
            try {
                if (description.nextLine().equals("Number of states:")) {
                    numberOfStates = Integer.parseInt(description.nextLine());
                }
                if (description.nextLine().equals("Alphabet (with \"_\" at the end):")) {
                    alphabet.append(description.nextLine());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid description file format\n");
            }
        }
        description.close();

        while (inputTape.hasNextLine()) {
            inputtedTape = inputTape.nextLine().toCharArray();
        }
        inputTape.close();

        statesMatrix = new String[alphabet.length()][numberOfStates];
        while (states.hasNextLine()) {
            String currentString = states.nextLine();
            int i = Character.getNumericValue(currentString.charAt(0)) - 1;
            char j = currentString.charAt(2);
            statesMatrix[alphabet.indexOf(String.valueOf(j))][i] = currentString.substring(4);
        }
        states.close();

    }

    private static void executeMachine() {
        char[] operatingTape = new char[1000];
        for (int j = 0; j < operatingTape.length; j++)
            operatingTape[j] = '_';
        System.arraycopy(inputtedTape, 0, operatingTape, 500, 500 + inputtedTape.length - 500);

        char currentValue;
        int currentState = 0;
        int currentPos = 500;
        char[] command;

        while (currentState != -1) {
            currentValue = operatingTape[currentPos];
            command = statesMatrix[alphabet.indexOf(String.valueOf(currentValue))][currentState].toCharArray();
            operatingTape[currentPos] = command[0];

            if (command[2] == '>') {
                currentPos++;
            } else if (command[2] == '<') {
                currentPos--;
            }
            currentState = Character.getNumericValue(command[4]) - 1;
        }

        for (int k = 0; k < operatingTape.length; k++) {
            if (operatingTape[k] != '_') {
                operatedTape += operatingTape[k];
            }
        }
    }

    private static void writeResult() {
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter("data//result.txt"));
            wr.write(operatedTape);
            wr.close();
        } catch (IOException e) {
            System.out.println("Error: the result file doesn't exist");
        }
    }

    public static void main(String[] args) {
        operateFiles();
        executeMachine();
        writeResult();
    }
}
