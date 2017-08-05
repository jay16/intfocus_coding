package com.yonghui.yhchart.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.yonghui.yhchart.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CANC on 2017/8/4.
 */

public class TranslateDataActivity extends Activity {

    private Context mContent;
    private String originString;
    private TextView editResult;
    private Gson gson;
    private List<Data> datas = new ArrayList<>();
    private List<Data> resultDatas = new ArrayList<>();


    class Data {
        public String id;
        public boolean isChild;
        public String fatherId;
        public List<String> main_data;
        public Data sub_data;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        mContent = this;
        gson = new Gson();
        editResult = (EditText) findViewById(R.id.edit_result);
        findViewById(R.id.tv_translate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translate();
            }
        });
    }

    public void translate() {
        datas.clear();
        resultDatas.clear();
        originString = getStringFromFile(mContent, "data.json");
        JsonArray jsonArray = new JsonParser().parse(originString).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonArray jsonArray1 = new JsonParser().parse(jsonArray.get(i).toString()).getAsJsonArray();
            Data data = new Data();
            data.id = replaceSymbol(jsonArray1.get(0).toString());
            data.fatherId = replaceSymbol(jsonArray1.get(1).toString());
            List<String> mainDatas = new ArrayList<>();
            for (int j = 2; j < jsonArray1.size(); j++) {
                mainDatas.add(replaceSymbol(jsonArray1.get(j).toString()));
            }
            data.main_data = mainDatas;
            datas.add(data);
        }

        /**
         *
         */
        for (int i = 0; i < datas.size(); i++) {
            if (!TextUtils.isEmpty(datas.get(i).fatherId)) {
                for (int j = 0; j < datas.size(); j++) {
                    if (datas.get(i).fatherId.equals(datas.get(j).id)) {
                        datas.get(j).sub_data = datas.get(i);
                        datas.get(i).isChild = true;
                        break;
                    }
                }
            }

        }
        for (Data data : datas) {
            if (!data.isChild) {
                resultDatas.add(data);
            }
        }

        editResult.setText("originSize:" + datas.size()
                + "\nresultSize:" + resultDatas.size()
                + "\n" + gson.toJson(resultDatas).toString());
    }

    /**
     * 去除引号
     *
     * @param str
     * @return
     */
    private String replaceSymbol(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.contains("\"")) {
                str = str.replace("\"", "");
                replaceSymbol(str);
            }
        }
        return str;
    }

    /**
     * @param mContext
     * @param fileName
     * @return
     */
    private String getStringFromFile(Context mContext, String fileName) {
        String newString = "";
        try {
            InputStreamReader isr = new InputStreamReader(mContext.getAssets().open(fileName), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
            newString = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newString;
    }
}
