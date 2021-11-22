package SGU.LTM.TimeTable.Common;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;

public class Server {
    protected ServerSocket serverSocket;
    protected Socket socket;
    protected IOStream io;
    protected int port;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server starting...");
        this.socket = this.serverSocket.accept();
        System.out.println("Client accepted");
        this.io = new IOStream(this.socket);
    }

    public Server(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        if (urlConnection instanceof HttpsURLConnection) {
            this.io = new IOStream((HttpsURLConnection) urlConnection);
        } else {
            this.io = new IOStream((HttpURLConnection) urlConnection);
        }
    }

    public void send(String message) throws IOException {
        this.io.send(message);
    }

    public String receive() throws IOException {
        return this.io.receive();
    }

    public void close() throws IOException {
        if (serverSocket != null)
            this.serverSocket.close();
        if (socket != null)
            this.socket.close();
        if (io != null)
            this.io.close();
    }
}
