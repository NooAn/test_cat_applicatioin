package com.cat.bit.catapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader.PreloadModelProvider
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.cat.bit.catapp.DEFAULT_IMAGE
import com.cat.bit.catapp.R
import com.cat.bit.catapp.databinding.ItemCatBinding
import com.cat.bit.catapp.entity.Cats
import com.cat.bit.catapp.presenter.ListPresenter
import com.cat.bit.catapp.view.ListView
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_list.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.core.KoinComponent
import java.io.File
import java.util.*
import org.koin.core.get

class ListFragment : MvpAppCompatFragment(), ListView, KoinComponent {

    @InjectPresenter
    lateinit var presenter: ListPresenter

    @ProvidePresenter
    fun providePresenter(): ListPresenter {
        return get()
    }

    private var adapter: LastAdapter? = null
    private var snackbar: Snackbar? = null
    private var listener: OnFragmentInteractionListener? = null
    private val MAX_PRELOAD = 5
    private var listCats = arrayListOf<Cats>()
    private val SIZE = 300

    private val sizeProvider = FixedPreloadSizeProvider<String>(SIZE, SIZE)
    private lateinit var rxPermissions: RxPermissions


    private val modelProvider: PreloadModelProvider<String> =
        object : PreloadModelProvider<String> {
            override fun getPreloadRequestBuilder(url: String): RequestBuilder<*>? {
                return Glide.with(context!!).load(url)
            }

            override fun getPreloadItems(position: Int): MutableList<String?> {
                val url = listCats[position].url
                if (TextUtils.isEmpty(url))
                    return Collections.emptyList()
                else
                    return Collections.singletonList(url)
            }
        }

    private val requestOptions = RequestOptions().override(SIZE)
        .downsample(DownsampleStrategy.CENTER_INSIDE)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)

    private val typeCats = Type<ItemCatBinding>(R.layout.item_cat)
        .onBind { holder ->
            val url = holder.binding.item!!.url ?: DEFAULT_IMAGE
            val bitmapBuilder = Glide
                .with(view ?: return@onBind)
                .asBitmap()
                .apply(requestOptions)
                .load(url)
            bitmapBuilder
                .into(holder.binding.ivCatImage)

            holder.binding.btnSave.setOnClickListener {
                rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe {
                        presenter.saveImage(bitmapBuilder.submit(), url)
                    }
            }

            holder.binding.btnBookmark.setOnClickListener {
                presenter.makeBookmark(bitmapBuilder.submit(), url)
            }

        }

    private fun galleryAddPic(f: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        activity?.sendBroadcast(mediaScanIntent)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewPreLoader =
            RecyclerViewPreloader(this, modelProvider, sizeProvider, MAX_PRELOAD)
        with(recyclerView) {
            addOnScrollListener(recyclerViewPreLoader)
            layoutManager =
                LinearLayoutManager(
                    this@ListFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
        swipeToRefresh.setOnRefreshListener {
            presenter.loadCats()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        listCats = arrayListOf()
        listCats.addAll(list)
        adapter = LastAdapter(listCats, BR.item)
            .map<Cats>(typeCats)
            .into(recyclerView as RecyclerView)
    }

    override fun showLoading() {
        swipeToRefresh?.isRefreshing = true
    }

    override fun hideLoading() {
        swipeToRefresh?.isRefreshing = false
    }

    override fun hideNoInternetConnection() {
        snackbar?.dismiss()
    }

    override fun showNoInternetConnection() {
        showSnack("No Internet Connection")
    }

    override fun showNotificationImageSaved(text: String, file: File) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
        galleryAddPic(file)
    }

    override fun showNotification(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
    }

    private fun showSnack(string: String) {
        snackbar = Snackbar.make(
            activity!!.findViewById(android.R.id.content),
            string,
            Snackbar.LENGTH_LONG
        )
        snackbar?.show()
    }
}


