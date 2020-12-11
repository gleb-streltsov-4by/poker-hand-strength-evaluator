package com.assignment.poker.core

import cats.implicits.catsSyntaxOptionId
import com.assignment.poker.core.impl.{FiveCardDrawEvaluator, OmahaHoldemEvaluator, TexasHoldemEvaluator}
import com.assignment.poker.domain.{Card, EvaluationDetail, Game, GameDetails, Hand, HandType}
import com.assignment.poker.parser.StringParser

import scala.annotation.tailrec

sealed trait GenericPokerEvaluator[I, O] {
  def evaluate(input: I): O
}

trait TypedPokerEvaluator extends GenericPokerEvaluator[GameDetails, List[EvaluationDetail]] {

  def evaluate(gameDetails: GameDetails): List[EvaluationDetail] = {
    val hands = gameDetails.hands
    val board = gameDetails.board

    for {
      hand  <- hands
      combos      = combinations(hand, board)
      evaluation  = strongest(hand, combos)
    } yield evaluation
  }

  // TODO: close the API (only evaluate is required)

  def combinations(originHand: Hand, board: Hand): List[Hand]

  def strongest(origin: Hand, combos: List[Hand]): EvaluationDetail = {
    val results = evaluations.view.map(_(origin, combos)).takeWhile(_.success).force

    results.last.detail.head
  }

  def tryStraightFlush(origin: Hand, combos: List[Hand]): EvaluationResult = {
    val result = combos.filter(combo => combo.isConsecutive && combo.isSameSuit)

    if (result.isEmpty) EvaluationResult(success = false, None)
    else EvaluationResult(success = true,
      EvaluationDetail(HandType.StraightFlush, Hand(result.head.sortedCards), origin).some)
  }

  def tryFourOfKind(origin: Hand, combos: List[Hand]): EvaluationResult = {
    val result = for {
      combo <- combos

      foundQuad = combo.quads.collect { case (_ , cards) => cards }.toList

      if foundQuad.nonEmpty
      quad = foundQuad.head
    } yield quad ++ combo.cards.filterNot(c => quad.contains(c))

    if (result.isEmpty) EvaluationResult(success = false, None)
    else EvaluationResult(success = true,
      EvaluationDetail(HandType.FourOfKind, Hand(result.head), origin).some)
  }

  def tryFullHouse(origin: Hand, combos: List[Hand]): EvaluationResult = {
    val result = for {
      combo <- combos

      foundTriple = combo.triple.collect { case (_ , cards) => cards }.toList
      foundPair = combo.pairs.collect { case (_ , cards) => cards }.toList

      if foundTriple.nonEmpty && foundPair.nonEmpty

      trip = foundTriple.head
      pair = foundPair.head
    } yield trip ++ pair

    if (result.isEmpty) EvaluationResult(success = false, None)
    else EvaluationResult(success = true,
      EvaluationDetail(HandType.FullHouse, Hand(result.head), origin).some)
  }

  def tryFlush(origin: Hand, combos: List[Hand]): EvaluationResult = {
    val result = for {
      combo <- combos
      if combo.isSameSuit
    } yield combo

    if (result.isEmpty) EvaluationResult(success = false, None)
    else EvaluationResult(success = true,
      EvaluationDetail(HandType.Flush, result.head, origin).some)
  }

  def tryStraight(origin: Hand, combos: List[Hand]): EvaluationResult = {
    val result = for {
      combo <- combos
      if combo.isConsecutive
    } yield combo

    if (result.isEmpty) EvaluationResult(success = false, None)
    else EvaluationResult(success = true,
      EvaluationDetail(HandType.Straight, Hand(result.head.sortedCards), origin).some)
  }

  def tryThreeOfKind(origin: Hand, combos: List[Hand]): EvaluationResult = {
    val result = for {
      combo <- combos
      foundTriples = combo.triple.collect { case (_ , cards) => cards }.toList
      if foundTriples.nonEmpty
      triple = foundTriples.head
    } yield triple ++ Hand(combo.cards.filterNot(c => triple.contains(c))).sortedCards

    if (result.isEmpty) EvaluationResult(success = false, None)
    else EvaluationResult(success = true,
      EvaluationDetail(HandType.ThreeOfKind, Hand(result.head), origin).some)
  }

  def tryTwoPairs(origin: Hand, combos: List[Hand]): EvaluationResult = {
    val result = for {
      combo <- combos
      foundPairs = combo.pairs.collect { case (_ , cards) => cards }.toList
      if foundPairs.nonEmpty && foundPairs.length == 2
      pairs = Hand(foundPairs.flatten).sortedCards
    } yield pairs ++ combo.cards.filterNot(c => pairs.contains(c))

    if (result.isEmpty) EvaluationResult(success = false, None)
    else EvaluationResult(success = true,
      EvaluationDetail(HandType.TwoPairs, Hand(result.head), origin).some)
  }

  def tryPair(origin: Hand, combos: List[Hand]): EvaluationResult = {
    val result = for {
      combo <- combos
      foundPairs = combo.pairs.collect { case (_ , cards) => cards }.toList
      if foundPairs.nonEmpty
      pair = foundPairs.head
    } yield pair ++ Hand(combo.cards.filterNot(c => pair.contains(c))).sortedCards

    if (result.isEmpty) EvaluationResult(success = false, None)
    else EvaluationResult(success = true,
      EvaluationDetail(HandType.Pair, Hand(result.head), origin).some)
  }

  def tryHighCard(origin: Hand, combos: List[Hand]): EvaluationResult = ???

  def evaluations: List[(Hand, List[Hand]) => EvaluationResult] = {
    List(
      tryStraightFlush,
      tryFourOfKind,
      tryFullHouse,
      tryFlush,
      tryStraight,
      tryThreeOfKind,
      tryTwoPairs,
      tryPair,
      tryHighCard
    )
  }
}

final case class EvaluationResult(success: Boolean, detail: Option[EvaluationDetail])

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
        case a :: Nil       => acc + a.origin.cards
        case a :: b :: Nil  => acc + mkLastStr(a, b)
        case a :: b :: tail =>
          converterHelper(acc + mkStr(a, b), b :: tail)
      }
    }

    def mkLastStr(a: EvaluationDetail, b: EvaluationDetail): String = {
      if (a.compareTo(b) == 0) s"${a.origin.cards}=${b.origin.cards}"
      else s"${a.origin.cards} ${b.origin.cards}"
    }

    def mkStr(a: EvaluationDetail, b: EvaluationDetail): String = {
      if (a.compareTo(b) == 0) s"${a.origin.cards}="
      else s"${a.origin.cards} "
    }

    converterHelper("", evaluationDetails)
  }
}
