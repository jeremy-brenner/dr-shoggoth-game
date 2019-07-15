package net.drshoggoth.games.drshoggoth.responses


data class GameResponse(val done: Boolean = false, val pause: Boolean = false): UpdateResponse()