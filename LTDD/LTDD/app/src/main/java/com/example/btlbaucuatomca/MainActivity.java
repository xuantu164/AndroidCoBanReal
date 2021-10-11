package com.example.btlbaucuatomca;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    Custom_GridView_BanCo adapter;
    Integer[] dsHinh= {R.drawable.nai, R.drawable.bau,R.drawable.ga,R.drawable.ca,R.drawable.cua,R.drawable.tom};
    AnimationDrawable cdXingau1,cdXingau2,cdXingau3;
    ImageView hinhXingau1,hinhXingau2,hinhXingau3;
    Random randomXingau;
    int giatriXingau1,giatriXingau2,giatriXingau3;
    public static Integer[] gtDatCuoc = new Integer[6];
    Timer timer = new Timer();
    int tongtiencu,tongtienmoi;
    int tienThuong,kiemtra;
    int idamthanh;
    TextView tvTien,tvthoigian;
    Handler handler;
    SharedPreferences luuTru;
    SoundPool amthanhXiNgau = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    MediaPlayer nhacnen = new MediaPlayer();
    CheckBox ktAmthanh;
    CountDownTimer demthoigian;


    Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg)
        {
            RandomXiNgau1();
            RandomXiNgau2();
            RandomXiNgau3();


            //Xử lí trúng thưởng
            for (int i= 0;i<gtDatCuoc.length;i++)
            {
                if(gtDatCuoc[i]!=0)
                {
                    if(i==giatriXingau1)
                    {
                        tienThuong += gtDatCuoc[i];
                    }
                    if(i==giatriXingau2)
                    {
                        tienThuong += gtDatCuoc[i];
                    }
                    if (i==giatriXingau3)
                    {
                        tienThuong += gtDatCuoc[i];
                    }
                    if(i!= giatriXingau1 && i!=giatriXingau2 && i!=giatriXingau3)
                    {
                        tienThuong -= gtDatCuoc[i];
                    }
                }

            }

            if(tienThuong>0)
            {
                Toast.makeText(getApplicationContext(),"Chúc mừng bạn đã trúng " +tienThuong,Toast.LENGTH_SHORT).show();
            }
            else if(tienThuong==0)
            {
                Toast.makeText(getApplicationContext(),"May quá không mất gì" ,Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Ôi xui quá. Bạn mất  " +tienThuong+" rồi!",Toast.LENGTH_SHORT).show();
            }

            LuuDuLieuNguoiDung(tienThuong);
            tongtiencu=tongtienmoi;
            tvTien.setText(String.valueOf(tongtiencu));

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hinhXingau1=(ImageView) findViewById(R.id.xingau1);
        hinhXingau2=(ImageView) findViewById(R.id.xingau2);
        hinhXingau3=(ImageView) findViewById(R.id.xingau3);

        tvTien= (TextView)findViewById(R.id.tvTien);
        tvthoigian= (TextView)findViewById(R.id.tvThoigian);


        gridView = findViewById(R.id.gvBanCo);
        adapter= new Custom_GridView_BanCo(this,R.layout.custom_banco,dsHinh);
        gridView.setAdapter(adapter);

        luuTru = getSharedPreferences("Luutruthongtin ", Context.MODE_PRIVATE);

        tongtiencu = luuTru.getInt("TongTien",500);
        tvTien.setText(String.valueOf(tongtiencu));

        /*ktAmthanh= findViewById(R.id.checkbox1);

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
        });*/

        demthoigian = new CountDownTimer(120000,1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                long milis = millisUntilFinished;
                long gio = TimeUnit.MILLISECONDS.toHours(milis);
                long phut = TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis));
                long giay = TimeUnit.MILLISECONDS.toSeconds(milis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milis));
                String giophutgiay= String.format("%02d:%02d:%02d",gio,phut,giay);
                tvthoigian.setText(giophutgiay);
            }

            @Override
            public void onFinish()
            {
                SharedPreferences.Editor edit=luuTru.edit();
                //tongtiencu=luuTru.getInt("Tongtien",1000);
                tongtienmoi = tongtiencu + 1000;
                edit.putInt("TongTien",tongtienmoi);
                edit.commit();

                tvTien.setText(String.valueOf(tongtienmoi));
                demthoigian.cancel();
                demthoigian.start();

            }
        };
        demthoigian.start();

        handler = new Handler(callback);


    }

    private void LuuDuLieuNguoiDung(int tienThuong)
    {
        SharedPreferences.Editor edit=luuTru.edit();
        //tongtiencu=luuTru.getInt("Tongtien",1000);
        tongtienmoi = tongtiencu +tienThuong;
        edit.putInt("TongTien",tongtienmoi);
        edit.commit();
        Log.d("KetQua"," "+giatriXingau1+" "+giatriXingau2+" "+giatriXingau3+"tiền thưởng="+tienThuong+"Tổng tiền"+tongtiencu);
    }

    public void Lacxingau(View v)
    {
        hinhXingau1.setImageResource(R.drawable.hinhdongxingau);
        hinhXingau2.setImageResource(R.drawable.hinhdongxingau);
        hinhXingau3.setImageResource(R.drawable.hinhdongxingau);

        cdXingau1 = (AnimationDrawable) hinhXingau1.getDrawable();
        cdXingau2 = (AnimationDrawable) hinhXingau2.getDrawable();
        cdXingau3 = (AnimationDrawable) hinhXingau3.getDrawable();

        //bắt lỗi game
        kiemtra = 0;
         for(int i =0; i<gtDatCuoc.length;i++)
        {
            kiemtra +=gtDatCuoc[i];
        }
        if(kiemtra==0)
        {
            Toast.makeText(getApplicationContext(),"Bạn vui lòng đặt cược !",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(kiemtra > tongtiencu)
            {
                Toast.makeText(getApplicationContext(),"Bạn không đủ tiền đặt cược !",Toast.LENGTH_SHORT).show();
            }
            else
            {
                amthanhXiNgau.play(idamthanh,1.0f,1.0f,1,0,1.0f);
                cdXingau1.start();
                cdXingau2.start();
                cdXingau3.start();

                tienThuong= 0;

                timer.schedule(new Lacxingau(),1000);
            }
        }

    }

    class Lacxingau extends TimerTask{
        @Override
        public void run() {
            handler.sendEmptyMessage(0);


        }
    }
    private void RandomXiNgau1()
    {
        randomXingau = new Random();
        int rd = randomXingau.nextInt(6);
        switch (rd)
        {
            case 0:
                hinhXingau1.setImageResource(dsHinh[0]);
                giatriXingau1=rd;
                break;
            case 1:
                hinhXingau1.setImageResource(dsHinh[1]);
                giatriXingau1=rd;
                break;
            case 2:
                hinhXingau1.setImageResource(dsHinh[2]);
                giatriXingau1=rd;
                break;
            case 3:
                hinhXingau1.setImageResource(dsHinh[3]);
                giatriXingau1=rd;
                break;
            case 4:
                hinhXingau1.setImageResource(dsHinh[4]);
                giatriXingau1=rd;
                break;
            case 5:
                hinhXingau1.setImageResource(dsHinh[5]);
                giatriXingau1=rd;
                break;

        }
    }

    private void RandomXiNgau2()
    {
        randomXingau = new Random();
        int rd = randomXingau.nextInt(6);
        switch (rd)
        {
            case 0:
                hinhXingau2.setImageResource(dsHinh[0]);
                giatriXingau2=rd;
                break;
            case 1:
                hinhXingau2.setImageResource(dsHinh[1]);
                giatriXingau2=rd;
                break;
            case 2:
                hinhXingau2.setImageResource(dsHinh[2]);
                giatriXingau2=rd;
                break;
            case 3:
                hinhXingau2.setImageResource(dsHinh[3]);
                giatriXingau2=rd;
                break;
            case 4:
                hinhXingau2.setImageResource(dsHinh[4]);
                giatriXingau2=rd;
                break;
            case 5:
                hinhXingau2.setImageResource(dsHinh[5]);
                giatriXingau2=rd;
                break;

        }
    }


    private void RandomXiNgau3()
    {
        randomXingau = new Random();
        int rd = randomXingau.nextInt(6);
        switch (rd)
        {
            case 0:
                hinhXingau3.setImageResource(dsHinh[0]);
                giatriXingau3=rd;
                break;
            case 1:
                hinhXingau3.setImageResource(dsHinh[1]);
                giatriXingau3=rd;
                break;
            case 2:
                hinhXingau3.setImageResource(dsHinh[2]);
                giatriXingau3=rd;
                break;
            case 3:
                hinhXingau3.setImageResource(dsHinh[3]);
                giatriXingau3=rd;
                break;
            case 4:
                hinhXingau3.setImageResource(dsHinh[4]);
                giatriXingau3=rd;
                break;
            case 5:
                hinhXingau3.setImageResource(dsHinh[5]);
                giatriXingau3=rd;
                break;

        }
    }

}
