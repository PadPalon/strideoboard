## Synopsis
A vlcj media player with a background usable for chroma keying.
The window decorations can be removed, so only the video is left.
Realized with [vlcj](http://caprica.github.io/vlcj/) and [jkeymaster](https://github.com/tulskiy/jkeymaster).

## Motivation
I always wondered how popular streams can play small clips and reactions with the press of a button.
A friend of mine gave me the idea that a vlc player in an undecorated window could be used for that.
So I tried building something like that for myself.

## Installation
 * Video files are defined in *video.properties* in the form of 'name=path', e.g. 'sample=C:\videos\sample.avi'
 * Hotkeys are defined in *hotkey.properties* in the form of 'hotkey=videoname', e.g. 'control-shift-1=sample'
 * Window decorations can be toggle on and off with *Alt+1* and *Alt+2*

## License
MIT license