package com.github.alinaforever

import com.github.alinaforever.data.Delta
import com.github.alinaforever.easing.Easing

/**
 * The core for any animatable value.
 *
 * @author Alina
 * @since 1.0.0
 */
abstract class Animation<T>(
    private val durationProvider: () -> Float,
    private val easing: Easing,
    initialState: Boolean
) {
    private val duration get() = durationProvider() / 1000f

    /**
     * The factor of this animation.
     *
     * Ranges from `0.0..1.0`, 0.0 being when [state] is false and 1.0 being when [state] is true.
     */
    protected var factor: Float = if(initialState) 1f else 0f

    /**
     * The state defines how this animation should run, for example, when [state] is false, it will animate from 1.0 -> 0.0, and vice-versa for when [state] is true.
     */
    var state: Boolean = false

    /**
     * Returns true if this animation is done, false if not.
     */
    val done: Boolean get() {
        return if(state) {
            factor == 1f
        } else {
            factor == 0f
        }
    }

    /**
     * The animated target value.
     */
    abstract val value: T

    /**
     * Update the animation.
     */
    fun update() {
        val deltaTime = Delta.value / 1000f

        // Calculate progress ratio
        val progressRatio = deltaTime / (duration / 1000f)

        // Update factor based on state
        factor += if (state) progressRatio else -progressRatio

        // Apply easing to factor
        factor = easing.apply(factor)

        // Clamp factor to ensure it stays within [0, 1]
        factor = factor.coerceIn(MIN, MAX)
    }


    companion object {
        private const val MIN = 0f
        private const val MAX = 1f
    }
}