// Gal Ben Arush 208723791

package com.example.flightsimulatormobileapp.viewModel;

import com.example.flightsimulatormobileapp.model.Model;

import java.io.IOException;

public class ViewModel {
    private Model model;
    private boolean isConnected;
    private String ip;
    private int port;

    /*** Constructor of the class ***/
    public ViewModel() {
        this.ip = "";
        this.port = 0;
        this.isConnected = false;
        this.model = new Model();
    }

    /*** Connection and Disconnection ***/
    public void connect(String ip, int port) throws IOException {
        if(isConnected) {
            return; // do nothing
        }
        this.ip = ip;
        this.port = port;
        this.isConnected = true;
        this.model.connect(ip, port);
    }

    public void disconnect() throws IOException {
        if(!isConnected) {
            return; // do nothing
        }
        this.isConnected = false;
        this.model.disconnect();
    }

    /*** Set flight controls surfaces ans send to server***/
    public void setRudder(int rudder) throws InterruptedException {
        if (this.model != null) {
            // convert rudder to double
            double newRudder = ((double)(rudder * 2) / 100) - 1;
            this.model.setRudder(newRudder);

        }
    }

    public void setThrottle(int throttle) throws InterruptedException {
        if (this.model != null) {
            // convert throttle to double
            double newThrottle = (double)throttle / 100;
            this.model.setThrottle(newThrottle);
        }
    }

    public void setAileron(double aileron) throws InterruptedException {
        if (this.model != null) {
            this.model.setAileron(aileron);
        }
    }

    public void setElevator(double elevator) throws InterruptedException {
        if (this.model != null) {
            this.model.setElevator(elevator);
        }
    }
}
