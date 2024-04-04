import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server.");
            System.out.println("Enter your vote (A or B): ");
            String vote = consoleReader.readLine();

            if (!vote.equals("A") && !vote.equals("B")) {
                System.out.println("Invalid vote. Please vote for 'A' or 'B'.");
                return;
            }

            writer.println(vote);

            String response = reader.readLine();
            System.out.println("Received response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
