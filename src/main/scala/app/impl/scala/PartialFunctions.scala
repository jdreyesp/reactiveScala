package app.impl.scala

import org.apache.commons.lang.StringUtils
import org.junit.Test

import scala.util.Try

/**
  * Partial function define tow arguments type, the input and output
  * By default you can implement apply function and isDefinedAt, and once you invoke this function
  * both function will be executed
  */
class PartialFunctions {

  @Test
  def partialFunction() {
    assert(Try(fraction(1)).isSuccess)
    assert(Try(fraction(0)).isFailure)
  }

  val fraction = new PartialFunction[Int /*Entry type*/ , Int /*Output type*/ ] {
    def apply(d: Int) = 42 / d

    def isDefinedAt(d: Int) = d != 0
  }

  @Test def upperCase(): Unit = {
    println(upperCaseIfString.isDefinedAt("hello scala world"))
    val triedUpperCase = Try(upperCaseIfString("hello scala world"))
    assert(triedUpperCase.isSuccess)
    println(triedUpperCase.get)
    val orElseFunction: (String => String) = upperCaseIfString orElse makeNumberString
    assert(Try(orElseFunction("0")).isSuccess)
    println(Try(orElseFunction("0")).get)
  }

  val upperCaseIfString: PartialFunction[String, String] = {
    case input if !StringUtils.isNumeric(input) => input.asInstanceOf[String].toUpperCase()
  }

  val makeNumberString = new PartialFunction[Any, String] {
    def apply(any: Any) = {
      any.asInstanceOf[String]
    }

    def isDefinedAt(any: Any) = {
      any.asInstanceOf[String] forall Character.isDigit
    }
  }

  /**
    * Also with partial function you can use pattern matching to execute one function or another
    * depending the input
    */
  @Test def partialFunctionString(): Unit = {
    val pf: PartialFunction[Int /*Entry type*/ , String /*Output type*/ ] = {
      case input if input > 100 => "higher"
    }
    val orElseFunction: (Int => String) = pf orElse { case _ => "lower" }
    println(orElseFunction(101))
    println(orElseFunction(99))
  }


}
