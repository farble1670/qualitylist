import java.util.regex.Pattern

/**
 * A representation of a quality item. A quality item is an item in a quality list. See:
 * https://www.gnu.org/software/guile/manual/html_node/HTTP-Headers.html
 *
 * This can be used parse certain HTTP headers such as "accept-encoding", and accept-language":
 * https://www.rfc-editor.org/rfc/rfc9110.html#name-accept-encoding
 *
 * For example:
 * ```
 * foo;q=0.1,bar;q=0.5,baz;q=1.0
 * ```
 */
data class QValue(val name: String, val quality: Float = 1.0f) {
  companion object {
    /**
     * To find each element in the list.
     */
    private val VALUE_PATTERN =
        Pattern.compile("([a-zA-Z]+|\\*)(?:[;]q=(\\d+(?:\\.\\d+)?))?")
    /**
     * To validate the list format.
     */
    private val LIST_PATTERN =
        Pattern.compile("${VALUE_PATTERN.pattern()}(?:\\s*,\\s*${VALUE_PATTERN.pattern()})*")

    /**
     * Parse a quality list (qlist) to individual quality items. If the list cannot be parsed
     * an empty list is returned.
     */
    fun decode(value: String?): List<QValue> {
      return value?.let { v ->
        return mutableListOf<QValue>().apply {
          if (LIST_PATTERN.matcher(v).matches()) {
            val matcher = VALUE_PATTERN.matcher(v)
            while (matcher.find()) {
              matcher.group(1)?.let { t ->
                matcher.group(2)?.let { q ->
                  add(QValue(t, q.toFloat()))
                } ?: run {
                  add(QValue(t))
                }
              }
            }
          }
        }
      } ?: emptyList()
    }
  }

  override fun toString(): String = "$name;q=$quality"
}

fun List<QValue>.encode(): String = this.joinToString(", ")