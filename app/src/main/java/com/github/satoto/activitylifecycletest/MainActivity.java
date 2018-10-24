package com.github.satoto.activitylifecycletest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogPrint("onCreate");

        // 設置したbuttonを取得
        Button btn = (Button)findViewById(R.id.moveSubActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ---- 1. 明示的インテントによるサブアクティビティの起動  ----
                // 新しいintentを作成
                Intent intent = new Intent();
                //intent.setClassName("com.github.satoto.activitylifecycletest.MainActivity", "com.github.satoto.activitylifecycletest.SubActivity");
                //intent.setClass(MainActivity.this, SubActivity.class);

                // ---- 3. 独自インテントによる文字列ポップアップ表示 ----
                // 独自intent(com.github.satoto.ACTION_SATOT)に文字列(! SATOTO !)を乗せて送信
                intent.setAction("com.github.satoto.ACTION_SATOTO");
                intent.putExtra("satoto", "! SATOTO !");

                // ---- 1. 明示的インテントによるサブアクティビティの起動  ----
                // ---- 3. 独自インテントによる文字列ポップアップ表示 ----
                // intentの送信
                startActivity(intent);

            }
        });
    }

    // ---- 4. システムからのブロードキャスト受信によるバッテリー状態通知 ----
    // BradCastレシーバの作成
    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        private int level; // 電池残量

        // ブロードキャストを受信した際に呼ばれる
        @Override
        public void onReceive(Context context, Intent intent) {
            LogPrint("---- " + intent.getAction() + " ----");
            // 受け取ったActionがバッテリー状態が変化に対するものなのか
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                // 電池残量の取得
                level = intent.getIntExtra("level", 0);
                // Toastを使用して電池残量の表示
                Toast.makeText(context, String.valueOf(level) + "%", Toast.LENGTH_LONG).show();
                LogPrint("onReceive");
            }
        }
    };

    @Override
    protected  void onStart() {
        LogPrint("onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        LogPrint("onResume");
        super.onResume();

        // ---- 4. システムからのブロードキャスト受信によるバッテリー状態通知 ----
        // intentフィルターで受け取るintentの種類を指定
        IntentFilter filter = new IntentFilter();
        // ブロードキャストレシーバで電池状態の変化を受け取るように登録
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver,filter);
    }

    @Override
    protected void onPause() {
        LogPrint("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        LogPrint("onStop");
        super.onStop();
        // ---- 4. システムからのブロードキャスト受信によるバッテリー状態通知 ----
        // 画面が表示されていないときにブロードキャストレシーバーが動かないように登録の解除
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onRestart() {
        LogPrint("onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        LogPrint("onDestroy");
        super.onDestroy();
    }

    private void LogPrint(String msg) {
        Log.d("ActivityLifecycleTest", msg);
    }
}
