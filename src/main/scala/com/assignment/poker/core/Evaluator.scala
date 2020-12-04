package com.assignment.poker.core

sealed trait Evaluator[I, O] {
  def evaluate(input: I): O
}

trait StringEvaluator extends Evaluator[String, String] {
  def evaluate(input: String): String
}

