import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QItemTest {
  @Test
  fun testNullValue() {
    val items = QItem.parse(null)
    assertNotNull(items)
    assertTrue(items.isEmpty())
  }

  @Test
  fun testEmptyValue() {
    val items = QItem.parse("")
    assertNotNull(items)
    assertTrue(items.isEmpty())
  }

  @Test
  fun testNotParsableValue1() {
    val items = QItem.parse("foo?baz?bar")
    assertNotNull(items)
    assertTrue(items.isEmpty())
  }

  @Test
  fun testNotParsableValue2() {
    val items = QItem.parse("foo*baz")
    assertNotNull(items)
    assertTrue(items.isEmpty())
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
    val items = QItem.parse("foo;q=0.5")
    assertNotNull(items)
    assertTrue(items.contains(QItem("foo", 0.5f)))
  }

  @Test
  fun testSingleValueWithInvalidQuality() {
    val items = QItem.parse("foo;q=XXX")
    assertNotNull(items)
    assertTrue(items.isEmpty())
  }

  @Test
  fun testMultiValue() {
    val items = QItem.parse("foo,bar,baz")
    assertNotNull(items)
    assertEquals(3, items.size)
    assertTrue(items.contains(QItem("foo")))
    assertTrue(items.contains(QItem("bar")))
    assertTrue(items.contains(QItem("baz")))
  }

  @Test
  fun testMultiValueWithQuality() {
    val items = QItem.parse("foo;q=0.1,bar;q=0.5,baz;q=1.0")
    assertNotNull(items)
    assertEquals(3, items.size)
    assertTrue(items.contains(QItem("foo", 0.1f)))
    assertTrue(items.contains(QItem("bar", 0.5f)))
    assertTrue(items.contains(QItem("baz", 1.0f)))
  }

  @Test
  fun testEncodeEmpty() {
    val items = listOf<QItem>()
    val encoded = items.encode()
    assertNotNull(encoded)
    assertEquals(0, encoded.length)
  }

  @Test
  fun testEncodeSingle() {
    val items = listOf(QItem("foo"))
    val encoded = items.encode()
    assertNotNull(encoded)
    assertEquals("foo;q=1.0", encoded)
  }

  @Test
  fun testEncodeMultiple() {
    val items = listOf(QItem("foo", 0.1f), QItem("bar", 0.5f), QItem("baz", 1.0f))
    val encoded = items.encode()
    assertNotNull(encoded)
    assertEquals("foo;q=0.1, bar;q=0.5, baz;q=1.0", encoded)
  }

  @Test
  fun testParseEncoded() {
    val items = listOf(QItem("foo", 0.1f), QItem("bar", 0.5f), QItem("baz", 1.0f))
    assertEquals(items, QItem.parse(items.encode()))
  }
}