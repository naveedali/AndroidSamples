package naveed.sample.collapsingtoolbar

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open class BaseActivity<T:ViewBinding> : AppCompatActivity()  {
    protected lateinit var binding:T
}