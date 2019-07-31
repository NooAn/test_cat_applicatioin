package com.cat.bit.catapp

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader.PreloadModelProvider
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.cat.bit.catapp.databinding.ItemCatBinding
import com.cat.bit.catapp.presenter.ListPresenter
import com.cat.bit.catapp.view.ListView
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import kotlinx.android.synthetic.main.fragment_list.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.*
import javax.inject.Inject
import javax.inject.Provider


class ListFragment : MvpAppCompatFragment(), ListView {

    @Inject
    lateinit var presenterProvider: Provider<ListPresenter>

    @InjectPresenter
    lateinit var presenter: ListPresenter


    @ProvidePresenter
    fun providePresenter(): ListPresenter {
        return presenterProvider.get()
    }

    private var adapter: LastAdapter? = null
    private var listener: OnFragmentInteractionListener? = null

    private val MAX_PRELOAD = 1

    private val typeCats = Type<ItemCatBinding>(R.layout.item_cat)
        .onBind {
            val url = it.binding.item!!.url ?: DEFAULT_IMAGE
            Glide
                .with(view ?: return@onBind)
                .load(url)
                .transform(RoundedCorners(8))
                .into(it.binding.catImage)
        }
        .onClick { println("${it.binding.item}") }

    private var listCats = listOf<Cats>()
    val sizeProvider = FixedPreloadSizeProvider<String>(300, 300)

    val modelProvider: PreloadModelProvider<String> = object : PreloadModelProvider<String> {
        override fun getPreloadRequestBuilder(url: String): RequestBuilder<*>? {
            return Glide.with(context!!)
                .load(url)
        }

        override fun getPreloadItems(position: Int): MutableList<String?> {
            val url = listCats.get(position).url
            if (TextUtils.isEmpty(url))
                return Collections.emptyList()
            else
                return Collections.singletonList(url)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (context!!.applicationContext as App).component.inject(this)
        val recyclerViewPreLoader =
            RecyclerViewPreloader<String>(this, modelProvider, sizeProvider, MAX_PRELOAD)
        with(recyclerView) {
            addOnScrollListener(recyclerViewPreLoader)
            layoutManager =
                LinearLayoutManager(this@ListFragment.context, LinearLayoutManager.VERTICAL, false)
        }

    }

    fun initList(list: List<Cats>) {
        adapter = LastAdapter(list, BR.item)
            .map<Cats>(typeCats)
            .into(recyclerView as RecyclerView)
    }

    override fun onResume() {
        super.onResume()
        Log.d("LOG", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LOG", "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LOG", "onPause")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("LOG", "onAttach")
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }


    override fun showList(list: List<Cats>) {

    }

    override fun showLoading() {
    }

    override fun hideLoading() {

    }


    override fun hideNoInternetConnection() {}

    override fun showNoInternetConnection() {}
}


