package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void submitOrder(View View) {
        EditText nameField = (EditText)findViewById(R.id.name_field) ;
        String name = nameField.getText().toString();

        // Figure out if the user wants whipped cream topping
        RadioButton whippedCreamCheckBox = (RadioButton) findViewById(R.id.radio_yes);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        RadioButton chocolateCheckBox = (RadioButton) findViewById(R.id.radio_no);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText tellingMe = (EditText)findViewById(R.id.tellingMe) ;
        String telling = tellingMe.getText().toString();


        // Display the order summary on the screen
        String priceMessage = createOrderSummary(name, hasWhippedCream, hasChocolate, telling);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.summary) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     *
     * @param name of the customer
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param telling for
     * @return text summary
     */
    private String createOrderSummary(String name, boolean addWhippedCream, boolean addChocolate, String telling) {
        String priceMessage = getString(R.string.name) + ": " + name;
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream) + " " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.order_summary_chocolate) + " " + addChocolate;
        priceMessage += "\n" + getString(R.string.order_summary_price) + ": " + telling;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }


}
