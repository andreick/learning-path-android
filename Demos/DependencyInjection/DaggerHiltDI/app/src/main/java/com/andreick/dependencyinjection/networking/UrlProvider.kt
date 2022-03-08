package com.andreick.dependencyinjection.networking

import com.andreick.dependencyinjection.Constants

class UrlProvider {

    fun getMainBaseUrl() = Constants.BASE_URL

    fun getOtherBaseUrl() = "other_base_url"
}