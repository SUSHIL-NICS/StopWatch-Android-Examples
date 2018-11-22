package com.android_examples.stopwatch_android_examplescom;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG=MainActivity.class.getSimpleName();

    TextView textView;
    TextView textView2;
    TextView current_textView;
    TextView elapsed_textView;
    private RecyclerView recyclerview;
    private ArrayList<ModelTimer> timeList=new ArrayList<ModelTimer>();
    private Adapter_Timer adapter_timer;

    Button start, pause, reset, lap ;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, MilliSeconds ;
    int Second, Minute, MilliSecond ;

    ListView listView ;

    String[] ListElements = new String[] {  };

    List<String> ListElementsArrayList ;

    ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        current_textView = (TextView)findViewById(R.id.current_textView);
        textView = (TextView)findViewById(R.id.textView);
        elapsed_textView = (TextView)findViewById(R.id.elapsed_textView);
        start = (Button)findViewById(R.id.button);
        pause = (Button)findViewById(R.id.button2);
        reset = (Button)findViewById(R.id.button3);
        lap = (Button)findViewById(R.id.button4) ;
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        listView = (ListView)findViewById(R.id.listview1);
        handler = new Handler() ;

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );

        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
        current_textView.setText(mDateFormat.format(System.currentTimeMillis()));
       // current_textView.setText(mDateFormat.format(new Date()));


        listView.setAdapter(adapter);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
                Log.i("SystemClockOnStart",SystemClock.uptimeMillis()+"");
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                 handler.removeCallbacks(runnable);

                reset.setEnabled(true);
                //to show elapsed time
                Seconds = (int) (MillisecondTime / 1000);
                Minutes = Seconds / 60;
                Seconds = Seconds % 60;
                MilliSeconds = (int) (MillisecondTime % 1000);
                elapsed_textView.setText("" + String.format("%02d", Minutes) + ":"
                        + String.format("%02d", Seconds) + ":"
                        + String.format("%03d", MilliSeconds));

                //to get startTime
                int stime= (int) (UpdateTime-MillisecondTime);
                Second = (int) (stime / 1000);
                Minute = Second / 60;
                Second = Second % 60;
                MilliSecond = (int) (stime % 1000);

                //set start,end,elapsed time in dto
                ModelTimer modelTimer=new ModelTimer();
                modelTimer.setStartTime("" + String.format("%02d", Minute) + ":"
                        + String.format("%02d", Second) + ":"
                        + String.format("%03d", MilliSecond));
                modelTimer.setEndTime(textView.getText().toString());
                modelTimer.setElapsedTime("" +String.format("%02d", Minutes) + ":"
                        + String.format("%02d", Seconds) + ":"
                        + String.format("%03d", MilliSeconds));
                modelTimer.setCurrentTime("");
                timeList.add(modelTimer);

                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
                recyclerview.setLayoutManager(linearLayoutManager);
                adapter_timer=new Adapter_Timer(MainActivity.this,timeList);
                recyclerview.setAdapter(adapter_timer);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;
                textView.setText("00:00:00");
                elapsed_textView.setText("00:00:00");

                ListElementsArrayList.clear();
                timeList.clear();
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
                recyclerview.setLayoutManager(linearLayoutManager);
                adapter_timer=new Adapter_Timer(MainActivity.this,timeList);
                recyclerview.setAdapter(adapter_timer);

                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(textView.getText().toString());

                adapter.notifyDataSetChanged();

            }
        });

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);

            Log.i("UpdateTime",UpdateTime+"");
            Log.i("MillisecondTime",MillisecondTime+"");
            Log.i("TimeBuff",TimeBuff+"");
            //Log.i("StartTimeOnStart",StartTime+"");
           // Log.i("SystemClock",SystemClock.uptimeMillis()+"");
        }
    };
}
