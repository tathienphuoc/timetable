package SGU.LTM.TimeTable.Common;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class Client {
    protected Socket socket;
    protected IOStream io;
    protected Console console;
    protected String host;
    protected int port;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.socket = new Socket(host, port);
        this.io = new IOStream(this.socket);
        this.console = new Console();
    }

    public Client(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        if (urlConnection instanceof HttpsURLConnection) {
            this.io = new IOStream((HttpsURLConnection) urlConnection);
        } else {
            this.io = new IOStream((HttpURLConnection) urlConnection);
        }
        this.console = new Console();
    }

    public String getInput() {
        return this.console.getInput();
    }

    public void send(String message) throws IOException {
        this.io.send(message);
    }
    
    public String receive() throws IOException {
        return this.io.receive();
    }
    
    public void close() throws IOException {
        if (this.io != null)
            this.io.close();
        if (this.socket != null)
            this.socket.close();
    }

}
