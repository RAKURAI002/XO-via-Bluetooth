package tni.korawit.xogame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BlueToothMode extends AppCompatActivity {

    Button listen, send, listDevices, ready, again;
    ListView listview;
    TextView msg_box, status, score, grid00, grid01, grid02, grid10, grid11, grid12, grid20, grid21, grid22;
    EditText writeMsg;
    int Device_mode  ;
    int Device_pos;

    int turn = 0;//0=o , 1=x
    int playernum = 0;
    int p1,p2 = 0;//p1=o , p2=x
    int stop = 0;
    int count = -1;
    int lock = 1;
    TextView[] retextView = new TextView[9];
    int[][] x = new int[][] {{1,1,1},{1,1,1},{1,1,1}};
    int[][] o = new int[][] {{1,1,1},{1,1,1},{1,1,1}};

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btArray;

    SendRecieve sendRecieve;

    static final String DEV_POS = "BT device position";
    static final String DEV_MODE = "Device Mode";
    static final String X_POS = "X Position";
    static final String O_POS = "O Position";
    static final String TURN = "Turn";

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED= 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;

    int REQUEST_ENABLE_BLUETOOTH = 1;

    private static final String APP_NAME = "xogames";
    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth_mode);
        findViewByIds();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled())
        {
            Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(btIntent,REQUEST_ENABLE_BLUETOOTH);
        }
        setListener();
        if (savedInstanceState != null) {
            Device_mode = savedInstanceState.getInt(DEV_MODE);
            if(Device_mode == 2)
            {
                Device_pos = savedInstanceState.getInt(DEV_POS);
                ClientClass clientClass = new ClientClass(btArray[Device_pos]);
                clientClass.start();
                status.setText(R.string.conting);

            }
            else if(Device_mode == 1) {
                ServerClass serverClass = new ServerClass();
                serverClass.start();
            }


        }
    }
    @Override

    protected void onSaveInstanceState(Bundle outState) {

// Save the scores.

        outState.putInt(DEV_POS, Device_pos);
        outState.putInt(DEV_MODE, Device_mode);
       // outState.putIntArray(X_POS, x);



        super.onSaveInstanceState(outState);

    }
    private void setListener() {
        listDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playernum = 2;
                score.setText(R.string.urx);
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                String[] strings = new String[pairedDevices.size()];
                btArray = new BluetoothDevice[pairedDevices.size()];
                int index = 0;
                Toast.makeText(getApplicationContext(),"Listing Available devices",Toast.LENGTH_LONG).show();
                Log.d("TEST", "SetListener READY");
                if(pairedDevices.size()>0) {

                    for(BluetoothDevice device : pairedDevices){
                        btArray[index] = device;
                        strings[index] = device.getName();
                        index++;
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, strings);
                    listview.setAdapter(arrayAdapter);

                }

            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playernum = 1;
                Device_mode = 1;
                score.setText(R.string.uro);
                ServerClass serverClass = new ServerClass();
                serverClass.start();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),String.valueOf(btArray[position]),Toast.LENGTH_LONG).show();
                Log.d("TEST", String.valueOf(btArray[position]));
                Device_pos = position ;
                ClientClass clientClass = new ClientClass(btArray[position]);
                clientClass.start();
                Device_mode = 2;
                status.setText(R.string.conting);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = String.valueOf(writeMsg.getText());
                sendRecieve.write(string.getBytes());
            }
        });
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "ready";
                sendRecieve.write(string.getBytes());
            }
        });
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "again";
                sendRecieve.write(string.getBytes());
                againfunc();
            }
        });
        grid00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock == 0) {playox(v,1);}
            }
        });
        grid01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock == 0) {playox(v,1);}
            }
        });
        grid02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock == 0) {playox(v,1);}
            }
        });
        grid10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock == 0) {playox(v,1);}
            }
        });
        grid11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock == 0) {playox(v,1);}
            }
        });
        grid12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock == 0) {playox(v,1);}
            }
        });
        grid20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock == 0) {playox(v,1);}
            }
        });
        grid21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock == 0) {playox(v,1);}
            }
        });
        grid22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock == 0) {playox(v,1);}
            }
        });
    }

    Handler handler = new Handler((new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case STATE_LISTENING:
                    status.setText(R.string.lising);
                    break;
                case STATE_CONNECTING:
                    status.setText(R.string.conting);
                    break;
                case STATE_CONNECTED:
                    status.setText(R.string.conted);
                    break;
                case STATE_CONNECTION_FAILED:
                    status.setText(R.string.confail);
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuff = (byte[])msg.obj;
                    String tmpMsg = new String(readBuff, 0, msg.arg1);
                    if(tmpMsg.equals("ready")&&playernum==1)
                    {lock = 0;}
                    else if(tmpMsg.equals("again"))
                    {againfunc();}
                    else  if(tmpMsg.equals("XO00"))
                    {playox(grid00,0);lock = 0;tmpMsg="";break;}
                    else  if(tmpMsg.equals("XO01"))
                    {playox(grid01,0);lock = 0;tmpMsg="";break;}
                    else  if(tmpMsg.equals("XO02"))
                    {playox(grid02,0);lock = 0;tmpMsg="";break;}
                    else  if(tmpMsg.equals("XO10"))
                    {playox(grid10,0);lock = 0;tmpMsg="";break;}
                    else  if(tmpMsg.equals("XO11"))
                    {playox(grid11,0);lock = 0;tmpMsg="";break;}
                    else  if(tmpMsg.equals("XO12"))
                    {playox(grid12,0);lock = 0;tmpMsg="";break;}
                    else  if(tmpMsg.equals("XO20"))
                    {playox(grid20,0);lock = 0;tmpMsg="";break;}
                    else  if(tmpMsg.equals("XO21"))
                    {playox(grid21,0);lock = 0;tmpMsg="";break;}
                    else  if(tmpMsg.equals("XO22"))
                    {playox(grid22,0);lock = 0;tmpMsg="";break;}
                    msg_box.setText(tmpMsg);
                    tmpMsg="";
                    break;

            }
            return true;
        }
    }));

    private void findViewByIds(){
        listen = findViewById(R.id.listen);
        send = findViewById(R.id.send);
        listDevices = findViewById(R.id.listDevices);
        listview = findViewById(R.id.listview);
        msg_box = findViewById(R.id.msg);
        status = findViewById(R.id.status);
        writeMsg = findViewById(R.id.writeMsg);
        ready = findViewById(R.id.ready);
        again = findViewById(R.id.againbt);
        score = findViewById(R.id.scoretxt);
        grid00 = findViewById(R.id.gridtxt00);
        grid01 = findViewById(R.id.gridtxt01);
        grid02 = findViewById(R.id.gridtxt02);
        grid10 = findViewById(R.id.gridtxt10);
        grid11 = findViewById(R.id.gridtxt11);
        grid12 = findViewById(R.id.gridtxt12);
        grid20 = findViewById(R.id.gridtxt20);
        grid21 = findViewById(R.id.gridtxt21);
        grid22 = findViewById(R.id.gridtxt22);
    }
    private class ServerClass extends Thread
    {
        private BluetoothServerSocket serverSocket;
        public ServerClass(){
            try {
                Log.d("TEST", "Server Socket");
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void run()
        {
            BluetoothSocket socket = null;
            while(socket == null)
            {
                try {
                    Log.d("TEST", "Server RUN");
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);
                    socket = serverSocket.accept();

                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }
                if(socket!=null)
                {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);

                    sendRecieve = new SendRecieve(socket);
                    sendRecieve.start();
                    break;
                }
            }
        }
    }
    private  class ClientClass extends  Thread
    {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1)
        {
            device = device1;
            try {
                Log.d("TEST", "Client Socket");
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                try {
                    Log.d("","trying fallback...");

                    socket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
                    socket.connect();

                    Log.d("","Connected");
                }
                catch (Exception e2) {
                    Log.e("", "Couldn't establish Bluetooth connection!");
                }

            }

        }
        public void run()
        {
            try {
                Log.d("TEST", "Client run");
                socket.connect();
                Log.d("TEST", "Client Connected");
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                handler.sendMessage(message);

                sendRecieve = new SendRecieve(socket);
                sendRecieve.start();

            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }

        }
    }
    private class SendRecieve extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;
        public SendRecieve(BluetoothSocket socket){
            bluetoothSocket = socket;
            InputStream tempIn = null;
            OutputStream tempout = null;

            try {
                tempIn = bluetoothSocket.getInputStream();
                tempout = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = tempIn;
            outputStream = tempout;

        }
        public void run()
        {
            byte[] buffer = new byte[1024];
            int bytes;

            while(true)
            {
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes,-1,buffer).sendToTarget();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void playox(View view,int s) {
            TextView p1txt = findViewById(R.id.onescoretxt);
            TextView p2txt = findViewById(R.id.bscoretxt);
            TextView textView = findViewById(view.getId());
            writeoxtxt(textView,s);
            if (stop == 0 && checkwino() == 1) {
                p1++;
                p1txt.setText("" + p1);
                stop = 1;
                displayToast("Player 1 WIN");
            }
            if (stop == 0 && checkwinx() == 1) {
                p2++;
                p2txt.setText("" + p2);
                stop = 1;
                displayToast("Player 2 WIN");
            }
            if (stop == 0 && count == 8) {
                stop = 1;
                displayToast("DRAW");
            }
    }

    public void writeoxtxt(TextView view, int s) {
        TextView turntxt = findViewById(R.id.turnox1txt);
        String string = "";
        if(stop == 0 && turn == 0 && view.getText() != "X" && view.getText() != "O") {
            count++;
            retextView[count] = findViewById(view.getId());
            view.setText("O");
            switch (view.getId()) {
                case R.id.gridtxt00: // Same day service
                    o[0][0]=0;
                    string = "XO00";
                    break;
                case R.id.gridtxt01: // Same day service
                    o[0][1]=0;
                    string = "XO01";
                    break;
                case R.id.gridtxt02: // Same day service
                    o[0][2]=0;
                    string = "XO02";
                    break;
                case R.id.gridtxt10: // Same day service
                    o[1][0]=0;
                    string = "XO10";
                    break;
                case R.id.gridtxt11: // Same day service
                    o[1][1]=0;
                    string = "XO11";
                    break;
                case R.id.gridtxt12: // Same day service
                    o[1][2]=0;
                    string = "XO12";
                    break;
                case R.id.gridtxt20: // Same day service
                    o[2][0]=0;
                    string = "XO20";
                    break;
                case R.id.gridtxt21: // Same day service
                    o[2][1]=0;
                    string = "XO21";
                    break;
                case R.id.gridtxt22: // Same day service
                    o[2][2]=0;
                    string = "XO22";
                    break;
                default:
                    // Do nothing.
                    break;
            }
            if(s==1)
            {sendRecieve.write(string.getBytes());}
            lock = 1;
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
                    string = "XO00";
                    break;
                case R.id.gridtxt01: // Same day service
                    x[0][1]=0;
                    string = "XO01";
                    break;
                case R.id.gridtxt02: // Same day service
                    x[0][2]=0;
                    string = "XO02";
                    break;
                case R.id.gridtxt10: // Same day service
                    x[1][0]=0;
                    string = "XO10";
                    break;
                case R.id.gridtxt11: // Same day service
                    x[1][1]=0;
                    string = "XO11";
                    break;
                case R.id.gridtxt12: // Same day service
                    x[1][2]=0;
                    string = "XO12";
                    break;
                case R.id.gridtxt20: // Same day service
                    x[2][0]=0;
                    string = "XO20";
                    break;
                case R.id.gridtxt21: // Same day service
                    x[2][1]=0;
                    string = "XO21";
                    break;
                case R.id.gridtxt22: // Same day service
                    x[2][2]=0;
                    string = "XO22";
                    break;
                default:
                    // Do nothing.
                    break;
            }
            if(s==1)
            {sendRecieve.write(string.getBytes());}
            lock = 1;
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

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    public void againfunc ()
    {
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
        if(playernum == 1)
        {lock = 0;turn = 0;}
        if(playernum == 2)
        {lock = 1;turn = 0;}
        stop = 0;
        count = -1;
    }
}
