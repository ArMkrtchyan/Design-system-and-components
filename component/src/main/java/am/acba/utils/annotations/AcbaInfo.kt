package am.acba.utils.annotations

@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class AcbaInfo(val value: String, val sources: Array<String> = [])