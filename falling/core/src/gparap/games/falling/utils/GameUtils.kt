package gparap.games.falling.utils

object GameUtils {
    fun getFriendFromFilePath(filePath: String): String {
        var friend = filePath
        friend = friend.substringAfter("/")
        friend = friend.substringBefore(".")
        return friend
    }
}