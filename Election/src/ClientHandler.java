import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String vote = reader.readLine();
            System.out.println("Received vote: " + vote);

            if (!vote.equals("A") && !vote.equals("B")) {
                writer.println("Invalid vote. Please vote for 'A' or 'B'.");
                return;
            }

            Server.addVote(Thread.currentThread().hashCode(), vote.charAt(0));
            writer.println("Vote received and processed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
