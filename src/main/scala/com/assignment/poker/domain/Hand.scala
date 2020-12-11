package com.assignment.poker.domain

final case class Hand(cards: List[Card]) {

  def sortedCards: List[Card] = this.cards.sortWith(_.value > _.value)

  def isSameSuit: Boolean = {
    cards.groupBy(_.suit).toList.length == 1
  }

  def isConsecutive: Boolean = {
    val sorted = sortedCards
    (sorted zip sorted.tail) forall { case (c1, c2) => c1.value - 1 == c2.value}
  }

  def pairs: Map[String, List[Card]] = group(2)
  def triple: Map[String, List[Card]] = group(3)
  def quads: Map[String, List[Card]] = group(4)

  override def toString: String = cards.mkString("")

  private def group(n: Int): Map[String, List[Card]] = {
    this.cards
      .groupBy(_.face)
      .filter { case (_, lst) => lst.length == n }
  }
}

object Hand {
  def apply(str: String): Hand = {
    val cards = str.grouped(2).map(s => Card.apply(s)).toList
    Hand(cards)
  }
}

sealed case class HandType(order: Int)

object HandType {
  object StraightFlush  extends HandType(0)
  object FourOfKind     extends HandType(1)
  object FullHouse      extends HandType(2)
  object Flush          extends HandType(3)
  object Straight       extends HandType(4)
  object ThreeOfKind    extends HandType(5)
  object TwoPairs       extends HandType(6)
  object Pair           extends HandType(7)
  object HighCard       extends HandType(8)
}