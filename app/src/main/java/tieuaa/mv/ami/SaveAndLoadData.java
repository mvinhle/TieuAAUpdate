package tieuaa.mv.ami;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

public class SaveAndLoadData{
    SaveAndLoadData(Activity activity){
        if (switchContractor){
            information = activity.getSharedPreferences(KeyAA.NAME_INFORMATION, activity.MODE_PRIVATE);
            trust = activity.getSharedPreferences(KeyAA.NAME_TRUST, activity.MODE_PRIVATE);
            restartUser();
            switchContractor = false;
            Log.d(KeyAA.KEY_LOG, "SaveAndLoadData: khởi tạo shared preferences vào static xong");
        }
    }

    SharedPreferences information, trust;
    private static String nameUser, aliasUser;
    private static boolean sexUserIsBoy;
    private static boolean switchContractor = true;

//    hàm lưu dữ liệu vào sharedPreferences information sau đó truyền dữ liệu vào static.
    public void setInformation(String nameUser, boolean sexUserIsBoy){
        String nameChange =
                String.valueOf(nameUser.trim().charAt(0))
                        .toUpperCase()
                        .concat(nameUser.trim().substring(1));
        SharedPreferences.Editor editor = information.edit();
        editor.putString(KeyAA.KEY_NAME_INFORMATION, nameChange);
        editor.putBoolean(KeyAA.KEY_SEX_INFORMATION, sexUserIsBoy);
        editor.apply();
        restartUser();
        Log.d(KeyAA.KEY_LOG, "setInformation: đã lưu information với: "+nameChange+sexUserIsBoy);
    }
//    hàm truyền dữ liệu vào static (programer chỉ dùng khi có dũ liệu để tránh lỗi nhé)
    public void restartUser(){
        String nameUser = information.getString(KeyAA.KEY_NAME_INFORMATION, KeyAA.KEY_FIX_BUG_INFORMARTION);
        boolean sexUserIsBoy = information.getBoolean(KeyAA.KEY_SEX_INFORMATION, true);

        SaveAndLoadData.nameUser = nameUser;
        SaveAndLoadData.sexUserIsBoy = sexUserIsBoy;
        if (sexUserIsBoy){
            SaveAndLoadData.aliasUser = KeyAA.ALIAS_BOY;
        }
        else {
            SaveAndLoadData.aliasUser = KeyAA.ALIAS_GIRL;
        }
        Log.d(KeyAA.KEY_LOG, "restartUser: đưa TT vào static với: "+aliasUser+nameUser);
    }

    public String getNameUser(){
        return SaveAndLoadData.nameUser;
    }
    public String getAliasUser(){
        return SaveAndLoadData.aliasUser;
    }
    public boolean getSexUser(){
        return SaveAndLoadData.sexUserIsBoy;
    }
    public String getNameAA(){
        return KeyAA.NAME_AA;
    }
    public String getAliasAA(){
        return KeyAA.ALIAS_AA;
    }
}
