package com.test.test.domain.models

data class Post(
    val id: Int,
    val title: String,
    val slug: String,
    val description: String,
    val date: String,
    val urlToImage: String,
)