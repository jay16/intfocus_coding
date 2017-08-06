package com.jameswong.test1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hjjzz";
    /**
     * 保存单行数据
     */
    private List<String> mFormattedItemData = null;
    private List<List<String>> mFormattedData = null;
    private SubDataBean mItemBean;
    /**
     * 保存预期 json 数据的对象
     */
    private List<SubDataBean> mExpectedListData;
    private List<SubDataBean> mSubDataBeen;

    private String replaceString = "\"";
    private String dataPath = "data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvShowJson = (TextView) findViewById(R.id.tv_show_json);

        getFormattedData();

        getDraftExpectedData();

        String json = getExpectedData();

        tvShowJson.setText(json);
        Log.i(TAG, "预期 json 数据:::" + json);
    }

    private String getExpectedData() {
        for (int i = 0; i < mFormattedData.size(); i++) {
            for (int k = 0; k < mFormattedData.size(); k++) {
                if (mFormattedData.get(k).get(0).equals(mFormattedData.get(i).get(1))) {
                    if (mExpectedListData.get(k).getSub_data().size() > 0) {
                        mExpectedListData.get(k).getSub_data().add(mExpectedListData.get(i));
                    } else {
                        mSubDataBeen = new ArrayList<>();
                        mSubDataBeen.add(mExpectedListData.get(i));
                        mExpectedListData.get(k).setSub_data(mSubDataBeen);
                    }
                    break;
                }
            }
        }
        for (int i = mExpectedListData.size() - 1; i >= 0; i--) {
            if (!"".equals(mFormattedData.get(i).get(1))) {
                mExpectedListData.remove(i);
            }
        }
        return new Gson().toJson(mExpectedListData);
    }

    private void getDraftExpectedData() {
        mExpectedListData = new ArrayList<>();
        ArrayList<String> mMainData;
        for (int i = 0; i < mFormattedData.size(); i++) {

            mItemBean = new SubDataBean();
            mMainData = new ArrayList<>();

            for (int j = 0; j < mFormattedData.get(i).size() - 2; j++) {
                mMainData.add(mFormattedData.get(i).get(j + 2));
            }

            mItemBean.setMain_data(mMainData);
            mItemBean.setSub_data(new ArrayList<SubDataBean>());
            mExpectedListData.add(mItemBean);
        }
    }

    /**
     * 将读取到的字符串转为 List<List<String>> 格式数据
     */
    private void getFormattedData() {

        mFormattedData = new ArrayList<>();
        String[] draftData = getStringFromAssets().replace(" ", "").split("\\],");
        String[] itemData = null;

        for (int i = 0; i < draftData.length; i++) {

            itemData = draftData[i].split("\\,");
            mFormattedItemData = new ArrayList<>();

            for (int j = 0; j < itemData.length; j++) {

                itemData[j] = itemData[j].replace("[", "").replace("]", "").replace(replaceString, "");
                mFormattedItemData.add(itemData[j]);

                Log.i(TAG, "String[" + i + "][" + j + "]:::" + itemData[j]);
            }
            mFormattedData.add(mFormattedItemData);
        }
        Log.i(TAG, mFormattedData.toString());
    }

    /**
     * IO 读取数据
     *
     * @return
     */
    private String getStringFromAssets() {

        InputStream inputStream = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        StringBuilder mBuilder = null;

        try {

            inputStream = getAssets().open(dataPath);
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);
            mBuilder = new StringBuilder();
            String read = null;

            while ((read = bufferedReader.readLine()) != null) {
                mBuilder.append(read);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mBuilder.toString();
    }
}
class SubDataBean {

    private List<String> main_data;
    private List<SubDataBean> sub_data;

    public List<String> getMain_data() {
        return main_data;
    }

    public void setMain_data(List<String> main_data) {
        this.main_data = main_data;
    }

    public List<SubDataBean> getSub_data() {
        return sub_data;
    }

    public void setSub_data(List<SubDataBean> sub_data) {
        this.sub_data = sub_data;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
