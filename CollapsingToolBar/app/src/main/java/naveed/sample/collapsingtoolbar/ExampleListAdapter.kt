package naveed.sample.collapsingtoolbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ExampleListAdapter(val onClick: (stringResId : Int) -> Unit) : RecyclerView.Adapter<ExampleListAdapter.ViewHolder>() {
    /*var imageList: List<Int> = mutableListOf()*/
    private var labelList: MutableList<Int> = mutableListOf()
    init {

        addStringResToList(R.string.enterAlwaysCollapsed)
        addStringResToList(R.string.exitUntilCollapsed)
        addStringResToList(R.string.enterAlways_EnterAlwaysCollapsed)

        /*imageList = listOf(
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground
        )*/

    }

    fun addStringResToList(@StringRes string:Int){
        labelList.add(string)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLabel: TextView = itemView.findViewById(R.id.tvLabel)
        val cvLabel: CardView = itemView.findViewById(R.id.cvLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_label, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvLabel.setText(labelList[position])
        holder.cvLabel.setOnClickListener {
            onClick.invoke(labelList[position])
        }
    }

    override fun getItemCount(): Int {
        return labelList.size
    }
}
