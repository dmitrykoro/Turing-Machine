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

    public static void main(String[] args) {
        operateFiles();
    }


}
