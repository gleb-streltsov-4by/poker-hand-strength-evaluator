package com.assignment.poker.parser

import com.assignment.poker.domain.{Game, Hand}
import scala.language.implicitConversions

trait Parser[I] {
  def parse(input: I): GameDetails
}

class StringParser extends Parser[String] {

  implicit def toGame(s: String): Game = Game.apply(s)
  implicit def toHand(s: String): Hand = Hand(s)
  implicit def toHandList(arr: Array[String]): List[Hand] = arr.map(s => Hand(s)).toList

  override def parse(input: String): GameDetails = {
    input.split(" ") match {
      case data @ Array(game, handValue, _*) => GameDetails(game, handValue, data.drop(2))
    }
  }
}

case class GameDetails(game: Game, handValue: Hand, hands: List[Hand])


