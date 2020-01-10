package xyz.kongzz.countdownview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private ProgressRing mProgressRing;
    private int mProgressSecond = 60;
    private final int MESSAGE_PROGRESS = 0;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_PROGRESS:
                    mProgressSecond--;
                    if (mProgressSecond < 0) {
                        // TODO 倒计时结束
                        mProgressSecond = 60;
                        return false;
                    }
                    mProgressRing.setText(mProgressSecond + "S");
                    mProgressRing.setProgress((float) (mProgressSecond * 10 / 6));
                    mHandler.sendEmptyMessageDelayed(MESSAGE_PROGRESS, 1000);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressRing = findViewById(R.id.pr_progress);

        mHandler.sendEmptyMessage(MESSAGE_PROGRESS);


    }
}
