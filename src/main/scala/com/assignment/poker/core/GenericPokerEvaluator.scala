package com.assignment.poker.core

import cats.implicits.catsSyntaxOptionId
import com.assignment.poker.domain.{EvaluationDetail, EvaluationResult, GameDetails, Hand, HandType}

trait GenericPokerEvaluator extends AbstractPokerEvaluator[GameDetails, List[EvaluationDetail]] {

  def evaluate(gameDetails: GameDetails): List[EvaluationDetail] = {
    val hands = gameDetails.hands
    val board = gameDetails.board

    for {
      hand  <- hands
      combos      = combinations(hand, board)
      evaluation  = strongest(hand, combos)
    } yield evaluation
  }

  def combinations(originHand: Hand, board: Hand): List[Hand]

  def strongest(origin: Hand, combos: List[Hand]): EvaluationDetail = {
    val result = evaluations.map(_(origin, combos)).takeWhile(_.success == false)
    val evaluation = evaluations(result.length)(origin, combos)
    evaluation.detail.head
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

      triplesValues = foundTriples.head.map(card => card.value)
      originValues = origin.cards.map(_.value)

      if triplesValues.intersect(originValues).nonEmpty

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
      foundPair = combo.pairs.collect { case (_ , cards) => cards }.toList
      if foundPair.nonEmpty

      pairValues = foundPair.head.map(_.value)
      originValues = origin.cards.map(_.value)

      if pairValues.intersect(originValues).nonEmpty

      pair = foundPair.head
    } yield pair ++ Hand(combo.cards.filterNot(c => pair.contains(c))).sortedCards

    if (result.isEmpty) EvaluationResult(success = false, None)
    else EvaluationResult(success = true,
      EvaluationDetail(HandType.Pair, Hand(result.head), origin).some)
  }

  def tryHighCard(origin: Hand, combos: List[Hand]): EvaluationResult = {
    EvaluationResult(success = true,
      EvaluationDetail(HandType.HighCard, Hand(origin.sortedCards), origin).some)
  }

  private[this] def evaluations: List[(Hand, List[Hand]) => EvaluationResult] = {
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
