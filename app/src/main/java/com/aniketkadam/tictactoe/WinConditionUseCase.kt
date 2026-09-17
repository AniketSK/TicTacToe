package com.aniketkadam.tictactoe

import kotlinx.collections.immutable.PersistentList

sealed class WinState {
    data object InProgress : WinState()
    data class Won(
        val winner: CurrentPlayer,
        val winIndices: Set<Int>
    ) : WinState()

    data object Draw : WinState()
}

class WinConditionUseCase {
    // We only need to check from the last index point, for what the connected points are.
    // 0 1 2
    // 3 4 5
    // 6 7 8
    private val winSets = listOf(
        setOf(0, 1, 2),
        setOf(3, 4, 5),
        setOf(6, 7, 8),
        setOf(0, 4, 8),
        setOf(2, 4, 6),
        setOf(0, 3, 6),
        setOf(1, 4, 7),
        setOf(2, 5, 8)
    )

    fun checkWinCondition(
        gridState: PersistentList<CellValue>,
        lastMoveIndex: Int
    ): WinState {
        val moveCharacter = gridState[lastMoveIndex]
        // For each index, check if the set contains the index at all, then check if all
        // items in the set are the same
        // if they are the person wins
        val answer = winSets.filter { line -> line.contains(lastMoveIndex) }
            // the lines that contain the index.
            .find {
                // Map indices to characters and check if they all match the move character.
                // By the nature of the game this can only be one set of points since the game would end at that point.
                it.map { gridState[it] }.all { it == moveCharacter }
            }

        return when (answer) {
            null -> if (gridState.none { it == CellValue.Empty }) WinState.Draw else WinState.InProgress
            else -> WinState.Won(moveCharacter.toPlayer()!!, answer)
        }
    }
}