package com.assignment.poker.domain

import org.scalatest.flatspec.AnyFlatSpec

class EvaluationDetailTest extends AnyFlatSpec {

  "EvaluationDetail" should "correctly compare `Straight` and `Flush` evaluations" in {
    val sortedHand1 = Hand(
      List(Card("Ah"), Card("Kc"), Card("Qd"), Card("Jc"), Card("Th"))
    )
    val hand1 = Hand(
      List(Card("Kc"), Card("Th"), Card("Jc"), Card("Ah"), Card("Qd"))
    )
    val straightEvaluation = EvaluationDetail(HandType.Straight, sortedHand1, hand1)

    val sortedHand2 = Hand(
      List(Card("Kc"), Card("Qc"), Card("Tc"), Card("5c"), Card("4c"))
    )
    val hand2 = Hand(
      List(Card("4c"), Card("Kc"), Card("Tc"), Card("5c"),  Card("Qc"))
    )
    val flushEvaluation = EvaluationDetail(HandType.Flush, sortedHand2, hand2)

    assert(straightEvaluation.compareTo(flushEvaluation) == -1)
  }

  "EvaluationDetail" should "correctly compare equal `Flush` evaluations" in {
    val sortedHand1 = Hand(
      List(Card("Kc"), Card("Jc"), Card("Tc"), Card("5c"), Card("4c"))
    )
    val hand1 = Hand(
      List(Card("4c"), Card("Kc"), Card("Tc"), Card("5c"),  Card("Qc"))
    )
    val flushEvaluation1 = EvaluationDetail(HandType.Flush, sortedHand1, hand1)

    val sortedHand2 = Hand(
      List(Card("Kc"), Card("Qc"), Card("Tc"), Card("5c"), Card("4c"))
    )
    val hand2 = Hand(
      List(Card("4c"), Card("Kc"), Card("Tc"), Card("5c"),  Card("Qc"))
    )
    val flushEvaluation2 = EvaluationDetail(HandType.Flush, sortedHand2, hand2)

    assert(flushEvaluation1.compareTo(flushEvaluation2) == -1)
  }

  "EvaluationDetail" should "correctly compare not equal `Straight` evaluations" in {
    val sortedHand1 = Hand(
      List(Card("Ah"), Card("Kc"), Card("Qd"), Card("Jc"), Card("Th"))
    )
    val hand1 = Hand(
      List(Card("Kc"), Card("Th"), Card("Jc"), Card("Ah"), Card("Qd"))
    )
    val straightEvaluation1 = EvaluationDetail(HandType.Straight, sortedHand1, hand1)

    val sortedHand2 = Hand(
      List(Card("Kc"), Card("Qd"), Card("Jc"), Card("Th"), Card("9c"))
    )
    val hand2 = Hand(
      List(Card("Kc"), Card("Th"), Card("9c"), Card("Jc"), Card("Qd"))
    )
    val straightEvaluation2 = EvaluationDetail(HandType.Straight, sortedHand2, hand2)

    assert(straightEvaluation1.compareTo(straightEvaluation2) == 1)
  }

  "EvaluationDetail" should "correctly compare equal `Straight` evaluations" in {
    val sortedHand1 = Hand(
      List(Card("Ah"), Card("Kc"), Card("Qd"), Card("Jc"), Card("Th"))
    )
    val hand1 = Hand(
      List(Card("Kc"), Card("Th"), Card("Jc"), Card("Ah"), Card("Qd"))
    )
    val straightEvaluation1 = EvaluationDetail(HandType.Straight, sortedHand1, hand1)

    val sortedHand2 = Hand(
      List(Card("Ac"), Card("Kc"), Card("Qh"), Card("Jc"), Card("Td"))
    )
    val hand2 = Hand(
      List(Card("Kc"), Card("Td"), Card("Jc"), Card("Ac"), Card("Qh"))
    )
    val straightEvaluation2 = EvaluationDetail(HandType.Straight, sortedHand2, hand2)

    assert(straightEvaluation1.compareTo(straightEvaluation2) == 0)
  }

}
