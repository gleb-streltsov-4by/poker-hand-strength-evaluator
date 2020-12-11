package com.assignment.poker.core.impl

import com.assignment.poker.domain.{Card, Game, GameDetails, Hand}
import org.scalatest.flatspec.AnyFlatSpec

class TexasHoldemEvaluatorTest extends AnyFlatSpec {

  val sut = new TexasHoldemEvaluator

  "TexasHoldemEvaluator" should "correctly evaluate input #1" in {
    val board = Hand(
      List(Card("Kc"), Card("8h"), Card("2c"), Card("Ah"), Card("Qd"))
    )
    val hand = Hand(
      List(Card("Th"), Card("Jc"))
    )
    val result = sut.combinations(board, hand)

    println()
  }
}
