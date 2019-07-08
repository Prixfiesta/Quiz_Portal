package sample.Server_Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private static Vector<ClientHandler> clienthandlers = new Vector<>();
    public static void main(String args[])throws IOException{
        ServerSocket server = new ServerSocket(6000);
        int i=0;
        Boolean k = true;
        System.out.println(k);
        while(true){
            Socket clientConnection = server.accept();
            System.out.println("Connected to server");
            ObjectOutputStream objotp = new ObjectOutputStream(clientConnection.getOutputStream());
            ObjectInputStream objinp = new ObjectInputStream(clientConnection.getInputStream());
            DataInputStream datainp = new DataInputStream(clientConnection.getInputStream());
            DataOutputStream dataotp = new DataOutputStream(clientConnection.getOutputStream());
            ClientHandler client = new ClientHandler(clientConnection,"Client_"+i,objotp,objinp,dataotp,datainp);
            i++;
            Thread t = new Thread(client);
            clienthandlers.add(client);
            t.start();
        }
    }


}
