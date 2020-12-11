package com.assignment.poker.core.impl

import com.assignment.poker.core.TypedPokerEvaluator
import com.assignment.poker.domain.Hand

class OmahaHoldemEvaluator extends TypedPokerEvaluator {

  override def combinations(originHand: Hand, board: Hand): List[Hand] = ???
}
