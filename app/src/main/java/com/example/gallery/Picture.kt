package com.example.gallery

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Picture (val uriString: String, val tags: ArrayList<String>) {
    val date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    val similarPictures = ArrayList<String>()
}