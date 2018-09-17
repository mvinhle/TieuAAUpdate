package tieuaa.mv.ami;

import android.util.Log;

public class HelpData {
    public int randomRange(int start, int end){
        int result = (int) (start + Math.random() * (end - start));
        Log.d(KeyAA.KEY_LOG, "random lớn từ: "+start+" đến "+end+" kết quả là "+result);
        return result;
    }
    public int randomRange(int range){
        int result = (int) (Math.random() * range);
        Log.d(KeyAA.KEY_LOG, "random nhỏ từ: 0 đến "+range+" kết quả là "+result);
        return result;
    }
    public String removeSubString(String stringDefault, String stringDelete) {
        String string = stringDefault.replace(stringDelete, "");
        return string;
    }
    public int randomLine(int rate, int max, int about){
        int result = (int) (SaveAndLoadData.getPointTrust() / rate);
        if (result < 0){
            result = 0;
        }
        else if (result > (max - about)){
            result = max - about;
        }
        return randomRange(result, result + about);
    }
    public int randomLine(int rate, int max){
        int result = (int) (SaveAndLoadData.getPointTrust() / rate);
        if (result < 0){
            result = 0;
        }
        else if (result > max){
            result = max;
        }
        return randomRange(result);
    }
}
