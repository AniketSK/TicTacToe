package com.aniketkadam.tictactoe

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aniketkadam.tictactoe.ui.theme.TicTacToeTheme

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
fun GreetingPreview() {
    TicTacToeTheme {
        Greeting("Android")
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
            .border(width = 2.dp, color = androidx.compose.ui.graphics.Color(Color.BLUE)),
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