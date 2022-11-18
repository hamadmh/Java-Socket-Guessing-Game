import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThree {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(2003);
            Scanner sc = new Scanner(System.in);
            int counter = 0;
            int tr = 0;
            while (true) {
                try {
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    System.out.println(dis.readUTF());
                    while (counter < 5) {
                        System.out.print("Enter your guess: ");
                        int guess = sc.nextInt();
                        dos.writeInt(guess);
                        counter++;
                        System.out.println(dis.readUTF());
                        tr = dis.readInt();
                        if (tr == -1) {
                            return;
                        }

                    }
                    dos.writeUTF("I finished in " + (counter + 1) + "tries");

                } catch (Exception e) {
                    System.out.println(e);
                    continue;
                }
                break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}