package tieuaa.mv.ami;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class SoundInGame {
    private static MediaPlayer mediaPlayer;
    public SoundInGame(Context context, int i){
        try {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        }
        catch (Exception e){
            Log.d(KeyAA.KEY_LOG, "SoundInGame: "+e.getMessage());
        }
        mediaPlayer = MediaPlayer.create(context, i);
        mediaPlayer.start();
        Log.d(KeyAA.KEY_LOG, "SoundInGame: Đã phát id: "+i+", tại context: "+context);
    }

    public void pauseSound(){
        mediaPlayer.pause();
        Log.d(KeyAA.KEY_LOG, "SoundInGame: Đã dừng");
    }
    public void continueSound(){
        mediaPlayer.start();
        Log.d(KeyAA.KEY_LOG, "SoundInGame: Đã Tiếp tục");
    }
}
