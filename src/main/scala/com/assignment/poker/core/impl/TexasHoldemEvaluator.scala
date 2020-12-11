package com.assignment.poker.core.impl

import com.assignment.poker.core.TypedPokerEvaluator
import com.assignment.poker.domain.Hand

class TexasHoldemEvaluator extends TypedPokerEvaluator {

  override def combinations(board: Hand, hand: Hand): List[Hand] = {
    val handCards = hand.cards
    val boardCards = board.cards

    val n = 5 // combination size

    Range(1, n).flatMap { k =>
      for {
        ls <- handCards.combinations(k) if handCards.length >= k
        ns <- boardCards.combinations(n - k) if boardCards.length >= n - k
      } yield Hand(ls ++ ns)
    }.toList
  }
}
