package com.example.androidmoduleaccessdemo.model

data class UserDataWrapper(
    val user: User,
    val modules: List<Module>
)

data class User(
    val userType: String,
    val coolingStartTime: String?,
    val coolingEndTime: String?,
    val accessibleModules: List<String>
)

data class Module(
    val id: String,
    val title: String,
    val requiresConsent: Boolean
)