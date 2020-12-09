package com.assignment.poker.core

import org.scalatest.flatspec.AnyFlatSpec

class ConsolePokerEvaluatorTest extends AnyFlatSpec {

  "ConsolePokerEvaluator" should "correctly evaluate input #1" in {
    val input = "texas-holdem 4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d"
    val expectedEvaluation = "Ac4d=Ad4s 5d6d As9s KhKd"
    val actualEvaluation = ConsolePokerEvaluator.evaluate(input)

    assert(actualEvaluation == expectedEvaluation)
  }

  "ConsolePokerEvaluator" should "correctly evaluate input #2" in {
    val input = "texas-holdem 2h3h4h5d8d KdKs 9hJh"
    val expectedEvaluation = "KdKs 9hJh"
    val actualEvaluation = ConsolePokerEvaluator.evaluate(input)

    assert(actualEvaluation == expectedEvaluation)
  }
}
