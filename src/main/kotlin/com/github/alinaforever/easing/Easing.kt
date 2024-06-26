package com.github.alinaforever.easing

/**
 * The easing for an animation.
 *
 * @author Alina
 * @since 1.0.0
 */

// This could be a sealed class, but you can't really use it with Java :(
class Easing(
    private val function: (Float) -> Float,
) {
    internal fun apply(value: Float): Float { return function(value) }

    companion object {
        @JvmStatic
        val Linear = Easing { it }
    }
}
