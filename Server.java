import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    static int clientCounter = 0; // client counter
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(2000);
            Random r = new Random();
            int randomInt = r.nextInt(100 - 1) + 1;
            int[] client = new int[3]; // client guesses number
            int nodeCounter = 0; // number of clients
            System.out.println(randomInt);
            while (true) {
                try {
                    int guessCounter = 0;
                    Socket clientSocket = new Socket("localhost", 2001 + (nodeCounter++ % 3));
                    Thread t = new ClientHandler(clientSocket, randomInt, client, guessCounter, clientCounter++);
                    t.start();
                } catch (Exception e) {
                    continue;
                }
            } 
        } catch (Exception e) {
            //System.out.println(e);
        }
    }
}

class ClientHandler extends Thread {
    Socket s = null;
    int randomInt;
    int guessCounter;
    int[] client = new int[3];
    int clientCounter;

    public ClientHandler(Socket s, int randomInt, int[] client, int guessCounter, int clientCounter) {
        this.s = s;
        this.randomInt = randomInt;
        this.client = client;
        this.guessCounter = guessCounter;
        this.clientCounter = clientCounter;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF("Pick a number between 1-100. You have 5 tries.");
            
            while (guessCounter < 5) {
                int guess = dis.readInt();
                guessCounter++;
                if (guess == randomInt) {
                    dos.writeUTF("You won");
                    dos.writeInt(-1);
                    break;
                } else {
                    if (guess > randomInt) {
                        dos.writeUTF("Guess lower");
                    } else {
                        dos.writeUTF("Guess higher");
                    }
                    dos.writeInt(-2);
                    System.out.println("WRONG!");
                }
            }
            client[clientCounter] = guessCounter;
            System.out.println(
                    "Client1 finished in " + client[0] + " tries,\n" +
                    "Client2 finished in " + client[1] + " tries,\n" +
                    "Client3 finished in " + client[2] + " tries\n"
            );
                            

        } catch (Exception e) {
        }
    }
}