package com.example.wade.noname;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class PingLun extends Activity {

    List<Plun> clist;
    int ii=0;
    TextView Ptitle;
    TextView Ptalk;
    TextView Ptime;
    TextView Pnum;

    Button Psure;
    ListView Mlistview;
    SimpleAdapter simpleAdapter;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_lun);
        Ptalk=(TextView)findViewById(R.id.pltalk);
        Ptitle=(TextView)findViewById(R.id.pltitle);
        Ptime=(TextView)findViewById(R.id.pltalktime);
        Pnum=(TextView)findViewById(R.id.plnum1);
        Psure=(Button)findViewById(R.id.plbut);
        id=getIntent().getStringExtra("objectId");
        Ptitle.setText(getIntent().getStringExtra("title"));
        Ptalk.setText(getIntent().getStringExtra("talk"));
        Ptime.setText(getIntent().getStringExtra("time"));
        clist=chaxun();
        Psure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder diglog = new AlertDialog.Builder(PingLun.this);
                View view1 = LayoutInflater.from(PingLun.this).inflate(R.layout.message, null);
                final EditText mTitle = (EditText) view1.findViewById(R.id.mtitle);
                final EditText mTalk = (EditText) view1.findViewById(R.id.mtalk);
                final Button mSent = (Button) view1.findViewById(R.id.mbsent);
                final Button mExit = (Button) view1.findViewById(R.id.mbexit);
                mSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BmobSMS.initialize(PingLun.this, "ec548d492d998eec850068fc1b9ead11");
                        Plun user = new Plun();
                        user.setUsername(getIntent().getStringExtra("num"));
                        user.setPluntalk(mTalk.getText().toString());
                        user.setTalkid(getIntent().getStringExtra("objectId"));
                        user.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "评论失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                chaxun();
                            }
                        });
                    }
                });
                diglog.setView(view1);
                final AlertDialog di = diglog.show();

            }
        });
    }
    public List<Plun> chaxun()
    {

        BmobSMS.initialize(PingLun.this, "ec548d492d998eec850068fc1b9ead11");
        BmobQuery<Plun> query=new BmobQuery<Plun>();
        query.setLimit(50);
        query.order("-createdAt");
        query.addWhereEqualTo("talkid", getIntent().getStringExtra("objectId"));
       query.findObjects(new FindListener<Plun>() {
           @Override
           public void done(List<Plun> list, BmobException e) {
               if (e == null) {
                   List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
                   Toast.makeText(getApplicationContext(), "查询成功：共" + list.size() + "条数据。", Toast.LENGTH_LONG).show();
                   Pnum.setText("" + list.size());
                   for (Plun gameScore : list) {
                       Map<String, Object> listItem = new HashMap<String, Object>();
                       listItem.put("Object", gameScore.getObjectId());
                       listItem.put("username", gameScore.getUsername());
                       listItem.put("talkid", gameScore.getTalkid());
                       listItem.put("pluntalk", gameScore.getPluntalk());
                       listItem.put("time", gameScore.getCreatedAt());
                       listItems.add(listItem);

                   }
                   Mlistview = (ListView) findViewById(R.id.pllist);
                   simpleAdapter = new SimpleAdapter(PingLun.this, listItems, R.layout.pinglunlist,
                           new String[]{"pluntalk", "time"},
                           new int[]{R.id.pluntalk, R.id.pluntime});
                   Mlistview.setAdapter(simpleAdapter);
               } else {
                   Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
               }

           }
       });

        return clist;

    }
}
