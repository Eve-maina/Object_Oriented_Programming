import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static final int PORT = 12345;
    private static List<Socket> clients = new ArrayList<>();
    private static Map<Integer, Character> votes = new HashMap<>();
    private static final int NUM_ELECTORATES = 5;
    private static int connectedClients = 0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients to connect...");

            while (connectedClients < NUM_ELECTORATES) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                connectedClients++;
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addVote(int electorateId, char vote) {
        if (!votes.containsKey(electorateId)) {
            votes.put(electorateId, vote);
            System.out.println("Received vote from electorate " + electorateId + ": " + vote);
            if (votes.size() == NUM_ELECTORATES) {
                displayResults();
            }
        }
    }

    private static void displayResults() {
        // Calculate and display election results
        int countA = 0;
        int countB = 0;
        for (Character vote : votes.values()) {
            if (vote == 'A') {
                countA++;
            } else if (vote == 'B') {
                countB++;
            }
        }

        String winner;
        if (countA > countB) {
            winner = "Candidate A";
        } else if (countB > countA) {
            winner = "Candidate B";
        } else {
            winner = "Tie";
        }

        System.out.println("Election Results:");
        System.out.println("Candidate A: " + countA + " votes");
        System.out.println("Candidate B: " + countB + " votes");
        System.out.println("Winner: " + winner);
    }
}
