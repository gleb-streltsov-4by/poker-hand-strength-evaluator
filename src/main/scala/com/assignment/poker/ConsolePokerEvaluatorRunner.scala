package com.assignment.poker

import com.assignment.poker.core.impl.{
  FiveCardDrawEvaluator,
  OmahaHoldemEvaluator,
  TexasHoldemEvaluator
}
import com.assignment.poker.domain.Game
import com.assignment.poker.parser.StringParser
import com.assignment.poker.util.ConsoleUtil.evaluationsToString

object ConsolePokerEvaluatorRunner {

  private val parser = new StringParser()

  def main(args: Array[String]): Unit = {
    println(evaluate(args.mkString(" ")))
  }

  def evaluate(input: String): String = {
    try {
      val gameDetails = parser.parse(input)

      val evaluator = gameDetails.game match {
        case Game.TexasHoldem   => new TexasHoldemEvaluator
        case Game.OmahaHoldem   => new OmahaHoldemEvaluator
        case Game.FiveCardDraw  => new FiveCardDrawEvaluator
        case _                  => throw new RuntimeException("unexpected game evaluation...")
      }

      val evaluationDetails = evaluator.evaluate(gameDetails)
      val sortedEvaluationDetails = evaluationDetails.sortWith((e1, e2) => e1.compareTo(e2) <= 0)

      sortedEvaluationDetails
    } catch {
      case e: Exception => s"Error: ${e.getMessage}"
    }
  }
}
