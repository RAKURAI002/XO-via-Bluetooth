package tni.korawit.xogame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static java.lang.Boolean.TRUE;

public class SingleMode extends AppCompatActivity {
    int turn = 0;//0=o , 1=x
    int p1,p2 = 0;
    int stop = 0;
    int count = -1;
    int stuck = 0;
    TextView[] retextView = new TextView[9];
    int[][] x = new int[][] {{1,1,1},{1,1,1},{1,1,1}};
    int[][] o = new int[][] {{1,1,1},{1,1,1},{1,1,1}};
    private static final String LOG_TAG = SingleMode.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_mode);
    }
    public void playox(View view) {
        TextView p1txt = findViewById(R.id.onescoretxt);
        TextView p2txt = findViewById(R.id.bscoretxt);
        TextView textView = findViewById(view.getId());
        writeoxtxt(textView);
        if(stop == 0 && checkwino()==1)
        {
            p1++;
            p1txt.setText(""+p1);
            stop = 1;
            displayToast("Player 1 WIN");
            return;
        }
        if(stop == 0 && checkwinx()==1)
        {
            p2++;
            p2txt.setText(""+p2);
            stop = 1;
            displayToast("Player 2 WIN");
            return;
        }
        if(stop == 0 && count==8)
        {
            stop = 1;
            displayToast("DRAW");
            Reset(textView);
            return;
        }
        if(turn==1)
        {botplay();}
        if(stop == 0 && checkwino()==1)
        {
            p1++;
            p1txt.setText(""+p1);
            stop = 1;
            displayToast("Player 1 WIN");
        }
        if(stop == 0 && checkwinx()==1)
        {
            p2++;
            p2txt.setText(""+p2);
            stop = 1;
            displayToast("Player 2 WIN");
        }
        Log.d(LOG_TAG, "E N D");
    }

    public void botplay()
    {
        TextView botturntxt;
        TextView turntxt = findViewById(R.id.turnox1txt);
        Random rx = new Random();
        Random ry = new Random();
        int botselx = rx.nextInt(2);
        int botsely = ry.nextInt(2);
        Log.d(LOG_TAG, "X : "+botselx);
        Log.d(LOG_TAG, "Y : "+botsely);
        Log.d(LOG_TAG, "o[][] : "+o[botselx][botsely]);
        Log.d(LOG_TAG, "x[][] : "+x[botselx][botsely]);
        while(TRUE) {
            botselx = rx.nextInt(3);
            botsely = ry.nextInt(3);
            Log.d(LOG_TAG, "X : "+botselx);
            Log.d(LOG_TAG, "Y : "+botsely);
            Log.d(LOG_TAG, "o[][] : "+o[botselx][botsely]);
            Log.d(LOG_TAG, "x[][] : "+x[botselx][botsely]);
            stuck++;
            if (o[botselx][botsely] == 1 && x[botselx][botsely] == 1) {
                stuck =0;
                break;
            }
        }
        if(botselx == 0)
        {
            if(botsely == 0)
            {
                botturntxt = findViewById(R.id.gridtxt00);
                Log.d(LOG_TAG, "Y E S");
                writeoxtxt(botturntxt);
                return;
            }
            else if(botsely == 1)
            {
                botturntxt = findViewById(R.id.gridtxt01);
                Log.d(LOG_TAG, "Y E S");
                writeoxtxt(botturntxt);
                return;
            }
            else if(botsely == 2)
            {
                botturntxt = findViewById(R.id.gridtxt02);
                Log.d(LOG_TAG, "Y E S");
                writeoxtxt(botturntxt);
                return;
            }
        }
        else if(botselx == 1)
        {
            if(botsely == 0)
            {
                botturntxt = findViewById(R.id.gridtxt10);
                Log.d(LOG_TAG, "Y E S");
                writeoxtxt(botturntxt);
                return;
            }
            else if(botsely == 1)
            {
                botturntxt = findViewById(R.id.gridtxt11);
                Log.d(LOG_TAG, "Y E S");
                writeoxtxt(botturntxt);
                return;
            }
            else if(botsely == 2)
            {
                botturntxt = findViewById(R.id.gridtxt12);
                Log.d(LOG_TAG, "Y E S");
                writeoxtxt(botturntxt);
                return;
            }
        }
        else if(botselx == 2)
        {
            if(botsely == 0)
            {
                botturntxt = findViewById(R.id.gridtxt20);
                Log.d(LOG_TAG, "Y E S");
                writeoxtxt(botturntxt);
                return;
            }
            else if(botsely == 1)
            {
                botturntxt = findViewById(R.id.gridtxt21);
                Log.d(LOG_TAG, "Y E S");
                writeoxtxt(botturntxt);
                return;
            }
            else if(botsely == 2)
            {
                botturntxt = findViewById(R.id.gridtxt22);
                Log.d(LOG_TAG, "Y E S");
                writeoxtxt(botturntxt);
                return;
            }
        }
    }

    public void writeoxtxt(TextView view) {
        Log.d(LOG_TAG, "Y A Y");
        TextView turntxt = findViewById(R.id.turnox1txt);
        if(stop == 0 && turn == 0 && view.getText() != "X" && view.getText() != "O") {
            count++;
            retextView[count] = findViewById(view.getId());
            view.setText("O");
            switch (view.getId()) {
                case R.id.gridtxt00: // Same day service
                    o[0][0]=0;
                    break;
                case R.id.gridtxt01: // Same day service
                    o[0][1]=0;
                    break;
                case R.id.gridtxt02: // Same day service
                    o[0][2]=0;
                    break;
                case R.id.gridtxt10: // Same day service
                    o[1][0]=0;
                    break;
                case R.id.gridtxt11: // Same day service
                    o[1][1]=0;
                    break;
                case R.id.gridtxt12: // Same day service
                    o[1][2]=0;
                    break;
                case R.id.gridtxt20: // Same day service
                    o[2][0]=0;
                    break;
                case R.id.gridtxt21: // Same day service
                    o[2][1]=0;
                    break;
                case R.id.gridtxt22: // Same day service
                    o[2][2]=0;
                    break;
                default:
                    // Do nothing.
                    break;
            }
            turn = 1;
            turntxt.setText("X");
        }
        else if(stop == 0 && turn == 1 && view.getText() != "X" && view.getText() != "O"){
            count++;
            retextView[count] = findViewById(view.getId());
            view.setText("X");
            switch (view.getId()) {
                case R.id.gridtxt00: // Same day service
                    x[0][0]=0;
                    break;
                case R.id.gridtxt01: // Same day service
                    x[0][1]=0;
                    break;
                case R.id.gridtxt02: // Same day service
                    x[0][2]=0;
                    break;
                case R.id.gridtxt10: // Same day service
                    x[1][0]=0;
                    break;
                case R.id.gridtxt11: // Same day service
                    x[1][1]=0;
                    break;
                case R.id.gridtxt12: // Same day service
                    x[1][2]=0;
                    break;
                case R.id.gridtxt20: // Same day service
                    x[2][0]=0;
                    break;
                case R.id.gridtxt21: // Same day service
                    x[2][1]=0;
                    break;
                case R.id.gridtxt22: // Same day service
                    x[2][2]=0;
                    break;
                default:
                    // Do nothing.
                    break;
            }
            turn = 0;
            turntxt.setText("O");
        }
    }

    public int checkwino ()
    {
        if(o != null) {
            if ((o[0][0] == 0 && o[0][1] == 0 && o[0][2] == 0) || (o[1][0] == 0 && o[1][1] == 0 && o[1][2] == 0) || (o[2][0] == 0 && o[2][1] == 0 && o[2][2] == 0)) {
                return 1;
            } else if ((o[0][0] == 0 && o[1][0] == 0 && o[2][0] == 0) || (o[0][1] == 0 && o[1][1] == 0 && o[2][1] == 0) || (o[0][2] == 0 && o[1][2] == 0 && o[2][2] == 0)) {
                return 1;
            } else if ((o[0][0] == 0 && o[1][1] == 0 && o[2][2] == 0) || (o[0][2] == 0 && o[1][1] == 0 && o[2][0] == 0)) {
                return 1;
            } else
                return 0;
        }
        else
            return -1;
    }

    public int checkwinx () {
        if (x != null) {
            if ((x[0][0] == 0 && x[0][1] == 0 && x[0][2] == 0) || (x[1][0] == 0 && x[1][1] == 0 && x[1][2] == 0) || (x[2][0] == 0 && x[2][1] == 0 && x[2][2] == 0)) {
                return 1;
            } else if ((x[0][0] == 0 && x[1][0] == 0 && x[2][0] == 0) || (x[0][1] == 0 && x[1][1] == 0 && x[2][1] == 0) || (x[0][2] == 0 && x[1][2] == 0 && x[2][2] == 0)) {
                return 1;
            } else if ((x[0][0] == 0 && x[1][1] == 0 && x[2][2] == 0) || (x[0][2] == 0 && x[1][1] == 0 && x[2][0] == 0)) {
                return 1;
            } else
                return 0;
        }
        else
            return -1;
    }

    public void Reset(View view) {
        for(int i = 0;i<3;++i)
        {
            for(int j = 0;j<3;++j)
            {
                x[i][j]=1;
                o[i][j]=1;
            }
        }
        while(count>=0)
        {
            retextView[count].setText(" ");
            --count;
        }
        stop = 0;
        count = -1;
        turn = 0;
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}
