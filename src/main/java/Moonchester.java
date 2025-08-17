public class Moonchester {

    public static void userGreeting () {
        String greetingMessage = """
        ____________________________________________________________
        Hello! I am Moonchester, your personal chatbot!
        What can I do for you?
        ____________________________________________________________
        """;
        System.out.print(greetingMessage);
    }

    public static void userExit () {
        String exitMessage = """
        Hope to see you again soon, goodbye!
        ____________________________________________________________
        """;
        System.out.print(exitMessage);
    }

    public static void main(String[] args) {
        userGreeting();
        userExit();
    }
}
