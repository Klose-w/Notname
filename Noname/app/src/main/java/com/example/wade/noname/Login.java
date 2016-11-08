package com.example.wade.noname;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.sms.listener.RequestSMSCodeListener;

import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;


public class Login extends Activity {

    EditText uname;
    EditText upassword;
    Button uload;
    Button uzhuce;
    EditText unum;
    Button uyanzhen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BmobConfig config=new BmobConfig.Builder(this).setApplicationId("ec548d492d998eec850068fc1b9ead11").build();
        Bmob.initialize(config);
        uname=(EditText)findViewById(R.id.name);
        upassword=(EditText)findViewById(R.id.password);
        unum=(EditText)findViewById(R.id.num);
        uload=(Button)findViewById(R.id.load);
        uzhuce=(Button)findViewById(R.id.zhuce);
        uyanzhen=(Button)findViewById(R.id.yanzhen);
        uzhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upassword.length()>6) {
                    BmobSMS.initialize(Login.this, "ec548d492d998eec850068fc1b9ead11");
                    BmobSMS.verifySmsCode(Login.this, uname.getText().toString(), unum.getText().toString(), new VerifySMSCodeListener() {
                        @Override
                        public void done(cn.bmob.sms.exception.BmobException e) {
                            if (e == null) {
                                Users user = new Users();
                                user.setUsername(uname.getText().toString());
                                user.setUserpassword(upassword.getText().toString());
                                user.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                if(e==null)
                                 {
                                        Toast.makeText(getApplicationContext(),"注册成功"+s,Toast.LENGTH_LONG).show();
                                }
                                else {
                                     Toast.makeText(getApplicationContext(),"添加失败"+e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                 }
                                 });
                                //Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "注册失败:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "密码格式不规范", Toast.LENGTH_LONG).show();
                }
            }
        });
        uyanzhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobSMS.initialize(Login.this, "ec548d492d998eec850068fc1b9ead11");
                if(uname.getText().length()==11) {
                    BmobSMS.requestSMSCode(Login.this, uname.getText().toString(), "sentmes", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
                            if (e == null) {//验证码发送成功
                                Log.i("bmob", "短信id：" + integer);//用于查询本次短信发送详情
                            } else {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "请输入有效的11位电话号码", Toast.LENGTH_LONG).show();
                }
            }
        });
        uload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Users> bmobQuery=new BmobQuery<Users>();
                bmobQuery.addWhereEqualTo("username",uname.getText().toString());
                bmobQuery.findObjects(new FindListener<Users>() {
                    @Override
                    public void done(List<Users> list, BmobException e) {
                        if(e==null){
                           if(upassword.getText().toString().equals(list.get(0).getUserpassword().toString()))
                           {
                               Intent intent=new Intent(Login.this,MainActivity.class);
                               intent.putExtra("num",uname.getText().toString());
                               startActivity(intent);
                               finish();
                              // Toast.makeText(getApplicationContext() ,"登陆成功",Toast.LENGTH_LONG).show();
                           }

                            }else{
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                    }
                });
//                bmobQuery.getObject(this, new QueryListener<Users>() {
//                        @Override
//                        public void done(Users users, BmobException e) {
//                            if(e==null){
//                                Toast.makeText(getApplicationContext(),users.getUserpassword(),Toast.LENGTH_LONG).show();
//                            }else{
//                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//                            }
//
//                        }
//                });
            }
        });

    }
}
