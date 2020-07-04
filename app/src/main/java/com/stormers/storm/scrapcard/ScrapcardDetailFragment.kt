package com.stormers.storm.scrapcard

import android.os.Bundle
import android.text.Editable
import android.text.Selection.setSelection
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stormers.storm.R
import kotlinx.android.synthetic.main.fragment_scrapcard_detail.*

class ScrapcardDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scrapcard_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText_scrapcard_memo.addTextChangedListener(textWatcher)

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val length = s.toString().length
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (editText_scrapcard_memo.lineCount > 3) {
                editText_scrapcard_memo.setText(s?.dropLast(1).toString()) // 2줄 초과 시 마지막 한글자 삭제
                editText_scrapcard_memo.setSelection(s?.length!! - 1) // 커서 위치 마지막으로 이동
            }
        }

    }
}