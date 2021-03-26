package de.androidnewcomer.eventbusdemo;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.NonNull;

public class MyDialog extends Dialog {
    public MyDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_my);
        EventBus.getDefault().register(this);
        findViewById(R.id.close).setOnClickListener(this::close);
    }

    @Subscribe
    public void onCloseMessage(CloseMessage closeMessage) {
        dismiss();
    }

    private void close(View view) {
        dismiss();
    }

    public static void show(Context context) {
        (new MyDialog(context)).show();
    }


}
