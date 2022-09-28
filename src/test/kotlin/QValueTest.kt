import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QValueTest {
  @Test
  fun testNullValue() {
    val values = QValue.decode(null)
    assertNotNull(values)
    assertTrue(values.isEmpty())
  }

  @Test
  fun testEmptyValue() {
    val values = QValue.decode("")
    assertNotNull(values)
    assertTrue(values.isEmpty())
  }

  @Test
  fun testNotParsableValue1() {
    val values = QValue.decode("foo?baz?bar")
    assertNotNull(values)
    assertTrue(values.isEmpty())
  }

  @Test
  fun testNotParsableValue2() {
    val values = QValue.decode("foo*baz")
    assertNotNull(values)
    assertTrue(values.isEmpty())
  }

  @Test
  fun testSingleValue() {
    val encodings = QValue.decode("foo")
    assertNotNull(encodings)
    assertEquals(1, encodings.size)
    assertTrue(encodings.contains(QValue("foo", 1.0f)))
  }

  @Test
  fun testSingleValueWithQuality() {
    val values = QValue.decode("foo;q=0.5")
    assertNotNull(values)
    assertTrue(values.contains(QValue("foo", 0.5f)))
  }

  @Test
  fun testSingleValueWithInvalidQuality() {
    val values = QValue.decode("foo;q=XXX")
    assertNotNull(values)
    assertTrue(values.isEmpty())
  }

  @Test
  fun testMultiValue() {
    val values = QValue.decode("foo,bar,baz")
    assertNotNull(values)
    assertEquals(3, values.size)
    assertTrue(values.contains(QValue("foo")))
    assertTrue(values.contains(QValue("bar")))
    assertTrue(values.contains(QValue("baz")))
  }

  @Test
  fun testMultiValueWithQuality() {
    val values = QValue.decode("foo;q=0.1,bar;q=0.5,baz;q=1.0")
    assertNotNull(values)
    assertEquals(3, values.size)
    assertTrue(values.contains(QValue("foo", 0.1f)))
    assertTrue(values.contains(QValue("bar", 0.5f)))
    assertTrue(values.contains(QValue("baz", 1.0f)))
  }

  @Test
  fun testEncodeEmpty() {
    val values = listOf<QValue>()
    val encoded = values.encode()
    assertNotNull(encoded)
    assertEquals(0, encoded.length)
  }

  @Test
  fun testEncodeSingle() {
    val values = listOf(QValue("foo"))
    val encoded = values.encode()
    assertNotNull(encoded)
    assertEquals("foo;q=1.0", encoded)
  }

  @Test
  fun testEncodeMultiple() {
    val values = listOf(QValue("foo", 0.1f), QValue("bar", 0.5f), QValue("baz", 1.0f))
    val encoded = values.encode()
    assertNotNull(encoded)
    assertEquals("foo;q=0.1, bar;q=0.5, baz;q=1.0", encoded)
  }

  @Test
  fun testParseEncoded() {
    val values = listOf(QValue("foo", 0.1f), QValue("bar", 0.5f), QValue("baz", 1.0f))
    assertEquals(values, QValue.decode(values.encode()))
  }
}