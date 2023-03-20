package io.github.wanghongzhou.javafx.tools;

import com.sun.jna.Native;

import static com.sun.jna.win32.W32APIOptions.DEFAULT_OPTIONS;

/**
 * @author Brian
 */
public interface User32 extends com.sun.jna.platform.win32.User32 {

    User32 INSTANCE = Native.load("user32", User32.class, DEFAULT_OPTIONS);

    BOOL ClipCursor(RECT rect);
}
