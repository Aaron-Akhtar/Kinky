package me.aaronakhtar.kinky.runnable;

import me.aaronakhtar.kinky.Kinky;
import uk.tsis.lib.Colour;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ScannerRunnable implements Runnable{

    private List<String> usedAddresses = new ArrayList<>();
    private int timeout;
    private Proxy.Type proxyType;

    public ScannerRunnable(int timeout, Proxy.Type proxyType) {
        this.timeout = timeout;
        this.proxyType = proxyType;
    }

    @Override
    public void run() {
        while (true) {
            String host = Kinky.generateRandomAddress();
            if (!usedAddresses.contains(host)) {

                for (int port : Kinky.ports) {
                    try {
                        Socket socket = new Socket();
                        socket.connect(new InetSocketAddress(host, port), timeout);
                        System.out.println(Colour.BRIGHT_YELLOW.get() + " - Starting Check Thread For Proxy: " + host + ":" + port);
                        Thread thread = new Thread(new CheckRunnable(host, port, proxyType));
                        thread.start();
                        Kinky.threadList.add(thread);

                    } catch (Exception e) {

                    }
                }
                usedAddresses.add(host);
            }

        }
    }
}
