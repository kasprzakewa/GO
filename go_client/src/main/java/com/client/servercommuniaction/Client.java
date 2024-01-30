package com.client.servercommuniaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;



    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Client(String hostname, int port) throws IOException{

        this.socket = new Socket(hostname, port);

        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
    }

    public void writeToServer(String message) throws IOException{
        out.println(message);
    }

    public String readFromServer() throws IOException {
        return in.readLine();
    }
    
}
