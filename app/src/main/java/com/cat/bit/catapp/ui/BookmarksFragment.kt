package com.cat.bit.catapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cat.bit.catapp.BR
import com.cat.bit.catapp.R
import com.cat.bit.catapp.databinding.ItemCatBinding
import com.cat.bit.catapp.entity.Cats
import com.cat.bit.catapp.presenter.BookmarksPresenter
import com.cat.bit.catapp.presenter.ListPresenter
import com.cat.bit.catapp.room.Bookmark
import com.cat.bit.catapp.view.BookmarksView
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import kotlinx.android.synthetic.main.fragment_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class BookmarksFragment : Fragment(), BookmarksView {
    private var adapter: LastAdapter? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: BookmarksPresenter

    @ProvidePresenter
    fun providePresenter(): BookmarksPresenter {
        return presenter
    }

    private var listCats = arrayListOf<Bookmark>()

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun hideNoInternetConnection() {
    }

    override fun showNoInternetConnection() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    override fun showList(list: List<Bookmark>) {
        listCats.addAll(list)
        adapter = LastAdapter(listCats, BR.item)
            .map<Cats>(R.layout.item_bookmarks)
            .into(recyclerView as RecyclerView)
    }


}
