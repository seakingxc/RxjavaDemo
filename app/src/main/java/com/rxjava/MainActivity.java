package com.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private List<Vo> dirPath = new ArrayList<Vo>() {{
        add(new Vo());
        add(new Vo());
        add(new Vo());
    }};


    class Vo {
        public List<String> filePath = new ArrayList<String>() {{
            add("123.image");
            add("1235.image");
            add("1237.image");
            add("123gvdf.image");
            add("1233.image");
            add("123k.image");
            add("123xuchun.image");

        }};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        findViewById(R.id.textview).postDelayed(new Runnable() {
            @Override
            public void run() {
//                findFile();
                findFileByRxJava();
            }
        }, 2000);


    }


    private void findFile() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (Vo vos : dirPath) {
                    for (final String filePath : vos.filePath) {
                        if (filePath.contains("xuchun")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, filePath, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }
            }
        }.start();

    }

    private void findFileByRxJava() {
        Observable.from(dirPath)
                .flatMap(new Func1<Vo, Observable<String>>() {
                    @Override
                    public Observable<String> call(Vo vo) {
                        return Observable.from(vo.filePath);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.contains("xuchun");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
