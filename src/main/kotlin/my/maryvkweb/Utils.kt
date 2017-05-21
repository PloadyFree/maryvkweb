package my.maryvkweb

import java.util.logging.Logger

inline fun <reified T> getLogger(): Logger = Logger.getLogger(T::class.java.name)