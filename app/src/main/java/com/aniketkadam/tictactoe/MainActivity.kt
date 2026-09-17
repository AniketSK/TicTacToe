package com.aniketkadam.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aniketkadam.tictactoe.ui.theme.TicTacToeTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val vm by viewModels<TicTacToeVm>()
                    val uiState by vm.uiState.collectAsStateWithLifecycle()
                    GameUi(innerPadding, uiState, vm::playerMove, vm::resetGame)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameUiPreview() {
    GameUi(
        PaddingValues(0.dp),
        UiState(
            CurrentPlayer.X,
            persistentListOf<CellValue>().mutate { mutableList ->
                repeat(9) {
                    mutableList.add(CellValue.O)
                }
            },
            WinState.InProgress
        ), {}, {})
}

@Composable
fun GameUi(
    innerPadding: PaddingValues,
    uiState: UiState,
    onPlayerMove: (Int) -> Unit,
    resetGame: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val text = remember(uiState.winState, uiState.currentPlayer) {
            when (uiState.winState) {
                WinState.Draw -> "Draw!"
                WinState.InProgress -> "Current Player: ${uiState.currentPlayer}"
                is WinState.Won -> "${uiState.winState.winner} wins!"
            }
        }

        Text(text)
        if (uiState.winState != WinState.InProgress) {
            Button(resetGame) { Text("Reset Game") }
        }

        TicTacToeGrid(uiState.gridState, onPlayerMove)
    }
}

@Preview(showBackground = true)
@Composable
fun GridPreview() {
    val data: PersistentList<CellValue> by remember {
        mutableStateOf(persistentListOf<CellValue>().mutate { mutableList ->
            repeat(
                9
            ) { mutableList.add(CellValue.O) }
        })
    }
    TicTacToeGrid(data, {})
}

@Composable
fun TicTacToeGrid(gridData: PersistentList<CellValue>, onTap: (Int) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier
            .size(600.dp)
            .background(Color.Blue),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(gridData) { idx, cell ->
            TicTacToeCell(cell, { onTap(idx) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CellPreview() {
    TicTacToeCell(CellValue.O, {})
}

enum class CellValue {
    X,
    O,
    Empty;

    fun toPlayer() = when (this) {
        X -> CurrentPlayer.X
        O -> CurrentPlayer.O
        Empty -> null
    }
}

@Composable
fun TicTacToeCell(cellValue: CellValue, onTap: () -> Unit) {
    fun getTextValueForCell(cellValue: CellValue): String = when (cellValue) {
        CellValue.X -> "X"
        CellValue.O -> "O"
        CellValue.Empty -> ""
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .clickable { onTap() }
            .background(Color.White),
        Alignment.Center
    ) {
        // Your content here
        val cellTextValue by remember(cellValue) {
            mutableStateOf(
                getTextValueForCell(cellValue)
            )
        }
        Text(cellTextValue, autoSize = TextAutoSize.StepBased())
    }
}