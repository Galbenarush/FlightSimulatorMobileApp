package com.example.flightsimulatormobileapp.model;
 // 192.168.56.1
// 10.0.0.26
import android.os.Build;
import android.view.View;
import android.widget.EditText;

import com.example.flightsimulatormobileapp.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.net.*;
import java.io.PrintWriter;
import java.io.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Model {
    private  double elevator = 0.0;
    private  double aileron = 0.0;
    private  double rudder = 0.0;
    private  double throttle = 0.0;
    private Socket socket;
    private PrintWriter out;
    private int port;
    private String ip;
    private DataOutputStream dataOutputStream;
    private BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();

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
        System.out.println("constructor vm");
    }

    // constructor
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

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void connect(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        try {
            this.socket = new Socket(ip, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("client connected");
        } catch (IOException e) {
            System.out.println("error");
        }


    }

    public void disconnect(){
        try{
            this.socket.close();
            this.dataOutputStream.close();
        }catch (Exception e){
            System.out.println("had trouble closing");
        }

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

}
