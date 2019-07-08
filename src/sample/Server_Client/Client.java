package sample.Server_Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectInputStream objinp;
    private ObjectOutputStream objotp;
    private DataInputStream datainp;
    private DataOutputStream dataotp;

    public Client(String ip,int port){
        try{
            socket = new Socket(ip,port);
            System.out.println(socket.getInputStream());


            // This order of constructors matter
            objotp = new ObjectOutputStream(socket.getOutputStream());
            objinp = new ObjectInputStream(socket.getInputStream());
            datainp = new DataInputStream(socket.getInputStream());
            dataotp = new DataOutputStream(socket.getOutputStream());

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendString(String s){
        try{
            dataotp.writeUTF(s);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public String recieveString(){
        try{
            return datainp.readUTF();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean recieveBoolean(){
        try{
            return datainp.readBoolean();
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }

    }

    public void sendInt(int a){
        try{
            dataotp.writeInt(a);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Object recieveObject(){
        try{
            return objinp.readObject();
        }
        catch(IOException|ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }



}
