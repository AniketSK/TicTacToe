# The way the code is organized:
The project follows a mvvm architecture with a UiState that encapsulates everything that's necessary to show on the UI.
The UI can request operations like:
* Reset game
* Make a move

When those operations are initated, the data is processed, UiState is updated and the jetpack compose UI responds.

Notes:
PersistentList is used because unlike a regular list, which is mutable and compose might not know it has changed if one
element in it changes, the persistentlist is guaranteed to be a different object when it changes which isn't true for even
a read-only List.

# Logic:
All business logic is orchestrated from [TicTacToeVm].
The two methods it exposes are:
* `playerMove` which is called when a player taps a box to mark it.
* `resetGame` which clears state and returns the game to default. It may be called any time after a single move is mode.

When `playerMove` is called, it makes necessary changes to [UiState] such as who the current player is, if there's a win,
draw etc.

[WinConditionUseCase] contains the use case that calculates if the board has reached a win/draw condition or is still in-progress.

# UI
The main UI screen is in [MainActivity]
The UI is built from parts such as:
[GameUi] contains the entire game screen, title and the board.

The title that tells you the state of the game Who's Turn/ Draw / Win.
[TicTacToeGrid] The grid for the tic tac toe game.
[TicTacToeCell] which is each cell of the grid, i.e one box that can contain X's or O's.


## Thoughts on how to proceed. (I have not pre-coded this, just thought about it)
A new button will be added for Play Computer. It allows the user to select whether they want the computer
to play X or O.
A new use case will be added for computer moving, I'll start by setting it to make a move in any 'empty' slot at random.
Then it can prioritise acting in this order:
1. Win with the next move if you can 
2. Block the player from winning if you can
3. Otherwise add an item to the same row you already have one.

It should be triggered from the current player aspect of UiState.

