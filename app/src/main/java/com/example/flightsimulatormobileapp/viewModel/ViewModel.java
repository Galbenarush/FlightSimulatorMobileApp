package com.example.flightsimulatormobileapp.viewModel;

import android.view.View;
import android.widget.EditText;

import com.example.flightsimulatormobileapp.R;
import com.example.flightsimulatormobileapp.model.Model;

import java.io.IOException;

public class ViewModel {
    private Model model;
    private String ip;
    private int port;

    /**
     * Constructor of the class.
     */
    public ViewModel() {
        this.ip = "";
        this.port = 0;
        this.model = new Model();
    }

    public void connect(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        System.out.println("constructor vm");
        this.model.connect(ip, port);
    }


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
            double newThrottle = (double)(throttle / 100);
            System.out.println(newThrottle);
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
