package ch.neukom.strideoboard;

import ch.neukom.strideoboard.gui.VlcFrame;
import ch.neukom.strideoboard.input.InputHandler;
import ch.neukom.strideoboard.validation.ValidationHelper;
import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * starts a vlcj media player with a background usable for chroma keying
 *
 * alt + 1 shows window decoration
 * alt + 2 removes window decoration
 *
 * video files are defined in video.properties in the form of 'name=path'
 * hotkeys are defined in hotkey.properties in the form of 'hotkey=videoname', e.g. 'control-shift-1=sample'
 */
public class Strideoboard {

    private final VlcFrame player;

    public Strideoboard(Properties videos, Properties hotkeys) {
        player = new VlcFrame("Title");

        InputHandler.registerHotkeys(hotkeys, videos, player);
    }

    public static void main(String[] args) {
        File videoFolder;

        Properties videos;
        Properties hotkeys;
        try {
            videoFolder = new File("config/videos");
            if (!videoFolder.exists()) {
                videoFolder.mkdir();
            }

            File hotkeyProperties = getOrCreateFile("config/hotkey.properties");
            File videoProperties = getOrCreateFile("config/video.properties");

            validate();

            videos = validateAndGatherVideoProperties(videoFolder, videoProperties);
            hotkeys = validateAndGatherHotKeyProperties(videos, hotkeyProperties);
        } catch (IOException e) {
            throw new IllegalStateException("Configs could not be found or created");
        }

        new Strideoboard(videos, hotkeys);
    }

    private static File getOrCreateFile(String filePath) throws IOException {
        File file;
        file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    private static void validate() {
        List<String> errors = ValidationHelper.validate();
        if (!errors.isEmpty()) {
            throw new IllegalStateException(errors.stream().collect(Collectors.joining("\n")));
        }
    }

    private static Properties validateAndGatherVideoProperties(File videoFolder, File videoProperties) throws IOException {
        Preconditions.checkArgument(videoFolder.isDirectory(), "Video folder is not a directory");

        Properties videos = new Properties();
        videos.load(new FileInputStream(videoProperties));

        List<String> videoNames = Arrays.stream(videoFolder.listFiles()).map(File::getName).collect(Collectors.toList());
        if(videos.values().stream().allMatch(videoNames::contains)) {
            return videos;
        } else {
            throw new IllegalStateException("Video configured in video.properties not found in videos folder");
        }
    }

    private static Properties validateAndGatherHotKeyProperties(Properties videos, File hotkeyProperties) throws IOException {
        Properties hotkeys = new Properties();
        hotkeys.load(new FileInputStream(hotkeyProperties));

        List<String> videoNames = videos.keySet().stream().map(Object::toString).collect(Collectors.toList());
        if(hotkeys.values().stream().allMatch(videoNames::contains)) {
            return hotkeys;
        } else {
            throw new IllegalStateException("Video configured in hotkey.properties not found in video.properties");
        }
    }
}
