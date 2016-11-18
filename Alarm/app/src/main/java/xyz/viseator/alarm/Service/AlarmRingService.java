package xyz.viseator.alarm.Service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by viseator on 2016/11/10.
 */

public class AlarmRingService extends Service {
    private String song;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void ringAlarm(String song) {
        Log.d("wudi", "Enter ringAlarm");
        AssetFileDescriptor assetFileDescriptor = null;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        try {
            //如果是默认铃声
            if (Objects.equals(song, "default")) {
                song = "everybody.mp3";
                assetFileDescriptor = this.getAssets().openFd(song);
                mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                        assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            } else {

                mediaPlayer.setDataSource(song);
            }
            mediaPlayer.setVolume(0.5f, 1f);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
    //开启服务时
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        song = intent.getStringExtra("Path");
        Log.d("wudi Path", song);
        ringAlarm(song);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{400, 800}, 0);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAlarm();
        vibrator.cancel();
    }

}
