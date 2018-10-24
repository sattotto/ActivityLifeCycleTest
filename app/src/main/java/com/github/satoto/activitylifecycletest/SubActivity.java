package com.github.satoto.activitylifecycletest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // 送られてきているintentの取得
        Intent intent = getIntent();
        if(intent != null){
            // ---- 3. 独自インテントによる文字列ポップアップ表示 ----
            // getStringExtraで送られてきたintentの文字列を取得
            String str = intent.getStringExtra("satoto");
            LogPrint(str);
            // 受け取った文字列をToastで表示
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }

        // 設置したTextFieldを取得
        final EditText URIText = (EditText)findViewById(R.id.URITextField);

        // 設置したbuttonを取得
        Button btn = (Button)findViewById(R.id.intentButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextFieldに入力した文字列の取得
                String URI = URIText.getText().toString();
                if (URI != "http://") { // 初期値がhttps://なのでその場合は処理をしない
                    // 入力された文字列をURIに変換して暗黙的intentを作成
                    Intent URIIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI));
                    // intentを送信
                    startActivity(URIIntent);
                }
            }
        });
    }

    private void LogPrint(String msg) {
        Log.d("ActivityLifecycleTest", msg);
    }
}