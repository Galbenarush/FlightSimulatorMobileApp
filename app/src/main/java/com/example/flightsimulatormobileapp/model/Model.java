// Gal Ben Arush 208723791

package com.example.flightsimulatormobileapp.model;
 // 192.168.56.1
// 10.0.0.26

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Model {
    private Socket socket;
    private PrintWriter out;
    private int port;
    private String ip;
    private BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();

    /*** Model constructors ***/
    public Model() {
        this.ip = "";
        this.port = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        dispatchQueue.take().run();
                    } catch (InterruptedException e) {
                        // nothing in queue
                    }
                }
            }
        }).start();
    }

    public Model(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            InetAddress serverIP = InetAddress.getByName(this.ip);
            System.out.println("connecting");
            this.socket = new Socket(this.ip, this.port);
            System.out.println("connection success");
            this.out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("connection success");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            dispatchQueue.take().run();
                        } catch (InterruptedException e) {
                            // nothing in queue
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            System.out.println("error occurred");

        }
    }

    /*** Connection and Disconnection ***/
    public void connect(String ip, int port) throws IOException {
        // set IP and Port
        this.ip = ip;
        this.port = port;
        try {
            // Open a socket
            this.socket = new Socket(ip, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("client connected");
        } catch (IOException e) {
            System.out.println("connection error");
        }


    }

    public void disconnect() {
        try{
            this.socket.close();
            System.out.println("connection closed successfully");
        }catch (Exception e){
            System.out.println("had trouble closing");
        }

    }

    /*** Set flight controls surfaces ans send to server***/
    public void setRudder(double rudder) throws InterruptedException {
        dispatchQueue.put(
                new Runnable() {
                    @Override
                    public void run() {
                        out.print("set /controls/flight/rudder " + rudder + "\r\n");
                        out.flush();
                    }
                }
        );
    }

    public void setThrottle(double throttle) throws InterruptedException {
        dispatchQueue.put(
                new Runnable() {
                    @Override
                    public void run() {
                        out.print("set /controls/engines/current-engine/throttle " + throttle + "\r\n");
                        out.flush();
                    }
                }
        );
    }

    public void setAileron(double aileron) throws InterruptedException {
        dispatchQueue.put(
                new Runnable() {
                    @Override
                    public void run() {
                        out.print("set /controls/flight/aileron " + aileron + "\r\n");
                        out.flush();
                    }
                }
        );
    }

    public void setElevator(double elevator) throws InterruptedException {
        dispatchQueue.put(
                new Runnable() {
                    @Override
                    public void run() {
                        out.print("set /controls/flight/elevator " + elevator + "\r\n");
                        out.flush();
                    }
                }
        );
    }

}
