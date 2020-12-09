package com.assignment.poker.parser

import com.assignment.poker.domain.{Game, GameDetails, Hand}

import scala.language.implicitConversions

sealed trait Parser[I] {
  def parse(input: I): GameDetails
}

class StringParser extends Parser[String] {

  implicit def toGame(s: String): Game = Game.apply(s)
  implicit def toHand(s: String): Hand = Hand(s)
  implicit def toHandList(arr: List[String]): List[Hand] = arr.map(s => Hand(s))

  override def parse(input: String): GameDetails = {
    input.split(" ") match {
      case data @ Array(game, handValue, _*) => GameDetails(game, handValue, data.drop(2).toList)
    }
  }
}

