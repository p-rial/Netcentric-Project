package com.example.iq180.lobby

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iq180.R
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment: Fragment() {

    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            onRestoreInstanceState(arguments)
        } else {
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvWelcome.text = "WELCOME, ${name}"
    }

    private fun onRestoreInstanceState(arguments: Bundle?) {
        name = arguments!!.getString("name")
    }

    companion object {
        private val welcomeFragment = WelcomeFragment()

        fun newInstance(name: String): WelcomeFragment {
            val args = Bundle()
            args.putString("name", name)
            welcomeFragment.arguments = args
            return welcomeFragment
        }
    }
}