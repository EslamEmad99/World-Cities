package com.example.data.util

import com.example.data.model.CityDto

class CitySearchTrie {

    private val root = TrieNode()

    fun insert(city: CityDto) {
        val keys = listOf(city.name.lowercase(), city.country.lowercase())
        for (key in keys) {
            var node = root
            for (char in key) {
                node = node.children.getOrPut(char) { TrieNode() }
            }
            node.isEndOfWord = true
            node.cities.add(city)
        }
    }

    fun search(prefix: String): List<CityDto> {
        var node = root
        for (char in prefix.lowercase()) {
            node = node.children[char] ?: return emptyList()
        }
        return collectCities(node)
    }

    private fun collectCities(node: TrieNode): List<CityDto> {
        val result = mutableListOf<CityDto>()
        val stack = ArrayDeque<TrieNode>()
        stack.add(node)

        while (stack.isNotEmpty()) {
            val current = stack.removeLast()
            result.addAll(current.cities)
            stack.addAll(current.children.values)
        }

        return result.distinctBy { it.id }
    }
}