package com.example.jspark.prodongnebaseball;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jspark on 2017-11-09.
 */

public class Sign_Up_Activity extends MainActivity {

    String data = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        final EditText id_editText = (EditText) findViewById(R.id.Input_id);
        final EditText pw_editText = (EditText) findViewById(R.id.Input_pw);
        final EditText email_editText = (EditText) findViewById(R.id.Input_email);




        Button Ok_btn = (Button) findViewById(R.id.signUp_Ok);
        Button Cancle_btn = (Button) findViewById(R.id.signUp_Cancle);

        Ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Group_Add_Dialog = new AlertDialog.Builder(Sign_Up_Activity.this);
                Group_Add_Dialog.setTitle("회원가입");

                if (id_editText.getText().toString().equals("")) {

                    Toast.makeText(Sign_Up_Activity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (pw_editText.getText().toString().equals("")) {
                    Toast.makeText(Sign_Up_Activity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();

                } else try {

                } catch (NullPointerException e){


                }
                registDB registDB = new registDB();
                registDB.execute();

            }
        });
        //취소버튼
        Cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //엔터 클릭시 다음 에디트텍스트로 넘어가게 만듬
        id_editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    pw_editText.requestFocus();
                    pw_editText.setCursorVisible(true);
                    return true;
                }
                return false;
            }
        });
        pw_editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    email_editText.requestFocus();
                    email_editText.setCursorVisible(true);
                    return true;
                }
                return false;
            }
        });
        email_editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    id_editText.requestFocus();
                    id_editText.setCursorVisible(true);
                    return true;
                }
                return false;
            }
        });//에딧텍스트 역할 끝

    }


    public class registDB extends AsyncTask<Void, Integer, Void> {


        EditText id_editText = (EditText) findViewById(R.id.Input_id);
        EditText pw_editText = (EditText) findViewById(R.id.Input_pw);
        EditText email_editText = (EditText) findViewById(R.id.Input_email);





        @Override
        protected Void doInBackground(Void... unused) {


            String sId = id_editText.getText().toString();
            String sPw = pw_editText.getText().toString();
            String sEmail = email_editText.getText().toString();

/* 인풋 파라메터값 생성 */
            String param = "u_id=" + sId + "&u_password=" + sPw + "" + "&u_email=" + sEmail;
            try {

/* 서버연결 */
                URL url = new URL(
                        "http://himerd0416.vps.phps.kr/PDB/SignUp.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

/* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

/* 서버 -> 안드로이드 파라메터값 전달 */
                InputStream is = null;
                BufferedReader in = null;


                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();

                while ((line = in.readLine()) != null) {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();
                Log.e("RECV DATA", data);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Sign_Up_Activity.this);

            if (data.equals("1")) {
                Log.e("RESULT", "성공적으로 처리되었습니다!");
                alertBuilder
                        .setTitle("알림")
                        .setMessage("성공적으로 로그인되었습니다")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Sign_Up_Activity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            } else if (data.equals("0")) {
                Log.e("RESULT", "아이디 또는 비밀번호가 일치하지 않습니다.");
                alertBuilder
                        .setTitle("알림")
                        .setMessage("아이디 또는 비밀번호가 일치하지 않습니다.")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            } else {
                Log.e("RESULT", "에러 발생! ERRCODE = " + data);
                alertBuilder
                        .setTitle("알림")
                        .setMessage("" + data)
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }

        }
    }


}

