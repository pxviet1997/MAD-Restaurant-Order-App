package com.example.vietpham.restaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static Menu menuList = new Menu();

    Button menu = null;
    Button mealStatus = null;
    Button requestAssist = null;
    Button checkOut = null;
    String tableMessage = "Thank you for calling an assistant.\n" +
            "Your assistant is coming please wait for a minute <3";
    String tableName = "TABLE 1";
    private static Socket s;
    private static ServerSocket ss;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static PrintWriter pw;
    private static String ip= "149.144.193.214";

    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> description = new ArrayList<String>();
    private ArrayList<String> category = new ArrayList<String>();
    private ArrayList<String> imgUrl = new ArrayList<String>();
    private ArrayList<Double> price = new ArrayList<Double>();
    private static int NUMBER_OF_MEALS;
    private static MyDBHandler myDBHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new RetrieveMenuTask().execute("https://api.myjson.com/bins/sea6o");
        myDBHandler = new MyDBHandler(this, null, null, 1);
        if (!myDBHandler.check()) {
            initMenu();
        }
        loadMenu();


        menu =  (Button) findViewById(R.id.menu);
        mealStatus = (Button) findViewById(R.id.mealStatus);
        requestAssist = (Button) findViewById(R.id.requestAssist);
        checkOut = (Button) findViewById(R.id.checkOut);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MealMenu.class);
                startActivity(intent);
            }
        });

        mealStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MealStatusActivity.class);
                startActivity(intent);
            }
        });

        //REQUESTASSISTANT
        onRequestAssistButton();

        //CHECKOUT

        onCheckOutButton();
    }

    public void loadMenu() {
        NUMBER_OF_MEALS = myDBHandler.loadHandler(id, name, description, category, imgUrl, price);

        //menuList = new Menu();
        ArrayList<Meal> meals = new ArrayList<Meal>();

        for (int i=0;i<NUMBER_OF_MEALS;i++) {
            meals.add(new Meal(id.get(i), name.get(i), description.get(i), category.get(i), imgUrl.get(i), price.get(i)));
            menuList.addToMenu(meals.get(i));
        }

        Log.d("meals:\n", menuList.menuList());
    }

    public void initMenu() {
        Meal meals[] = new Meal[14];
        meals[0] = new Meal("PI01", "BBQ Bonanza",
                "Classic pepperoni, premium beef, sliced leg ham, crispy bacon and mozzarella on a smokey BBQ sauce base. Garnished with fresh herbs.",
                "pizza", "https://d1btgctt6xberu.cloudfront.net/uploads/BBQ-Bonanza_Pizza.png", 20.95);
        meals[1] = new Meal("PI02", "Reef & Beef",
                "Premium beef, Cajun prawns, rasher bacon, Spanish onion, capsicum, lemon pepper, mozzarella on a pizza sauce base. Finished with hollandaise, fresh shallots and a lemon wedge",
                "pizza", "https://d1btgctt6xberu.cloudfront.net/uploads/Reef-_-Beef.png", 23.95);

        meals[2] = new Meal("PI03", "Vegan Satay Tofu",
                "Luscious satay marinated tofu, shallots, onion, red capsicum & vegan cheese on a light olive oil base. Finished with satay sauce, cucumber & carrot slaw & cashews.",
                "pizza", "https://d1btgctt6xberu.cloudfront.net/uploads/Satay_Chicken.png", 20.95);

        meals[3] = new Meal("PI04", "Karaage Chicken",
                "Japanese crumbed Karaage Chicken, Avocado, Onion, Diced Tomato and Mozzarella on a Sweet Soy Sauce base. Garnished with Shallots and finished with Kewpie Mayo, Sweet Soy Sauce and a Lemon wedge.",
                "pizza", "https://d1btgctt6xberu.cloudfront.net/uploads/Karaage_Chicken.png", 20.95);

        meals[4] = new Meal("PI05", "Supremo",
                "Mild pepperoni, sliced leg ham, fresh mushrooms, red onion, roasted capsicum, Kalamata olives, crushed garlic, mixed herbs, pizza sauce and mozzarella. Finished with a sprinkling of Parmesan and parsley.",
                "pizza", "https://d1btgctt6xberu.cloudfront.net/uploads/Supremo.png", 20.95);

        meals[5] = new Meal("PI06", "Vegan TABASCO Spiced Smashed Avo",
                "Smashed Avocado, Vegan Cheese, Charred Corn and Diced Tomato lightly seasoned with Garlic and Green Tabasco. Finished with Coriander and a Lime Wedge.",
                "pizza", "https://d1btgctt6xberu.cloudfront.net/uploads/Vegan_Tabasco_Avo.png", 20.95);

        meals[6] = new Meal("PA01", "Garlic Prawn Pasta",
                "Olive oil, a little tomato pasta sauce, fresh shallots, cherry tomatoes, garlic, herbs and prawns with house cooked linguine. Finished with fresh herbs, parmesan & a lemon wedge.",
                "pasta", "https://d1btgctt6xberu.cloudfront.net/uploads/Garlic_Prawn2.png.png", 14.95);

        meals[7] = new Meal("PA02", "Amatriciana Pasta",
                "Classic tomato pasta sauce with fresh diced tomato, onion, garlic, chilli flakes and bacon with house cooked linguine. Finished with fresh herbs & parmesan.",
                "pasta", "https://d1btgctt6xberu.cloudfront.net/uploads/Amatriciana.png", 14.95);

        meals[8] = new Meal("PA03", "Four Cheeses Pasta",
                "Mozzarella, bocconcini, parmesan, a hint of blue cheese, thickened cream and fresh shallots with house cooked linguine. Finished with fresh shallots.",
                "pasta", "https://d1btgctt6xberu.cloudfront.net/uploads/Four_Cheeses.png", 14.85);

        meals[9] = new Meal("PA04", "Bacon & Mushroom Carbonara Pasta",
                "Creamy mushroom and white wine sauce, fresh shallots, rasher bacon and house cooked linguini. Finished with fresh herbs and Parmesan.",
                "pasta", "https://d1btgctt6xberu.cloudfront.net/uploads/Bacon___Mushroom_Carbonara.png", 14.95);

        meals[10] = new Meal("PA05", "Pesto Chicken Pasta",
                "Tender chicken slices, basil pesto, Roma tomatoes, Spanish onion, garlic and house cooked linguini in a creamy white wine sauce. Garnished with cashews, Parmesan and fresh parsley.",
                "pasta", "https://d1btgctt6xberu.cloudfront.net/uploads/Pesto_Chicken.png", 14.95);

        meals[11] = new Meal("D01", "1.25L Coca Cola",
                "",
                "drinks", "https://d1btgctt6xberu.cloudfront.net/uploads/Coke_Classic_1.25L.png", 4.95);

        meals[12] = new Meal("D02", "450ml Coca Cola",
                "",
                "drinks", "https://d1btgctt6xberu.cloudfront.net/uploads/Coke_Classic_450ml.png", 3.5);

        meals[13] = new Meal("D03", "600ml Mt Franklin Still",
                "",
                "drinks", "https://d1btgctt6xberu.cloudfront.net/uploads/Mt_Frank_600ml.png", 3.5);

        for (Meal m: meals) {
            myDBHandler.addHandler(m);
        }

        Log.d("meals:\n", menuList.menuList());


    }

    class myTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                s = new Socket(ip,5000);
                pw = new PrintWriter(s.getOutputStream());
                pw.write(tableName);
                pw.flush();
                s.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void onCheckOutButton() {
        checkOut = (Button) findViewById(R.id.checkOut);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, CheckOutActivity.class);
                startActivity(intent);
            }
        });
    }


    public void onRequestAssistButton(){
        requestAssist = (Button)findViewById(R.id.requestAssist);
        requestAssist.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                        a_builder.setMessage("Do you want to call an Assistant ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String description = "Need Assistant";
                                        FirebaseDatabase.getInstance().getReference().child("restaurant").push()
                                                .setValue(new ChatMessage(description, tableName, "MANAGER"));
                                        Toast.makeText(getApplicationContext(), "Assistant is coming",Toast.LENGTH_LONG).show();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Alert !!!");
                        alert.show();
                    }
                }
        );
    }

    public static Menu getMenu() {
        return menuList;
    }



/*class RetrieveMenuTask extends AsyncTask<String, Void, Integer> {
        private static final int NUMBER_OF_MEALS = 14;
        private Exception exception;
        private Bitmap[] img = new Bitmap[NUMBER_OF_MEALS];
        private String[] id = new String[NUMBER_OF_MEALS];
        private String[] name = new String[NUMBER_OF_MEALS];
        private String[] description = new String[NUMBER_OF_MEALS];
        private String[] category = new String[NUMBER_OF_MEALS];
        private String[] imageUrl = new String[NUMBER_OF_MEALS];
        private double[] price = new double[NUMBER_OF_MEALS];
        //private String[] meal = new String[NUMBER_OF_MEALS];

        protected Integer doInBackground(String... urlStrs) {
            try {
                java.net.URL url = new java.net.URL(urlStrs[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                InputStream stream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray arr = jsonObject.getJSONArray("items");

                for (int i=0;i<NUMBER_OF_MEALS;i++) {
                    id[i] = arr.getJSONObject(i).get("id").toString();
                    name[i] = arr.getJSONObject(i).get("name").toString();
                    description[i] = arr.getJSONObject(i).get("description").toString();
                    category[i] = arr.getJSONObject(i).get("category").toString();
                    imageUrl[i] = arr.getJSONObject(i).get("imageUrl").toString();
                    getBitmapFromURL(arr.getJSONObject(i).get("imageUrl").toString(), i);
                    price[i] = Double.parseDouble(arr.getJSONObject(i).get("price").toString());
                }

                connection.disconnect();

                return new Integer(0);
            } catch (Exception e) {
                this.exception = e;
                return new Integer(-1);
            }
        }

        private void getBitmapFromURL(String src, int index) {
            try {
                java.net.URL url = new java.net.URL(src);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                img[index] = BitmapFactory.decodeStream(input);
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(Integer res) {
            menuList = new Menu();
            Meal[] meals = new Meal[NUMBER_OF_MEALS];

            for (int i=0;i<NUMBER_OF_MEALS;i++) {
                meals[i] = new Meal(id[i], name[i], description[i], category[i], imageUrl[i], price[i]);
                menuList.addToMenu(meals[i]);
            }

            Log.d("meals:\n", menuList.menuList());


        }

    }*/
}
