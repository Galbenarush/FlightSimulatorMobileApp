package com.example.flightsimulatormobileapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle; // a mapping from String keys to various Parcelable values.
import android.view.View;
import android.widget.EditText;

import com.example.flightsimulatormobileapp.R;
import com.example.flightsimulatormobileapp.viewModel.ViewModel;

public class MainActivity extends AppCompatActivity {
    private String ip;
    private int port;
    private ViewModel vm;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.vm = new ViewModel();
    }

    public void Connect(View view) throws InterruptedException {
        // Edit text IP coming from view
        EditText editTextIp = (EditText) findViewById(R.id.ipString);
        String ip = editTextIp.getText().toString();
        // Edit text Port coming from view
        EditText editTextPort = (EditText) findViewById(R.id.editPort);
        String portString = editTextPort.getText().toString();
        int port = Integer.parseInt(portString);
        // Create new TCP connection and connection
        this.vm.connect(ip, port);

    }
}