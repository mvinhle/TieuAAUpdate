package tieuaa.mv.ami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AmiActivity extends AppCompatActivity {

    int changeRoom = 3;
    boolean atHome = true;
    int eyeChange,  eyebrowChange, featureChange,   mouthChange;
    int result = 0;
    String testResult = "";

    private int bodyDefault;
    private int hairDefault;
    private int eyeDefault;
    private int eyebrowDeafault;
    private int clothesDefault;
    private int glassDefault;
    private int featureDefault;
    private int mouthDefault;

    HelpData helpData;
    SaveAndLoadData saveAndLoadData;

    ImageView imageBody, imageHair, imageClothes, imageEye, imageGlass, imageEyebrow, imageMouth, imageFeature;
    TextView textViewAmiChat;
    TextView textViewStt;
    Button buttonChat, buttonTest, button0, button1, button2, button3;
    LinearLayout linearLayoutChat, linearLayoutTest, linearLayoutBackground;
    Button buttonTouchHair, buttonTouchFace, buttonTouchShoulder, buttonTouchBody;
    EditText editTextChatToAmi;
    Button buttonChatToAmi;
    Button buttonTestCode;

    final int BODY_DEFAULT      = 1; // body.length;		// HAIR DEFAULT = BODY DEFAULT = 1
    final int EYE_TINY          = 2;
    final int EYE_BIG           = EYE_TINY + 4; 	    	// [indexTinyDefault,5] = 6 (4 is number of eyeBig)
    final int EYE_FUN           = EYE_BIG + 3; 	    	// [indexTinyBig, 9] = 10
    final int EYEBROW_DEFAULT   = 1;
    final int EYEBROW_ANGRY     = EYEBROW_DEFAULT + 1; 	// [EYEBORW_DEFAULT, 1] = 2
    final int EYEBROW_SAD       = EYEBROW_ANGRY + 1;  	// [EYEBROW_ANGRY, 2] = 3
    final int GLASS_DEFAULT     = 4; // glass.length; 	// [0, 3] = 4
    final int FEATURE_FUN       = 9;
    final int FEATURE_ANGRY     = FEATURE_FUN + 9; 	    // [FEATURE_FUN, 17] = 18
    final int FEATURE_SAD       = FEATURE_ANGRY + 2; 	    // [FEATURE_FUN, 19] = 20
    final int MOUTH_DEFAULT     = 8;
    final int MOUTH_FUN         = MOUTH_DEFAULT + 31; 	// [MOUTH_DEFAULT, 38] = 39
    final int MOUTH_ANGRY       = MOUTH_FUN + 6; 		    // [MOUTH_FUN, 44] = 45
    final int MOUTH_SAD         = MOUTH_ANGRY + 6; 	    // [MOUTH_ANGRY, 50] = 51
    final int MOUTH_SUDDENT     = MOUTH_SAD + 6; 		    // [MOUTH_SAD, 56] = 57
    final int CHANGE_ROOM       = 3;
    final int CHAT_ABOUT        = 7;
    final int TEST_ABOUT        = 35; // chia cho 5
    final int HELLO_WORLD_ABOUT = 3;
    final int TEST_CODE_ABOUT   = 14; // chia cho 2
    final int CHAT_WIN          = 10;
    final int TEST_WIN          = 100;
    final int TEST_LOST         = -70;
    final int TOUCH_ABOUT       = 7;
    final int TL_CLOTHES        = 1000;
    final int TL_CHAT           = 800;
    final int TL_TEST           = 800;
    final int TL_HELLO_WORLD    = 1000;
    final int TL_TOUCH          = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ami);

        helpData        = new HelpData();
        saveAndLoadData = new SaveAndLoadData(this);
        imageBody       = findViewById(R.id.image_body);
        imageHair       = findViewById(R.id.image_hair);
        imageClothes    = findViewById(R.id.image_clothes);
        imageEye        = findViewById(R.id.image_eye);
        imageEyebrow    = findViewById(R.id.image_eyebrow);
        imageFeature    = findViewById(R.id.image_feature);
        imageGlass      = findViewById(R.id.image_glass);
        imageMouth      = findViewById(R.id.image_mouth);
        textViewAmiChat = findViewById(R.id.text_AmiChat);
        setIndexDefault(false);
        textViewStt = findViewById(R.id.textView_HomeAndClass);

        linearLayoutChat  = findViewById(R.id.linearLayout_home);
        linearLayoutTest = findViewById(R.id.linearLayout_class);
        linearLayoutBackground = findViewById(R.id.linearLayout_BackgroundHomeAndClass);

        amiHelloWorld();
        buttonTouch();
        buttonChat();
        buttonTest();
        buttonTestCode();
        buttonChatToAmi();
    }

    private void amiHelloWorld(){
        final String textAmiHelloWorld[] = getResources().getStringArray(R.array.ami_HelloWorld);
        textViewAmiChat.setText(chatWithAmi(textAmiHelloWorld[helpData.randomLine(TL_HELLO_WORLD, textAmiHelloWorld.length, HELLO_WORLD_ABOUT)]));
    }
    private void buttonChat(){
        buttonChat = findViewById(R.id.button_chat);
        final String textAmiChat[] = getResources().getStringArray(R.array.ami_chat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changeHomeAndClass(true)){
                    textViewAmiChat.setText(chatWithAmi(textAmiChat[helpData.randomLine(TL_CHAT, textAmiChat.length, CHAT_ABOUT)]));
                    saveAndLoadData.addPointTrust(CHAT_WIN);
                }
            }
        });
    }
    private void buttonTest(){
        buttonTest = findViewById(R.id.button_test);
        button0    = findViewById(R.id.button_testA);
        button1    = findViewById(R.id.button_testB);
        button2    = findViewById(R.id.button_testC);
        button3    = findViewById(R.id.button_testD);
        final String textAmiTest[] = getResources().getStringArray(R.array.ami_test);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changeHomeAndClass(false)) {
                    changeChatAndTest(false);
                    int line = helpData.randomLine(TL_TEST, textAmiTest.length, TEST_ABOUT);
                    int question = (int) (line - (line % 5));
                    String textTest = chatWithAmi("<^> " + textAmiTest[question]).concat("\n\n");
                    Integer nT[] = {2, 3, 4};
                    ArrayList<Integer> NT = new ArrayList<>(Arrays.asList(nT));
                    Collections.shuffle(NT);
                    String N0 = textAmiTest[question + 1];
                    String N1 = textAmiTest[question + NT.get(0)];
                    String N2 = textAmiTest[question + NT.get(1)];
                    String N3 = textAmiTest[question + NT.get(2)];
                    String testR[][] = {
                            {getResources().getString(R.string.A).concat(": ").concat(N0),
                                    getResources().getString(R.string.B).concat(": ").concat(N1),
                                    getResources().getString(R.string.C).concat(": ").concat(N2),
                                    getResources().getString(R.string.D).concat(": ").concat(N3)},
                            {getResources().getString(R.string.A).concat(": ").concat(N1),
                                    getResources().getString(R.string.B).concat(": ").concat(N0),
                                    getResources().getString(R.string.C).concat(": ").concat(N2),
                                    getResources().getString(R.string.D).concat(": ").concat(N3)},
                            {getResources().getString(R.string.A).concat(": ").concat(N1),
                                    getResources().getString(R.string.B).concat(": ").concat(N2),
                                    getResources().getString(R.string.C).concat(": ").concat(N0),
                                    getResources().getString(R.string.D).concat(": ").concat(N3)},
                            {getResources().getString(R.string.A).concat(": ").concat(N1),
                                    getResources().getString(R.string.B).concat(": ").concat(N2),
                                    getResources().getString(R.string.C).concat(": ").concat(N3),
                                    getResources().getString(R.string.D).concat(": ").concat(N0)}
                    };
                    result = helpData.randomRange(testR.length);
                    for (int i = 0; i < testR[result].length; i++) {
                        if (i < testR[result].length - 1) {
                            textTest += "\t" + testR[result][i].concat("\n\n");
                        } else {
                            textTest += "\t" + testR[result][i];
                        }
                    }
                    testResult = testR[result][result];
                    Log.d(KeyAA.KEY_LOG, "Click vào test: " + textTest + " \nresult: " + N0);
                    textViewAmiChat.setText(textTest);
                }
            }
        });
        final String textAmiWin[]  = getResources().getStringArray(R.array.ami_win);
        final String textAmiLost[] = getResources().getStringArray(R.array.ami_lost);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTest(textAmiWin, textAmiLost, 0);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTest(textAmiWin, textAmiLost, 1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTest(textAmiWin, textAmiLost, 2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTest(textAmiWin, textAmiLost, 3);
            }
        });
    }
    private void buttonTouch(){
        buttonTouchHair = findViewById(R.id.button_Touch_Hair);
        buttonTouchFace = findViewById(R.id.button_Touch_Face);
        buttonTouchBody = findViewById(R.id.button_Touch_Body);
        buttonTouchShoulder = findViewById(R.id.button_Touch_Body_Up);

        final String[] textTouchHair = getResources().getStringArray(R.array.stringTouchHair);
        final String[] textTouchFace = getResources().getStringArray(R.array.stringTouchFace);
        final String[] textTouchBodyB = getResources().getStringArray(R.array.stringTouchBodyB);
        final String[] textTouchBodyG = getResources().getStringArray(R.array.stringTouchBodyG);
        final String[] textTouchShoulder = getResources().getStringArray(R.array.stringTouchShoulder);

        buttonTouchHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAmiChat.setText(chatWithAmi(textTouchHair[helpData.randomLine(TL_TOUCH, textTouchHair.length, TOUCH_ABOUT)]));
                saveAndLoadData.addPointTrust(TEST_WIN/2);
            }
        });
        buttonTouchFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAmiChat.setText(chatWithAmi(textTouchFace[helpData.randomLine(TL_TOUCH,textTouchFace.length, TOUCH_ABOUT)]));
                saveAndLoadData.addPointTrust(CHAT_WIN/2);
            }
        });
        buttonTouchShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAmiChat.setText(chatWithAmi(textTouchShoulder[helpData.randomLine(TL_TOUCH,textTouchShoulder.length, TOUCH_ABOUT)]));
                saveAndLoadData.addPointTrust(CHAT_WIN/2);
            }
        });
        buttonTouchBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveAndLoadData.getSexUser()) {
                    textViewAmiChat.setText(chatWithAmi(textTouchBodyB[helpData.randomLine(TL_TOUCH,textTouchBodyB.length, TOUCH_ABOUT)]));
                }
                else {
                    textViewAmiChat.setText(chatWithAmi(textTouchBodyG[helpData.randomLine(TL_TOUCH,textTouchBodyG.length, TOUCH_ABOUT)]));
                }
                saveAndLoadData.addPointTrust(CHAT_WIN/2);
            }
        });
    }
    private void buttonChatToAmi(){
        editTextChatToAmi = findViewById(R.id.editText_Chat_To_Ami);
        buttonChatToAmi   = findViewById(R.id.button_Chat_To_Ami);
        final String textAmiWin[]  = getResources().getStringArray(R.array.ami_win);
        final String textAmiLost[] = getResources().getStringArray(R.array.ami_lost);
        buttonChatToAmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textResult = helpData.removeSubString(editTextChatToAmi.getText().toString(), " ");
                editTextChatToAmi.setText(null);
                if (textResult.equalsIgnoreCase(helpData.removeSubString(testResult, " "))){
                    textViewAmiChat.setText(chatWithAmi(textAmiWin[helpData.randomRange(textAmiWin.length)]));
                    saveAndLoadData.addPointTrust(TEST_WIN * 2);
                }
                else {
                    textViewAmiChat.setText(chatWithAmi(textAmiLost[helpData.randomRange(textAmiLost.length)].replace("[result]", testResult)));
                    saveAndLoadData.addPointTrust(TEST_LOST * 2);
                }
                changeChatToAmiAndAmiChat(false);
            }
        });
    }
    private void buttonTestCode(){
        buttonTestCode = findViewById(R.id.button_test_code);
        final String[] textAmiTestCode = getResources().getStringArray(R.array.ami_test_code);
        buttonTestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeChatToAmiAndAmiChat(true);
                int ran = helpData.randomLine(TL_TOUCH, textAmiTestCode.length, TEST_CODE_ABOUT);
                int rand = (int) ran - (ran % 2);
                textViewAmiChat.setText(chatWithAmi(textAmiTestCode[rand]));
                testResult = textAmiTestCode[rand+1];
            }
        });
    }
    // set index and image ami default
    private void setIndexDefault(boolean sglass){
        String body[]       = getResources().getStringArray(R.array.body);
        String hair[]       = getResources().getStringArray(R.array.hair); 	// intdex array hair = body
        String eye[]        = getResources().getStringArray(R.array.eye);
        String eyebrow[]    = getResources().getStringArray(R.array.eyebrow);
        String clothes[]    = getResources().getStringArray(R.array.clothes);
        String glass[]      = getResources().getStringArray(R.array.glass);
        //String feature[]    = getResources().getStringArray(R.array.feature); // no feature default and feature don't change.
        String mouth[]      = getResources().getStringArray(R.array.mouth);

        int indexBodyAndHair    = helpData.randomRange(BODY_DEFAULT);
        bodyDefault     = idImageFromName(body[indexBodyAndHair]);
        hairDefault     = idImageFromName(hair[indexBodyAndHair]); // index body = index hair
        eyeDefault      = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_DEFAULT)]);
        eyebrowDeafault = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_DEFAULT)]);
        mouthDefault    = idImageFromName(mouth[helpData.randomRange(MOUTH_DEFAULT)]);
        featureDefault  = R.drawable.imagenull; //(NO FEATURE DEFAULT)
        if (sglass){
            eyeDefault   = idImageFromName(eye[helpData.randomRange(EYE_TINY)]);
            glassDefault = idImageFromName(glass[helpData.randomRange(GLASS_DEFAULT)]);
        }
        else{
            eyeDefault   = idImageFromName(eye[helpData.randomRange(EYE_TINY, EYE_BIG)]);
            glassDefault = R.drawable.imagenull;
        }
        clothesDefault = idImageFromName(clothes[helpData.randomLine(TL_CLOTHES, clothes.length)]);
        setImageAmi("Default");
    }
    // set image ami (Fun, Angry, Suddent, Sad, Default)
    private void setImageAmi(String type){
        String eye[]     = getResources().getStringArray(R.array.eye);
        String eyebrow[] = getResources().getStringArray(R.array.eyebrow);
        String feature[] = getResources().getStringArray(R.array.feature);
        String mouth[]   = getResources().getStringArray(R.array.mouth);
        switch (type){
            case "Fun":
                eyeChange       = idImageFromName(eye[helpData.randomRange(EYE_BIG,EYE_FUN)]);
                mouthChange     = idImageFromName(mouth[helpData.randomRange(MOUTH_DEFAULT, MOUTH_FUN)]);
                featureChange   = idImageFromName(feature[helpData.randomRange(FEATURE_FUN)]);
                imageEye    .setImageResource(eyeChange);
                imageMouth  .setImageResource(mouthChange);
                imageFeature.setImageResource(featureChange);
                imageEyebrow.setImageResource(eyebrowDeafault);
                Log.d(KeyAA.KEY_LOG, "Ami đổi stt: fun feeling");
                break;
            case "Angry":
                featureChange = idImageFromName(feature[helpData.randomRange(FEATURE_FUN,FEATURE_ANGRY)]);
                mouthChange   = idImageFromName(mouth[helpData.randomRange(MOUTH_FUN, MOUTH_ANGRY)]);
                eyebrowChange = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_DEFAULT, EYEBROW_ANGRY)]);
                imageFeature.setImageResource(featureChange);
                imageMouth  .setImageResource(mouthChange);
                imageEyebrow.setImageResource(eyebrowChange);
                imageEye    .setImageResource(eyeDefault);
                Log.d(KeyAA.KEY_LOG, "Ami đổi stt: angry feeling");
                break;
            case "Sad":
                mouthChange   = idImageFromName(mouth[helpData.randomRange(MOUTH_ANGRY, MOUTH_SAD)]);
                eyebrowChange = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_ANGRY, EYEBROW_SAD)]);
                featureChange = idImageFromName(feature[helpData.randomRange(FEATURE_ANGRY, FEATURE_SAD)]);
                imageMouth  .setImageResource(mouthChange);
                imageEyebrow.setImageResource(eyebrowChange);
                imageFeature.setImageResource(featureChange);
                imageEye    .setImageResource(eyeDefault);
                Log.d(KeyAA.KEY_LOG, "Ami đổi stt: sad feeling");
                break;
            case "Suddent":
                mouthChange = idImageFromName(mouth[helpData.randomRange(MOUTH_SAD, MOUTH_SUDDENT)]);
                imageMouth.setImageResource(mouthChange);
                imageEye  .setImageResource(eyeDefault);
                Log.d(KeyAA.KEY_LOG, "Ami đổi stt: suddent feeling");
                break;
            case "Default":
                imageBody   .setImageResource(bodyDefault);
                imageHair   .setImageResource(hairDefault);
                imageClothes.setImageResource(clothesDefault);
                imageEye    .setImageResource(eyeDefault);
                imageEyebrow.setImageResource(eyebrowDeafault);
                imageGlass  .setImageResource(glassDefault);
                imageFeature.setImageResource(featureDefault);
                imageMouth  .setImageResource(mouthDefault);
                Log.d(KeyAA.KEY_LOG, "Ami đổi stt: Default feeling");
                break;
            default:
                Log.d(KeyAA.KEY_LOG, "Ami Không nhận được trạng thái nhưng vẫn gửi vào hàm setAmiImage !!");
                break;
        }
    }

    private int idImageFromName(String nameImage){
        int idResource = getResources().getIdentifier(nameImage, "drawable", getPackageName());
        Log.d(KeyAA.KEY_LOG, "Chuyển tấm ảnh: "+nameImage+", thành id: "+idResource);
        return idResource;
    }

    private String chatWithAmi(String text){
        if(text.contains("[fun]")){setImageAmi("Fun");     text = helpData.removeSubString(text, "[fun]");}
        if(text.contains("[ang]")){setImageAmi("Angry");   text = helpData.removeSubString(text, "[ang]");}
        if(text.contains("[sad]")){setImageAmi("Sad");     text = helpData.removeSubString(text, "[sad]");}
        if(text.contains("[sud]")){setImageAmi("Suddent"); text = helpData.removeSubString(text, "[sud]");}
        if(text.contains("[def]")){setImageAmi("Default"); text = helpData.removeSubString(text, "[def]");}
        String string = text
                .replace(KeyAA.KEY_ALIAS_USER, saveAndLoadData.getAliasUser())
                .replace(KeyAA.KEY_NAME_USER, saveAndLoadData.getNameUser())
                .replace(KeyAA.KEY_ALIAS_AA, saveAndLoadData.getAliasAA())
                .replace(KeyAA.KEY_NAME_AA, saveAndLoadData.getNameAA());
        return string;
    }

    private void changeChatAndTest(boolean chatTrue){
        if (chatTrue){
            linearLayoutChat.setVisibility(View.VISIBLE);
            linearLayoutTest.setVisibility(View.INVISIBLE);
            buttonTouchShoulder.setVisibility(View.VISIBLE);
            buttonTouchBody.setVisibility(View.VISIBLE);
            buttonTouchFace.setVisibility(View.VISIBLE);
            buttonTouchHair.setVisibility(View.VISIBLE);
        }
        else {
            linearLayoutTest.setVisibility(View.VISIBLE);
            linearLayoutChat.setVisibility(View.INVISIBLE);
            buttonTouchShoulder.setVisibility(View.INVISIBLE);
            buttonTouchBody.setVisibility(View.INVISIBLE);
            buttonTouchFace.setVisibility(View.INVISIBLE);
            buttonTouchHair.setVisibility(View.INVISIBLE);
        }
    }

    private boolean changeHomeAndClass(boolean buttonChat){
        if (buttonChat && !atHome){
            if (changeRoom > CHANGE_ROOM){
                linearLayoutBackground.setBackgroundResource(R.drawable.athome);
                atHome = true;
                setIndexDefault(false);
                buttonTestCode.setVisibility(View.GONE);
                final String[] textAmiMoveHome = getResources().getStringArray(R.array.move_home);
                textViewAmiChat.setText(chatWithAmi(textAmiMoveHome[helpData.randomRange(textAmiMoveHome.length)]));
                return false;
            }
            else {
                changeRoom+=1;
                textViewStt.setText("Độ lười: ".concat(String.valueOf(changeRoom)));
            }
        }
        else if (!buttonChat && atHome){
            if (changeRoom < -CHANGE_ROOM){
                linearLayoutBackground.setBackgroundResource(R.drawable.atclass);
                atHome = false;
                setIndexDefault(true);
                buttonTestCode.setVisibility(View.VISIBLE);
                final String[] textAmiMoveClass = getResources().getStringArray(R.array.move_class);
                textViewAmiChat.setText(chatWithAmi(textAmiMoveClass[helpData.randomRange(textAmiMoveClass.length)]));
                return false;
            }
            else {
                changeRoom -= 1;
                textViewStt.setText("Độ lười: ".concat(String.valueOf(changeRoom)));
            }
        }
        return true;
    }

    private void changeChatToAmiAndAmiChat(boolean chatToAmi){
        if (chatToAmi){
            editTextChatToAmi.setVisibility(View.VISIBLE);
            buttonChatToAmi.setVisibility(View.VISIBLE);
            buttonTouchShoulder.setVisibility(View.INVISIBLE);
            buttonTouchBody.setVisibility(View.INVISIBLE);
            buttonTouchFace.setVisibility(View.INVISIBLE);
            buttonTouchHair.setVisibility(View.INVISIBLE);
            buttonChat.setEnabled(false);
            buttonTest.setEnabled(false);
            buttonTestCode.setEnabled(false);
        }
        else {
            editTextChatToAmi.setVisibility(View.GONE);
            buttonChatToAmi.setVisibility(View.GONE);
            buttonTouchShoulder.setVisibility(View.VISIBLE);
            buttonTouchBody.setVisibility(View.VISIBLE);
            buttonTouchFace.setVisibility(View.VISIBLE);
            buttonTouchHair.setVisibility(View.VISIBLE);
            buttonChat.setEnabled(true);
            buttonTest.setEnabled(true);
            buttonTestCode.setEnabled(true);
        }
    }

    private void clickTest(String[] win,String[] lost, int indexResult){
        changeChatAndTest(true);
        if (this.result == indexResult){
            textViewAmiChat.setText(chatWithAmi(win[helpData.randomRange(win.length)]));
            saveAndLoadData.addPointTrust(TEST_WIN);
        }
        else {
            textViewAmiChat.setText(chatWithAmi(lost[helpData.randomRange(lost.length)].replace("[result]", testResult)));
            saveAndLoadData.addPointTrust(TEST_LOST);
        }
    }
}
