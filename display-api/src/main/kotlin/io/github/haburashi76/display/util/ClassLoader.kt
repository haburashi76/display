package io.github.haburashi76.display.util

import org.apache.commons.lang3.reflect.ConstructorUtils
import java.lang.reflect.InvocationTargetException

object ImplLoader {
    fun <T> load(clazz: Class<T>): T {
        val packaGe = clazz.`package`.name
        val name = "${clazz.simpleName}Impl"
        return try {
            val internalClass =
                Class.forName("$packaGe.internal.$name", true, clazz.classLoader).asSubclass(clazz)
            val constructor = ConstructorUtils.getMatchingAccessibleConstructor(internalClass)
            constructor.newInstance() as T
        } catch (exception: ClassNotFoundException) {
            throw UnsupportedOperationException("${clazz.name} a does not have implement", exception)
        } catch (exception: IllegalAccessException) {
            throw UnsupportedOperationException("${clazz.name} constructor is not visible")
        } catch (exception: InstantiationException) {
            throw UnsupportedOperationException("${clazz.name} is abstract class")
        } catch (exception: InvocationTargetException) {
            throw UnsupportedOperationException(
                "${clazz.name} has an error occurred while creating the instance",
                exception
            )
        }
    }
}