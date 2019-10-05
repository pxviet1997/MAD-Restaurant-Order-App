package com.example.vietpham.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vietpham.restaurant.Config.Config;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.text.DecimalFormat;
import java.util.Calendar;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class CheckOutActivity extends AppCompatActivity {

    ArrayList<Meal> orderListMenu;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Config.PAYPAL_CLIENT_ID);
    Button btnPayPaypal, btnPayCash;
    TextView nameView, quantityView, priceView, totalPriceView, restaurantInfo, timeInfo;
    String nameS, quantityS, priceS, totalPriceS;
    String billPayByCash="";
    String tableName = "TABLE 1";
    double amount = 0;

    @Override
    protected void onDestroy(){
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        btnPayPaypal = (Button) findViewById(R.id.btnPayPaypal);
        btnPayCash = (Button) findViewById(R.id.btnPayCash);
        nameView = (TextView) findViewById(R.id.nameView);
        quantityView = (TextView) findViewById(R.id.quantityView);
        priceView = (TextView) findViewById(R.id.priceView);
        totalPriceView = (TextView) findViewById(R.id.totalPriceView);
        restaurantInfo = (TextView) findViewById(R.id.restaurantInfo);
        timeInfo = (TextView) findViewById(R.id.timeInfo);

        restaurantInfo.setText("Thank You For Eating At Our Restaurant\n Have a nice day\n See you soon!");
        Date currentTime = Calendar.getInstance().getTime();
        timeInfo.setText(currentTime+"\nYOUR BILL");
        nameS="NAME\n";
        quantityS="QUANTITY\n";
        priceS="PRICE\n";
        totalPriceS="TOTAL\n";
        if (OrderedListActivity.getCheck() == 1) {
            orderListMenu = OrderedListActivity.getTempList();
        }
        else orderListMenu = new ArrayList<>();
        billPayByCash+=tableName + " WANT TO PAY A BILL" + "\nTHE BILL OF "+ tableName;
        billPayByCash+="\n"+ String.format("%1$-20s\t\t%2$-20s\t\t%3$-20s\t\t%4$-20s","Name","Quantity","Price","Total Price");
        for (Meal item: orderListMenu)
        {
            double totalItemPrice = item.getQuantity() * Double.valueOf(item.getPrice());
            nameS+=item.getName()+"\n";
            quantityS+=item.getQuantity()+"\n";
            priceS+=item.getPrice()+"\n";
            totalPriceS+=df2.format(totalItemPrice)+"\n";
            amount+=totalItemPrice;
            billPayByCash+="\n"+ String.format("%1$-20s\t\t%2$-20s\t\t%3$-20s\t\t%4$-20s",item.getName(),item.getQuantity(),item.getPrice(),df2.format(totalItemPrice));
        }
        nameS += "TOTAL BILL";
        totalPriceS += df2.format(amount);
        nameView.setText(nameS);
        quantityView.setText(quantityS);
        priceView.setText(priceS);
        totalPriceView.setText(totalPriceS);
        billPayByCash+="\n" + String.format("%1$-20s\t\t%2$-100s","TOTAL BILL",df2.format(amount));
        btnPayPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();
            }
        });

        if (amount != 0)
            btnPayCash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().child("restaurant").push()
                            .setValue(new ChatMessage(billPayByCash, tableName, "MANAGER"));
                    Toast.makeText(CheckOutActivity.this, "Thank you for eating here\nStaff is coming to give your receipt", Toast.LENGTH_SHORT).show();
                    OrderedListActivity.resetTemp();
                    Intent intentMenu = new Intent(CheckOutActivity.this, MainActivity.class);
                    startActivity(intentMenu);
                }
            });
    }

    private void processPayment(){
        OrderedListActivity.resetTemp();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "AUD", "Pay for your meal", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PAYPAL_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null)
                {
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent (this, PaymentDetails.class).putExtra("PaymentDetails", paymentDetails).putExtra("PaymentAmount", String.valueOf(amount)));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
            else if(resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel",Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
        {
            Toast.makeText(this,"Invalid", Toast.LENGTH_SHORT).show();
        }
    }
}
