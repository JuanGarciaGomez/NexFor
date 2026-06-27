package com.project.nexfor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform