package com.example.vietpham.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    TextView textId, textAmount, textStatus;
    Button goToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        textId = (TextView)findViewById(R.id.textId);
        textAmount = (TextView)findViewById(R.id.textAmount);
        textStatus = (TextView)findViewById(R.id.textStatus);
        Intent intent = getIntent();

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));

        } catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount){
        try{
            textId.setText(response.getString("id"));
            textStatus.setText(response.getString("state"));
            textAmount.setText("$"+paymentAmount);
            if (textStatus.getText().equals("approved"))
            {
                goToMenu = (Button)findViewById(R.id.goToMenu);
                goToMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentMenu = new Intent(PaymentDetails.this, MainActivity.class);
                        startActivity(intentMenu);
                    }
                });
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
