package com.example.akshay.harsoda.permission

import android.Manifest
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.akshay.harsoda.permission.helper.AksPermission
import com.akshay.harsoda.permission.helper.request.SettingDialogRequest
import com.example.akshay.harsoda.permission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = javaClass.simpleName

    private var mActivity: MainActivity = this@MainActivity
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this@MainActivity

        this.mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.mBinding.root)

        mBinding.btnMainActivityAskMultiplePermission.setOnClickListener(this)
        mBinding.btnMainActivityAskSinglePermission.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when (v) {

            mBinding.btnMainActivityAskSinglePermission -> {

                AksPermission.with(mActivity)
                    .permissions(Manifest.permission.READ_CALENDAR)
                    // Default value is true, Pass false if you don't want to showing a default setting dialog if some permission are permanently denied.
                    .isShowDefaultSettingDialog(mBinding.cbMainActivityShowDefaultSettingDialog.isChecked)
                    .request { grantedList ->
                        Log.i(
                            TAG,
                            "Ask Single Permission -> Granted Permission List -> $grantedList"
                        )
                        // Your Code Here
                    }
            }

            mBinding.btnMainActivityAskMultiplePermission -> {

                AksPermission.with(mActivity)
                    .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                    )
                    // if you want to Customise Default dialog
                    .setDefaultSettingDialog(
                        SettingDialogRequest()
                            .setDialogTitleColor(Color.YELLOW)
                            .setDialogMessageColor(Color.BLUE)
                            .setDialogPositiveColor(Color.GREEN)
                            .setDialogNegativeColor(Color.RED)
                    )
                    // Default value is true, Pass false if you don't want to showing a default setting dialog if some permission are permanently denied.
                    .isShowDefaultSettingDialog(mBinding.cbMainActivityShowDefaultSettingDialog.isChecked)
                    .request(
                        onGrantedResult = { grantedList ->
                            Log.i(
                                TAG,
                                "Ask Multiple Permission -> Granted Permission List -> $grantedList"
                            )
                        },
                        onDeniedResult = { deniedList ->
                            Log.i(
                                TAG,
                                "Ask Multiple Permission -> Denied Permission List -> $deniedList"
                            )
                        },
                        onPermanentlyDeniedResult = { permanentlyDeniedList ->
                            Log.i(
                                TAG,
                                "Ask Multiple Permission -> PermanentlyDenied Permission List -> $permanentlyDeniedList"
                            )
                        }
                    )
            }
        }
    }
}