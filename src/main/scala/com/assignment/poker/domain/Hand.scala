package com.assignment.poker.domain

final case class Hand(cards: List[Card]) {
  override def toString: String = cards.mkString("")
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