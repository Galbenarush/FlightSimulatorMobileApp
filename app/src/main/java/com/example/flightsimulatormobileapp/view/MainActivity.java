package com.example.flightsimulatormobileapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import com.google.android.material.snackbar.Snackbar;
import android.graphics.Color;
import android.os.Bundle; // a mapping from String keys to various Parcelable values.
import android.view.View;
import android.widget.EditText;
import android.os.StrictMode;
import android.widget.SeekBar;

import com.example.flightsimulatormobileapp.R;
import com.example.flightsimulatormobileapp.attributes.VerticalSeekBar;
import com.example.flightsimulatormobileapp.viewModel.ViewModel;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ViewModel vm;
    private String ip;
    private int port;
    private VerticalSeekBar throttle;
    private SeekBar rudder;
    private Joystick joystick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.vm = new ViewModel();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setAttributes();
        setActionListeners();
    }

    public void Connect(View view) throws IOException {
        EditText editTextIp = (EditText) findViewById(R.id.ipString);
        String ip = editTextIp.getText().toString();
        EditText editTextPort = (EditText) findViewById(R.id.editPort);
        String portString = editTextPort.getText().toString();
        if (!ip.isEmpty() && !portString.isEmpty()) {
            int port = Integer.parseInt(portString);
            this.ip = ip;
            this.port = port;
            this.vm.connect(ip, port);
        }
        this.joystick.onChange = (a, e) -> {
            this.vm.setAileron(a);
            this.vm.setElevator(e);
        };
    }

    private void setAttributes() {
        this.rudder = findViewById(R.id.rudder);
        this.throttle = findViewById(R.id.throttle);
         this.joystick = findViewById(R.id.joystick);
    }

    private void setActionListeners() {
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