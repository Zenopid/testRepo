import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Dungeon extends Primary {

    public static int dnumofchest;
    public static int dungeonEnemies = 0;
    public static int dungeonTempVal = 0;

    public static int dungeonTempVal2 = 0;
    
    public static int[] dEnemyPosX = new int[500];

    public static int[] dEnemyPosY = new int[500];

    public static int dungeonTimer = 0;

    public static int dungeonTurn = 0;

    public static int dungeonExitX = 100;

    public static int dungeonExitY = 100;

    public static int dungeonPower = 0;


    public static void  undergroundExplore() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        dungeonPath();
        dungeonEnemySpawner();
        startDungeon();
            while (inDungeon) {
                if (dungeonTimer - dungeonTurn == 0) {
                     dungeonExit();
                     inDungeon = false;
                }
                dungeonTurnLoop();
                playerResponse();
                if (answer == 1) {
                    dungeonRecover();
                }
                else if (answer == 2) {
dungeonMove();
                }
                for (int m = 0; m < enemyPosX.length; m++) {
                    if (playerPosX == dEnemyPosX[m] && playerPosY == dEnemyPosY[m] && !inDungeon) {
                        isFighting = true;
                        clearConsole();
                        decayImp -= 5;
                        Combat.fight();
                    }
                }
                if (playerPosX == dungeonExitX && playerPosY == dungeonExitY) {
                    dungeonExit();
                    inDungeon = false;
                }
            }
        }
        static void chestNumber() throws InterruptedException {
        if (inWildlands) {
            rngValmin = 1;
            rngValmax = 2;
            genericRNG();
            dnumofchest  = rngVal;
        }
        else if (inMountains) {
            rngValmin = 2;
            rngValmax = 3;
            genericRNG();
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
            rngValmin = 350;
            rngValmax = 100;
            genericRNG();
            dungeonPower = rngVal;
        } else if (inMountains) {
            dungeonTimer = 30;
            rngValmin = 700;
            rngValmax = 125;
            genericRNG();
            dungeonPower = rngVal;
        }
    }

    static void distanceFromExit() throws InterruptedException {
        if ((tempVal - dungeonExitX) > (playerPosX - dungeonExitX) || (tempVal2 - dungeonExitY) > (playerPosY - dungeonExitY))
        {
            System.out.println("Colder...");
        }
        else
        {
            System.out.println("Warmer...");
        }
        if ((playerPosX - dungeonExitX) <= 5 || (playerPosY - dungeonExitY) <= 5)
        {
            System.out.println("Getting pretty warm...");
        }
    }
    static void dungeonEnemySpawner() throws InterruptedException {
        if (inWildlands)
        {
            rngValmin = 10;
            rngValmax = 5;
            genericRNG();
            dungeonEnemies = rngVal;
        }
        else 
         {
             rngValmin = 20;
             rngValmax = 5;
             genericRNG();
             dungeonEnemies = rngVal;
          }
        for (int z = 0; z < dungeonEnemies; z++) {
            //This checks if a enemy is on a shop tile.
            if (dEnemyPosX[z] <= 0 && dEnemyPosY[z] <= 0)
            //Hopefully stops enemy locations from changing...
            {
                    rngValmin = 1;
                    rngValmax = dungeonExitX - 1;
                    genericRNG();
                    dEnemyPosX[z] = rngVal;
                    rngValmax = dungeonExitY - -1;
                    genericRNG();
                    dEnemyPosY[z] = rngVal;
            }
            //Fills up the vacant spots where shelters should be
        }
    }
    static void dungeonTurnLoop() throws InterruptedException {
        System.out.println("You have " + (dungeonTimer - dungeonTurn) + " turns remaining.");
        System.out.println("Your hp: " + playerHP);
        System.out.println("Your coordinates: (" + playerPosX + "," + playerPosY + " ).");
        System.out.println("Press 1 to regain your strength, or press 2 to keep going...");
    }

    static void dungeonRecover() throws InterruptedException {
        System.out.println("You recover a bit of your strength.");
        tempVal = Math.round (playerHP/10);
        playerHP += tempVal;
        if (playerHP > 250 + (level*25))
        {
            playerHP = 250 + (level*25);
        }
    }
    static void dungeonPath() throws InterruptedException {
        if (inWildlands) {
            rngValmin = 1;
            rngValmax = 14;
            genericRNG();
            dungeonExitX = rngVal;
            rngValmax = 20 - dungeonExitX;
            genericRNG();
            dungeonExitY = rngVal;
        }
        else if (inMountains) {
            rngValmin = 1;
            rngValmax = 19;
            genericRNG();
            dungeonExitX = rngVal;
            rngValmax = 25 - dungeonExitX;
            genericRNG();
            dungeonExitY = rngVal;
        }
    }

    static void dungeonMove() throws InterruptedException {
        tempVal = playerPosX;
        tempVal2 = playerPosY;
        System.out.println("Press W,A,S, or D to choose how you want to move. Only 1 letter.");
        answerType = KB.next();
            while (answerType.length() > 1) {
                System.out.println("Too long. Try again.");
                answerType = KB.next();
            }
            hasConfirmed = false;
            while (!hasConfirmed) {
                if (answerType.contains("A") || answerType.contains("a")) {
                    playerPosX -= 1;
                    if (playerPosX < 0) {
                        System.out.println("You almost fall into a secret pit.");
                        playerPosX = (int) tempVal;
                    } else {
                        hasConfirmed = true;
                    }
                }
                if (answerType.contains("D") || answerType.contains("d")) {
                    playerPosX += 1;
                    if (playerPosX > dungeonExitX) {
                        System.out.println("You almost run into spikes.");
                    } else {
                        hasConfirmed = true;
                    }
                }
                    if (answerType.equals("W") || answerType.equals("w")) {
                        playerPosY += 1;
                        if (playerPosY > dungeonExitY) {
                            System.out.println("A dark voice starts chanting, so you figure it's best to turn around.");
                            playerPosY = (int) tempVal2;
                        } else {
                            hasConfirmed = true;
                        }
                    }
                    if (answerType.equals("S") || answerType.equals("s")) {
                        playerPosY = playerPosY - 1;
                        if (playerPosY < 0) {
                            System.out.println("You almost fell into a secret pit.");
                            playerPosY = playerPosY + 1;
                            playerPosY = (int) tempVal2;
                        } else {
                            hasConfirmed = true;
                        }
                    }
                }
            }


static void dungeonExit() throws InterruptedException {
    if (dungeonTimer - dungeonTurn != 0)
    {
        System.out.println("Through your might, you escaped!");
        tempVal = playerexp;
        playerexp += (dungeonPower*1.5);
        System.out.println("You gained " + (playerexp - tempVal) + " exp!");
        tempVal = coin;
        rngValmin = dungeonPower/4;
        rngValmax = dungeonPower/2;
        genericRNG();
        coin += Math.round(rngVal);
        System.out.println("You gained " + (coin - tempVal) + "coins!");
        playerPosX = dungeonTempVal;
        playerPosY = dungeonTempVal2;
}
    else {
        System.out.println("You ran out of time...");
        dungeonPosX[dungeonTempVal] = -6;
        dungeonPosY[dungeonTempVal2] = -6;
        playerPosX = dungeonTempVal;
        playerPosY = dungeonTempVal2;
    }
}

static void dChestSpawner() throws InterruptedException {
    for (int b = 0; b == dnumofchest; b++) {
        // the if statement checks if the chest is on a enemy tile or a shop tile
        while (chestPosX[b] <= 0 && shopPosY[b] <= 0 && chestPosX[b] != shopPosX[b] && chestPosY[b] != shopPosY[b] && chestPosX[b] != enemyPosX[b] && chestPosY[b] != enemyPosY[b]) {
            rngValmin = 0;
            rngValmax = 25;
            genericRNG();
            chestPosX[b] = rngVal;
            genericRNG();
            chestPosY[b] = rngVal;
        }
}
}
}