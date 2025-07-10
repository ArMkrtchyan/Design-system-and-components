package am.acba.utils.annotations

@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class AcbaScheme(val value: String, val examples: Array<String> = [])