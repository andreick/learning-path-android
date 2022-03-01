package com.example.core.usecase

import com.example.core.data.Note

class GetWordCount {

    operator fun invoke(note: Note) = getCount(note.title) + getCount(note.content)

    private fun getCount(str: String) =
        str.split(" ")
            .filter { it.contains(Regex(".*[A-Za-z].*")) }
            .count()
}