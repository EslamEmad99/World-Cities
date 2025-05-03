package com.example.data.util

import com.example.data.model.CityDto

class TrieNode {
    val children = mutableMapOf<Char, TrieNode>()
    val cities = mutableListOf<CityDto>()
    var isEndOfWord = false
}