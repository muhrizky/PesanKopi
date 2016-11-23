package com.example.muhammadrizqi.pesankopi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
implements View.OnClickListener{

    Button about,order;
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        about = (Button) findViewById(R.id.AboutBtn);
        order = (Button) findViewById(R.id.ordetbtn);
        /**
         * This method is called when the order button is clicked.
         */
    about.setOnClickListener(this); }

    public void submitOrder(View view) {
        displayQuantity(quantity);
        int priceValue;
        CheckBox checkWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox checkChocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        Boolean hasWhippedCream = checkWhippedCream.isChecked();
        Boolean hasChocolate = checkChocolate.isChecked();

        priceValue = calculatePrice(hasWhippedCream, hasChocolate);

        final EditText nameEditText = (EditText)findViewById(R.id.name_edit_text);
        final EditText alamatEditText = (EditText) findViewById(R.id.name_edit_text2);
        final EditText nohpEditText = (EditText) findViewById(R.id.name_edit_text3);
        Editable name = nameEditText.getText();
        Editable alamat = alamatEditText.getText();
        Editable nohp = nohpEditText.getText();

        if(nameEditText.getText().toString().equals("") || alamatEditText.getText().toString().equals("") || nohpEditText.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this,"Harap Lengkapi identitas Anda", Toast.LENGTH_SHORT).show();
            return;
        }
        if(quantity == 0) {
            Toast.makeText(getBaseContext (), "Harap Lengkapi Quantty Anda", Toast.LENGTH_SHORT).show();
            return;
        }

        String orderSummary = createOrderSummary(name, alamat, nohp, priceValue, hasWhippedCream, hasChocolate);
        Log.i("MainActivity.java", orderSummary);
        displayMessage(orderSummary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // hanya bisa dihandle aplikasi email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pesanan Kopi Dari " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "admin@pesankopi.com" });


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //order.setOnClickListener(new View.OnClickListener() {
        //    @Override
         //   public void onClick(View v) {


         //   }
        //});
    }


    /**
     * This method is used to create the order summary.
     * @return order summary
     */
    public String createOrderSummary(Editable name, Editable alamat, Editable nohp, int priceOrder, Boolean hasWhippedCream, Boolean hasChocolate){
        String orderSummary = "Name : " + name;
        orderSummary = orderSummary + "\nAlamat : " + alamat;
        orderSummary = orderSummary + "\nNo Hp : " + nohp;
        orderSummary = orderSummary + "\nAdd Whipped Cream? " + hasWhippedCream;
        orderSummary = orderSummary + "\nAdd Chocolate? " + hasChocolate;
        orderSummary = orderSummary + "\nQuantity : " + quantity;
        orderSummary = orderSummary + "\nTotal : $" + priceOrder ;
        orderSummary = orderSummary + "\nThank You!";
        return orderSummary;
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(Boolean hasWhippedCream, Boolean hasChocolate) {
        int price = 0;
        if(hasWhippedCream && hasChocolate){
            price = quantity * (5 + 3);
        }else if(hasWhippedCream){
            price = quantity * (5 + 1);
        }else if(hasChocolate){
            price = quantity * (5 + 2);
        }else{
            price = quantity * 5;
        }
        return price;
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void incrementOrder(View view) {
        if(quantity>=100){
            Toast.makeText(MainActivity.this,"Sorry! You can't order more than 100 cup of coffees.", Toast.LENGTH_SHORT).show();
        }else{
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void decrementOrder(View view) {
        if(quantity <= 1){
            Toast.makeText(getBaseContext(),"Invalid Order! Please try again.", Toast.LENGTH_SHORT).show();
        }else{
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfOrder) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfOrder);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayMessage(String a) {

        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        //priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
        orderSummaryTextView.setText(a);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {//switch-case pada button yg di klik
            case R.id.AboutBtn://jika button about diklik
                //membuat intent baru
                Intent intentAbout = new Intent(MainActivity.this, about.class);
                startActivity(intentAbout);
    }
} }
