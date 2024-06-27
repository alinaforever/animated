package com.github.alinaforever.type

import com.github.alinaforever.Animation
import com.github.alinaforever.easing.Easing
import java.awt.Color

/**
 * Allows you to transition between colors.
 * @see [Animation.factor]
 *
 * @author Alina
 * @since 1.0.0
 */
class AnimatedColor @JvmOverloads constructor(
    durationProvider: () -> Float,
    private val from: () -> Color,
    private val to: () -> Color,
    easing: Easing = Easing.Linear,
    initialState: Boolean = false
) : Animation<Color>(durationProvider, easing, initialState) {
    private fun calculateCurrentColor(progress: Float): Color {
        val startColor = from()
        val endColor = to()

        val interpolatedColor = Color(
            interpolate(startColor.red, endColor.red, progress),
            interpolate(startColor.green, endColor.green, progress),
            interpolate(startColor.blue, endColor.blue, progress),
            interpolate(startColor.alpha, endColor.alpha, progress)
        )

        return interpolatedColor
    }

    override val value: Color
        get() = calculateCurrentColor(factor)

    private fun interpolate(startValue: Int, endValue: Int, fraction: Float): Int {
        return (startValue + fraction * (endValue - startValue)).toInt()
    }
}