import java.util.Scanner;

public class Help {
    private static String aboutText =  " This program will allow the user to choose from a list of exercises from the Introduction to Java Programming textbook. Upon selecting an exercise, \n the program will output a visual representation of the solution to the problem, as well as a few tips about solving the problem. After working an \n exercise the user will be able to select a new one or exit the program";
    public static void about() {
        Scanner key = new Scanner(System.in);

        String aboutText =  "This program will allow the user to choose from a list of exercises from the Introduction to Java Programming textbook" + "\n" +
                            "Upon selecting aan exercise, the program will output a visual representation of the solution to the problem, as well as a few tips about solving the problem" + "\n" +
                            "After working an exercise the user will be able to select a new one or exit the program";


        System.out.println("Help-About:\n"+aboutText+"\n\nPress enter to continue.");

        key.nextLine();
    }
    public static String getAboutText() {
        return aboutText;
    }
}
