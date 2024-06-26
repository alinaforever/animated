package com.github.alinaforever.type

import com.github.alinaforever.Animation
import com.github.alinaforever.easing.Easing

/**
 * A basic animated float that ranges from 0.0 to 1.0, depending on the state.
 * @see [Animation.factor]
 *
 * @author Alina
 * @since 1.0.0
 */
class AnimatedFloat @JvmOverloads constructor(
    durationProvider: () -> Float,
    easingProvider: () -> Easing,
    initialState: Boolean = false
) : Animation<Float>(durationProvider, easingProvider, initialState) {
    override val value get() = factor
}