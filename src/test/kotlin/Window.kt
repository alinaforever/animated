import com.github.alinaforever.data.Delta
import com.github.alinaforever.easing.Easing
import com.github.alinaforever.type.AnimatedColor
import com.github.alinaforever.type.AnimatedFloat
import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.nanovg.NVGColor
import org.lwjgl.nanovg.NanoVG.*
import org.lwjgl.nanovg.NanoVGGL3.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.awt.Color

class Window {
    // The window handle
    private var window: Long = 0

    var w = 0f
    var h = 0f

    // Animation will last 240 ms
    val anim = AnimatedFloat({ 440f })
    val anim2 = AnimatedColor({ 440f }, { Color.RED }, { Color.WHITE })

    var deltaTime = 0.0
    var lastFrame = 0.0

    fun run() {
        println("Hello LWJGL " + Version.getVersion() + "!")

        init()
        loop()

        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

    private fun init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        check(GLFW.glfwInit()) { "Unable to initialize GLFW" }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints() // optional, the current window hints are already the default
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE) // the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE) // the window will be resizable

        // Create the window
        window = GLFW.glfwCreateWindow(300, 300, "Hello World!", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        GLFW.glfwSetKeyCallback(
            window
        ) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(
                window,
                true
            ) // We will detect this in the rendering loop
        }

        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            GLFW.glfwGetWindowSize(window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())

            // Center the window
            GLFW.glfwSetWindowPos(
                window,
                (vidmode!!.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )

            w = pWidth[0].toFloat()
            h = pHeight[0].toFloat()
        }
        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window)
        // Enable v-sync
        GLFW.glfwSwapInterval(1)

        // Make the window visible
        GLFW.glfwShowWindow(window)
    }

    private fun loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()

        // Set the clear color
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        var lastRenderBegin = 0f
        var lastRenderEnd = 0f

        val vg = nvgCreate(NVG_ANTIALIAS or NVG_STENCIL_STROKES)

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT) // clear the framebuffer

            nvgBeginFrame(vg, w, h, 1f)

            rect(vg, 10f + 180f * anim.value, 10f, 100f, 100f)

            nvgEndFrame(vg)

            anim.update()
            anim2.update()

            if(anim.done) {
                anim.state = !anim.state
            }

            if(anim2.done) {
                anim2.state = !anim2.state
            }

            val currentTime = (System.currentTimeMillis().toDouble()) / 1000
            deltaTime = currentTime - lastFrame
            lastFrame = currentTime

            Delta.set(deltaTime.toFloat())

            GLFW.glfwSwapBuffers(window) // swap the color buffers
            GLFW.glfwPollEvents()
        }
    }

    private fun rect(vg: Long, x: Float, y: Float, w: Float, h: Float) {
        NVGColor.calloc().use {
            nvgRGBf(anim2.value.red / 255f, anim2.value.green / 255f, anim2.value.blue / 255f, it)

            nvgBeginPath(vg)
            nvgRect(vg, x, y, w, h)
            nvgFillColor(vg, it)
            nvgFill(vg)
        }
    }
}