package scala.learning.exception

class RunTimeException(val message: String, val cause: Throwable) extends Exception(message, cause)
