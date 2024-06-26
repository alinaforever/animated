package com.github.alinaforever.data

/**
 * Utility for accessing the current delta (time it took to render a frame)
 *
 * @author Alina
 * @since 1.0.0
 */
object Delta {
    internal var value: Float = 0f

    /**
     * Set the current delta.
     *
     * @param delta The delta
     */
    @JvmStatic
    fun set(delta: Float) {
        this.value = delta
    }
}