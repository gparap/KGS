/*******************************
 * Katocheánian Gaming Studios *
 * Little Jerry's Friends      *
 * created by gparap           *
 *******************************/
package gparap.games.falling.debris

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.RandomXS128
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import gparap.games.falling.utils.GameConstants
import java.util.*
import kotlin.properties.Delegates

abstract class Debris(private val sprite: Sprite) {
    private var position = Vector2(GameConstants.OFF_SCREEN_X, GameConstants.OFF_SCREEN_Y)
    protected var speed by Delegates.notNull<Float>()
    protected var isActive = false
    protected var isHit = false

    abstract fun isActiveInGame(): Boolean
    abstract fun setActiveInGame(active: Boolean)

    abstract fun isHitInGame(): Boolean
    abstract fun setHitInGame(hit: Boolean)

    open fun update(delta: Float) {
        //debris is falling
        position.y -= speed + delta
        sprite.y = position.y

        //debris reaches the ground
        if (position.y < 0 - sprite.height) {
            setActiveInGame(false)
            randomizePosition()
            randomizeSpeed(GameConstants.DEBRIS_MAX_SPEED)
        }
    }

    open fun draw(spriteBatch: SpriteBatch) {
        sprite.draw(spriteBatch)
    }

    open fun randomizeSpeed(maxSpeed: Float) {
        speed = Random().nextFloat() * (maxSpeed - GameConstants.DEBRIS_MIN_SPEED) + GameConstants.DEBRIS_MAX_SPEED
    }

    /* Randomizes X position (x > 0 && x < screen_width - debris_width) */
    open fun randomizePosition() {
        val random = RandomXS128().nextInt((Gdx.graphics.width - sprite.width).toInt())
        position = Vector2(random.toFloat(), Gdx.graphics.height.toFloat())
        sprite.setPosition(position.x, position.y)
    }

    /* Returns the collision boundaries for this debris */
    open fun getCollisionBounds(): Rectangle {
        val rectangle = Rectangle()
        rectangle.width = sprite.width - (sprite.width / 10)
        rectangle.height = sprite.height - (sprite.height / 10)
        rectangle.setPosition(sprite.x, sprite.y)
        return rectangle
    }
}