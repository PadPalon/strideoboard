package ch.neukom.strideoboard.input

import ch.neukom.strideoboard.gui.VlcFrame
import com.tulskiy.keymaster.common.Provider
import javax.swing._
import java.io.File
import java.util.Properties

/**
  * registers hotkeys
  */
object InputHandler {
    def registerHotkeys(hotkeys: Properties, videos: Properties, player: VlcFrame) = {
        val videoFolder: String = new File("videos").getAbsolutePath
        val hotKeyProvider: Provider = Provider.getCurrentProvider(false)
        hotkeys.entrySet.forEach(entry => {
            val videoPath: String = videos.getProperty(entry.getValue.toString)
            hotKeyProvider.register(KeyStroke.getKeyStroke(entry.getKey.toString.replace("-", " ")), event => player.playVideo(String.format("%s\\%s", videoFolder, videoPath)))
        })
    }
}