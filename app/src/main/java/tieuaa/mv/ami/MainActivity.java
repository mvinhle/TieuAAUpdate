package tieuaa.mv.ami;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SaveAndLoadData saveAndLoadData;
    byte clickDeleteData = 0;

    @Override
    protected void onRestart() {
        super.onRestart();
        SoundInGame soundInGame = new SoundInGame(this,R.raw.rawmain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.titleMain));

        saveAndLoadData = new SaveAndLoadData(MainActivity.this);
        SoundInGame soundInGame = new SoundInGame(this,R.raw.rawmain);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent);
            break;
            case R.id.menu_reset:
                dialogDeleteData();
            break;
            case R.id.menu_exit:
            finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogDeleteData(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_reset);
        dialog.setTitle(getResources().getString(R.string.deleteData));
        Button buttonYes = dialog.findViewById(R.id.deletaDataYes);
        Button buttonNo  = dialog.findViewById(R.id.deletaDataNo);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDeleteData += 1;
                if (clickDeleteData > 50){
                    saveAndLoadData.resetData();
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.deleteDataComplete),Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
                if (clickDeleteData % 10 == 0 && clickDeleteData > 0){
                    Toast.makeText(
                            MainActivity.this,
                            getResources().getString(R.string.deleteDataCompletess).replace("[n]",String.valueOf(clickDeleteData)),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        MainActivity.this,
                        getResources().getString(R.string.deleteDataError),
                        Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
        if (dialog.isShowing()){
            clickDeleteData = 0;
        }
    }
}
