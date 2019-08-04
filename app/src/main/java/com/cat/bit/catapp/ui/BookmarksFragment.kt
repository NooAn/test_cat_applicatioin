package com.cat.bit.catapp.ui


import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cat.bit.catapp.App
import com.cat.bit.catapp.BR
import com.cat.bit.catapp.R
import com.cat.bit.catapp.databinding.ItemBookmarksBinding
import com.cat.bit.catapp.databinding.ItemCatBinding
import com.cat.bit.catapp.entity.Bookmark
import com.cat.bit.catapp.entity.Cats
import com.cat.bit.catapp.presenter.BookmarksPresenter
import com.cat.bit.catapp.view.BookmarksView
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import kotlinx.android.synthetic.main.fragment_bookmarks.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.recyclerView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class BookmarksFragment : MvpAppCompatFragment(), BookmarksView {

    private var adapter: LastAdapter? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: BookmarksPresenter

    @ProvidePresenter
    fun providePresenter(): BookmarksPresenter {
        return presenter
    }

    private var listCats = arrayListOf<Bookmark>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as App).component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(recyclerView) {
            layoutManager =
                LinearLayoutManager(
                    this@BookmarksFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
        presenter.loadAllImages()

    }

    override fun showList(list: List<Bookmark>) {
        sorry.visibility = View.INVISIBLE
        listCats.addAll(list)
        adapter = LastAdapter(listCats, BR.item)
            .map<Bookmark>(Type<ItemBookmarksBinding>(R.layout.item_bookmarks).onBind { holder ->
                holder.binding.item!!.bitmap?.let {
                    holder.binding.ivCatImage.setImageBitmap(it)
                }
            })
            .into(recyclerView as RecyclerView)
    }

    override fun showSorry() {
        sorry.visibility = View.VISIBLE
    }


}
