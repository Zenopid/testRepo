import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;



/*
Primary is the main class
Music extends Primary
Combat extends Music
Dungeon extends Combat
 */
 class Music extends Primary {
     static Clip dayMusic;

     private static Clip combatMusic;

     private static Clip dungeonMusic;
     static URL[] soundURL = new URL[50];

     public Music() {
         //Sound tracks
         //  Day Themes
         soundURL[0] = getClass().getResource("sound_effects/day_themes/day_Loop_Theme.wav");
         //  Battle Themes
         soundURL[1] = getClass().getResource("sound_effects/battle_themes/generic_battle_theme.wav");
         soundURL[2] = getClass().getResource("sound_effects/battle_themes/alt_battle_theme.wav");
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

         //Axe
         soundURL[15] = getClass().getResource("sound_effects/axe_sfx/axe_hit_1.wav");
         soundURL[16] = getClass().getResource("sound_effects/axe_sfx/axe_draw.wav");
         soundURL[17] = getClass().getResource("sound_effects/axe_sfx/axe_ignite.wav");

         //Shop
         soundURL[12] = getClass().getResource("sound_effects/shop_themes/shop_theme.wav");
         soundURL[18] = getClass().getResource("sound_effects/universal_sfx/coin.sfx.wav");

         //Dungeon
         soundURL[13] = getClass().getResource("sound_effects/dungeon_themes/dungeon_Theme.wav");

         //Upgrades
         soundURL[14] = getClass().getResource("sound_effects/upgrade_sfx/hammer_sfx.wav");
     }

     public static void setFile(int trackNum) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
         try {
             //System.out.println(trackNum);
             //System.out.println(soundURL[trackNum]);// in case I need to check if a file is null
             AudioInputStream track = AudioSystem.getAudioInputStream(soundURL[trackNum]);
             dayMusic = AudioSystem.getClip();
             dayMusic.open(track);
         } catch (Exception e) {
             System.out.println(e);
         } finally {
             musicFile = trackNum;
         }
     }

     public static void startClip() {
         dayMusic.start();
     }

     public static void loopClip() {
         dayMusic.loop(dayMusic.LOOP_CONTINUOUSLY);
     }


     public static void stopClip() throws LineUnavailableException, IOException {
         if (dayMusic != null && dayMusic.isRunning()) {
             dayMusic.stop();
             dayMusic.drain();
             dayMusic.close();
         }
     }

     public static void combatSetFile(int trackNum) {
             try {
                 //System.out.println(trackNum);
                 //System.out.println(soundURL[trackNum]);// in case I need to check if a file is null
                 AudioInputStream track = AudioSystem.getAudioInputStream(soundURL[trackNum]);
                 combatMusic = AudioSystem.getClip();
                 combatMusic.open(track);
             } catch (Exception e) {
                 System.out.println(e);
             } finally {
                 musicFile = trackNum;
             }
     }

     public static void combatStartClip() {
         combatMusic.start();
     }

     public static void combatLoopClip() {
         combatMusic.loop(Clip.LOOP_CONTINUOUSLY);
     }

     public static void combatStopClip() {
         if (combatMusic != null && combatMusic.isRunning()) {
             combatMusic.stop();
             combatMusic.drain();
             combatMusic.close();
         }
     }

     public static void dungeonSetFile(int trackNum) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
         //System.out.println(trackNum);
         //System.out.println(soundURL[trackNum]);// in case I need to check if a file is null
        try {
            AudioInputStream track = AudioSystem.getAudioInputStream(soundURL[trackNum]);
         dungeonMusic = AudioSystem.getClip();
         dungeonMusic.open(track);
     } catch (Exception e) {
         System.out.println(e);
     } finally {
         musicFile = trackNum;
     }
     }

     public static void dungeonStartClip() {
         dungeonMusic.start();
     }

     public static void dungeonLoopClip() {
         dungeonMusic.loop(Clip.LOOP_CONTINUOUSLY);
     }

     public static void dungeonStopClip() {
         if (dungeonMusic != null && dungeonMusic.isRunning()) {
             dungeonMusic.stop();
             dungeonMusic.drain();
             dungeonMusic.close();
         }
     }
 }
