package me.aaronakhtar.kinky;

import me.aaronakhtar.kinky.runnable.ScannerRunnable;
import uk.tsis.lib.Colour;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * @author Aaron Akhtar - aaronakhtar.me
 */
public class Kinky {

    public static List<Thread> threadList = new ArrayList<>();
    public static File file = new File("./proxy_" + new SimpleDateFormat("HH-mm-ss").format(new Date()) + ".txt");

    /**Common proxy ports*/
    public static int[] ports = {80, 8080, 8888, 8081, 36081};

    public static void main(String[] args) throws InterruptedException{
        int timeout;
        Proxy.Type proxyType;

        if (args.length != 2){
            System.out.println("Invalid Arguments...");
            System.out.println("  timeout - How long until software gives up on connection [Milliseconds]");
            System.out.println("  proxy type - What kind of proxies you want to find [HTTP or SOCKS]");
            return;
        }

        try {
            timeout = Integer.parseInt(args[0]);
        }catch (Exception e){
            System.out.println("Invalid Timeout Setting..");
            return;
        }

        if (args[1].equalsIgnoreCase("http")) {
            proxyType = Proxy.Type.HTTP;
        } else if (args[1].equalsIgnoreCase("socks")) {
            proxyType = Proxy.Type.SOCKS;
        } else {
            System.out.println("Proxy Type can only be HTTP or SOCKS...");
            return;
        }

        System.out.println("Developed by Aaron Akhtar -> github.com/aaron-akhtar");
        Thread.sleep(5000);

        for (int i =0; i < 100; i++){
            try {
                threadList.add(new Thread(new ScannerRunnable(timeout, proxyType)));
                threadList.get(i).start();
                System.out.println(Colour.BRIGHT_CYAN.get() + " - Starting Scanning Thread #" + i);
            }catch (Exception e){

            }
        }

        while(true){
            Thread.sleep(10000);
            System.out.println(Colour.BRIGHT_YELLOW.get() + " - Total Running Threads: " + threadList.size());
        }

    }

    public static String generateRandomAddress(){
        return new Random().nextInt(255) + "." + new Random().nextInt(255) + "." + new Random().nextInt(255) + "."  + new Random().nextInt(255);
    }

    public static void saveProxy(String proxy){
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter out = new PrintWriter(new FileWriter(file, true));
            out.write("\n" + proxy);
            out.flush();
            out.close();
        }catch (Exception e){

        }
    }

}
