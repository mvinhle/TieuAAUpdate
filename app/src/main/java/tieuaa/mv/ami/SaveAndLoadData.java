package tieuaa.mv.ami;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.security.Key;

public class SaveAndLoadData{
    public SaveAndLoadData(Activity activity){
        if (switchContractor){
            information = activity.getSharedPreferences(KeyAA.NAME_INFORMATION, activity.MODE_PRIVATE);
            trust = activity.getSharedPreferences(KeyAA.NAME_TRUST, activity.MODE_PRIVATE);
            restartUser();
            SaveAndLoadData.pointTrust = trust.getLong(KeyAA.KEY_POINT_TRUST, KeyAA.POINT_TRUST_DEFAULT);
            SaveAndLoadData.subjectLearning = trust.getInt(KeyAA.KEY_SUBJECT_LEARNING, 0);
            switchContractor = false;
            Log.d(KeyAA.KEY_LOG, "SaveAndLoadData: khởi tạo shared preferences vào static xong");
        }
    }

    private static int subjectLearning;
    private static SharedPreferences information, trust;
    private static String nameUser, aliasUser;
    private static boolean sexUserIsBoy;
    private static boolean switchContractor = true;
    private static long pointTrust;

//    hàm lưu trust vào sharedPrefeerences sau đó truyền dữ liệu vào static.
    public void addPointTrust(float f){
        pointTrust += (int) f;
        if (pointTrust < -300) pointTrust = -300;
        SharedPreferences.Editor editor = trust.edit();
        editor.putLong(KeyAA.KEY_POINT_TRUST, pointTrust);
        editor.apply();
    }
//    hàm lưu dữ liệu vào sharedPreferences information sau đó truyền dữ liệu vào static.
    public void setInformation(String nameUser, boolean sexUserIsBoy, int subjectLearning){
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
        SharedPreferences.Editor editorT = trust.edit();
        editor.apply();
        editorT.putInt(KeyAA.KEY_SUBJECT_LEARNING, subjectLearning);
        editorT.apply();
        restartUser();
        Log.d(KeyAA.KEY_LOG, "setInformation: đã lưu information với: "+nameChange+sexUserIsBoy);
    }
//    hàm truyền dữ liệu vào static (programer chỉ dùng khi có dũ liệu để tránh lỗi!)
    public void restartUser(){
        String nameUser = information.getString(KeyAA.KEY_NAME_INFORMATION, KeyAA.KEY_FIX_BUG_INFORMARTION);
        boolean sexUserIsBoy = information.getBoolean(KeyAA.KEY_SEX_INFORMATION, true);
        int subjectLearning  = trust.getInt(KeyAA.KEY_SUBJECT_LEARNING, 0);
        SaveAndLoadData.nameUser = nameUser;
        SaveAndLoadData.sexUserIsBoy = sexUserIsBoy;
        if (sexUserIsBoy){
            SaveAndLoadData.aliasUser = KeyAA.ALIAS_BOY;
        }
        else {
            SaveAndLoadData.aliasUser = KeyAA.ALIAS_GIRL;
        }
        addPointTrust(0);
        SaveAndLoadData.subjectLearning = subjectLearning;
        Log.d(KeyAA.KEY_LOG, "restartUser: đưa TT vào static với: "+aliasUser+nameUser);
    }
//    hàm xóa dữ liệu
    public void resetData(){
        SharedPreferences.Editor editorInfor = information.edit();
        SharedPreferences.Editor editorTrust = trust.edit();
        editorInfor.remove(KeyAA.KEY_NAME_INFORMATION);
        editorInfor.remove(KeyAA.KEY_SEX_INFORMATION);
        editorInfor.apply();
        editorTrust.remove(KeyAA.KEY_POINT_TRUST);
        editorTrust.remove(KeyAA.KEY_SUBJECT_LEARNING);
        editorTrust.apply();
        restartUser();
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
    public int getSubjectLearning(){
        return SaveAndLoadData.subjectLearning;
    }
}
