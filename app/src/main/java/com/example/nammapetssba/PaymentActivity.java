package com.example.nammapetssba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
// import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
// import com.stripe.android.paymentsheet.PaymentSheetResultCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private PaymentSheet paymentSheet;
    private String paymentIntentClientSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Retrieve data from intent
        Intent intent = getIntent();
        String cartItemsJson = intent.getStringExtra("cart_items");

        // Convert JSON back to list of CartItem
        Gson gson = new Gson();
        Type type = new TypeToken<List<CartItem>>() {}.getType();
        List<CartItem> cartItems = gson.fromJson(cartItemsJson, type);

        // Get the container for cart items
        LinearLayout cartItemsContainer = findViewById(R.id.cart_items_container);
        TextView totalPriceTextView = findViewById(R.id.total_price);

        double totalPrice = 0.0;

        // Dynamically add each cart item to the UI
        for (CartItem item : cartItems) {
            View itemView = getLayoutInflater().inflate(R.layout.cart_item_layout, null);

            ImageView productImageView = itemView.findViewById(R.id.product_image);
            TextView productNameTextView = itemView.findViewById(R.id.product_name);
            TextView productQuantityTextView = itemView.findViewById(R.id.product_quantity);
            TextView productPriceTextView = itemView.findViewById(R.id.product_price);

            productImageView.setImageResource(item.getPetImageId());
            productNameTextView.setText(item.getPetName());
            productQuantityTextView.setText(item.getQuantity() + " x set");
            productPriceTextView.setText(item.getPetPrice());

            totalPrice += item.calculateTotalPrice();

            cartItemsContainer.addView(itemView);
        }

        // Display total price
        totalPriceTextView.setText(String.format("Total: $%.2f", totalPrice));

        // Initialize the PaymentSheet
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        // Fetch the PaymentIntent client secret from your server
        fetchPaymentIntent();

        // Show the payment sheet when ready
        findViewById(R.id.pay_button).setOnClickListener(v -> presentPaymentSheet());
    }

    private void fetchPaymentIntent() {
        // Normally, you would fetch this from your server
        // For demonstration, we're using a hardcoded client secret
        paymentIntentClientSecret = "your_payment_intent_client_secret";
    }

    private void presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            new PaymentSheet.Configuration("Example, Inc.")
        );
    }

    private void onPaymentSheetResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment complete!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Payment failed or canceled", Toast.LENGTH_SHORT).show();
        }
    }
} 