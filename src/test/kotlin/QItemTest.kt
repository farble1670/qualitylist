import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QItemTest {
  @Test
  fun testNullValue() {
    val encodings = QItem.parse(null)
    assertNotNull(encodings)
    assertTrue(encodings.isEmpty())
  }

  @Test
  fun testEmptyValue() {
    val encodings = QItem.parse("")
    assertNotNull(encodings)
    assertTrue(encodings.isEmpty())
  }

  @Test
  fun testNotParsableValue1() {
    val encodings = QItem.parse("foo?baz?bar")
    assertNotNull(encodings)
    assertTrue(encodings.isEmpty())
  }

  @Test
  fun testNotParsableValue2() {
    val encodings = QItem.parse("foo*baz")
    assertNotNull(encodings)
    assertTrue(encodings.isEmpty())
  }

  @Test
  fun testSingleValue() {
    val encodings = QItem.parse("foo")
    assertNotNull(encodings)
    assertEquals(1, encodings.size)
    assertTrue(encodings.contains(QItem("foo", 1.0f)))
  }

  @Test
  fun testSingleValueWithQuality() {
    val encodings = QItem.parse("foo;q=0.5")
    assertNotNull(encodings)
    assertTrue(encodings.contains(QItem("foo", 0.5f)))
  }

  @Test
  fun testSingleValueWithInvalidQuality() {
    val encodings = QItem.parse("foo;q=XXX")
    assertNotNull(encodings)
    assertTrue(encodings.isEmpty())
  }

  @Test
  fun testMultiValue() {
    val encodings = QItem.parse("foo,bar,baz")
    assertNotNull(encodings)
    assertEquals(3, encodings.size)
    assertTrue(encodings.contains(QItem("foo")))
    assertTrue(encodings.contains(QItem("bar")))
    assertTrue(encodings.contains(QItem("baz")))
  }

  @Test
  fun testMultiValueWithQuality() {
    val encodings = QItem.parse("foo;q=0.1,bar;q=0.5,baz;q=1.0")
    assertNotNull(encodings)
    assertEquals(3, encodings.size)
    assertTrue(encodings.contains(QItem("foo", 0.1f)))
    assertTrue(encodings.contains(QItem("bar", 0.5f)))
    assertTrue(encodings.contains(QItem("baz", 1.0f)))
  }
}