package tni.korawit.xogame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void singlestart(View view) {
        Intent it = new Intent(this, SingleMode.class);
        startActivity(it);
    }

    public void twostart(View view) {
        Intent it = new Intent(this, Two_Mode.class);
        startActivity(it);
    }

    public void twoBstart(View view) {
        Intent it = new Intent(this, BlueToothMode.class);
        startActivity(it);
    }
}
