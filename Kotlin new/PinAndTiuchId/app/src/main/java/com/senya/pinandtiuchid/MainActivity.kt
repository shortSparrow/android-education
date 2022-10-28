package com.senya.pinandtiuchid

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senya.pinandtiuchid.presentation.app.Home
import com.senya.pinandtiuchid.presentation.app.HomeViewModel
import com.senya.pinandtiuchid.ui.theme.PinAndTiuchIdTheme


class MainActivity : FragmentActivity() {
    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun getActivity(): Activity {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PinAndTiuchIdTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val homeViewModel: HomeViewModel = viewModel()
                    val state = homeViewModel.state
                    val action = homeViewModel::onAction
                    Home(state = state, onAction = action)
                }
            }
        }
    }

}

