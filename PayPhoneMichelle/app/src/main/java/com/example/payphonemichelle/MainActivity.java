package com.example.payphonemichelle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView txtDNI, txtTelefono;
    private Button btnEnviarT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDNI = findViewById(R.id.txtDNI);
        txtTelefono = findViewById(R.id.txtTelefono);
        btnEnviarT=findViewById(R.id.btnEnviarT);

        btnEnviarT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cobroPayPhone();
            }
        });
    }

    public void cobroPayPhone(){

        String url = "https://pay.payphonetodoesposible.com/api/Sale";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject dataPay = new JSONObject();
        try {
            dataPay.put("phoneNumber", txtTelefono.getText().toString());
            dataPay.put("countryCode", "593");
            dataPay.put("clientUserId", txtDNI.getText().toString());
            dataPay.put("reference", "none");
            dataPay.put("responseUrl", "http://paystoreCZ.com/confirm.php");
            dataPay.put("amount", "4500");
            dataPay.put("amountWithTax", "4000");
            dataPay.put("amountWithoutTax", "0");
            dataPay.put("tax", "500");
            dataPay.put("clientTransactionId", "040345");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, dataPay, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                error.printStackTrace();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Accept","application/json");
                headers.put("Authorization", "Bearer KOVHdDKdeuyjQoKUk5KzCO82BjiWq3o_-oe-lAKFMLEmc13G7Snw0URgeqylUtCKf0Nnve1e4eH5Y56J2NlJzpRwZshH2B9qYpIOgtzmvbBWi3q0XK9ZXMoz1RYu5oTetasnzs0yvyhmjpVWgpKwYIWIAooz3T-YLaZGrvPSWJrNlZryXq6WK3vKmU1Rb29mjpXVbjLNGnjevv-aEg0XfAArufW1SgPyY_1_JW0K4zoU7BqaOfH6Ilt4Tag73dhbgncJ2YPuB9uIwLVerEQPMB2gPz_hB1OiWUPTXeToVxLC-OhrcgF6c9MmpOxhMHpl2l3v3jjMkgw6z88uXz-A32nhyLM" );
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}