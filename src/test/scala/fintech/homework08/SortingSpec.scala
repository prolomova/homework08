package fintech.homework08

import java.time.LocalDate
import PeopleApp._

import org.scalatest.{FlatSpec, Matchers}

class DBResTest extends FlatSpec with Matchers {

  it should "work correct" in {
    val request = for {
      _ <- setup()
      oldPerson <- getOldPerson()
      clone <- clonePerson(oldPerson)
    } yield clone
    val result: Person = DBRes(request.run).execute(uri)
    result should be(Person("Alice", LocalDate.now()))
  }
}