package com.assignment.poker.domain

import scala.util.matching.Regex

final case class Card(card: String) {
  require(isValid(card), "Invalid card ...")

  def face: String = card.take(1)
  def suit: String = card.takeRight(1)

  def value: Int = Card.value(face)

  private def isValid(card: String): Boolean = {
    val regexTemplate = "[2-9TJQKA]{1}[hdcs]{1}"
    new Regex(regexTemplate).findAllIn(card).nonEmpty
  }

  override def toString: String = card
}

object Card {

  val faces: List[String] = (2 to 9).map(_.toString).toList ::: List("T", "J", "Q", "K", "A")
  val suits: List[String] = List("h", "d", "c", "s")

  def value(key: String): Int = cardCatalogue.apply(key)

  private val cardCatalogue: Map[String, Int] = Map(
    ("2", 0),
    ("3", 1),
    ("4", 2),
    ("5", 3),
    ("6", 4),
    ("7", 5),
    ("8", 6),
    ("9", 7),
    ("T", 8),
    ("J", 9),
    ("Q", 10),
    ("K", 11),
    ("A", 12)
  )

  val minValue: Int = 0
  val maxValue: Int = cardCatalogue.values.foldLeft(0)((acc, v) => v.max(acc))
}
