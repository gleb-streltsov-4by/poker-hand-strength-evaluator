package com.assignment.poker.core

trait AbstractPokerEvaluator[I, O] {
  def evaluate(input: I): O
}
