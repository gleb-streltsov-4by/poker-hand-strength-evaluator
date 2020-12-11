package com.assignment.poker.core

import cats.implicits.catsSyntaxOptionId
import com.assignment.poker.core.impl.TexasHoldemEvaluator
import com.assignment.poker.domain.{Card, EvaluationDetail, EvaluationResult, Hand, HandType}
import org.scalatest.flatspec.AnyFlatSpec

class GenericPokerEvaluatorTest extends AnyFlatSpec {

  private[this] val sut = new TexasHoldemEvaluator()

  "TypedPokerEvaluator" should "correctly evaluate StraightFlush " in {
    val origin = Hand(List(Card("Jh"), Card("Th")))

    val combos = List(
      Hand(List(Card("2h"), Card("Ah"), Card("Jh"), Card("Th"), Card("3h"))),
      Hand(List(Card("Qh"), Card("Ah"), Card("Jh"), Card("Th"), Card("Kh"))),
      Hand(List(Card("2h"), Card("Ah"), Card("Jh"), Card("Th"), Card("Kh"))),
      Hand(List(Card("Qh"), Card("Th"), Card("Kh"), Card("Ah"), Card("2c")))
    )

    val expectedEvaluation = EvaluationResult(success = true, EvaluationDetail(
      HandType.StraightFlush,
      Hand(List(Card("Ah"), Card("Kh"), Card("Qh"), Card("Jh"), Card("Th"))),
      origin).some)

    val actualEvaluation = sut.tryStraightFlush(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "correctly evaluate FourOfKind " in {
    val origin = Hand(List(Card("Ah"), Card("Kh")))

    val combos = List(
      Hand(List(Card("Ah"), Card("Ah"), Card("Kd"), Card("Ah"), Card("Ac"))),
      Hand(List(Card("Ah"), Card("Ah"), Card("Ac"), Card("Ah"), Card("Kd"))),
      Hand(List(Card("Ac"), Card("Ah"), Card("Kd"), Card("Ah"), Card("Ah"))),
      Hand(List(Card("Qh"), Card("Th"), Card("Kh"), Card("Ah"), Card("2c")))
    )

    val expectedEvaluation = EvaluationResult(success = true, EvaluationDetail(
      HandType.FourOfKind,
      Hand(List(Card("Ah"), Card("Ah"), Card("Ah"), Card("Ac"), Card("Kd"))),
      origin).some)

    val actualEvaluation = sut.tryFourOfKind(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "correctly evaluate FullHouse " in {
    val origin = Hand(List(Card("Ah"), Card("Th")))

    val combos = List(
      Hand(List(Card("Ah"), Card("Ah"), Card("Ah"), Card("Th"), Card("Tc"))),
      Hand(List(Card("Th"), Card("Tc"), Card("Ah"), Card("Ah"), Card("Ah"))),
      Hand(List(Card("Ah"), Card("Th"), Card("Ah"), Card("Ah"), Card("Tc"))),
      Hand(List(Card("Qh"), Card("Th"), Card("Kh"), Card("Ah"), Card("2c")))
    )

    val expectedEvaluation = EvaluationResult(success = true, EvaluationDetail(
      HandType.FullHouse,
      Hand(List(Card("Ah"), Card("Ah"), Card("Ah"), Card("Th"), Card("Tc"))),
      origin).some)

    val actualEvaluation = sut.tryFullHouse(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  // Need to clarify requirements

//  "TypedPokerEvaluator" should "not evaluate FullHouse with board cards" in {
//    val origin = Hand(List(Card("Ah"), Card("Ac")))
//
//    val combos = List(
//      Hand(List(Card("Ah"), Card("Ac"), Card("Ad"), Card("Th"), Card("Tc")))
//    )
//
//    val expectedEvaluation = EvaluationResult(success = false, None)
//    val actualEvaluation = sut.tryFullHouse(origin, combos)
//
//    assert(actualEvaluation == expectedEvaluation)
//  }

  "TypedPokerEvaluator" should "correctly evaluate Flush " in {
    val origin = Hand(List(Card("Ah"), Card("Th")))

    val combos = List(
      Hand(List(Card("Th"), Card("Tc"), Card("Ah"), Card("Ah"), Card("Ah"))),
      Hand(List(Card("Ah"), Card("Th"), Card("Ah"), Card("Ah"), Card("Tc"))),
      Hand(List(Card("Ah"), Card("Ah"), Card("Ah"), Card("Th"), Card("Th"))),
      Hand(List(Card("Qh"), Card("Th"), Card("Kh"), Card("Ah"), Card("2c")))
    )

    val expectedEvaluation = EvaluationResult(success = true, EvaluationDetail(
      HandType.Flush,
      Hand(List(Card("Ah"), Card("Ah"), Card("Ah"), Card("Th"), Card("Th"))),
      origin).some)

    val actualEvaluation = sut.tryFlush(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "correctly evaluate `Straight`" in {
    val origin = Hand(List(Card("2c"), Card("4h")))

    val combos = List(
      Hand(List(Card("Th"), Card("Tc"), Card("Ah"), Card("Ah"), Card("Ah"))),
      Hand(List(Card("Ah"), Card("Th"), Card("Ah"), Card("Ah"), Card("Tc"))),
      Hand(List(Card("Ah"), Card("Ah"), Card("Ah"), Card("Th"), Card("Th"))),
      Hand(List(Card("Qh"), Card("Th"), Card("Kh"), Card("Ah"), Card("2c"))),
      Hand(List(Card("3h"), Card("4h"), Card("6h"), Card("5h"), Card("2c")))
    )

    val expectedEvaluation = EvaluationResult(success = true, EvaluationDetail(
      HandType.Straight,
      Hand(List(Card("6h"), Card("5h"), Card("4h"), Card("3h"), Card("2c"))),
      origin).some)

    val actualEvaluation = sut.tryStraight(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "correctly evaluate `ThreeOfKind`" in {
    val origin = Hand(List(Card("2c"), Card("4h")))

    val combos = List(
      Hand(List(Card("2c"), Card("Tc"), Card("Ah"), Card("4h"), Card("Ah"))),
      Hand(List(Card("4h"), Card("Th"), Card("Ah"), Card("2c"), Card("Tc"))),
      Hand(List(Card("Qh"), Card("Th"), Card("Kh"), Card("Ah"), Card("2c"))),
      Hand(List(Card("4c"), Card("4d"), Card("Th"), Card("4h"), Card("2c")))
    )

    val expectedEvaluation = EvaluationResult(success = true, EvaluationDetail(
      HandType.ThreeOfKind,
      Hand(List(Card("4c"), Card("4d"), Card("4h"), Card("Th"), Card("2c"))),
      origin).some)

    val actualEvaluation = sut.tryThreeOfKind(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "not evaluate `ThreeOfKind` with board cards" in {
    val origin = Hand(List(Card("2c"), Card("4h")))

    val combos = List(
      Hand(List(Card("2c"), Card("4h"), Card("Ac"), Card("Ah"), Card("Ad")))
    )

    val expectedEvaluation = EvaluationResult(success = false, None)
    val actualEvaluation = sut.tryThreeOfKind(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "correctly evaluate `TwoPairs`" in {
    val origin = Hand(List(Card("2c"), Card("4h")))

    val combos = List(
      Hand(List(Card("4h"), Card("Th"), Card("Ah"), Card("2c"), Card("Tc"))),
      Hand(List(Card("Qh"), Card("Th"), Card("Kh"), Card("Ah"), Card("2c"))),
      Hand(List(Card("4c"), Card("Qd"), Card("Th"), Card("4h"), Card("2c"))),
      Hand(List(Card("2c"), Card("2h"), Card("Ah"), Card("4h"), Card("4d")))
    )

    val expectedEvaluation = EvaluationResult(success = true, EvaluationDetail(
      HandType.TwoPairs,
      Hand(List(Card("4h"), Card("4d"), Card("2c"), Card("2h"), Card("Ah"))),
      origin).some)

    val actualEvaluation = sut.tryTwoPairs(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "not evaluate `TwoPairs` with board cards" in {
    val origin = Hand(List(Card("2c"), Card("4h")))

    val combos = List(
      Hand(List(Card("4h"), Card("4c"), Card("Th"), Card("2c"), Card("Tc")))
    )

    val expectedEvaluation = EvaluationResult(success = false, None)
    val actualEvaluation = sut.tryTwoPairs(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "correctly evaluate `Pair`" in {
    val origin = Hand(List(Card("2c"), Card("4h")))

    val combos = List(
      Hand(List(Card("5h"), Card("4h"), Card("Ah"), Card("2c"), Card("Tc"))),
      Hand(List(Card("Qh"), Card("Th"), Card("Kh"), Card("Ah"), Card("2c"))),
      Hand(List(Card("2c"), Card("Qd"), Card("Th"), Card("4h"), Card("4c"))),
      Hand(List(Card("Tc"), Card("5c"), Card("Ah"), Card("2h"), Card("2c")))
    )

    val expectedEvaluation = EvaluationResult(success = true, EvaluationDetail(
      HandType.Pair,
      Hand(List(Card("4h"), Card("4c"), Card("Qd"), Card("Th"), Card("2c"))),
      origin).some)

    val actualEvaluation = sut.tryPair(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "not evaluate `Pair` with board cards" in {
    val origin = Hand(List(Card("2c"), Card("4h")))

    val combos = List(
      Hand(List(Card("2c"), Card("4h"), Card("Ah"), Card("Ac"), Card("Tc")))
    )

    val expectedEvaluation = EvaluationResult(success = false, None)
    val actualEvaluation = sut.tryPair(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TypedPokerEvaluator" should "correctly evaluate `HighCard`" in {
    val origin = Hand(List(Card("6c"), Card("7h")))

    val combos = List(
      Hand(List(Card("6c"), Card("7h"), Card("4h"), Card("Qd"), Card("Jc"))),
      Hand(List(Card("6c"), Card("7h"), Card("5h"), Card("Qd"), Card("Jc"))),
      Hand(List(Card("Qd"), Card("6c"), Card("7h"), Card("5h"), Card("Jc")))
    )

    val expectedEvaluation = EvaluationResult(success = true, EvaluationDetail(
      HandType.HighCard,
      Hand(List(Card("7h"), Card("6c"))),
      origin).some)

    val actualEvaluation = sut.tryHighCard(origin, combos)

    assert(actualEvaluation == expectedEvaluation)
  }
}
