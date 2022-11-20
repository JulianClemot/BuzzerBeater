package com.julian.buzzerbeater

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform