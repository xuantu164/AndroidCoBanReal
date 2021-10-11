package com.example.btlbaucuatomca;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    int idamthanh;
    SoundPool amthanhXiNgau = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    MediaPlayer nhacnen = new MediaPlayer();
    CheckBox ktAmthanh;

    ImageButton btnPlay;
        ImageButton btnExit;
        ImageButton btnHuongDan;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            btnPlay=findViewById(R.id.btnPlay);
            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(intent);
                }
            });
            btnHuongDan=findViewById(R.id.btnHuongDan);
            btnHuongDan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Main2Activity.this,Huongdan.class);
                    startActivity(intent);
                }
            });

            //nhac nen bat dau
            ktAmthanh= findViewById(R.id.checkbox1);

            idamthanh=amthanhXiNgau.load(this,R.raw.dice,1);
            nhacnen = MediaPlayer.create(this,R.raw.nhacgamebaucua);
            nhacnen.start();
            ktAmthanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean kt) {
                    if(kt)
                    {
                        nhacnen.stop();
                    }
                    else
                    {
                        try
                        {
                            nhacnen.prepare();
                            nhacnen.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }

        public void Thoat(View view) {
            finish();
        }
    }

