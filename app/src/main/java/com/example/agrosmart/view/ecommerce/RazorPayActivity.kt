package com.example.agrosmart.view.ecommerce

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agrosmart.databinding.ActivityRazorPayBinding
import com.example.agrosmart.model.Order
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class RazorPayActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding: ActivityRazorPayBinding

    private var itemCost: Int = 0
    private var quantity: Int = 0
    private var deliveryCost: Int = 0
    private var productId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRazorPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            productId = it.getString("productId")
            itemCost = it.getString("itemCost")?.toIntOrNull() ?: 0
            quantity = it.getString("quantity")?.toIntOrNull() ?: 0
            deliveryCost = it.getString("deliveryCost")?.toIntOrNull() ?: 0
        }

        val totalCost = (itemCost * quantity) + deliveryCost
        binding.netValue.text = "Net Value: ₹$totalCost"

        binding.orderNowBtn.setOnClickListener {
            if (validateInput()) {
                startPayment(totalCost)
            }
        }
    }

    private fun validateInput(): Boolean {
        if (binding.fullNamePrePay.text.isNullOrEmpty() ||
            binding.localityPrePay.text.isNullOrEmpty() ||
            binding.cityPrePay.text.isNullOrEmpty() ||
            binding.statePrePay.text.isNullOrEmpty() ||
            binding.pincodePrePay.text.isNullOrEmpty() ||
            binding.mobileNumberPrePay.text.isNullOrEmpty()
        ) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun startPayment(totalCost: Int) {
        val co = Checkout()
        try {
            val options = JSONObject().apply {
                put("name", "AgroSmart")
                put("description", "Order Payment")
                put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                put("currency", "INR")
                put("amount", "${totalCost * 100}")

                val prefill = JSONObject().apply {
                    // In a real app, you would get this from user data
                    put("email", "test@example.com")
                    put("contact", binding.mobileNumberPrePay.text.toString())
                }
                put("prefill", prefill)
            }
            co.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        Toast.makeText(this, "Payment Successful: $razorpayPaymentID", Toast.LENGTH_LONG).show()

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, (0..12).random())
        val deliveryDate = sdf.format(calendar.time)

        val order = Order(
            name = binding.fullNamePrePay.text.toString(),
            locality = binding.localityPrePay.text.toString(),
            city = binding.cityPrePay.text.toString(),
            state = binding.statePrePay.text.toString(),
            pincode = binding.pincodePrePay.text.toString(),
            mobile = binding.mobileNumberPrePay.text.toString(),
            currentDate = currentDate,
            productId = productId ?: "",
            itemCost = itemCost,
            quantity = quantity,
            deliveryCost = deliveryCost,
            deliveryStatus = "Arriving By: $deliveryDate"
        )

        // You would typically save the order to your backend or a local database here
        finish()
    }

    override fun onPaymentError(code: Int, description: String?) {
        Toast.makeText(this, "Payment Failed: $description", Toast.LENGTH_LONG).show()
    }
}
