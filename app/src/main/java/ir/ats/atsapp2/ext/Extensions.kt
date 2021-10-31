package ir.ats.atsapp2.ext

fun <T, R> List<T>.wrapChildrenWith(lambda: (T) -> R): List<R> {
	return mutableListOf<R>().also { newList ->
		this.forEach {
			newList.add(lambda(it))
		}
	}
}
