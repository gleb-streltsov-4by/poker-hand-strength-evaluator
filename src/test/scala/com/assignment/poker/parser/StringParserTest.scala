package com.assignment.poker.parser

import com.assignment.poker.domain.{Game, GameDetails, Hand}
import org.scalatest.flatspec.AnyFlatSpec

class StringParserTest extends AnyFlatSpec{

  private[this] val sut = new StringParser()

  "StringParser" should "correctly parse input #1" in {
    val input = "texas-holdem 4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d"
    val actual = sut.parse(input)

    val expected = GameDetails(
      Game.TexasHoldem,
      Hand("4cKs4h8s7s"),
      List(
        Hand("Ad4s"),
        Hand("Ac4d"),
        Hand("As9s"),
        Hand("KhKd"),
        Hand("5d6d")
      )
    )

    assert(actual == expected)
  }

  "StringParser" should "correctly parse input #2" in {
    val input = "texas-holdem 2h3h4h5d8d KdKs 9hJh"
    val actual = sut.parse(input)

    val expected = GameDetails(
      Game.TexasHoldem,
      Hand("2h3h4h5d8d"),
      List(
        Hand("KdKs"),
        Hand("9hJh")
      )
    )

    assert(actual == expected)
  }

  "StringParser" should "correctly parse input #3" in {
    val input = "omaha-holdem 3d3s4d6hJc Js2dKd8c KsAsTcTs Jh2h3c9c Qc8dAd6c 7dQsAc5d"
    val actual = sut.parse(input)

    val expected = GameDetails(
      Game.OmahaHoldem,
      Hand("3d3s4d6hJc"),
      List(
        Hand("Js2dKd8c"),
        Hand("KsAsTcTs"),
        Hand("Jh2h3c9c"),
        Hand("Qc8dAd6c"),
        Hand("7dQsAc5d")
      )
    )

    assert(actual == expected)
  }

  "StringParser" should "correctly parse input #4" in {
    val input = "five-card-draw 7h4s4h8c9h Tc5h6dAc5c Kd9sAs3cQs Ah9d6s2cKh 4c8h2h6c9c"
    val actual = sut.parse(input)

    val expected = GameDetails(
      Game.FiveCardDraw,
      Hand(List()),
      List(
        Hand("7h4s4h8c9h"),
        Hand("Tc5h6dAc5c"),
        Hand("Kd9sAs3cQs"),
        Hand("Ah9d6s2cKh"),
        Hand("4c8h2h6c9c")
      )
    )

    assert(actual == expected)
  }
}
