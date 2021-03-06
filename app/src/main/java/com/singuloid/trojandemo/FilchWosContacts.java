package com.singuloid.trojandemo;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.singuloid.trojandemo.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by administrator on 14-8-31.
 */
public class FilchWosContacts extends Activity {
    private static final String TAG = "FilchWosContacts";

    private ArrayList<HashMap<String, Object>> mContactsMsg;
    private ListView mListView = null;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcontainer);
        mListView = (ListView) findViewById(R.id.list_container);
        try {
            items = getContactMsg();
            mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        } catch (Exception e) {
            Utils.dialog(this, this, "请检查是否安装了WorkPhone！");
        }
    }

    public String[] getContactMsg() throws UnsupportedEncodingException {

        final String WOS_URI = "content://com.singuloid.workphone.apps.contacts/";
        Uri contentUri = Uri.parse(WOS_URI + "contacts");
        Cursor cursor = getContentResolver().query(contentUri,
                null, null, null, null);
        try {
            int contactIdIndex = 0;
            int nameIndex = 0;
            if (cursor.getCount() > 0) {
                contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            }
            String[] result = new String[cursor.getCount()];
            int k = 0;
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(contactIdIndex);
                String name = cursor.getString(nameIndex);
                byte[] temp = name.getBytes("utf-8");
                String luanmaName = new String(temp, "gbk");
                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append("Name :" + luanmaName + "\n");
                msgBuilder.append("Number :" + Utils.getFakeNumber(11));
                result[k++] = msgBuilder.toString();

            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
