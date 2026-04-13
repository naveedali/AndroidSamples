package naveed.sample.collapsingtoolbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import naveed.sample.collapsingtoolbar.data.Animal

class AnimalsListAdapter : RecyclerView.Adapter<AnimalsListAdapter.ViewHolder>() {
    var imageList: List<Animal> = mutableListOf()
    init {


        imageList = listOf(
            Animal("Tiger", R.drawable.tiger),
            Animal("Cat", R.drawable.cat),
            Animal("Dog", R.drawable.dog),
            Animal("Tiger", R.drawable.tiger),
            Animal("Cat", R.drawable.cat),
            Animal("Dog", R.drawable.dog),
            Animal("Tiger", R.drawable.tiger),
            Animal("Cat", R.drawable.cat),
            Animal("Dog", R.drawable.dog),
            Animal("Tiger", R.drawable.tiger),
            Animal("Cat", R.drawable.cat),
            Animal("Dog", R.drawable.dog),
            Animal("Tiger", R.drawable.tiger),
            Animal("Cat", R.drawable.cat),
            Animal("Dog", R.drawable.dog),
            Animal("Tiger", R.drawable.tiger)
        )

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
