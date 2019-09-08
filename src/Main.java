import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    private static String[][] statesMatrix;
    private static StringBuilder alphabet = new StringBuilder();
    private static int numberOfStates;
    private static char[] inputtedTape;

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
                if (description.nextLine().equals("Alphabet:")) {
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
        StringBuilder tempOperatingTape = new StringBuilder();
        tempOperatingTape.setLength(1000);
        for (int j = 0; j < tempOperatingTape.length(); j++)
            tempOperatingTape.setCharAt(j, ' ');
        for (int i = 500; i < 500 + inputtedTape.length; i++)
            tempOperatingTape.setCharAt(i, inputtedTape[i - 500]);
        //operatingTape.toString();
        String operatingTape = tempOperatingTape.toString();
        //operatingTape.toCharArray();

        char currentValue;
        int currentState = 0;
        int currentPos = 500;
        char[] command;

        while (currentState != -1) {
            ///
            currentValue = operatingTape.charAt(currentPos);
            command = statesMatrix[alphabet.indexOf(String.valueOf(currentValue))]
                    [currentState].toCharArray();
            operatingTape.toCharArray()[currentPos] = command[0];
            if (command[2] == '>') {
                currentPos++;
            }
            else if (command[2] == '<') {
                currentPos--;
            }
            currentState = Character.getNumericValue(command[4]) - 1;
            ///
        }
    }

    public static void main(String[] args) {
        operateFiles();
        executeMachine();
    }


}
