package tieuaa.mv.ami;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

public class SaveAndLoadData{
    public SaveAndLoadData(Activity activity){
        if (switchContractor){
            information = activity.getSharedPreferences(KeyAA.NAME_INFORMATION, activity.MODE_PRIVATE);
            trust = activity.getSharedPreferences(KeyAA.NAME_TRUST, activity.MODE_PRIVATE);
            restartUser();
            SaveAndLoadData.pointTrust = trust.getLong(KeyAA.KEY_POINT_TRUST, KeyAA.POINT_TRUST_DEFAULT);
            switchContractor = false;
            Log.d(KeyAA.KEY_LOG, "SaveAndLoadData: khởi tạo shared preferences vào static xong");
        }
    }

    private static SharedPreferences information, trust;
    private static String nameUser, aliasUser;
    private static boolean sexUserIsBoy;
    private static boolean switchContractor = true;
    private static long pointTrust;

//    hàm lưu trust vào sharedPrefeerences sau đó truyền dữ liệu vào static.
    public void addPointTrust(float f){
        pointTrust += (int) f;
        SharedPreferences.Editor editor = trust.edit();
        editor.putLong(KeyAA.KEY_POINT_TRUST, pointTrust);
        editor.apply();
    }
//    hàm lưu dữ liệu vào sharedPreferences information sau đó truyền dữ liệu vào static.
    public void setInformation(String nameUser, boolean sexUserIsBoy){
        while (nameUser.contains("  ")){
            nameUser = nameUser.replace("  ", " ");
        }
        String nameChange = "";
        for (int i = 0; i < nameUser.split(" ").length; i++){
            nameChange +=
                    String.valueOf(nameUser.split(" ")[i].trim().charAt(0))
                            .toUpperCase()
                            .concat(nameUser.split(" ")[i].trim().substring(1).toLowerCase());
            nameChange += " ";
        }
        nameChange = nameChange.trim();
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
//    hàm get các giá trị
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
    public static long getPointTrust(){
        return SaveAndLoadData.pointTrust;
    }
}
