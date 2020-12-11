package com.assignment.poker.core.impl

import com.assignment.poker.core.GenericPokerEvaluator
import com.assignment.poker.domain.Hand

class FiveCardDrawEvaluator extends GenericPokerEvaluator {

  override def combinations(originHand: Hand, board: Hand): List[Hand] = {
    List(originHand)
  }
}
