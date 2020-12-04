package com.assignment.poker.parser

import org.scalatest.flatspec.AnyFlatSpec

class StringParserTest extends AnyFlatSpec{

  private[this] val sut = new StringParser()

  "StringParser" should "correctly parse input #1" in {
    val input = "texas-holdem 4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d"
    val result = sut.parse(input)
    print("str")

    //assert(actualEvaluation == expectedEvaluation)
  }
}
