package com.jjy.aidl.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jjy.aidl.serve.Book;
import com.jjy.aidl.serve.IBookManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "client";
    IBookManager iBookManager;
    private boolean connected=false;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text);
        bindService();
    }

    public void getClick(View v){
        textView.setText("");
        if (connected){
            try {
                for (Book book : iBookManager.getBookList()) {
               String name=     book.getName()+"\n";
                    textView.setText(textView.getText()+name);
                }

            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "getClick: "+e.getMessage());
            }
        }

    }
    public void addClick(View v){
        if (connected){
            try {
                iBookManager.addBook(new Book("Java核心技术卷II："));
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "addClick: "+e.getMessage() );
            }
        }

    }


    private ServiceConnection connectionService=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBookManager=IBookManager.Stub.asInterface(iBinder);
            connected=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            connected=false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected) {
            unbindService(connectionService);
        }
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.jjy.aidl.serve");
        intent.setAction("com.jjy.aidl.serve");
        bindService(intent, connectionService, Context.BIND_AUTO_CREATE);
    }


}
