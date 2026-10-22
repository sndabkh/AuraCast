package com.example.car

import android.view.KeyEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

enum class SteeringWheelAction {
    NEXT_ITEM,
    PREVIOUS_ITEM,
    PLAY_PAUSE,
    TRIGGER_VOICE,
    SELECT,
    NAVIGATE_UP,
    NAVIGATE_DOWN,
    TOGGLE_RADAR,
    BACK
}

class SteeringWheelHandler {

    private val _actions = MutableSharedFlow<SteeringWheelAction>(extraBufferCapacity = 8)
    val actions: SharedFlow<SteeringWheelAction> = _actions.asSharedFlow()

    fun handleKeyEvent(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action != KeyEvent.ACTION_DOWN) return false

        val action = when (keyCode) {
            KeyEvent.KEYCODE_MEDIA_NEXT -> SteeringWheelAction.NEXT_ITEM
            KeyEvent.KEYCODE_MEDIA_PREVIOUS -> SteeringWheelAction.PREVIOUS_ITEM
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> SteeringWheelAction.PLAY_PAUSE
            KeyEvent.KEYCODE_HEADSETHOOK -> SteeringWheelAction.TRIGGER_VOICE
            KeyEvent.KEYCODE_VOICE_ASSIST, KeyEvent.KEYCODE_SEARCH -> SteeringWheelAction.TRIGGER_VOICE
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> SteeringWheelAction.SELECT
            KeyEvent.KEYCODE_DPAD_LEFT -> SteeringWheelAction.PREVIOUS_ITEM
            KeyEvent.KEYCODE_DPAD_RIGHT -> SteeringWheelAction.NEXT_ITEM
            KeyEvent.KEYCODE_DPAD_UP -> SteeringWheelAction.NAVIGATE_UP
            KeyEvent.KEYCODE_DPAD_DOWN -> SteeringWheelAction.NAVIGATE_DOWN
            KeyEvent.KEYCODE_BACK -> SteeringWheelAction.BACK
            else -> null
        }

        return if (action != null) {
            _actions.tryEmit(action)
            true
        } else {
            false
        }
    }
}
