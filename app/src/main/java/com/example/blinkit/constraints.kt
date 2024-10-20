package com.example.blinkit
import com.example.blinkit.models.Product
object constraints {
    val allProductsCategory = arrayOf(
        "Vegetable & Fruits",
        "Dairy & Breakfast",
        "Munchies & Chips",
        "Cold Drinks & Juices",
        "Instant & Frozen Food",
        "Tea Coffee & Health Drinks",
        "Sweet & Milky Chocolate",
        "Atta Rice & Dal",
        "Dry Masala & Oil",
        "Sauces & Spreads",
        "Chicken, Meat & Fish",
        "Organic & Premium",
        "Baby Care Products",
        "Pharma & Wellness",
        "Cleaning Essentials",
        "Home & Office",
        "Personal Care",
        "Pet Care"
    )

    val allProductsCategoryIcon = arrayOf(
        R.drawable.vegetable,
        R.drawable.dairy_breakfast,
        R.drawable.munchies,
        R.drawable.cold_and_juices,
        R.drawable.instant_frozen,
        R.drawable.tea_coffee,
        R.drawable.sweet_tooth,
        R.drawable.atta_rice,
        R.drawable.dry_masala,
        R.drawable.sauce_spreads,
        R.drawable.chicken_meat,
        R.drawable.organic_premium,
        R.drawable.baby_care,
        R.drawable.pharma_wellness,
        R.drawable.cleaning,
        R.drawable.home_office,
        R.drawable.personal_care,
        R.drawable.pet_care,
    )

    val groceryCategory = arrayOf(
        "Vegetable & Fruits",
        "Dairy & Breakfast",
        "Atta ,Rice & Daal",
        "Ktchen Appliances"
    )
    val groceryCategoryIcon = arrayOf(
        R.drawable.vegetable,
        R.drawable.dairy_breakfast,
        R.drawable.atta_rice,
        R.drawable.appliance
    )

    val snacksCategory = arrayOf(
        "Noodles & Maggie",
        "Sweet & chocolata",
        "Drinks & juices",
        "Icecreams & more"
    )
    val snacksCategoryIcon = arrayOf(
        R.drawable.magggi4,
        R.drawable.sweet_tooth,
        R.drawable.cold_and_juices,
        R.drawable.ice1
    )


    val beautyCategory = arrayOf(
        "Noodles & Maggie",
        "Sweet & chocolata",
        "Drinks&juice",
        "Icecreams & more"
    )
    val beautyCategoryIcon = arrayOf(
        R.drawable.skin,
        R.drawable.bodycare,
        R.drawable.hair,
        R.drawable.cosmetics
    )

    val productsByCategory: Map<String, List<Product>> = mapOf(
        "Vegetable & Fruits" to listOf(
            Product(R.drawable.vegetable, "Potatoes", "$2"),
            Product(R.drawable.vegetable, "Tomatoes", "$3")
        ),
        "Dairy & Breakfast" to listOf(
            Product(R.drawable.dairy_breakfast, "Milk", "$1"),
            Product(R.drawable.dairy_breakfast, "Eggs", "$2")
        )
        // Add products for other categories in a similar manner
    )
}
