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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AmiActivity extends AppCompatActivity {

    int changeRoom = 3;
    boolean atHome = true;
    int eyeChange,  eyebrowChange, featureChange,   mouthChange;
    int result = 0;
    String testResult = "";

    private String[] textAmiTestCode;
    private String[] textAmiChat;
    private String[] textAmiTest;

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
    SoundInGame soundInGame;

    ImageView imageBody, imageHair, imageClothes, imageEye, imageGlass, imageEyebrow, imageMouth, imageFeature;
    TextView textViewAmiChat;
    TextView textViewStt;
    ImageView imageViewAmiChat;
    Button buttonChat, buttonTest, button0, button1, button2, button3;
    LinearLayout linearLayoutChat, linearLayoutTest, linearLayoutBackground;
    Button buttonTouchHair, buttonTouchFace, buttonTouchShoulder, buttonTouchBody;
    EditText editTextChatToAmi;
    Button buttonOkTestCode;
    Button buttonTestCode;

    final int BODY_DEFAULT      = 1; // body.length;	// HAIR DEFAULT = BODY DEFAULT = 1
    final int EYE_TINY          = 2;
    final int EYE_BIG           = EYE_TINY + 4; 	    // [indexTinyDefault,5] = 6 (4 is number of eyeBig)
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
    final int MOUTH_ANGRY       = MOUTH_FUN + 6; 		// [MOUTH_FUN, 44] = 45
    final int MOUTH_SAD         = MOUTH_ANGRY + 6; 	    // [MOUTH_ANGRY, 50] = 51
    final int MOUTH_SUDDENT     = MOUTH_SAD + 6; 		// [MOUTH_SAD, 56] = 57
    final int CHANGE_ROOM       = 3;
    final int CHAT_ABOUT        = 7;
    final int HELLO_WORLD_ABOUT = 3;
    final int TEST_ABOUT        = 25; // chia cho 5
    final int TEST_CODE_ABOUT   = 10; // chia cho 2
    final int CHAT_WIN          = 10;
    final int TEST_WIN          = 125;
    final int TEST_LOST         = -50;
    final int TL_CLOTHES        = 500;
    final int TL_CHAT           = 150;
    final int TL_TEST           = 30; // nhân cho 5
    final int TL_TEST_CODE      = 75; // nhân cho 2
    final int TL_HELLO_WORLD    = 1000;
    final int TL_TOUCH          = 1000;

    @Override
    protected void onResume() {
        super.onResume();
        soundInGame.continueSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundInGame.pauseSound();
    }

    private void setText(int i){
        switch (i){
            case 1:
                textAmiChat = getResources().getStringArray(R.array.aa_chat_1);
                textAmiTest = getResources().getStringArray(R.array.aa_test_1);
                textAmiTestCode = getResources().getStringArray(R.array.aa_test_code_1);
                break;
            case 2:
                textAmiChat = getResources().getStringArray(R.array.aa_chat_2);
                textAmiTest = getResources().getStringArray(R.array.aa_test_2);
                textAmiTestCode = getResources().getStringArray(R.array.aa_test_code_2);
                break;
            default:
                Toast.makeText(this,"Lỗi không nhận được môn học đã đăng kí!", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ami);

        saveAndLoadData = new SaveAndLoadData(this);
        soundInGame = new SoundInGame(this,R.raw.rawhome);
        setText(saveAndLoadData.getSubjectLearning());

        helpData        = new HelpData();
        imageBody       = findViewById(R.id.image_body);
        imageHair       = findViewById(R.id.image_hair);
        imageClothes    = findViewById(R.id.image_clothes);
        imageEye        = findViewById(R.id.image_eye);
        imageEyebrow    = findViewById(R.id.image_eyebrow);
        imageFeature    = findViewById(R.id.image_feature);
        imageGlass      = findViewById(R.id.image_glass);
        imageMouth      = findViewById(R.id.image_mouth);
        textViewAmiChat = findViewById(R.id.text_AmiChat);
        imageViewAmiChat = findViewById(R.id.image_chat);
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
        buttonOkTestCode();
    }

    //    dùng để đổi từ name ảnh sang id
    private int idImageFromName(String nameImage){
        int idResource = getResources().getIdentifier(nameImage, "drawable", getPackageName());
        Log.d(KeyAA.KEY_LOG, "Chuyển tấm ảnh: "+nameImage+", thành id: "+idResource);
        return idResource;
    }

    //    hàm dùng dể nhận id ảnh mặt định tại vị trí lớp hoặc nhà (tốt nhất chỉ dùng 1 lần tại 1 ngữ cảnh)
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

    //    hàm thay đổi văn bản và chạy hàm thể hiện cảm xúc
    private String textAmiChat(String text){
        if(text.contains("[fun]")){setImageAmi("Fun");     text = helpData.removeSubString(text, "[fun]");}
        if(text.contains("[ang]")){setImageAmi("Angry");   text = helpData.removeSubString(text, "[ang]");}
        if(text.contains("[sad]")){setImageAmi("Sad");     text = helpData.removeSubString(text, "[sad]");}
        if(text.contains("[sud]")){setImageAmi("Suddent"); text = helpData.removeSubString(text, "[sud]");}
        if(text.contains("[def]")){setImageAmi("Default"); text = helpData.removeSubString(text, "[def]");}
        String startImage = "[im", endImage = "]";
        if(text.contains(startImage)){
            String nameImage = helpData.StringCut(text, startImage, endImage);
            text = helpData.removeSubString(text, nameImage);
            nameImage = helpData.removeSubString(nameImage, "[");
            nameImage = helpData.removeSubString(nameImage, "]");
            int idImage = idImageFromName(nameImage);
            imageViewAmiChat.setVisibility(View.VISIBLE);
            imageViewAmiChat.setImageResource(idImage);
        }
        else {
            imageViewAmiChat.setVisibility(View.GONE);
        }
        String string = text
                .replace(KeyAA.KEY_ALIAS_USER, saveAndLoadData.getAliasUser())
                .replace(KeyAA.KEY_NAME_USER, saveAndLoadData.getNameUser())
                .replace(KeyAA.KEY_ALIAS_AA, saveAndLoadData.getAliasAA())
                .replace(KeyAA.KEY_NAME_AA, saveAndLoadData.getNameAA());
        return string;
    }

    //    hàm dùng để kiểm tra câu trả lời test có đúng không
    private void clickTest(String[] win,String[] lost, int indexResult){
        changeChatAndTest(true);
        if (this.result == indexResult){
            textViewAmiChat.setText(textAmiChat(win[helpData.randomRange(win.length)]));
            saveAndLoadData.addPointTrust(TEST_WIN);
        }
        else {
            textViewAmiChat.setText(textAmiChat(lost[helpData.randomRange(lost.length)].replace("[result]", testResult)));
            saveAndLoadData.addPointTrust(TEST_LOST);
        }
    }

    //    hàm để truyền hình ảnh vào layout. cũng như thể hiện cảm xúc
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
                imageEyebrow.setImageResource(eyebrowDeafault);
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

    //    hàm true để bật chế độ chat và ẩn phím abcd
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

    //    hàm kiểm tra vị trí nhà và trường và thay đổi ngữ cảnh (true để xác nhận nhận dữ liệu từ button chat)
    private boolean changeHomeAndClass(boolean buttonChat){
        if (buttonChat && !atHome){
            if (changeRoom > CHANGE_ROOM){
                linearLayoutBackground.setBackgroundResource(R.drawable.athome);
                atHome = true;
                setIndexDefault(false);
                buttonTestCode.setVisibility(View.GONE);
                final String[] textAmiMoveHome = getResources().getStringArray(R.array.move_home);
                textViewAmiChat.setText(textAmiChat(textAmiMoveHome[helpData.randomRange(textAmiMoveHome.length)]));
                textViewStt.setText(null);
                soundInGame = new SoundInGame(this,R.raw.rawhome);
                return false;
            }
            else {
                changeRoom+=1;
                if (changeRoom >= CHANGE_ROOM) textViewStt.setText(textAmiChat("[ni] đang muốn về nhà!"));
                else textViewStt.setText(null);
            }
        }
        else if (!buttonChat && atHome){
            if (changeRoom < -CHANGE_ROOM){
                linearLayoutBackground.setBackgroundResource(R.drawable.atclass);
                atHome = false;
                setIndexDefault(true);
                buttonTestCode.setVisibility(View.VISIBLE);
                final String[] textAmiMoveClass = getResources().getStringArray(R.array.move_class);
                textViewAmiChat.setText(textAmiChat(textAmiMoveClass[helpData.randomRange(textAmiMoveClass.length)]));
                textViewStt.setText(null);
                soundInGame = new SoundInGame(this,R.raw.rawclass);
                return false;
            }
            else {
                changeRoom -= 1;
                if (changeRoom <= -CHANGE_ROOM) textViewStt.setText(textAmiChat("[ni] đang muốn đến trường!"));
                else textViewStt.setText(null);
            }
        }
        return true;
    }

    //    hàm true để bật mọi thứ cần thiết khi testCode
    private void changeTestCodeAndChat(boolean chatToAmi){
        if (chatToAmi){
            editTextChatToAmi.setVisibility(View.VISIBLE);
            buttonOkTestCode.setVisibility(View.VISIBLE);
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
            buttonOkTestCode.setVisibility(View.GONE);
            buttonTouchShoulder.setVisibility(View.VISIBLE);
            buttonTouchBody.setVisibility(View.VISIBLE);
            buttonTouchFace.setVisibility(View.VISIBLE);
            buttonTouchHair.setVisibility(View.VISIBLE);
            buttonChat.setEnabled(true);
            buttonTest.setEnabled(true);
            buttonTestCode.setEnabled(true);
        }
    }

    //    các hàm có công dụng như tên (chỉ nên dùng 1 lần khi vào activity)
    private void amiHelloWorld(){
        final String textAmiHelloWorld[] = getResources().getStringArray(R.array.ami_HelloWorld);
        textViewAmiChat.setText(textAmiChat(textAmiHelloWorld[helpData.randomLine(TL_HELLO_WORLD, textAmiHelloWorld.length, HELLO_WORLD_ABOUT)]));
    }
    private void buttonChat(){
        buttonChat = findViewById(R.id.button_chat);

        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changeHomeAndClass(true)){
                    textViewAmiChat.setText(textAmiChat(textAmiChat[helpData.randomLine(TL_CHAT, textAmiChat.length, CHAT_ABOUT)]));
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
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changeHomeAndClass(false)) {
                    changeChatAndTest(false);
                    int line = helpData.randomLine(TL_TEST, textAmiTest.length, TEST_ABOUT);
                    int question = (int) (line - (line % 5));
                    String textTest = textAmiChat("<^> " + textAmiTest[question]).concat("\n\n");
                    Integer nT[] = {2, 3, 4};
                    ArrayList<Integer> NT = new ArrayList<>(Arrays.asList(nT));
                    Collections.shuffle(NT);
                    String N0 = textAmiChat(textAmiTest[question + 1]);
                    String N1 = textAmiChat(textAmiTest[question + NT.get(0)]);
                    String N2 = textAmiChat(textAmiTest[question + NT.get(1)]);
                    String N3 = textAmiChat(textAmiTest[question + NT.get(2)]);
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
        final String[] textTouchShoulder = getResources().getStringArray(R.array.stringTouchShoulder);

        buttonTouchHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAmiChat.setText(textAmiChat(textTouchHair[helpData.randomLine(TL_TOUCH, textTouchHair.length)]));
                saveAndLoadData.addPointTrust(CHAT_WIN);
            }
        });
        buttonTouchFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAmiChat.setText(textAmiChat(textTouchFace[helpData.randomLine(TL_TOUCH,textTouchFace.length)]));
                saveAndLoadData.addPointTrust(CHAT_WIN);
            }
        });
        buttonTouchShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAmiChat.setText(textAmiChat(textTouchShoulder[helpData.randomLine(TL_TOUCH,textTouchShoulder.length)]));
                saveAndLoadData.addPointTrust(CHAT_WIN);
            }
        });
        buttonTouchBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAmiChat.setText(textAmiChat(textTouchShoulder[helpData.randomLine(TL_TOUCH,textTouchShoulder.length)]));
            }
        });
    }
    private void buttonOkTestCode(){
        editTextChatToAmi = findViewById(R.id.editText_Chat_To_Ami);
        buttonOkTestCode   = findViewById(R.id.button_Chat_To_Ami);
        final String textAmiWin[]  = getResources().getStringArray(R.array.ami_win);
        final String textAmiLost[] = getResources().getStringArray(R.array.ami_lost);
        buttonOkTestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textResult = editTextChatToAmi.getText().toString().trim();
                editTextChatToAmi.setText(null);
                if (textResult.equalsIgnoreCase(testResult.trim())){
                    textViewAmiChat.setText(textAmiChat(textAmiWin[helpData.randomRange(textAmiWin.length)]));
                    saveAndLoadData.addPointTrust(TEST_WIN * 2);
                }
                else {
                    textViewAmiChat.setText(textAmiChat(textAmiLost[helpData.randomRange(textAmiLost.length)].replace("[result]", testResult)));
                    saveAndLoadData.addPointTrust(TEST_LOST);
                }
                changeTestCodeAndChat(false);
            }
        });
    }
    private void buttonTestCode(){
        buttonTestCode = findViewById(R.id.button_test_code);
        buttonTestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTestCodeAndChat(true);
                int ran = helpData.randomLine(TL_TEST_CODE, textAmiTestCode.length, TEST_CODE_ABOUT);
                int rand = (int) ran - (ran % 2);
                textViewAmiChat.setText("<^>".concat(textAmiChat(textAmiTestCode[rand])));
                testResult = textAmiChat(textAmiTestCode[rand+1]);
            }
        });
    }
}
