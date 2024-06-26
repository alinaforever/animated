<center>
<h1>Animated</h1>
<h4>A very basic animation library for small applications.</h4>
</center>

### Getting this library

You can use https://jitpack.io to get this library.

### Usage

Kotlin:

```kotlin
fun main() {
    val animation = AnimatedFloat({ 120f }, { Easing.LINEAR })

    // Value can be accessed anytime using:
    animation.value

    animation.update() // needs to be run every frame

    // Don't forget to set the delta time!
    Delta.set(deltaTime)
}
```

_Note: Although Kotlin interop for Java is great, this library is meant to be used with, well, Kotlin._

Java:

```java
public static void main(String[] args) {
    AnimatedFloat animation = new AnimatedFloat(() -> 120f, Easing::getLinear);

    // Value can be accessed anytime using:
    animation.getValue();

    animation.update(); // needs to be run every frame

    // Don't forget to set the delta time!
    Delta.set(deltaTime);
}
```

### Alina's Note

I'm well aware this code is absolute crap, and the example is even worse, but it gets the job done.

I spent longer trying to get an example running than on the animation library itself...

Made with <3 by Alina