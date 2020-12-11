package com.assignment.poker

import org.scalatest.flatspec.AnyFlatSpec

class ConsolePokerEvaluatorTest extends AnyFlatSpec {

  "ConsolePokerEvaluator" should "correctly evaluate input #1" in {
    val input = "texas-holdem 4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d"
    val expectedEvaluation = "Ac4d=Ad4s 5d6d As9s KhKd"
    val actualEvaluation = ConsolePokerEvaluatorRunner.process(input)

    assert(actualEvaluation == expectedEvaluation)
  }

  "ConsolePokerEvaluator" should "correctly evaluate input #2" in {
    val input = "texas-holdem 2h3h4h5d8d KdKs 9hJh"
    val expectedEvaluation = "KdKs 9hJh"
    val actualEvaluation = ConsolePokerEvaluatorRunner.process(input)

    assert(actualEvaluation == expectedEvaluation)
  }

  "ConsolePokerEvaluator" should "correctly evaluate input #3" in {
    val input = "omaha-holdem 3d3s4d6hJc Js2dKd8c KsAsTcTs Jh2h3c9c Qc8dAd6c 7dQsAc5d"
    val expectedEvaluation = "Qc8dAd6c KsAsTcTs Js2dKd8c 7dQsAc5d Jh2h3c9c"
    val actualEvaluation = ConsolePokerEvaluatorRunner.process(input)

    assert(actualEvaluation == expectedEvaluation)
  }

  "ConsolePokerEvaluator" should "correctly evaluate input #4" in {
    val input = "five-card-draw 7h4s4h8c9h Tc5h6dAc5c Kd9sAs3cQs Ah9d6s2cKh 4c8h2h6c9c"
    val expectedEvaluation = "4c8h2h6c9c Ah9d6s2cKh Kd9sAs3cQs 7h4s4h8c9h Tc5h6dAc5c"
    val actualEvaluation = ConsolePokerEvaluatorRunner.process(input)

    assert(actualEvaluation == expectedEvaluation)
  }

  "ConsolePokerEvaluator" should "throw a card error" in {
    val input = "five-card-draw Tc5h6dAc12c"
    val expectedEvaluation = "Error: requirement failed: Invalid card ..."
    val actualEvaluation = ConsolePokerEvaluatorRunner.process(input)

    assert(actualEvaluation == expectedEvaluation)
  }

  "ConsolePokerEvaluator" should "throw a parse error" in {
    val input = "invalid"
    val expectedEvaluation = "Error: parse error ..."
    val actualEvaluation = ConsolePokerEvaluatorRunner.process(input)

    assert(actualEvaluation == expectedEvaluation)
  }

}
