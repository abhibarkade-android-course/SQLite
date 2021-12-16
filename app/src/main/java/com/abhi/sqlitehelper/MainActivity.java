package com.abhi.sqlitehelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText ed_name, ed_phone;
    Button btn_add;
    LinearLayout root;
    List<CustomUserData> list;
    DB_Helper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_name = findViewById(R.id.ed_name);
        ed_phone = findViewById(R.id.ed_phone);

        btn_add = findViewById(R.id.btn_add);

        root = findViewById(R.id.root);

        list = new ArrayList<>();

        dbHelper = new DB_Helper(this);

        setUsersData();
    }

    public void addUser(View view) {
        String name = ed_name.getText().toString();
        String phone = ed_phone.getText().toString();

        if (name.length() > 0 && phone.length() > 0) {
            CustomUserData userData = new CustomUserData(name, phone);
            dbHelper.addUser(userData);
            setUsersData();
            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show();
    }

    public void setUsersData() {
        try {
            if (root.getChildCount() > 0)
                root.removeAllViews();
            if (list.size() > 0)
                list.removeAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        list = dbHelper.getUsersData();
        for (int i = 0; i < list.size(); i++) {
            CustomUserData userData = list.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.custom_layout_for_item, null);

            TextView tx_name = view.findViewById(R.id.tx_name);
            TextView tx_phone = view.findViewById(R.id.tx_phone);

            tx_name.setText(userData.getName());
            tx_phone.setText(userData.getPhone());

            ImageView im_delete = view.findViewById(R.id.im_delete);
            ImageView im_edit = view.findViewById(R.id.im_edit);

            im_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.deleteUser(userData.getName());
                    setUsersData();
                    Toast.makeText(MainActivity.this, "User Removed", Toast.LENGTH_SHORT).show();
                }
            });

            im_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_add.setVisibility(View.GONE);
                    Button btn_up = findViewById(R.id.btn_update);
                    btn_up.setVisibility(View.VISIBLE);

                    ed_name.setText(userData.getName());
                    ed_phone.setText(userData.getPhone());

                    btn_up.setOnClickListener(v1 -> {
                        String name = ed_name.getText().toString();
                        String phone = ed_phone.getText().toString();

                        if (name.length() > 0 && phone.length() > 0) {
                            CustomUserData userData1 = new CustomUserData(name, phone);
                            dbHelper.updateUser(userData.getName(), userData1);
                            setUsersData();
                            Toast.makeText(MainActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
                            btn_up.setVisibility(View.GONE);
                            btn_add.setVisibility(View.VISIBLE);
                        } else
                            Toast.makeText(MainActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                    });
                }
            });

            root.addView(view);
        }
    }
}















