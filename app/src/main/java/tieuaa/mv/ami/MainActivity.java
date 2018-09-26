package tieuaa.mv.ami;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SaveAndLoadData saveAndLoadData;
//    byte clickDeleteData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.titleMain));

        saveAndLoadData = new SaveAndLoadData(this);

        Button buttonYes = findViewById(R.id.ButtonYes);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveAndLoadData.getNameUser().equals(KeyAA.KEY_FIX_BUG_INFORMARTION)){
                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, AmiActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button buttonNo = findViewById(R.id.ButtonNo);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, getResources().getString(R.string.messengerExit), Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
