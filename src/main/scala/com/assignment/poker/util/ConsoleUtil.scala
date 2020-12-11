package com.assignment.poker.util

import com.assignment.poker.domain.{Card, EvaluationDetail}

import scala.annotation.tailrec

object ConsoleUtil {

  implicit class CardListPrinter(cards: List[Card]) {
    def print: String = cards.mkString("")
  }

  implicit def evaluationsToString(evaluationDetails: List[EvaluationDetail]): String = {

    @tailrec
    def converterHelper(acc: String, evaluationDetails: List[EvaluationDetail]): String = {
      evaluationDetails match {
        case Nil            => acc
        case head :: Nil    => acc + head.origin.cards.print
        case head :: tail   => converterHelper(acc + mkStr(head, tail.head), tail)
      }
    }

    def mkStr(a: EvaluationDetail, b: EvaluationDetail): String = {
      if (a.compareTo(b) == 0)
        s"${a.origin.cards.print}="
      else
        s"${a.origin.cards.print} "
    }

    converterHelper("", evaluationDetails)
  }
}
