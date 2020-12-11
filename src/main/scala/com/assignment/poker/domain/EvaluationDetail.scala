package com.assignment.poker.domain

final case class EvaluationDetail(handType: HandType, sorted: Hand, origin: Hand) {

  /**
   * Compares evaluation details
   * 1  - if this is bigger than the other
   * 0  - if this is equal to the other
   * -1 - if this is smaller than the other
   */
  def compareTo(other: EvaluationDetail): Int = {
    if (this.handType.order < other.handType.order) 1
    else if (this.handType.order > other.handType.order) -1

    else {
      val thisCards = this.sorted.cards
      val otherCards = other.sorted.cards

      thisCards.zip(otherCards).foldLeft(0)((acc, pair) => {
        val thisCardValue = pair._1.value
        val otherCardValue = pair._2.value

        if (acc == 0) {
          if (thisCardValue > otherCardValue) 1
          else if (thisCardValue < otherCardValue) -1
          else acc
        } else acc
      })
    }
  }
}



