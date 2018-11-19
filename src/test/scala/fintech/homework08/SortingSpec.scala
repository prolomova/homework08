package fintech.homework08

import java.time.LocalDate
import PeopleApp._

import org.scalatest.{FlatSpec, Matchers}

class DBResTest extends FlatSpec with Matchers {

  val uri = "jdbc:h2:~/dbres"
  val p1 = Person("Alice", LocalDate.of(1970, 1, 1))
  val p2 = Person("Bob", LocalDate.of(1981, 5, 12))
  val p3 = Person("Charlie", LocalDate.of(1979, 2, 20))
  val request = for {
    _ <- setup()
    oldPerson <- getOldPerson()
    clonePerson <- clonePerson(oldPerson)
  } yield clonePerson

  it should "work correct" in {
    val result: Person = DBRes(request.run).execute(uri)
    result should be(Person("Alice", LocalDate.now()))
  }

  "FlatMap" should "work correct" in {
    val name = "Alice"
    val bd = LocalDate.now()
    val p4 = Person(name, bd)
    request
      .flatMap(_=> DBRes.select("SELECT * FROM people",
        List.empty)(readPerson))
      .execute(uri) should be(List(p1, p2, p3, p4))
  }
}