package ch.neukom.strideoboard.validation

import uk.co.caprica.vlcj.discovery.NativeDiscovery
import java.util.Optional

/**
  * cheks that vlc files are available on system
  */
class VlcValidator extends Validator {
    def validate: Optional[String] = {
        if (new NativeDiscovery().discover)
            Optional.empty()
        else
            Optional.of("VLC libraries could not be found")
    }
}