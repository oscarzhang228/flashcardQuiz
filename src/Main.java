import java.util.*;
import java.io.*;

public class Main {
    private static List<String> flashcards = new ArrayList<>();
    private static String storeCards;

    public static void main(String[] args) throws IOException {
        //to recreate quizlet in the command line we need flashcards, learn, test, and match with functionality
        //also allow the user to login and have their flashcards from that account saved
        //if they are logged in then save
        Scanner input = new Scanner(System.in);
        System.out.println(new File("usernames.properties").getAbsolutePath());


        //signing up/login section
        System.out.println("Sign Up or Log In.");
        String userAnswer = input.nextLine().toLowerCase();

        while (!userAnswer.equals("sign up") && !userAnswer.equals("log in")) {
            System.out.println("Please enter either sign up or log in.");
            userAnswer = input.nextLine().toLowerCase();
        }

        if(userAnswer.equals("sign up")) {
            System.out.println("Please enter a username");
            userAnswer = input.nextLine();
            while(!signUp(userAnswer)) {
                System.out.println(userAnswer + " has already been taken. Please enter a different username.");
                userAnswer = input.nextLine();
            }
        } else {
            System.out.println("Please enter your username.");
            userAnswer = input.nextLine();
            while(!login(userAnswer)) {
                System.out.println("We could not find an account with that username. Please be aware that it is case sensitive. Please try again.");
                userAnswer = input.nextLine();
            }
        }


    }

    public static boolean signUp(String username) throws IOException {
        FileReader reader = new FileReader("usernames.properties");

        Properties p = new Properties();

        p.load(reader);

        if (p.contains(username)) {
            return false;
        } else {
            p.setProperty(username, "");
            p.store(new FileWriter("usernames.properties"), "");
            return true;
        }


    }

    public static boolean login(String username) throws IOException {
        FileReader reader = new FileReader("usernames.properties");
        Properties p = new Properties();

        p.load(reader);

        if (p.containsKey(username)) {
            storeCards = p.getProperty(username);

            int start = 0;

            for (int i = 3; i < storeCards.length(); i++) {
                if (storeCards.substring(i - 3, i + 1).equals("..//")) {
                    flashcards.add(storeCards.substring(start, i - 3));
                    start = i + 1;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}