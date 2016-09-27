package com.tatva.tatvaadventure.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tatva.tatvaadventure.utils.Employee;
import com.tatva.tatvaadventure.R;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {
            JSONObject json = new JSONObject();
            json.put("id", 2);
            json.put("firstName", "hello");
            json.put("lastName", "world");

            byte[] jsonData = json.toString().getBytes();

            ObjectMapper mapper = new ObjectMapper();
            Employee employee = mapper.readValue(jsonData,
                    Employee.class);
            System.out.println(employee.id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
