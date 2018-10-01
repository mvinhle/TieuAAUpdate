package tieuaa.mv.ami;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundInGame {
    private static MediaPlayer mediaPlayer;
    public SoundInGame(Context context, int i){
        try {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        }
        catch (Exception e){
            e.getMessage();
        }
        mediaPlayer = MediaPlayer.create(context, i);
        mediaPlayer.start();
    }
//    public void playMain(){
//        if (mediaPlayerHome.isPlaying()){
//            mediaPlayerHome.pause();
//        }
//        if (mediaPlayerClass.isPlaying()){
//            mediaPlayerClass.pause();
//        }
//        mediaPlayerMain.reset();
//        mediaPlayerMain.start();
//        Log.d(KeyAA.KEY_LOG, "bật nhạc nền main");
//    }
//    public void playHome(){
//        if (mediaPlayerHome.isPlaying()){
//            mediaPlayerHome.pause();
//        }
//        if (mediaPlayerClass.isPlaying()){
//            mediaPlayerClass.pause();
//        }
//        mediaPlayerHome.reset();
//        mediaPlayerHome.start();
//        Log.d(KeyAA.KEY_LOG, "bật nhạc nền home");
//    }
//    public void playClass(){
//        if (mediaPlayerMain.isPlaying()){
//            mediaPlayerMain.pause();
//        }
//        if (mediaPlayerHome.isPlaying()){
//            mediaPlayerHome.pause();
//        }
//        mediaPlayerClass.reset();
//        mediaPlayerClass.start();
//        Log.d(KeyAA.KEY_LOG, "bật nhạc nền class");
//    }
}
