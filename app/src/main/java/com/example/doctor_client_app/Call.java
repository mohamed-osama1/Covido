package com.example.doctor_client_app;


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.rtp.AudioCodec;
import android.net.rtp.AudioGroup;
import android.net.rtp.AudioStream;
import android.net.rtp.RtpStream;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.net.InetAddress;

import java.net.UnknownHostException;

public class Call extends Activity {

    AudioGroup m_AudioGroup;
    AudioStream m_AudioStream;
    TextView srcIP , srcPort;
    String ipHelper ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);


        srcIP = findViewById(R.id.myIp);
        srcPort = findViewById(R.id.myPort);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            AudioManager audio =  (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audio.setMode(AudioManager.MODE_IN_COMMUNICATION);
            m_AudioGroup = new AudioGroup();
            m_AudioGroup.setMode(AudioGroup.MODE_NORMAL);
            m_AudioStream = new AudioStream(InetAddress.getByAddress(getLocalIPAddress()));
            int localPort = m_AudioStream.getLocalPort();
            srcPort.setText(localPort+"");
            srcIP.setText(ipHelper);
            m_AudioStream.setCodec(AudioCodec.PCMU);
            m_AudioStream.setMode(RtpStream.MODE_NORMAL);

            ((Button) findViewById(R.id.connectBtn)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String remoteAddress = ((EditText)findViewById(R.id.destIpText)).getText().toString();
                    String remotePort = ((EditText)findViewById(R.id.destPortText)).getText().toString();

                    try {
                        try {
                            m_AudioStream.associate(InetAddress.getByName(remoteAddress), Integer.parseInt(remotePort));
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    m_AudioStream.join(m_AudioGroup);
                }
            });

            ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    m_AudioStream.release();
                }
            });

        } catch (Exception e) {
            Log.e("----------------------", e.toString());
            e.printStackTrace();
        }
    }

    private byte[] getLocalIPAddress() {
        byte[] bytes = null;

        try {
            // get the string ip
            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

            // convert to bytes
            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getByName(ip);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            bytes = new byte[0];
            if (inetAddress != null) {
                bytes = inetAddress.getAddress();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (byte b : bytes)
            ipHelper+=(b+".");
        ipHelper=ipHelper.substring(0,ipHelper.length()-1);
        return bytes;
    }
}
