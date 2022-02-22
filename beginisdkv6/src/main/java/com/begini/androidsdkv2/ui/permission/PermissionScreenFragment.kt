package com.begini.androidsdkv2.ui.permission

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import com.begini.androidsdk.model.isPermissionAllowed
import com.begini.androidsdk.model.replaceFragment
import com.begini.androidsdk.model.showDialogMessage
import com.begini.androidsdkv2.R
import com.begini.androidsdkv2.ui.home.view.BeginiDataSdkFragment
import com.begini.androidsdkv2.workerutil.PermissionUtil
import com.vmadalin.easypermissions.EasyPermissions
import kotlinx.android.synthetic.main.fragment_permission_rationale.*

class PermissionScreenFragment() : Fragment(), EasyPermissions.PermissionCallbacks {
    val PERMISSION_REQUEST_CODE = 989
    val TAG_PERMISSION_SCREEN = "PermissionFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_permission_rationale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPermissionRationaleText()
        btn_continue.setOnClickListener {
            requestPermissions.launch(PermissionUtil.requirePermissionList)
        }
        imgBtnGoBack.setOnClickListener{
            activity?.finish()
        }

    }

    private fun setPermissionRationaleText() {
        val str = SpannableStringBuilder()
            .append(getString(R.string.permission_rationale_info_1) + "\n\n")
            .bold { append(getString(R.string.permission_rationale_title_1) + "\n\n") }
            .append(getString(R.string.permission_rationale_content_1) + "\n\n")
            .bold { append(getString(R.string.permission_rationale_title_2) + "\n\n") }
            .append(getString(R.string.permission_rationale_content_2) + "\n\n")
            .bold { append(getString(R.string.permission_rationale_title_3) + "\n\n") }
            .append(getString(R.string.permission_rationale_content_3) + "\n\n")
            .bold { append(getString(R.string.permission_rationale_title_4) + "\n\n") }
            .append(getString(R.string.permission_rationale_content_4) + "\n\n")
            .append(getString(R.string.permission_rationale_info_2))

        tvRationaleText.text = str
    }


    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.e(TAG_PERMISSION_SCREEN, "onPermissionsDenied")

        var isStoragePermissionDenied = false
        for (permission in perms) {
            Log.e(TAG_PERMISSION_SCREEN, "Denied permission: " + permission)
            if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE)
                isStoragePermissionDenied = true
        }
        if (!isStoragePermissionDenied) {
            goToDataCollectionFragment()
        } else {
            checkStoragePermission()
        }
    }

    private fun checkStoragePermission() {
        val isWriteExternalStorage =
            activity?.isPermissionAllowed(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!!
        val isReadExternalStorage =
            activity?.isPermissionAllowed(android.Manifest.permission.READ_EXTERNAL_STORAGE)!!
        if (!isWriteExternalStorage || !isReadExternalStorage) {
            activity?.showDialogMessage(
                title = getString(R.string.title_dialog_important),
                message = getString(R.string.message_dialog_need_storage_permission),
                positiveButtonName = getString(R.string.btn_goto_app_info),
                positiveButtonClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
                    //Open the specific App Info page:
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:${activity?.packageName}")
                    startActivity(intent)
                    dialogInterface.dismiss()
                }
            )
        }
    }

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var deniedPermissionList = it.filterValues { it.not() }
            var allowedPermissionList = it.filterValues { it }
            Log.e("permission denied: ", deniedPermissionList.toString())
            Log.e("permission allowed: ", allowedPermissionList.toString())
            Log.e("Storage permission ", it[Manifest.permission.WRITE_EXTERNAL_STORAGE].toString())
            if (it[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true) {
                goToDataCollectionFragment()
            } else {
                checkStoragePermission()
            }
        }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.e(TAG_PERMISSION_SCREEN, "onPermissionsGranted")

        for (permission in perms) {
            Log.e(TAG_PERMISSION_SCREEN, "Allowed permission: " + permission)
        }
        if (perms.size >= 8) {
            goToDataCollectionFragment()
        }

    }

    private fun goToDataCollectionFragment() {
        Log.e(TAG_PERMISSION_SCREEN, "Go to next screen")

        (activity as AppCompatActivity).replaceFragment(
            BeginiDataSdkFragment(),
            "BeginiDataSdkFragment",
            R.id.fl_container
        )
    }




}