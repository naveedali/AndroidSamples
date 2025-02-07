package naveed.sample.collapsingtoolbar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView:RecyclerView
    lateinit var collapsingToolbar:AppBarLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        collapsingToolbar = findViewById(R.id.appBar)
        recyclerView = findViewById(R.id.recyclerView)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter() // Replace with your adapter

//        collapsingToolbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//            // Calculate the percentage of the AppBarLayout that is visible
//            val percentage = Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat() * 100
//
//            // Update UI based on the percentage (e.g., fade in/out toolbar title)
//            // ...
//        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
               if (dy>0){
                   collapsingToolbar.setExpanded(false,true)
               }else collapsingToolbar.setExpanded(true,true)
            }
        })
        // Optional: Set title for collapsing toolbar
        //collapsingToolbar.title = "Your Title"
    }
}