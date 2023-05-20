import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;




public class Music extends Primary {
        static Clip clip;
        static URL[] soundURL = new URL[50];

        /* C:\Users\temia\IdeaProjects\projectMajor\src\sound_effects\battle_theme */
        public Music() {
            //Sound tracks
            //  Day Themes
            soundURL[0] = getClass().getResource("sound_effects/day_themes/day_Loop_Theme.wav");
            //  Battle Themes
            soundURL[1] = getClass().getResource("sound_effects/battle_theme/generic_battle_theme.wav");
            soundURL[2] = getClass().getResource("sound_effects/battle_theme/alt_battle_theme.wav");
            //Universal
            soundURL[3] = getClass().getResource("sound_effects/universal_sfx/block_sfx.wav");
            soundURL[11] = getClass().getResource("sound_effects/universal_sfx/footstep_dirt.wav");

            //Sword
            soundURL[4] = getClass().getResource("sound_effects/sword_sfx/draw_sword.wav");
            soundURL[5] = getClass().getResource("sound_effects/sword_sfx/sword_swoosh_1.wav");
            soundURL[6] = getClass().getResource("sound_effects/sword_sfx/sword_swoosh_2.wav");
            soundURL[7] = getClass().getResource("sound_effects/sword_sfx/sword_swoosh_3.wav");
            soundURL[8] = getClass().getResource("sound_effects/sword_sfx/sword_hit_1.wav");
            soundURL[9] = getClass().getResource("sound_effects/sword_sfx/ki_charge.wav");
            soundURL[10] = getClass().getResource("sound_effects/sword_sfx/ki_charge_hit.wav");
        }

        public static void setFile(int trackNum) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
            try {
                // System.out.println(soundURL[trackNum]);// in case I need to check if a file is null
                AudioInputStream track = AudioSystem.getAudioInputStream(soundURL[trackNum]);
                clip = AudioSystem.getClip();
                clip.open(track);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public static void startClip() {
            clip.start();
        }

        public static void loopClip() {
            clip.loop(clip.LOOP_CONTINUOUSLY);
        }


    public static void stopClip() {
            clip.stop();
        }
}
