package tni.korawit.xogame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Two_Mode extends AppCompatActivity {
    int turn = 0;//0=o , 1=x
    int p1,p2 = 0;
    int stop = 0;
    int count = -1;
    TextView[] retextView = new TextView[9];
    int[][] x = new int[][] {{1,1,1},{1,1,1},{1,1,1}};
    int[][] o = new int[][] {{1,1,1},{1,1,1},{1,1,1}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two__mode);
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
        }
        if(stop == 0 && checkwinx()==1)
        {
            p2++;
            p2txt.setText(""+p2);
            stop = 1;
            displayToast("Player 2 WIN");
        }
        if(stop == 0 && count==8)
        {
            stop = 1;
            displayToast("DRAW");
            Reset(textView);
        }
    }

    public void writeoxtxt(TextView view) {
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
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
