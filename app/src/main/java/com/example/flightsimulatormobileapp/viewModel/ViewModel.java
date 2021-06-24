package com.example.flightsimulatormobileapp.viewModel;

import android.view.View;
import android.widget.EditText;

import com.example.flightsimulatormobileapp.R;
import com.example.flightsimulatormobileapp.model.Model;

public class ViewModel {
    private Model model;
    private int ip;

    /**
     * Constructor of the class.
     */
    public ViewModel() {
        this.model = new Model();

    }

    public void connect(String ip, int port) throws InterruptedException {
        this.model.connect(ip, port);
    }
}
