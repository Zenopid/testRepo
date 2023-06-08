import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Dungeon extends Combat {

    public static int dnumofchest = 0;
    public static int dungeonEnemies = 0;
    public static int dungeonTempVal = 0;

    public static int dungeonTempVal2 = 0;
    
    public static int[] dEnemyPosX = new int[50];

    public static int[] dEnemyPosY = new int[50];

    public static int[] dchestPosX = new int[50];

    public static int[] dchestPosY = new int[50];
    public static int dungeonTimer = 0;

    public static int dungeonTurn = 0;

    public static int dungeonExitX = 100;

    public static int dungeonExitY = 100;

    public static int dungeonPower = 0;
    static int spotCheckX = 0;
    static int spotCheckY = 0;

    public Dungeon(Music Music) {
        super(Music);
    }
    public static void  undergroundExplore() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        underShelter = true;
        dungeonPath();
        for (int g = 0; g == dEnemyPosX.length; g++) {
            dEnemyPosX[g] = -20;
            dEnemyPosY[g] = -20;
            dchestPosX[g] = -21;
            dchestPosY[g] = -21;
        }
        dEnemyPosX[0] = dungeonExitX;
        dEnemyPosY[0] = dungeonExitY -1;
        dungeonEnemySpawner();
        chestNumber();
        dChestSpawner();
        startDungeon();
        System.out.println("You have " + dungeonTimer + " turns to escape the dungeon.");
        while (inDungeon) {
            spotCheckX = playerPosX;
            spotCheckY = playerPosY;
                if (!musicPlaying) dungeonMusic();
                if (dungeonTimer - dungeonTurn == 0) {
                     dungeonExit();
                     dungeonStopMusic();
                     inDungeon = false;
                }
                dungeonTurnLoop();
                playerResponse();
                if (answer == 1) dungeonRecover();
                else if (answer == 2) {
                    dungeonMove();
                    distanceFromExit();
                }
                for (int m = 0; m < dEnemyPosX.length ; m++) {
                    if (playerPosX == dEnemyPosX[m] && playerPosY == dEnemyPosY[m]) {
                        isFighting = true;
                        dungeonStopMusic();
                        Combat.fight();
                    }
                }

                if (playerPosX == dungeonExitX && playerPosY == dungeonExitY || playerHP <= 0) {
                    dungeonExit();
                    inDungeon = false;
                }
                dungeonTurn += 1;
            }
        }
        static void chestNumber() {
        if (inWildlands) {
            genericRNG(1,2);
            dnumofchest  = rngVal;
        }
        else if (inMountains) {
            genericRNG(2,3);
            dnumofchest = rngVal;
        }
        }

    static void startDungeon() throws InterruptedException {
        System.out.println("You begin your decent...");
        dungeonTempVal = playerPosX;
        dungeonTempVal2 = playerPosY;
        playerPosX = 0;
        playerPosY = 0;
        if (inWildlands) {
            dungeonTimer = 25;
            genericRNG(350,100);
            dungeonPower = rngVal;
        } else if (inMountains) {
            dungeonTimer = 30;
            genericRNG(700,125);
            dungeonPower = rngVal;
        }
    }

    static void distanceFromExit() {
        if ((spotCheckX - dungeonExitX) > (playerPosX - dungeonExitX) || (spotCheckY - dungeonExitY) > (playerPosY - dungeonExitY)) System.out.println("Colder...");
        else System.out.println("Warmer...");
        if ((dungeonExitX - playerPosX) <= 5 && (dungeonExitY - playerPosY) <= 5) System.out.println("Getting pretty warm...");
        else if ((playerPosX - dungeonExitX) >= 2 && (playerPosY - dungeonExitY) >= 2) System.out.println("Extremely warm...");
    }
    static void dungeonEnemySpawner() {
        if (inWildlands)
        {
            genericRNG(10,5);
            dungeonEnemies = rngVal;
        }
        else 
         {
             genericRNG(15,5);
             dungeonEnemies = rngVal;
          }
        for (int z = 0; z < dungeonEnemies; z++) {
            //This checks if a enemy is on a shop tile.
            while (dEnemyPosX[z] <= 0 && dEnemyPosY[z] <= 0)
            //Hopefully stops enemy locations from changing...
            {
                if (playerPosX != dungeonExitX) {
                    genericRNG(1, dungeonExitX);
                    dEnemyPosX[z] = rngVal;
                    genericRNG(1, dungeonExitY - 1);
                    dEnemyPosY[z] = rngVal;
                } else {
                    dEnemyPosX[z] = dungeonExitX;
                    genericRNG(1, dungeonExitY - 1);
                    dEnemyPosY[z] = rngVal;
                }
            }
            //Fills up the vacant spots where shelters should be
        }

       //Prints all the enemy locations in case I need to debug
       for (int g = 0; g < dEnemyPosX.length; g++) {
            if (dEnemyPosX[g] > 0 && dEnemyPosY[g] > 0) {
                System.out.println("Enemy at (" + dEnemyPosX[g] + "," + dEnemyPosY[g] + ").");
            }
        }
    }
    static void dungeonTurnLoop() {
        System.out.println("You have " + (dungeonTimer - dungeonTurn) + " turns remaining.");
        System.out.println("Your hp: " + playerHP);
        System.out.println("Your coordinates: (" + playerPosX + "," + playerPosY + ").");
        System.out.println("Press 1 to regain your strength, or press 2 to keep going...");
    }

    static void dungeonRecover() {
        System.out.println("You recover a bit of your strength.");
        tempVal = Math.round (playerHP/10);
        playerHP += tempVal;
        if (playerHP > 250 + (level*25)) playerHP = 250 + (level*25);
    }
    static void dungeonPath() {
        if (inWildlands) {
            genericRNG(3,14);
            dungeonExitX = rngVal;
            genericRNG(3, 20 - rngVal);
            dungeonExitY = rngVal;
// The idea is to make the exit max 20 turns away
        }
        else if (inMountains) {
            genericRNG(1,19);
            dungeonExitX = rngVal;
            genericRNG(2, 25 - dungeonExitX);
            dungeonExitY = rngVal;
        }
        System.out.println("Exit is at (" + dungeonExitX + "," + dungeonExitY + ").");
    }

    static void dungeonMove() {
        System.out.println("Press W,A,S, or D to choose how you want to move. Only 1 letter.");
        answerType = KB.next();
            while (answerType.length() > 1) {
                System.out.println("Too long. Try again.");
                answerType = KB.next();
            }
            hasConfirmed = false;
            while (!hasConfirmed) {
                switch (answerType) {
               case "a":
                case "A": {
                    playerPosX -= 1;
                    if (playerPosX < 0) {
                        System.out.println("You almost fall into a secret pit.");
                        playerPosX = spotCheckX;
                    } else { hasConfirmed = true; break;}
                }
                case "d":
                case "D": 
                {
                    playerPosX += 1;
                    if (playerPosX > dungeonExitX) {
                        System.out.println("You almost run into spikes.");
                        playerPosX = spotCheckX;
                    } else { hasConfirmed = true; break; }
                }
                case "w":
                case "W":
                    {
                        playerPosY += 1;
                        if (playerPosY > dungeonExitY) {
                            System.out.println("A dark voice starts chanting, so you figure it's best to turn around.");
                            playerPosY = spotCheckY;
                        } else { hasConfirmed = true; break; }
                     }
                case "s": 
                case "S": {
                        playerPosY = playerPosY - 1;
                        if (playerPosY < 0) {
                            System.out.println("You almost fell into a secret pit.");
                            playerPosY = spotCheckY;
                        } else { hasConfirmed = true; break; }
                    }
                }
            }
}

static void dungeonExit() throws InterruptedException {
    dungeonStopMusic();
    if (dungeonTimer - dungeonTurn != 0)
    {
        System.out.println("Through your might, you escaped!");
        tempVal = coin;
        genericRNG(dungeonPower/4, dungeonPower/2);
        coin += Math.round(rngVal);
        System.out.println("You gained " + (coin - tempVal) + " coins!");
        genericRNG(dungeonPower/4,dungeonPower/2);
        tempVal = playerexp;
        playerexp += rngVal;
        System.out.println("You gained " + (playerexp - tempVal) + " exp!");
}
    else if (playerHP > 0) {
        System.out.println("You ran out of time...");
        coin -= coin/10;
    }
    else {
        System.out.println("The horrors of the dungeon proved too much for you to handle...");
        coin -= coin/10;
    }
        playerPosX = dungeonTempVal;
        playerPosY = dungeonTempVal2;
        dungeonPosX[dungeonTempVal] = -6;
        dungeonPosY[dungeonTempVal2] = -6;
        Thread.sleep(2000);
}

static void dChestSpawner() {
    for (int b = 0; b == dnumofchest; b++) {
        // the if statement checks if the chest is on a enemy tile or a shop tile
        while (chestPosX[b] <= 0 || chestPosY[b] <= 0) {
            genericRNG(1,dungeonExitX);
            dchestPosX[b] = rngVal;
            genericRNG(1, dungeonExitY);
            dchestPosY[b] = rngVal;
        }
}
    for (int b = 0; b < dchestPosX.length; b++){
        if (dchestPosX[b] != 0 && dchestPosY[b] != 0) System.out.println("Chest at (" + dchestPosX[b] + "," + dchestPosY + ").");
    }
}
     protected static void dungeonMusic() {
         Music.dungeonSetFile(13);
         Music.dungeonStartClip();
         Music.dungeonLoopClip();
         musicPlaying = true;
     }

     protected static void dungeonStopMusic() {
         if (Music.dayMusic != null) {
             Music.dungeonStopClip();
             musicPlaying = false;
         } else System.out.println("Clip is already stopped or not initialized.");
     }
     protected static void dungeonSoundEffect(int trackNum) {
         Music.setFile(trackNum);
         Music.startClip();
     }
}