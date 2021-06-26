// Gal Ben Arush 208723791




package com.example.flightsimulatormobileapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.StrictMode;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.flightsimulatormobileapp.R;
import com.example.flightsimulatormobileapp.attributes.VerticalSeekBar;
import com.example.flightsimulatormobileapp.viewModel.ViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ViewModel vm;
    private VerticalSeekBar throttle;
    private SeekBar rudder;
    private Joystick joystick;
    private Button connect;
    private boolean isConnected;
    private String ip;
    private int port;

    /*** OnCreate function that renders the activity main ***/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.vm = new ViewModel();
        // set attributes
        this.rudder = findViewById(R.id.rudder);
        this.throttle = findViewById(R.id.throttle);
        this.joystick = findViewById(R.id.joystick);
        this.connect = findViewById(R.id.connect);
        // set listeners
        setActionListeners();
    }

    /*** Connection function that convert the values to string and send to VM.connect ***/
    public void Connect(View view) throws IOException {
        if(isConnected) {
            this.Disconnect(view);
            this.connect.setText(R.string.connect);
            this.isConnected = false;
            return;
        }
        // re-format IP and PORT
        EditText editTextIp = (EditText) findViewById(R.id.ipString);
        String ip = editTextIp.getText().toString();
        EditText editTextPort = (EditText) findViewById(R.id.editPort);
        String portString = editTextPort.getText().toString();
        // sanity test
        if (!ip.isEmpty() && !portString.isEmpty()) {
            int port = Integer.parseInt(portString);
            this.ip = ip;
            this.port = port;
            // send to connect VM
            this.vm.connect(ip, port);
            // set onChange joystick
            this.joystick.onChange = (a, e) -> {
                this.vm.setAileron(a);
                this.vm.setElevator(e);
            };
            // change button to disconnect
            this.connect.setText(R.string.disconnect);
            this.isConnected = true;
        } else {
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout);
            Snackbar snackbar = Snackbar.make(rl, R.string.failure, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void Disconnect(View view) throws IOException {
        try {
            this.vm.disconnect();
        } catch (IOException e) {

        }

    }

    /*** Function that set listeners to SeekBar objects in display ***/
    /*** Rudder and Throttle are controlled by MainActivity ***/
    private void setActionListeners() {
        // rudder listener
        this.rudder.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        try {
                            vm.setRudder(progress);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
        // throttle listener
        this.throttle.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        try {
                            vm.setThrottle(progress);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

    }

}