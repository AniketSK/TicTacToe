package com.aniketkadam.tictactoe

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate

fun PersistentList<CellValue>.defaultGrid(cell : CellValue = CellValue.O) = this.mutate { mutableList ->
    repeat(9) {
        mutableList.add(cell)
    }
}