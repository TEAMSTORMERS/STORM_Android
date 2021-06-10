package com.stormers.storm.base

import android.os.Bundle
import android.view.*
import android.widget.ScrollView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.stormers.storm.util.KeyBoardVisibilityUtils


open class BaseBindingFragment<B : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    Fragment() {
    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    protected fun setSoftInputModeToResize() {
        val window = requireActivity().window

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)

            binding.root.setOnApplyWindowInsetsListener { _, insets ->
                val imeHeight = insets.getInsets(WindowInsets.Type.ime()).bottom
                binding.root.setPadding(0, 0, 0, imeHeight)
                insets
            }
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    protected fun setRootScrollView(scrollView: ScrollView){
        KeyBoardVisibilityUtils(requireActivity().window,
            onShowKeyboard = { keyboardHeight ->
                scrollView.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight)
                }
            }
        )
    }
}