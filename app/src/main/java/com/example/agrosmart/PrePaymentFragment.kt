package com.example.agrosmart

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.agrosmart.databinding.FragmentPrePaymentBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PrePaymentFragment : Fragment(), PaymentResultListener {

    private var _binding: FragmentPrePaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrePaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.payBtn.setOnClickListener {
            startPayment()
        }
    }

    private fun startPayment() {
        val activity: Activity = requireActivity()
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name", "AgroSmart")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "10000")//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);


            co.open(activity, options)

        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        Toast.makeText(requireContext(), "Payment Successful: $razorpayPaymentID", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(code: Int, response: String?) {
        Toast.makeText(requireContext(), "Payment Failed: $code $response", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
