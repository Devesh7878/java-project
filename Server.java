import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1234);
            System.out.println("Server is listening on port 1234...");

            while (true) {
                Socket socket = serverSocket.accept();

                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                String msgFromClient;
                while ((msgFromClient = bufferedReader.readLine()) != null) {
                    System.out.println("Client: " + msgFromClient);
                    bufferedWriter.write("MSG Received\n");
                    bufferedWriter.flush();

                    if (msgFromClient.equalsIgnoreCase("Bye")) {
                        break;
                    }
                }

                socket.close();
                bufferedReader.close();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
