package com.begini.androidsdkv2.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.begini.androidsdk.model.replaceFragment
import com.begini.androidsdkv2.R
import com.begini.androidsdkv2.ui.home.view.BeginiDataSdkFragment
import com.begini.androidsdkv2.ui.permission.PermissionScreenFragment
import com.begini.androidsdkv2.workerutil.PermissionUtil
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnGetStarted.setOnClickListener {
            if (PermissionUtil().getDeniedPermissionList(requireActivity()).size <= 0) {
                (activity as AppCompatActivity).replaceFragment(
                    BeginiDataSdkFragment(),
                    "BeginiDataSdkFragment",
                    R.id.fl_container
                )
            } else {
                (activity as AppCompatActivity).replaceFragment(
                    PermissionScreenFragment(),
                    "PermissionScreenFragment",
                    R.id.fl_container
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

}