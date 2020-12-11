package com.assignment.poker.parser

import com.assignment.poker.domain.{Game, GameDetails, Hand}

import scala.language.implicitConversions

sealed trait Parser[I] {
  def parse(input: I): GameDetails
}

class StringParser extends Parser[String] {

  implicit def toHand(s: String): Hand = Hand(s)
  implicit def toHandList(arr: List[String]): List[Hand] = arr.map(s => Hand(s))

  override def parse(input: String): GameDetails = {
    input.split(" ").toList match {
      case "texas-holdem" :: board :: hands => GameDetails(Game.TexasHoldem, board, hands)
      case "omaha-holdem" :: board :: hands => GameDetails(Game.OmahaHoldem, board, hands)
      case "five-card-draw" :: hands        => GameDetails(Game.FiveCardDraw, Hand(List.empty), hands)
    }
  }
}

