package com.assignment.poker.domain

sealed case class Game(value: String)

object Game {
  object TexasHoldem extends Game("texas-holdem")
  object OmahaHoldem extends Game("omaha-holdem")
  object FiveCardDraw extends Game("five-card-draw")
}

final case class GameDetails(game: Game, board: Hand, hands: List[Hand])
