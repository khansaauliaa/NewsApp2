package com.khansafzh.newsapp2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class Users (
    private var uid : String = "0",
    private var fullName : String = "",
    private var email : String = "",
    private var linkedIn : String = "",
    private var instagram : String = "",
    private var medium : String = "",
    private var photo : String = ""

):Parcelable