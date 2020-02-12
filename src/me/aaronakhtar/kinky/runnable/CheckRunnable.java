package me.aaronakhtar.kinky.runnable;

import me.aaronakhtar.kinky.Kinky;
import uk.tsis.lib.Colour;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class CheckRunnable implements Runnable{

    private String host;
    private int port;
    private Proxy.Type proxyType;

    public CheckRunnable(String host, int port, Proxy.Type proxyType) {
        this.host = host;
        this.port = port;
        this.proxyType = proxyType;
    }

    @Override
    public void run() {
        try {
            Proxy proxy = new Proxy(proxyType, new InetSocketAddress(host, port));
            URL url = new URL("https://google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
            connection.setConnectTimeout(2000);
            connection.getResponseCode();
            System.out.println(Colour.GREEN.get() + " - Found Proxy: " + host + ":" + port);
            Kinky.saveProxy(host + ":" + port);
        }catch (Exception e){
            System.out.println(Colour.RED.get() + " - Proxy Failed: " + host + ":" + port);
        }
    }
}
