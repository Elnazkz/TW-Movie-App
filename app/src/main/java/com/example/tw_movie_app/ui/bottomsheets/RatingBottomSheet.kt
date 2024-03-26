package com.example.tw_movie_app.ui.bottomsheets

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.tw_movie_app.R
import com.example.tw_movie_app.baseconf.BACK_DROP_BASE_URL
import com.example.tw_movie_app.baseconf.POSTER_IMAGE_BASE_URL
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.databinding.BottomSheetRatingBinding
import com.example.tw_movie_app.ui.fragments.MOVIE_OBJ
import com.example.tw_movie_app.utils.dpToPx
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RatingBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetRatingBinding
    private var movie: Movie? = null
    var onFavButtonClickListener: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       movie = arguments?.takeIf {
            it.containsKey(MOVIE_OBJ)
        }?.getParcelable(MOVIE_OBJ)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetRatingBinding.inflate(inflater, container, false)
        setToolbar()
        movie?.posterPath?.let { setMoviePic(it) }
        setFaveButton()
        binding.viewFavs.setOnClickListener {
            dismiss()
            onFavButtonClickListener()
        }
        return binding.root

    }

    private fun setFaveButton() {
        with(binding.faveButton) {
            if (movie?.favourite == true)
                setImageResource(R.drawable.ic_favourite_filled)
            else
                setImageResource(R.drawable.ic_favourite_outline)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheetInternal =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
            bottomSheetInternal.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from(bottomSheetInternal)
            behavior.apply {
                peekHeight = Resources.getSystem().displayMetrics.heightPixels
                state = BottomSheetBehavior.STATE_EXPANDED
            }

        }


        return dialog
    }

    private fun setToolbar() {
        with(binding.toolbar) {
            setImageHeight(requireContext().dpToPx(450))
            movie?.let { setTitleText(it.originalTitle) }
            setBackgroundImageLink(BACK_DROP_BASE_URL + (movie?.backdropPath ?: ""))
            setBackButtonClickListener {
                dismiss()
            }
        }
    }

    private fun setMoviePic(path: String) {
        Glide
            .with(requireContext())
            .load(POSTER_IMAGE_BASE_URL + path)
            .error(R.drawable.placeholder)
            .into(binding.moviePic)

    }

}