package com.zhzch0x.bluetooth.assistant.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.zhzch0x.bluetooth.assistant.ui.theme.BluetoothAssistantTheme
import com.zhzch0x.bluetooth.assistant.ui.widgets.LoadingDialog
import kotlinx.coroutines.launch


abstract class ComposeBaseActivity: ComponentActivity() {

    private val showLoading = mutableStateOf(false)
    private var loadingCancelable by mutableStateOf(false)
    private val snackBarState = SnackbarHostState()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(intent != null){
            handleIntent(intent)
        }
        initData()
        setContent {
            BluetoothAssistantTheme {
                Content()
                LoadingDialog(showLoading, loadingCancelable)
                SnackbarHost(hostState = snackBarState){
                    Snackbar(it)
                }
            }
        }
    }

    protected open fun handleIntent(intent: Intent){}
    
    protected open fun initData(){}

    @Composable
    protected abstract fun Content()

    open fun showLoading(cancelable: Boolean = true) {
        loadingCancelable = cancelable
        showLoading.value = true
    }

    open fun hideLoading() {
        showLoading.value = false
    }

    open fun showSnackBar(message: String,
                     actionLabel: String? = null,
                     withDismissAction: Boolean = true,
                     duration: SnackbarDuration =
                         if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite){
        lifecycleScope.launch {
            snackBarState.showSnackbar(message, actionLabel, withDismissAction, duration)
        }
    }

    open fun showToast(msg: String){
        runOnUiThread {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
    }
    
}