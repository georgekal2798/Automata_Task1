import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public abstract class UI {

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        HashMap<Integer, String> options = new HashMap<>();

//      Main menu options
        options.put(1,"Check word");
        options.put(2,"Print automaton details");
        options.put(3,"Load automaton from file");
        options.put(4,"Exit");

        do {
            System.out.println("\nChoose one of the options below:");
            for(Map.Entry<Integer, String> o : options.entrySet()) {
                System.out.println(o);
            }

            try {
                input = scanner.nextInt();

                switch (input){
                    case 1:{
                        stringInputPrompt("check");
                        break;
                    }
                    case 2:{
                        Automaton.printAutomatonDetails();
                        break;
                    }
                    case 3:{
                        stringInputPrompt("file");
                        break;
                    }
                    case 4:{
                        //exits the app
                        break;
                    }
                    default:{
                        System.out.println("Must enter one of the given options. Try again");
                    }
                }
            } catch (InputMismatchException e) {
                //Input is not an integer
                System.out.println("Must enter a number. Try again");
                scanner.nextLine();
            }
        } while (input != 4); //Displays main menu until the exit option (4) is selected
    }

    private static void stringInputPrompt(String option){
        //Accepts parameter option: "check" for checking a word, "file" for file path input
        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean wrongInput = false;

        System.out.println(option.equals("check")?
                "Enter a word to check if it is part of the automaton's language:":
                "Enter automaton's file path. JSON file type accepted");

        do {
            try {
                input = scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Wrong input. Try again");
                scanner.nextLine();
                wrongInput = true;
            }
        } while (wrongInput);

        if (option.equals("check"))
            Automaton.checkWord(input);
        else if (option.equals("file"))
            Automaton.createAutomatonFromFile(input);
    }
}
