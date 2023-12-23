package com.client.servercommuniaction;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;



    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Client(String hostname, int port) throws IOException{

        this.socket = new Socket(hostname, port);

        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        
    }

    public void writeToServer(int i) throws IOException{
        out.writeInt(i);
    }

    public int readFromServer() throws IOException {
        return in.readInt();
    }
    
}
