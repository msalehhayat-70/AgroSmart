package com.example.agrosmart.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EcommRepository {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?> = _product

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    fun getAllProducts() {
        // Placeholder data
        val placeholderProducts = listOf(
            Product("1", "Organic Fertilizer", 469, 50, "Agro Retailers", "In Stock", listOf("https://investingnews.com/media-library/cse-erth.jpg?id=27745881&width=1200&height=800&quality=70&coordinates=0%2C0%2C0%2C0"), 4.5f),
            Product("2", "Pesticide Spray", 799, 60, "Farm Essentials", "In Stock", listOf("https://bestbeebrothers.com/cdn/shop/articles/bbb-pesticides.jpg?v=1524752687&width=1400"), 4.5f),
            Product("3", "Neem Oil", 550, 40, "Green Agro", "In Stock", listOf("https://www.gardenbenches.com/blog/wp-content/uploads/2024/11/neem-oil-for-plants-ftd-394x218.jpg"), 4.2f),
            Product("4", "Gardening Tools", 1250, 100, "Home Garden", "In Stock", listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJxlgcNJXH-rLajyTv-_kn5Sc_gTdc6fxz5w&s"), 4.8f),
            Product("5", "Compost Bin", 2500, 200, "Eco Living", "Out of Stock", listOf("https://m.media-amazon.com/images/I/81cb8WzS3JL._AC_SL1500_.jpg"), 4.6f),
            Product("6", "Seed Pack", 300, 20, "Seed World", "In Stock", listOf("https://cdn.shopify.com/s/files/1/2016/2681/files/chefs-speciality-shabu-shabu-garden.jpg?v=1762446929?width=732&height=732"), 4.4f),
            Product("7", "Watering Can", 650, 50, "GardenNeeds", "In Stock", listOf("https://images-cdn.ubuy.co.in/6439521e954a5002851fe322-watering-can-for-indoor-plants-garden.jpg"), 4.1f),
            Product("8", "Gardening Gloves", 350, 25, "SafetyGear", "In Stock", listOf("https://m.media-amazon.com/images/I/81osBHoh4gL._AC_UF350,350_QL80_.jpg"), 4.7f),
            Product("9", "Pruning Shears", 950, 70, "SharpCut", "In Stock", listOf("https://plastrip.co.za/cdn/shop/files/WEBDSC_6444editDura5PruningShearjpeg_600x.jpg?v=1701413406"), 4.6f)
        )
        _products.postValue(placeholderProducts)
    }

    fun getProductById(id: String) {
        // Placeholder data
        val placeholderProduct = when(id) {
            "1" -> Product(id, "Organic Fertilizer", 469, 50, "Agro Retailers", "In Stock", listOf("https://investingnews.com/media-library/cse-erth.jpg?id=27745881&width=1200&height=800&quality=70&coordinates=0%2C0%2C0%2C0"), 4.5f)
            "2" -> Product(id, "Pesticide Spray", 799, 60, "Farm Essentials", "In Stock", listOf("https://bestbeebrothers.com/cdn/shop/articles/bbb-pesticides.jpg?v=1524752687&width=1400"), 4.5f)
            "3" -> Product(id, "Neem Oil", 550, 40, "Green Agro", "In Stock", listOf("https://www.gardenbenches.com/blog/wp-content/uploads/2024/11/neem-oil-for-plants-ftd-394x218.jpg"), 4.2f)
            "4" -> Product(id, "Gardening Tools", 1250, 100, "Home Garden", "In Stock", listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJxlgcNJXH-rLajyTv-_kn5Sc_gTdc6fxz5w&s"), 4.8f)
            "5" -> Product(id, "Compost Bin", 2500, 200, "Eco Living", "Out of Stock", listOf("https://m.media-amazon.com/images/I/81cb8WzS3JL._AC_SL1500_.jpg"), 4.6f)
            "6" -> Product(id, "Seed Pack", 300, 20, "Seed World", "In Stock", listOf("https://cdn.shopify.com/s/files/1/2016/2681/files/chefs-speciality-shabu-shabu-garden.jpg?v=1762446929?width=732&height=732"), 4.4f)
            "7" -> Product(id, "Watering Can", 650, 50, "GardenNeeds", "In Stock", listOf("https://images-cdn.ubuy.co.in/6439521e954a5002851fe322-watering-can-for-indoor-plants-garden.jpg"), 4.1f)
            "8" -> Product(id, "Gardening Gloves", 350, 25, "SafetyGear", "In Stock", listOf("https://m.media-amazon.com/images/I/81osBHoh4gL._AC_UF350,350_QL80_.jpg"), 4.7f)
            "9" -> Product(id, "Pruning Shears", 950, 70, "SharpCut", "In Stock", listOf("https://plastrip.co.za/cdn/shop/files/WEBDSC_6444editDura5PruningShearjpeg_600x.jpg?v=1701413406"), 4.6f)
            else -> null
        }
        _product.postValue(placeholderProduct)
    }

    fun getCartItems() {
        // Placeholder data
        val placeholderCartItems = listOf(
            CartItem("1", "Organic Fertilizer", "469", "https://investingnews.com/media-library/cse-erth.jpg?id=27745881&width=1200&height=800&quality=70&coordinates=0%2C0%2C0%2C0", 2),
            CartItem("2", "Pesticide Spray", "799", "https://bestbeebrothers.com/cdn/shop/articles/bbb-pesticides.jpg?v=1524752687&width=1400", 1)

        )
        _cartItems.postValue(placeholderCartItems)
    }
}
