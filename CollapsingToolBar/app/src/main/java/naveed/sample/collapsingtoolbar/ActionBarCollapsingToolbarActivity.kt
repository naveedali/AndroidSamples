package naveed.sample.collapsingtoolbar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import naveed.sample.collapsingtoolbar.databinding.ActivityCollapsingToolbarBinding
import naveed.sample.collapsingtoolbar.databinding.ActivityMainBinding


class ActionBarCollapsingToolbarActivity : BaseActivity<ActivityCollapsingToolbarBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCollapsingToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CollapsingBehaviorListAdapter() // Replace with your adapter
        updateScrollingFlags(intent?.getIntExtra("id",0)?:R.string.enterAlwaysCollapsed)
        registerScrollListener()
    }

    private fun updateScrollingFlags(stringResId:Int){
        val layoutParams:AppBarLayout.LayoutParams =
            binding.collapsingToolbar.getLayoutParams() as AppBarLayout.LayoutParams

        when(stringResId){
            R.string.enterAlwaysCollapsed->{
                layoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
            }
            R.string.enterAlways_EnterAlwaysCollapsed->{
                layoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
            }
            R.string.exitUntilCollapsed->{
                layoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                        AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or  AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            }
        }
        binding.collapsingToolbar.setLayoutParams(layoutParams)
    }

    private fun registerScrollListener(){
        binding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>0){
                    binding.appBar.setExpanded(false,true)
                }else if( dy < 0)
                    binding.appBar.setExpanded(true,true)
            }
        })
    }
}