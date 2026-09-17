package com.aniketkadam.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.TextAutoSize
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
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GridPreview() {
    val initialList =
        persistentListOf<CellValue>().mutate { mutableList -> repeat(9) { mutableList.add(CellValue.O) } }
    val data: PersistentList<CellValue> by remember { mutableStateOf(initialList) }
    TicTacToeGrid(data)
}

@Composable
fun TicTacToeGrid(gridData: PersistentList<CellValue>) {
    LazyVerticalGrid(
        modifier = Modifier.background(Color.Blue),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(gridData) { cell ->
            TicTacToeCell(cell)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CellPreview() {
    TicTacToeCell(CellValue.O)
}

enum class CellValue {
    X,
    O,
    Empty
}

@Composable
fun TicTacToeCell(cellValue: CellValue) {
    fun getTextValueForCell(cellValue: CellValue): String = when (cellValue) {
        CellValue.X -> "X"
        CellValue.O -> "O"
        CellValue.Empty -> ""
    }

    Box(
        modifier = Modifier
            .size(100.dp)
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