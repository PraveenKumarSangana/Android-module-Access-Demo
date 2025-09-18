package com.example.androidmoduleaccessdemo.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmoduleaccessdemo.common.component.AccessManagerComponent
import com.example.androidmoduleaccessdemo.databinding.ActivityMainBinding
import com.example.androidmoduleaccessdemo.model.Module
import com.example.androidmoduleaccessdemo.model.User
import com.example.androidmoduleaccessdemo.repository.MainRepository
import com.example.androidmoduleaccessdemo.ui.adapter.ModuleAdapter
import com.example.androidmoduleaccessdemo.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ModuleAdapter.ItemClickListener {

    private var viewModel: MainViewModel = MainViewModel(MainRepository(this))
    private lateinit var binding: ActivityMainBinding
    private var moduleAdapter: ModuleAdapter? = null
    private var modulesList: ArrayList<Module> = ArrayList()
    private var user: User? = null
    private lateinit var accessManager: AccessManagerComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        initRecyclerView()
        observeData()
    }

    private fun initRecyclerView() {
        binding.rvModules.apply {
            moduleAdapter = ModuleAdapter(modulesList, this@MainActivity)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = moduleAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeData() {
        viewModel.getUserData().observe(this@MainActivity) { userDataWrapper ->
            user = userDataWrapper.user
            val modules = userDataWrapper.modules
            modulesList.clear()
            modulesList.addAll(modules)
            moduleAdapter?.notifyDataSetChanged()
            accessManager = AccessManagerComponent(userDataWrapper.user)
            startCoolingTimerUpdates()
        }
    }

    private fun startCoolingTimerUpdates() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    updateCoolingTimer()
                    delay(1000)
                }
            }
        }
    }

    private fun updateCoolingTimer() {
        val coolingText = accessManager.getCoolingTimerText()
        if (coolingText != null) {
            binding.tvCoolingTimer.visibility = View.VISIBLE
            binding.tvCoolingTimer.text = coolingText
        } else {
            binding.tvCoolingTimer.visibility = View.GONE
        }
    }

    override fun onItemClick(data: Module) {
        if (user?.userType == "active") {
            val accessStatus = accessManager.getModuleAccessStatus(data)
            when (accessStatus) {
                "ACCESS_GRANTED" -> {
                    Toast.makeText(
                        this, "Navigating to ${data.title}", Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    Toast.makeText(this, accessStatus, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}