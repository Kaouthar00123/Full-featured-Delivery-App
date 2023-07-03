package com.example.menuapplication.Entity

    object SharedData {
        var FoodInformation: MenuEntity? = null
        var RestauInfos: RestauEntity? = null

        private var instance: SharedData? = null
        fun getInstance(): SharedData {
            if (instance == null) {
                instance = SharedData
            }
            return instance as SharedData
        }
    }
