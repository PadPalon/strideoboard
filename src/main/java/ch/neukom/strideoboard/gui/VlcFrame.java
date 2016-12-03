package ch.neukom.strideoboard.gui;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.condition.conditions.PositionReachedCondition;
import uk.co.caprica.vlcj.player.condition.conditions.VideoOutputCreatedCondition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * the frame that hosts the vlcj component
 */
public class VlcFrame extends JFrame {
    private EmbeddedMediaPlayerComponent mediaPlayer;

    public VlcFrame(String title) {
        super(title);
        mediaPlayer = new EmbeddedMediaPlayerComponent();
        mediaPlayer.getVideoSurface().setBackground(Color.GREEN);
        mediaPlayer.getMediaPlayer().addMediaPlayerEventListener(resizeFrameToVideo());
        mediaPlayer.getMediaPlayer().addMediaPlayerEventListener(stopMediaPlayerWhenEndReached());

        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mediaPlayer);
        setVisible(true);

        mediaPlayer.getVideoSurface().addKeyListener(removeOrAddDecoration());

    }

    public void playVideo(String path) {
        mediaPlayer.getMediaPlayer().playMedia(path);
    }

    private VideoOutputCreatedCondition resizeFrameToVideo() {
        return new VideoOutputCreatedCondition(mediaPlayer.getMediaPlayer()) {
            @Override
            public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
                setSize(mediaPlayer.getVideoDimension());
            }
        };
    }

    private PositionReachedCondition stopMediaPlayerWhenEndReached() {
        return new PositionReachedCondition(mediaPlayer.getMediaPlayer(), 1) {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
            }
        };
    }

    private KeyAdapter removeOrAddDecoration() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.isAltDown()) {
                    if(e.getKeyChar() == KeyEvent.VK_1) {
                        dispose();
                        setUndecorated(false);
                        setVisible(true);
                    } else if (e.getKeyChar() == KeyEvent.VK_2) {
                        dispose();
                        setUndecorated(true);
                        setVisible(true);
                    }
                }
            }
        };
    }
}
