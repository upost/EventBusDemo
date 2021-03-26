package de.androidnewcomer.eventbusdemo;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "mychannel";
    private Handler handler = new Handler();
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.show).setOnClickListener(this::onShowClicked);
        EventBus.getDefault().register(this);
        createNotificationChannel();
    }

    private void onShowClicked(View view) {
        MyDialog.show(this);

        EventBus.getDefault().post(new StartCalculationMessage("berechnen"));

        handler.postDelayed(()->{
            EventBus.getDefault().post(new CloseMessage());
        },2000);
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void startCalculation(StartCalculationMessage message) {
        // calculation in background ...
        Log.i(getLocalClassName(),"startCalculation");
        String result="";
        for(int i=0; i<1; i++) {
            if(result.length()!=0) result+=",";
            result += message.getParameter();
        }
        EventBus.getDefault().post(
                new CalculationResultMessage(TextUtils.substring(result,0,100)));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCalculationCompleted(CalculationResultMessage message) {

        Log.i(getLocalClassName(),"completed Calculation");
        // show result ...
        Notification notification = (new NotificationCompat.Builder(this,CHANNEL_ID))
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Berechnung beendet!")
                .setContentText(message.getResult()).build();

        NotificationManagerCompat.from(this).notify(0,notification);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}