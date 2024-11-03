package com.example.blinkit.adapters
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.blinkit.adapters.AdapterCategory.CategoryViewHolder
import com.example.blinkit.databinding.ItemViewProductCategoryBinding
import com.example.blinkit.databinding.ItemviewcartproductBinding

class AdapterCartProducts(private val cartItems: List<CartItem>) :
    RecyclerView.Adapter<AdapterCartProducts.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.ivProductimage)
        val productName: TextView = itemView.findViewById(R.id.tvProducttitle)
        val productQuantity: TextView = itemView.findViewById(R.id.tvProductquantity)
        val productPrice: TextView = itemView.findViewById(R.id.tvProductprice)
        val productCount: TextView = itemView.findViewById(R.id.tvproductcount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.productImage.setImageResource(cartItem.productImage)
        holder.productName.text = cartItem.productName
        holder.productQuantity.text = cartItem.productQuantity
        holder.productPrice.text = cartItem.productPrice
        holder.productCount.text = cartItem.productCount.toString()
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}
