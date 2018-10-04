package tieuaa.mv.ami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class InformationActivity extends AppCompatActivity {

    private SaveAndLoadData saveAndLoadData;

    private EditText editTextName;
    private RadioGroup radioGroupBoyAndGirl;
    private RadioButton radioButtonBoy, radioButtonGirl;
    private Button buttonOk;

    private boolean sexUserIsBoy;
    private boolean bErrorName = false;
    private String  nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        this.setFinishOnTouchOutside(false);
        setTitle(getResources().getString(R.string.titleActivityInformation));

        saveAndLoadData = new SaveAndLoadData(this);
        editTextName = findViewById(R.id.editText_InformationName);
        buttonOk     = findViewById(R.id.button_Sign_up);
        radioGroupBoyAndGirl = findViewById(R.id.radioGroup_BoyAndGirl);
        radioButtonBoy       = findViewById(R.id.radioButton_Boy);
        radioButtonGirl      = findViewById(R.id.radioButton_Girl);

        saveAndLoadData.restartUser();

        if (saveAndLoadData.getNameUser().equals(KeyAA.KEY_FIX_BUG_INFORMARTION)){
            buttonOk.setText(getResources().getString(R.string.ok));
        }
        else {
            editTextName.setText(saveAndLoadData.getNameUser());
            if (saveAndLoadData.getSexUser()){
                radioButtonGirl.setChecked(false);
                radioButtonBoy.setChecked(true);
                sexUserIsBoy = true;
            }
            else {
                radioButtonBoy.setChecked(false);
                radioButtonGirl.setChecked(true);
                sexUserIsBoy = false;
            }
            buttonOk.setText(getResources().getString(R.string.restartOk));
        }

        radioGroupBoyAndGirl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton_Boy:
                        sexUserIsBoy = true;
                        break;
                    case R.id.radioButton_Girl:
                        sexUserIsBoy = false;
                        break;
                }
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameUser = editTextName.getText().toString().trim();
                String errorName[] = {"\n", "\t", "\\", "\"", "/", ".", ",", "<", ">", "(", ")", "|", ";", ":", "{", "}", "?",
                        "!", "@", "#", "$", "%", "^", "&", "*", "-", "=", "_", "+"};
                for (String s : errorName){
                    bErrorName = nameUser.contains(s);
                    if (bErrorName){
                        Log.d(KeyAA.KEY_LOG,"error name!"+s+bErrorName);
                        break;
                    }
                }
                if (bErrorName){
                    TextView textView = findViewById(R.id.note_informartion_error);
                    textView.setText(getResources().getString(R.string.errorSignName));
                }
                else if ((nameUser.length() < 1) ||
                        (!radioButtonBoy.isChecked() && !radioButtonGirl.isChecked())){
                    Toast.makeText(
                            InformationActivity.this,
                                    getResources().getString(R.string.noteInformationError),
                                    Toast.LENGTH_LONG).show();
                }
                else {
                    saveAndLoadData.setInformation(nameUser, sexUserIsBoy);
                    Toast.makeText(
                            InformationActivity.this,
                                    getResources().getString(R.string.noteInformationComplete),
                                    Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
