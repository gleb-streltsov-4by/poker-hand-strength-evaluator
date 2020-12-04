package com.assignment.poker.core.impl

import org.scalatest.flatspec.AnyFlatSpec

class TexasHoldemEvaluatorTest extends AnyFlatSpec {

  private[this] val sut = new TexasHoldemEvaluator()

  "TexasHoldemEvaluator" should "correctly evaluate input #1" in {
    val input = "texas-holdem 4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d"
    val expectedEvaluation = "Ac4d=Ad4s 5d6d As9s KhKd"
    val actualEvaluation = sut.evaluate(input)

    assert(actualEvaluation == expectedEvaluation)
  }

  "TexasHoldemEvaluator" should "correctly evaluate input #2" in {
    val input = "texas-holdem 2h3h4h5d8d KdKs 9hJh"
    val expectedEvaluation = "KdKs 9hJh"
    val actualEvaluation = sut.evaluate(input)

    assert(actualEvaluation == expectedEvaluation)
  }
}
