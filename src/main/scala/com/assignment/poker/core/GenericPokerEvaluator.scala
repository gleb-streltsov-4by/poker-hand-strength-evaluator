package com.assignment.poker.core

import com.assignment.poker.core.impl.{
  FiveCardDrawEvaluator,
  OmahaHoldemEvaluator,
  TexasHoldemEvaluator
}
import com.assignment.poker.domain.{EvaluationDetail, Game, GameDetails}
import com.assignment.poker.parser.StringParser

import scala.annotation.tailrec

sealed trait GenericPokerEvaluator[I, O] {
  def evaluate(input: I): O
}

trait TypedPokerEvaluator extends GenericPokerEvaluator[GameDetails, List[EvaluationDetail]] {
  def evaluate(gameDetails: GameDetails): List[EvaluationDetail]
}

object ConsolePokerEvaluator {

  private val parser = new StringParser()

  def evaluate(input: String): String = {
    val gameDetails = parser.parse(input)

    // TODO: handle card duplicates
    val evaluator = gameDetails.game match {
      case Game.TexasHoldem   => new TexasHoldemEvaluator
      case Game.OmahaHoldem   => new OmahaHoldemEvaluator
      case Game.FiveCardDraw  => new FiveCardDrawEvaluator // current approach won't work with FiveCardDraw
    }

    val evaluationDetails = evaluator.evaluate(gameDetails)
    toString(evaluationDetails)
  }

  private def toString(evaluationDetails: List[EvaluationDetail]): String = {

    @tailrec
    def converterHelper(acc: String, evaluationDetails: List[EvaluationDetail]): String = {
      evaluationDetails match {
        case Nil            => acc
        case a :: Nil       => acc + a.hand.cards
        case a :: b :: Nil  => acc + mkLastStr(a, b)
        case a :: b :: tail =>
          converterHelper(acc + mkStr(a, b), b :: tail)
      }
    }

    def mkLastStr(a: EvaluationDetail, b: EvaluationDetail): String = {
      if (a.compareTo(b) == 0) s"${a.hand.cards}=${b.hand.cards}"
      else s"${a.hand.cards} ${b.hand.cards}"
    }

    def mkStr(a: EvaluationDetail, b: EvaluationDetail): String = {
      if (a.compareTo(b) == 0) s"${a.hand.cards}="
      else s"${a.hand.cards} "
    }

    converterHelper("", evaluationDetails)
  }
}
