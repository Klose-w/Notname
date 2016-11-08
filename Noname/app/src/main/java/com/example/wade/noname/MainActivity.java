package com.example.wade.noname;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity {

    int ii;
    public int kk=0;
    List<talk> clist;
    String Num;
    Button Addtalk;
    ListView Mlistview;
    SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mlistview=(ListView)findViewById(R.id.meslistview);
        Num=getIntent().getStringExtra("num");
        Addtalk=(Button)findViewById(R.id.addtalk);
        Mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,PingLun.class);
                intent.putExtra("objectId",clist.get(position).getObjectId().toString());
                intent.putExtra("title",clist.get(position).getTitle().toString());
                intent.putExtra("username", clist.get(position).getUsername().toString());
                intent.putExtra("talk", clist.get(position).getTalk().toString());
                intent.putExtra("time", clist.get(position).getCreatedAt().toString());
                intent.putExtra("plnum", "0");
                intent.putExtra("num",Num);
                startActivity(intent);
            }
        });
        Addtalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder diglog = new AlertDialog.Builder(MainActivity.this);
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.message, null);
                final EditText mTitle = (EditText) view1.findViewById(R.id.mtitle);
                final EditText mTalk = (EditText) view1.findViewById(R.id.mtalk);
                final Button mSent = (Button) view1.findViewById(R.id.mbsent);
                final Button mExit = (Button) view1.findViewById(R.id.mbexit);
                mSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        talk user = new talk();
                        user.setUsername(Num);
                        user.setTalk(mTalk.getText().toString());
                        user.setTitle(mTitle.getText().toString());
                        user.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "发布失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                chaxun();
                                ;
                            }
                        });
                    }
                });
                diglog.setView(view1);
                final AlertDialog di = diglog.show();

            }
        });
        chaxun();
    }
    public void chaxun()
    {
        BmobSMS.initialize(MainActivity.this, "ec548d492d998eec850068fc1b9ead11");
        BmobQuery<talk> query=new BmobQuery<talk>();
        query.setLimit(50);
        query.order("-createdAt");
        query.findObjects(new FindListener<talk>() {
            @Override
            public void done(List<talk> list, BmobException e) {
                ii = list.size();
                clist = list;
                List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
                for (talk ta : list) {


                    Map<String, Object> listItem = new HashMap<String, Object>();
                    listItem.put("Object", ta.getObjectId().toString());
                    BmobQuery<Plun> query1=new BmobQuery<Plun>();
                    query1.setLimit(50);
                    query1.order("-createdAt");
                    query1.addWhereEqualTo("talkid", ta.getObjectId().toString());
                    query1.findObjects(new FindListener<Plun>() {
                        @Override
                        public void done(List<Plun> list1, BmobException e) {
                            kk = list1.size();
                        }
                    });
                    listItem.put("username", ta.getUsername().toString());
                    listItem.put("title", ta.getTitle().toString());
                    listItem.put("talk", ta.getTalk().toString());
                    listItem.put("time", ta.getCreatedAt().toString());
                    listItem.put("plnum", kk);
                    listItems.add(listItem);

                }
                simpleAdapter = new SimpleAdapter(MainActivity.this, listItems, R.layout.talklist,
                        new String[]{"title", "talk", "time", "plnum"},
                        new int[]{R.id.title, R.id.talk, R.id.talktime, R.id.plnum});
                Mlistview.setAdapter(simpleAdapter);
                Toast.makeText(getApplicationContext(), "" + list.size(), Toast.LENGTH_LONG);
            }
        });


    }

}
