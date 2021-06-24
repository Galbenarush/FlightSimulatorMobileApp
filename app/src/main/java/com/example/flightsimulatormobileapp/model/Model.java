package com.example.flightsimulatormobileapp.model;
 // 192.168.56.1
import android.os.Build;
import android.view.View;
import android.widget.EditText;

import com.example.flightsimulatormobileapp.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.net.*;
import java.io.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Model {
    private  double elevator = 0.0;
    private  double aileron = 0.0;
    private  double rudder = 0.0;
    private  double throttle = 0.0;
    private Socket fg;
    private PrintWriter out;
    //private BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingDeque<Runnable>();
    private Socket socket;
    private int port;
    private String ip;
    private DataOutputStream dataOutputStream;
    private BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();

    // constructor
    public Model() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        dispatchQueue.take().run();
                    } catch (InterruptedException e) {
                        System.out.println("error occurred");
                    }
                }
            }
        }).start();
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void connect(String ip, int port) throws InterruptedException {
        this.ip = ip;
        this.port = port;
        try {
            InetAddress serverIP = InetAddress.getByName(this.ip);
            this.socket = new Socket(serverIP, this.port);
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            System.out.println("connection success");
        } catch (Exception e) {
            System.out.println("error occurred");
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

}
