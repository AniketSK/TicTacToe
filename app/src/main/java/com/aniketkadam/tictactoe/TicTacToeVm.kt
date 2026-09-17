package com.aniketkadam.tictactoe

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

enum class CurrentPlayer {
    X,
    O;

    fun toCellType(): CellValue = when (this) {
        X -> CellValue.X
        O -> CellValue.O
    }
}

data class UiState(
    val currentPlayer: CurrentPlayer,
    val gridState: PersistentList<CellValue>,
    val winState: WinState
)

class TicTacToeVm(val winConditionUseCase: WinConditionUseCase = WinConditionUseCase()) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(getDefaultGameState())

    val uiState: StateFlow<UiState> = _uiState

    // When the player moves, the grid is mutated to update the cell with a move for the current player.
    fun playerMove(cellIndex: Int) {
        _uiState.update { state ->
            // Once the game ends, ignore clicks.
            if(state.winState != WinState.InProgress)
                return

            // Add the move to the grid
            val updatedGrid = state.gridState.mutate {
                it[cellIndex] = state.currentPlayer.toCellType()
            }

            // Toggle the current player
            val curPlayer = if(state.currentPlayer == CurrentPlayer.X) CurrentPlayer.O else CurrentPlayer.X

            // Check if the game is won/draw/inprogress
            val updatedGameState = winConditionUseCase.checkWinCondition(updatedGrid, cellIndex)


            // Set the updated state
            state.copy(gridState = updatedGrid,
                winState = updatedGameState,
                currentPlayer = curPlayer)
        }
    }

    fun resetGame() {
        _uiState.update { getDefaultGameState() }
    }

    private fun getDefaultGameState() =
        UiState(CurrentPlayer.X, getInitialGridState(), WinState.InProgress)

    private fun getInitialGridState() =
        persistentListOf<CellValue>().defaultGrid(CellValue.Empty)
}