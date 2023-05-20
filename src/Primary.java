
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.util.HashMap;
public class Primary {
    static Scanner KB = new Scanner(System.in);


    /*
    Used in case I want to display a difference (I.E stat improvement)

    tempVal = current value
    then I run the formula to increase/decrease value
    then I do (newval - tempVal) for increase or (tempVal - newval) for decrease
     */
    protected static int decayImp = 0;
    //imp stands for improve, this is to decrease gear improvement if used too much

    protected static int level = 1;

    protected static int rngVal = 0;

    protected static int rngValmin = 0;

    protected static int rngValmax = 0;


    /*
     generic ints for all random number generator needs
    rngVal = the value created in the rng code
    rngValmin = the minimum amount the code can create
    rngValmax = the random number possibility

    for example

    rngValmin = 50
    rngValmax = 50

    this would create a value of 50 and a random number between 0 and a maximum of 50

    then these 2 values would be added together to form rngVal

    which then can be pulled by another method.
     */

    //Time and weather
    protected static int day = 1;

    protected static int weather = 0;

    protected static boolean spentDay = false;

    // Gear and stats

    protected static int requiredXP = 100;

    protected static int skillPoints = 0;
    protected static double playerDefense = 0;

    protected static double playerSpeed = 0;

    protected static double weapondmg = 0;

    protected static double playerPower = 0;

    protected static boolean masteredWeapon = false;
    protected static boolean hasSuper = false;

    protected static String weaponName = "";
    protected static int grapplingHook = 0;

    protected static int regenerativeStone = 0;

    protected static int soulSnatcher = 0;

    protected static double playerHP = 200 + (50* level);


    protected static int coin = 0;

    //Money. Used to buy utility items as well as fast travel.


    //Combat

    static HashMap<String, String> moveSet = new HashMap<>();


    protected static int numofmoves = 5;

    protected static String enemyName = "";
    protected static boolean playerIsBlocking = false;

    protected static boolean enemyIsBlocking = false;

    protected static int ccAmount = 0;

    protected static boolean playercounterHit = false;

    protected static boolean enemycounterHit = false;
    protected static int playerStamina = 100;

    protected static int enemyStamina = 100;

    protected static int staminaDamage = 0;

    protected static int enemyStaminaDamage = 0;

    protected static int superMeter = 0;
    protected static boolean playerIsAttacking = false;

    protected static boolean playerHasWhiffed = false;

    protected static int playerWhiffedturns = 0;

    protected static int enemyWhiffedturns = 0;

    protected static boolean enemyIsAttacking = false;

    protected static boolean enemyHasWhiffed = false;

    protected static String moveName = "";
    protected static int rngbaddie = 0;

    protected static double enemyHP;

    protected static double enemyDefense = 0;

    protected static double enemyDamage = 0;

    protected static double enemyPower = 0;

    protected static int moveRange = 0;

    protected static double moveDamage = 0;

    protected static double moveSpeed = 0;

    protected static String enemymoveName = "";

    protected static double enemymoveDamage = 0;

    protected static double enemymoveSpeed = 0;

    protected static int enemymoveRange = 0;

    protected static double enemySpeed = 0;

    protected static double enemyQuiver = 5;

    protected static boolean hasGrapplingHook = false;

    protected static int fightRange = 2;
    protected static int turn = 1;
    protected static boolean isFighting;

    protected static boolean isBoss;

    protected static boolean bossIsSwordsman;

    protected static boolean bossIsBowman;

    protected static boolean bossIsShieldsman;

    protected static int bossHasSwapped = 0;


    //World interaction

    protected static boolean playerLeft = false;

    protected static boolean hasConfirmedShop = false;

    protected static boolean onShop;

    protected static boolean onDungeon = false;

    protected static int numofshops = 1;

    protected static int exchangeRate, improveRate = 0;
    protected static int[] shopPosX = new int[100];

    protected static int[] shopPosY = new int[100];

    protected static int shopCNT = 0;
    protected static int numofchest = 1;
    protected static int[] chestPosX = new int[100];
    protected static int[] chestPosY = new int[100];
    protected static int numofenemy = 1;

    protected static int numOfEnemiesDefeated = 0;
    protected static boolean hasConfirmed = false;
    protected static int[] enemyPosX = new int[100];

    protected static int[] enemyPosY = new int[100];

    protected static int[] shelterPosX = new int[100];

    protected static int[] shelterPosY = new int[100];

    protected static int numofshelters = 10;

    protected static int boltPosX = -6;

    protected static int boltPosY = -6;

    protected static int[] dungeonPosX = new int[100];

    protected static int[] dungeonPosY = new int[100];

    protected static int numofdungeons = 0;
    protected static boolean underShelter = true;
    protected static int playerexp = 0;

    protected static int playerPosX = 0;

    protected static int playerPosY = 0;

    protected static boolean inWildlands = true;

    protected static boolean inMountains = false;

    protected static boolean inDungeon = false;

    //Assorted Variables (Not specfic to any one aspect of the game)

    protected static double tempVal = 0;

    protected static double tempVal2 = 0;

    protected static double tempVal3 = 0;

        /*
     generic ints for all random number generator needs
    rngVal = the value created in the rng code
    rngValmin = the minimum amount the code can create
    rngValmax = the random number possibility

    for example

    rngValmin = 50
    rngValmax = 50

    this would create a value of 50 and a random number between 0 and a maximum of 50

    then these 2 values would be added together to form rngVal

    which then can be pulled by another method.
     */


    protected static int answer = 0;

    protected static String answerType = "";
    protected static boolean isSnowing = false;
    protected static boolean hasConfirmedAtk;
    protected static boolean hasConfirmedMove;

    protected static boolean hasConfirmedCombat = false;
    private static int cost;

    //Music

    protected static boolean musicPlaying = false;


    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
     Music music = new Music();
        for (int cnt = 0; cnt < enemyPosX.length; cnt++) {
            enemyPosX[cnt] = -2;
            enemyPosY[cnt] = -2;
            shopPosX[cnt] = -3;
            shopPosY[cnt] = -3;
            chestPosX[cnt] = -4;
            chestPosY[cnt] = -4;
            shelterPosX[cnt] = -5;
            shelterPosY[cnt] = -5;
            dungeonPosX[cnt] = -7;
            dungeonPosY[cnt] = -7;
        }
        enemyPosX[1] = 1;
        enemyPosY[1] = 1;
        shopPosX[1] = 2;
        shopPosY[1] = 2;
        dungeonPosX[1] = 3;
        dungeonPosY[1] = 3;
        chestPosX[0] = 1;
        chestPosY[0] = 0;
        System.out.println("Major Project");
        Thread.sleep(1000);
        System.out.println("The king of Zenotopia is holding a battle royale. Survive 100 days, and defeat the last person to win any 1 gift from the king.");
        System.out.println("Press 1 to skip offense tutorial, or press 2 to continue.");
        playerResponse();
        if (answer != 1) offenseTutorial();
        System.out.println("Press 1 to skip defense tutorial, or press 2 to continue.");
        playerResponse();
        if (answer != 1) defenseTutorial();
        selectWeapon();
        playerResponse();
        if (answer == 1) getSword();
        else if (answer == 2) getShield();
        else if (answer >= 3) getAxe();
        //Day loop
        while (day <= 100) {
            if (playerHP <= 0) playerDied();
            System.out.println("Its day " + day);
            enemySpawner();
            chestSpawner();
            shopSpawner();
            dungeonSpawner();
            shelterSpawner();
            randomWeather();
            weatherEffects();
            spentDay = false;
            Thread.sleep(1000);
            while (!spentDay) {
                if(!musicPlaying) playMusic(0);
                dayLoop();
                for (int cnt3 = 0; cnt3 < shopPosX.length; cnt3++) {
                    if (playerPosX == shopPosX[cnt3] && playerPosY == shopPosY[cnt3] && !isSnowing && !inDungeon) {
                        System.out.println("You found a shop! Press 5 to go in!");
                        onShop = true;
                        underShelter = true;
                        break;
                    } else if (isSnowing && tempVal < -999) {
                        System.out.println("You found a shop, but it seems like they're closed...");
                        onShop = false;
                        underShelter = true;
                        tempVal = 2;
                    } else {
                        onShop = false;
                    }
                }
                playerResponse();
                if (answer == 1 || answer >= 6) upgradeGear();
                else if (answer == 2) {
                    hasConfirmed = false;
                    tempVal = playerPosX;
                    tempVal2 = playerPosY;
                    exploreZenotopia();
                    playerPosChecker();
                } else if (answer == 3) skillTree();
                else if (answer == 4) playerLoadout();
                else if (answer >= 5 && onShop) {
                    if (inWildlands) basicShop();
                    if (inMountains) advancedShop();
                } else if (answer >= 5 && onDungeon) Dungeon.undergroundExplore();
            }
            playerPosChecker();
            for (int m = 0; m < enemyPosX.length; m++) {
                if (playerPosX == enemyPosX[m] && playerPosY == enemyPosY[m] && !inDungeon) {
                    tempVal3 = m;
                    isFighting = true;
                    clearConsole();
                    decayImp -= 5;
                    stopMusic();
                    resetMusic();
                    rngValmin = 1;
                    rngValmax = 2;
                    genericRNG();
                     playMusic(rngVal);
                    Combat.fight();
                    stopMusic();
                    resetMusic();
                }
            }
            endOfDay();
        }
        Combat.finalBattle();
        Combat.fight();
        ending();
    }
    // mayowa.... why....
    //This was supposed to be less complicated then the 1st version...
    static void offenseTutorial() throws InterruptedException {
        System.out.println("Offense");
        Thread.sleep(1000);
        System.out.println("Hit the other guy until they have no more hp.");
        Thread.sleep(1000);
        System.out.println("All moves have range,speed, damage, stamina damage and stamina cost.");
        Thread.sleep(1000);
        System.out.println("Some moves also have crowd control.");
        Thread.sleep(1000);
        System.out.println("Stun: Prevent your opponent from moving.");
        System.out.println("Knockback: Pushes your opponent back, creating space.");
        System.out.println("Vaccum: Brings your enemy closer.");
        System.out.println("Cripple: Reduces the enemy's damage and defense.");
        Thread.sleep(3000);
        System.out.println("There are also utility items that allow you to increase your stats.");
        Thread.sleep(1000);
    }

    static void defenseTutorial() throws InterruptedException {
        System.out.println("Defense");
        Thread.sleep(1000);
        System.out.println("Move out of the way of attacks to dodge them, allowing you to counter attack.");
        Thread.sleep(1000);
        System.out.println("If you counter attack in the same turn that an opponent whiffs, you do 35% more damage.");
        Thread.sleep(1000);
        System.out.println("Can also block to reduce incoming damage, as well as get some stamina back.");
        Thread.sleep(1000);
        System.out.println("If your stamina drops to 0, you will be briefly stunned. Move or block an attack to regain some stamina.");
        Thread.sleep(1000);
    }

    static void selectWeapon() throws InterruptedException {
        System.out.println("Select your starting weapon: ");
        System.out.println("1: Sword: Balanced between offense and defense.");
        System.out.println("2: Shield: High defense and speed at the cost of offense.");
        System.out.println("3: Axe: High offense at the cost of defense and speed.");
    }


    static void genericRNG() throws InterruptedException {
        Random rand = new Random();
        rngVal = rand.nextInt(rngValmax) + rngValmin;
    }

    static void enemySpawner() throws InterruptedException {
        if (!isSnowing && day % 2 == 0) {
            rngValmax = 2;
            rngValmin = 2;
            genericRNG();
            numofenemy += rngVal;
        } else if (isSnowing) {
            System.out.println("No new enemies are coming because of the snow...");
        }
        for (int z = 0; z < numofenemy; z++) {
            //This checks if an enemy is on a shop tile.
            while (enemyPosX[z] <= 0 && enemyPosY[z] <= 0 && enemyPosX[z] != shopPosX[z] && enemyPosY[z] != shopPosY[z])
            //Hopefully stops enemy locations from changing...
            {
                if (inWildlands) {
                    rngValmin = 1;
                    rngValmax = 24;
                    genericRNG();
                    enemyPosX[z] = rngVal;
                    genericRNG();
                    enemyPosY[z] = rngVal;
                } else if (inMountains) {
                    rngValmin = 25;
                    rngValmax = 25;
                    genericRNG();
                    enemyPosX[z] = rngVal;
                    rngValmin = 1;
                    rngValmax = 24;
                    enemyPosY[z] = rngVal;
                }
            }
            //Fills up the vacant spots where shelters should be
        }
    }

    static void dungeonSpawner() throws InterruptedException {
        /*if (day%5 == 0)*/{
            rngValmin = 10;
            rngValmax = 4;
            genericRNG();
            numofdungeons -= rngVal;
        }
        for (int z = 0; z < numofdungeons; z++) {
            //This checks if an shelter is on a shop tile.
            while (dungeonPosX[z] <= 0 && dungeonPosY[z] <= 0 && dungeonPosX[z] != dungeonPosX[z] && dungeonPosY[z] != dungeonPosY[z])
            //Hopefully stops shelter locations from changing...
            {
                if (inWildlands) {
                    rngValmin = 1;
                    rngValmax = 24;
                    genericRNG();
                    dungeonPosX[z] = rngVal;
                    genericRNG();
                    dungeonPosY[z] = rngVal;
                } else if (inMountains) {
                    rngValmin = 25;
                    rngValmax = 25;
                    genericRNG();
                    dungeonPosX[z] = rngVal;
                    rngValmin = 1;
                    rngValmax = 24;
                    dungeonPosY[z] = rngVal;
                }
            }
            //Fills up the vacant spots where shelters should be
        }
        /* dungeon locator for debugging
        for (int c = 0; c < dungeonPosX.length; c++) {
            if (dungeonPosX[c] > 0 && dungeonPosY[c] > 0) {
                System.out.println("Dungeon at (" + dungeonPosX[c] + "," + dungeonPosY[c] + ").");
            }
        }
         */

    }
    static void shelterSpawner() throws InterruptedException {
        rngValmin = 0;
        rngValmax = 3;
        genericRNG();
        numofshelters += rngVal;
        for (int z = 0; z < numofshelters; z++) {
            //This checks if a shelter is on a shop tile.
            while (shelterPosX[z] <= 0 && shelterPosY[z] <= 0 && shelterPosX[z] != shopPosX[z] && shelterPosY[z] != shopPosY[z])
            //Hopefully stops shelter locations from changing...
            {
                if (inWildlands) {
                    rngValmin = 1;
                    rngValmax = 24;
                    genericRNG();
                    shelterPosX[z] = rngVal;
                    genericRNG();
                    shelterPosY[z] = rngVal;
                } else if (inMountains) {
                    rngValmin = 25;
                    rngValmax = 25;
                    genericRNG();
                    shelterPosX[z] = rngVal;
                    rngValmin = 1;
                    rngValmax = 24;
                    shelterPosY[z] = rngVal;
                }
            }
            //Fills up the vacant spots where shelters should be
        }
    }

    static void playerPosChecker() throws InterruptedException {
        //Things to check for when the player is moving around
        for (int cnt4 = 0; cnt4 < shelterPosX.length; cnt4++) {
            if (playerPosX == shelterPosX[cnt4] && playerPosY == shelterPosY[cnt4]) {
                System.out.println("You are under shelter.");
                underShelter = true;
                break;
            } else {
                underShelter = false;
            }
        }
        for (int m = 0; m < dungeonPosX.length; m++) {
            if (playerPosX == dungeonPosX[m] && playerPosY == dungeonPosY[m]) {
                System.out.println("Press 5 to enter a dungeon. Great treasures await, but at a great risk...");
                onDungeon = true;
                break;
            } else {
                onDungeon = false;
            }
        }
        for (int c = 0; c < numofchest; c++) {
            if (playerPosX == chestPosX[c] && playerPosY == chestPosY[c]) {
                System.out.println("You've found a chest!");
                rngValmin = 1;
                rngValmax = 3;
                genericRNG();
                tempVal = rngVal;
                if (tempVal == 1) {
                    tempVal = weapondmg;
                    rngValmin = (day * level)/2 + 25;
                    rngValmax = 50;
                    genericRNG();
                    weapondmg += rngVal;
                    System.out.println("Your damage increased by " + (weapondmg - tempVal) + " !");
                } else if (tempVal == 2) {
                    tempVal = playerSpeed;
                    rngValmin = (day * level)/2 + 25;
                    rngValmax = 50;
                    genericRNG();
                    playerSpeed += rngVal;
                    System.out.println("Your speed increased by " + (playerSpeed - tempVal) + " !");
                } else if (tempVal == 3) {
                    tempVal = playerDefense;
                    rngValmin = (day * level)/2 + 25;
                    rngValmax = 50;
                    genericRNG();
                    playerDefense += rngVal;
                    System.out.println("Your defense increased by " + (playerDefense - tempVal) + " !");
                }
                chestPosX[c] = -3;
                chestPosY[c] = -3;
                numofchest -= 1;
            }
        }
    }

    static void getSwordRNG() throws InterruptedException {
        rngValmin = 95;
        rngValmax = 35;
        genericRNG();
        weapondmg = weapondmg + rngVal;
        genericRNG();
        playerDefense = playerDefense + rngVal;
        genericRNG();
        playerSpeed = playerSpeed + rngVal;

    }

    static void getSword() throws InterruptedException {
        System.out.println("Sword? You're really basic.");
        weaponName = "Sword";
        getSwordRNG();
        moveSet.put("Move 1","Forward Swipe");
        moveSet.put("Move 2","Downward Slice");
        moveSet.put("Move 3","Pommel");
        moveSet.put("Move 4","Shove");
        moveSet.put("Move 5","Thrust");
        numofmoves = 5;
    }

    static void getShieldRNG() throws InterruptedException {
        rngValmin = 65;
        rngValmax = 25;
        genericRNG();
        weapondmg = weapondmg + rngVal;
        rngValmin = 150;
        rngValmax = 25;
        genericRNG();
        playerDefense = playerDefense + rngVal;
        rngValmin = 120;
        rngValmax = 25;
        genericRNG();
        playerSpeed = playerSpeed + rngVal;
    }

    static void getShield() throws InterruptedException {
        System.out.println("Shield? You must not be very confident.");
        weaponName = "Shield";
        getShieldRNG();
        moveSet.put("Move 1", "Bash");
        moveSet.put("Move 2", "Uppercut");
        moveSet.put("Move 3", "Slam");
        moveSet.put("Move 4", "Shove");
        moveSet.put("Move 5", "Shield Punch");
    }

    static void getAxeRNG() throws InterruptedException {
        rngValmin = 125;
        rngValmax = 50;
        genericRNG();
        weapondmg += rngVal;
        rngValmin = 65;
        rngValmax = 30;
        genericRNG();
        playerSpeed += rngVal;
        rngValmin = 35;
        rngValmax = 35;
        genericRNG();
        playerDefense += rngVal;
    }

    static void getAxe() throws InterruptedException {
        System.out.println("Axe? Now we're talking...");
        weaponName = "Axe";
        getAxeRNG();
        moveSet.put("Move 1", "Forward Slash");
        moveSet.put("Move 2", "Shatter");
        moveSet.put("Move 3", "Butcher");
        moveSet.put("Move 4", "Shove");
        moveSet.put("Move 5", "Skewer");
    }

    protected static void skillTree() throws InterruptedException {
        cost = 0;
        answerType = "";
        hasConfirmed = false;
        while (!playerLeft) {
            System.out.println("Skill points: " + skillPoints);
            System.out.println("Press 0 to exit, or press another number to continue.");
            playerResponse();
            if (answer == 0){
                break;
            }
            System.out.println(weaponName);
             if (weaponName.contains("Sword")) {
                if (level > 1) {
                    System.out.println("0: Ultimate: Phantom Slash: Strike faster then the eye can see, ignoring defense.");
                }
                if (level >= 3) {
                    System.out.println("1: Ki Charge: Powers up the blade, giving you extra range, and bonus damage if spaced.");
                    System.out.println("2: Whirlwind: A giant swinging attack.");
                    System.out.println("3: Rising Upper: Moves forward and slashes upwards, knocking the opponent back.");
                    //  cost = 1;
                }
                if (level >= 5)
                    System.out.println("4: Vanishing Strike: Teleport to a location and slash your opponent.");
                //  cost = 2;
                if (level >= 8) {
                    System.out.println("5: Ki Charge 2: Increases move speed as well as knocking the opponent back.");
                    System.out.println("6: Whirlwind 2: Hits multiple times.");
                    System.out.println("7: Rising Upper 2: Stuns the opponent, allowing for a follow up attack. ");
                    // cost = 2;
                }
                if (level >= 10) {
                    System.out.println("8: Vanishing Strike 2: Deals bonus damage if it counter hits the opponent. ");
                    //    cost = 3;
                }
                if (level >= 12) {
                    System.out.println("9: Sword Mastery: Landing hits with sweet-spot Ki Charge refunds the charge.");
                    //  cost = 4;
                }
                if (level >= 15) {
                    System.out.println("10: Phantom Slash 2: Deals bonus damage for every point of HP missing.");
                    // cost = 5;
                }
                playerResponse();
                if (answer == 0 && level > 1) {
                    answerType = "Phantom Slash";
                    cost = 1;
                    confirmMove();
                }
                if (answer == 1 && level >= 3) {
                    answerType = "Ki Charge";
                    cost = 1;
                    confirmMove();
                }
                if (answer == 2 && level >= 3) {
                    answerType = "Whirlwind";
                    cost = 1;
                    confirmMove();
                }
                if (answer == 3 && level >= 3) {
                    answerType = "Rising Upper";
                    cost = 1;
                    confirmMove();
                }
                if (answer == 4 && level >= 5) {
                    answerType = "Vanishing Strike";
                    cost = 3;
                   confirmMove();
                }
                if (answer == 5 && level >= 8)
                {
                    answerType = "Ki Charge 2";
                    cost = 2;
                  confirmMove();
                }
                if (answer == 6 && level >=8)
                {
                    answerType = "Whirlwind 2";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 7 && level >= 8)
                {
                    answerType = "Rising Upper 2";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 8 && level >= 10)
                {
                    answerType = "Vanishing Strike 2";
                    cost = 3;
                    confirmMove();
                }
                if (answer == 9 && level >= 12)
                {
                    answerType = "Sword Mastery";
                    cost = 3;
                    confirmMove();
                }
                if (answer == 10 && level >= 15)
                {
                    answerType = "Phantom Slash 2";
                    cost = 4;
                    confirmMove();
                }
            } else if (weaponName.contains("Shield")) {
                if (level > 1) {
                    System.out.println("0: Ultimate: Earthquake: Slams the shield into the ground, stunning the enemy.");
                }
                if (level >= 3) {
                    System.out.println("1: Shield Sweep: Spin around, striking your opponents ankles.");
                    System.out.println("2: Swap Hands: Off hand: Deals bonus damage. Dominant hand: Increased speed.");
                    System.out.println("3: Deflect: Parries the opponent's strike, stunning them.");
                }
                if (level >= 5) {
                    System.out.println("4: Shield Toss: Throw your shield like a projectile.");
                }
                if (level >= 8) {
                    System.out.println("5: Shield Sweep 2: Has vacuum.");
                    System.out.println("6: Swap Hands 2: Gives access to Retreating Strike in dominant, and Tai Otoshi in off hand.");
                    System.out.println("Retreating Strike: Dodge backwards, before kicking your opponent.");
                    System.out.println("Tai Otoshi: Grabs the opponent, throwing them down to the ground. Ignores block. ");
                    System.out.println("7: Deflect 2: Deals 25% of the opponent's damage as well.");
                }
                if (level >= 10){
                  System.out.println("8: Shield Toss 2: Get the shield back on successful hit, and do bonus damage with your next strike.");
                }
                if(level >= 12)
                {
                    System.out.println("9: Shield Mastery: Take reduced damage on moves that involve your shield.");
                }
                if (level >= 15)
                {
                    System.out.println("10: Ultimate: Earthquake 2: The next attack deals bonus damage.");
                }
                playerResponse();
                if (answer == 0 && level > 1) {
                    answerType = "Earthquake";
                    cost = 1;
                    confirmMove();
                }
                 if (answer == 1 && level >= 3) {
                     answerType = "Shield Sweep";
                     cost = 1;
                     confirmMove();
                 }
                 if (answer == 2 && level >= 3) {
                     answerType = "Swap Hands";
                     cost = 2;
                     confirmMove();
                 }
                 if (answer == 3 && level >= 3) {
                     answerType = "Deflect";
                     cost = 1;
                     confirmMove();
                 }
                if (answer == 4 && level >= 5) {
                    answerType = "Shield Toss";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 5 && level >= 8)
                {
                    answerType = "Shield Toss 2";
                    cost = 3;
                    confirmMove();
                }
                if (answer == 6 && level >=8)
                {
                    answerType = "Swap Hands 2";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 7 && level >= 8)
                {
                    answerType = "Deflect 2";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 8 && level >= 10)
                {
                    answerType = "Shield Toss 2";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 9 && level >= 12)
                {
                    answerType = "Shield Mastery";
                    cost = 3;
                    confirmMove();
                }
                if (answer == 10 && level >= 15)
                {
                    answerType = "Earthquake 2";
                    cost = 4;
                    confirmMove();
                }
            }
            else if (weaponName.contains("Axe")) {
                if (level > 1) {
                    System.out.println("0: Ultimate: Eradicate: Gives you better ignite for 5 turns, giving bonus burn,armor, and stun on moves with cc.");
                }
                if (level >= 3) {
                    System.out.println("1: Ignite: The next strike does burn damage.");
                }
                if (level >= 5) {
                    System.out.println("2: Overhead Cleave: Swings downward at your opponent, slowing the opponent on hit. ");
                    System.out.println("3: Cripple: Bashes your opponent's head with the blunt side of the axe, lowering the opponent's damage.");
                    System.out.println("4: Pyro-slash: A slow flame wave that ignites the opponent on hit.");
                }
                if (level >= 8) {
                    System.out.println("5:Ignite 2: Burn damage now heals you.");
                }
                if (level >= 10){
                    System.out.println("6: Overhead Cleave 2: Stuns on ignited opponents.");
                    System.out.println("7: Cripple 2: Prevents the opponent from regaining stamina.");
                    System.out.println("8: Pyro-slash 2: Deals impact damage.");
                }
                if(level >= 12)
                {
                    System.out.println("9: Axe Mastery: All moves ignite on counter hit. If already ignited, stun.");
                }
                if (level >= 15)
                {
                    System.out.println("10: Ultimate: Eradicate 2: Ignite stacks, increasing the damage for every successful hit.");
                }
                playerResponse();
                if (answer == 0 && level > 1) {
                    answerType = "Eradicate";
                    cost = 1;
                    confirmMove();
                }
                if (answer == 1 && level >= 3) {
                    answerType = "Ignite";
                    cost = 1;
                    confirmMove();
                }
                if (answer == 2 && level >= 3) {
                    answerType = "Overhead Cleave";
                    cost = 1;
                    confirmMove();
                }
                if (answer == 3 && level >= 3) {
                    answerType = "Cripple";
                    cost = 1;
                    confirmMove();
                }
                if (answer == 4 && level >= 5) {
                    answerType = "Pyroslash";
                    cost = 1;
                    confirmMove();
                }
                if (answer == 5 && level >= 8)
                {
                    answerType = "Ignite 2";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 6 && level >=8)
                {
                    answerType = "Overhead Cleave 2";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 7 && level >= 8)
                {
                    answerType = "Cripple 2";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 8 && level >= 10)
                {
                    answerType = "Pyroslash 2";
                    cost = 2;
                    confirmMove();
                }
                if (answer == 9 && level >= 12)
                {
                    answerType = "Axe Mastery";
                    cost = 3;
                    confirmMove();
                }
                if (answer == 10 && level >= 15)
                {
                    answerType = "Eradicate 2";
                    cost = 4;
                    confirmMove();
                }
            }
            }
        }

    static void confirmMove() throws InterruptedException {
        System.out.println("Cost: " + cost);
        System.out.println("Press 1 to confirm you want " + answerType);
        playerResponse();
        boolean hasPrevious = false;
        if (answer == 1 && skillPoints >= cost) {
            if (answerType.contains("2"))
                for (int counter = 0; counter < numofmoves; counter++) {
                    moveSet.get("Move " + numofmoves);
                    if (moveSet.containsValue(answerType)) {
                        //They have the previous move, so they're ok to get the next one
                        hasPrevious = true;
                        break;
                    }
                }
            if (hasPrevious || !answerType.contains("2")) {
                numofmoves += 1;
                moveSet.put("Move " + numofmoves, answerType);
                skillPoints -= cost;
                System.out.println("You have unlocked " + answerType);
                answer = 0;
            } else if (answerType.contains("2")) {
                System.out.println("You do not have the previous move, so you cannot get this.");
            }
        } else if (skillPoints < cost) {
            System.out.println("Not enough points.");
        } else {
            System.out.println("You did not confirm.");
        }
    }

    static void playerLoadout() throws InterruptedException {
        boolean countPast5;
        boolean changingLoadout = true;
        System.out.println("Press 0 to exit, or press another number to continue.");
        playerResponse();
        if (answer == 0)  changingLoadout = false;
        while (changingLoadout) {
            countPast5 = false;
            {
                System.out.println("Equipped: ");
                System.out.println("---------------------------------------------------------------");
            }
            for (int count = 1; count < numofmoves + 1; count++) {
                System.out.println("Move " + count + ": " + moveSet.get("Move " + count) + ".");
                if (count >= 5 && !countPast5) {
                    System.out.println("---------------------------------------------------------------");
                    //When the 5tb move is displayed, this line will also appear, shwoing that it's the last equipped move.
                    countPast5 = true;
                }
            }
            System.out.println("Select the move you want to swap out using its corresponding number.");
            playerResponse();
            String equippedMove = moveSet.get("Move " + answer);
            while (equippedMove == null) {
                System.out.println("Invalid answer. Please try again.");
                playerResponse();
                equippedMove = moveSet.get("Move " + answer);
            }
            System.out.println("Old move is " + equippedMove);
            int x = answer;
            System.out.println("Select the move you want to swap in.");
            playerResponse();
            String newMove = moveSet.get("Move " + answer);
            while (equippedMove.equals(newMove) || newMove == null) {
                System.out.println("Invalid answer. Please try again.");
                playerResponse();
                newMove = moveSet.get("Move " + answer);
            }
            System.out.println("New move is " + newMove);
            moveSet.replace("Move " + x, equippedMove, newMove);
            moveSet.replace("Move " + answer, newMove, equippedMove);
            {
                System.out.println("Equipped: ");
                System.out.println("---------------------------------------------------------------");
            }
            countPast5 = false;
            for (int count = 1; count < numofmoves + 1; count++) {
                System.out.println("Move " + count + ": " + moveSet.get("Move " + count) + ".");
                if (count >= 5 && !countPast5) {
                    System.out.println("---------------------------------------------------------------");
                    countPast5 = true;
                    //welp... i have no idea.
                }
            }
            System.out.println("Press 0 to leave, or press another number to continue.");
            playerResponse();
            if (answer == 0) changingLoadout = false;
        }
    }

    static void chestSpawner() throws InterruptedException {
            rngValmin = 1;
            rngValmax = 4;
            genericRNG();
            numofchest += rngVal;
        for (int b = 0; b < numofchest; b++) {
            // the if statement checks if the chest is on a enemy tile or a shop tile
            while (chestPosX[b] <= 0 && chestPosY[b] <= 0 && chestPosX[b] == shopPosX[b] && chestPosY[b] == shopPosY[b] && chestPosX[b] == enemyPosX[b] && chestPosY[b] == enemyPosY[b]) {
                rngValmin = 0;
                rngValmax = 25;
                genericRNG();
                chestPosX[b] = rngVal;
                genericRNG();
                chestPosY[b] = rngVal;
            }
        }
        for (int h = 0; h < chestPosX.length; h++) {
            if (chestPosX[h] > 0 && chestPosY[h] > 0) {
                System.out.println("Chest at (" + chestPosX[h] + "," + chestPosY[h] + ").");
            }
        }
    }

    static void shopSpawner() throws InterruptedException {
        if (day % 6 == 0) {
            rngValmin = 1;
            rngValmax = 2;
            genericRNG();
            numofshops += rngVal;
        }
        for (int b = 0; b < numofshops; b++) {
            while (shopPosX[b] <= 0 && shopPosY[b] <= 0) {
                rngValmin = 0;
                rngValmax = 25;
                genericRNG();
                shopPosX[b] = rngVal;
                genericRNG();
                shopPosY[b] = rngVal;
                for (int c = b + 1; c < numofshops; c++)
                    while (shopPosX[b] == enemyPosX[b] && shopPosY[b] == enemyPosY[b] || shopPosX[b] == dungeonPosX[b] && shopPosY[b] == dungeonPosY[b] || shopPosX[b] == shopPosY[c]) {
                        rngValmin = 0;
                        rngValmax = 25;
                        genericRNG();
                        shopPosX[b] = rngVal;
                        genericRNG();
                        shopPosY[b] = rngVal;
                    }
            }
        }
            /* in case I need to check where shops are for debugging
            for (int cnt = 0; cnt < shopPosX.length; cnt++) {
                if (shopPosX[cnt] > 0)
                    System.out.println("There's a shop at (" + shopPosX[cnt] + "," + shopPosY[cnt] + ").");
            }
        }
             */
    }

    static void clearConsole() throws InterruptedException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void dayLoop() throws InterruptedException {
        System.out.println("Number of enemies: " + numofenemy);
        playerPower = (playerHP + weapondmg + playerDefense + playerSpeed) / 4;
        System.out.println("Your hp: " + Math.round(playerHP));
        System.out.println("Your level: " + level);
        System.out.println("Your money: " + coin);
        System.out.println("Your gear:");
        System.out.println("Your power rating: " + Math.round(playerPower));
        System.out.println("Defense: " + Math.round(playerDefense));
        System.out.println("Damage: " + Math.round(weapondmg));
        System.out.println("Speed: " + Math.round(playerSpeed));
        System.out.println("Your weapons:");
        System.out.println(weaponName);
        System.out.println("Your coordinates: (" + playerPosX + "," + playerPosY + ")");
        System.out.println("1: Upgrade Gear");
        System.out.println("2: Explore");
        System.out.println("3: Skill Tree");
        System.out.println("4: Change Loadout");
        System.out.println("5: Settings");

    }

    static void settings() throws InterruptedException {
        System.out.println("1: Volume");
        System.out.println("there's litteraly nothing else to change except volume lol");
        playerResponse();
        if (answer == 1) {
            System.out.println("Type desired volume.");
            playerResponse();

        }
    }

    static void playerDied() throws InterruptedException {
        System.out.println("You lost. The hours turn into days as you regain your strength...");
        day += 10;
        playerexp += 15;
        playerHP = 200 + (level*50);
    }

    static void randomWeather() throws InterruptedException {
        if (day % 6 == 0) {
            //Every 7 days, the weather will change
            rngValmin = 1;
            rngValmax = 100;
            genericRNG();
            if (rngVal <= 1 && rngVal >= 14) {
                weather = 2;
                //Thunderstorm
                //Has a chance of randomly destroying generated things like shops, enemies and dungeons
            } else if (rngVal <= 15 && rngVal >= 23) {
                weather = 3;
                //Duststorm
                //Makes it impossible to see, hiding fight distance
            } else if (rngVal <= 24 && rngVal >= 40) {
                weather = 4;
                //Temporal wind
                //Randomly changes the day count
            } else if (rngVal >= 40 && rngVal <= 47) {
                weather = 5;
                //Incendiary rain
                //Deals damage over time if you're not under shelter
            } else if (rngVal >= 48 && rngVal <= 59) {
                weather = 6;
                //Snow
                //Shops are closed, enemies hide under shelter.
            }
            else {
                weather = 1;
                //Clear
                //No effect
            }
        }
    }

    static void weatherEffects() throws InterruptedException {
        System.out.println("Weather: ");
        if (!inDungeon) {
            if (weather == 2) {
                System.out.println("Thunderstorm: Lighting randomly strikes a tile, destroying anything on it.");
                if (inWildlands) {
                    rngValmin = 1;
                    rngValmax = 24;
                    genericRNG();
                    boltPosX = rngVal;
                    genericRNG();
                    boltPosY = rngVal;
                } else if (inMountains) {
                    rngValmin = 25;
                    rngValmax = 25;
                    genericRNG();
                    boltPosX = rngVal;
                    rngValmin = 1;
                    rngValmax = 24;
                    boltPosY = rngVal;
                }
                for (int count = 0; count < enemyPosX.length; count++) {
                    if (boltPosX == enemyPosX[count] && boltPosY == enemyPosY[count]) {
                        numofenemy -= 1;
                        enemyPosX[count] = -2;
                        enemyPosY[count] = -2;
                        System.out.println("An enemy was struck by lighting and was destroyed!");
                    } else if (boltPosX == shopPosX[count] && boltPosY == shopPosY[count]) {
                        numofshops -= 1;
                        shopPosX[count] = -3;
                        shopPosY[count] = -3;
                        System.out.println("A shop was struck by lighting and was destroyed!");
                    } else if (boltPosX == shelterPosX[count] && boltPosY == shelterPosY[count]) {
                        numofshelters -= 1;
                        shelterPosX[count] = -3;
                        shelterPosY[count] = -3;
                        System.out.println("A shelter was struck by lighting and was destroyed!");
                    } else if (boltPosX == playerPosX && boltPosY == playerPosY) {
                        tempVal = playerHP;
                        tempVal2 = playerHP / 4;
                        playerHP -= tempVal2;
                        System.out.println("You were struck by lighting! and lost " + (tempVal - playerHP) + " hp!");
                    }
                }

            } else if (weather == 3) {
                System.out.println("Dust storm: Prevents you from seeing combat distance.");
            } else if (weather == 4) {
                System.out.println("Temporal wind: Blows you into the future if touched.");
                int[] temporalWindPosX =  new int[3];
                int[] temporalWindPosY =  new int[3] ;
                for (int x = 0; x < 3; x++) {
                    if (inWildlands) {
                        rngValmin = 1;
                        rngValmax = 24;
                        genericRNG();
                        temporalWindPosX[x] = rngVal;
                        genericRNG();
                        temporalWindPosY[x] = rngVal;
                    } else if (inMountains) {
                        rngValmin = 25;
                        rngValmax = 25;
                        genericRNG();
                        temporalWindPosX[x] = rngVal;
                        rngValmin = 1;
                        rngValmax = 24;
                        temporalWindPosY[x] = rngVal;
                    }
                }
                for (int count = 0; count < 3; count++) {
                    if (temporalWindPosX[count] == playerPosX && temporalWindPosY[count] == playerPosY) {
                        rngValmin = 1;
                        rngValmax = 9;
                        genericRNG();
                        tempVal = day;
                        day += rngVal;
                        System.out.println("You were blown " + (day - tempVal) + " days into the future!");
                    }
                }

            } else if (weather == 5) {
                System.out.println("Incendiary rain: Deals damage over time if not in shelter.");
                for (int r = 0; r < shelterPosX.length; r++) {
                    if (shelterPosX[r] > 0) {
                        System.out.println("Shelter at (" + shelterPosX[r] + "," + shelterPosY[r] + ")!");
                    }
                }
                if (!underShelter) {
                    tempVal = playerHP/10;
                    playerHP -= tempVal + (25*level);
                    //the tempVal is to cancel out the heal you get at the end of every day.
                    System.out.println("The fire rain burns your skin, causing you extreme pain...");
                }
            } else if (weather == 6) {
                System.out.println("Snow: Enemy count doesn't increase, but shops are closed.");
                isSnowing = true;
            } else System.out.println("Clear skies!");
        }
    }

    static void basicShop() throws InterruptedException {
        hasConfirmedShop = false;
        playerLeft = false;
        System.out.println("Welcome to Wanderer's Source, the number 1 shop for all things with pointy ends or go boom.");
        while (!playerLeft) {
            exchangeRate = 0;
            improveRate = 0;
            System.out.println("Your coins: " + coin);
            System.out.println("Press 1 to upgrade your gear, press 2 to buy items, press 3 to fast travel, press 4 to get healed, or press 5 to leave.");
            playerResponse();
            if (answer == 1) {
                System.out.println("What would you like to upgrade?");
                Thread.sleep(500);
                System.out.println("1. Damage");
                System.out.println("2. Defense");
                System.out.println("3. Speed");
                playerResponse();
                while (!hasConfirmedShop) {
                    if (answer == 1) {
                        exchangeRate = 10;
                        improveRate = 1;
                        System.out.println("The rate is " + improveRate + " increase per " + exchangeRate + " coins.");
                        Thread.sleep(500);
                        System.out.println("Press 1 to confirm, or press another number to go back.");
                        playerResponse();
                        if (answer == 1) {
                            System.out.println("How much would you like to increase your damage?");
                            playerResponse();
                            tempVal2 = weapondmg;
                            shopExchange();
                            weapondmg += tempVal;
                            System.out.println("We improved your damage by " + (weapondmg - tempVal2) + ".");
                            hasConfirmedShop = true;
                        } else {
                            System.out.println("Then why did you say you wanted your gear improved?");
                            hasConfirmedShop = true;
                        }
                    } else if (answer == 2) {
                        improveRate = 2;
                        exchangeRate = 13;
                        Thread.sleep(500);
                        System.out.println("Press 1 to confirm, or press another number to go back.");
                        playerResponse();
                        if (answer == 1) {
                            System.out.println("How much would you like to increase your defense?");
                            hasConfirmed = false;
                            playerResponse();
                            tempVal = playerDefense;
                            shopExchange();
                            playerDefense += tempVal;
                            System.out.println("We improved your defense by " + (playerDefense - tempVal2) + ".");
                            hasConfirmedShop = true;
                        } else {
                            System.out.println("Then why did you say you wanted your gear improved?");
                            hasConfirmedShop = true;
                        }
                    } else if (answer == 3) {
                        exchangeRate = 9;
                        improveRate = 1;
                        System.out.println("The rate is 1 increase per 9 coins.");
                        Thread.sleep(500);
                        System.out.println("Press 1 to confirm, or press another number to go back.");
                        playerResponse();
                        if (answer == 1) {
                            System.out.println("How much would you like to increase your speed?");
                            playerResponse();
                            tempVal = playerSpeed;
                            shopExchange();
                            playerSpeed += tempVal;
                            System.out.println("We improved your speed by " + (playerSpeed - tempVal) + ".");
                            hasConfirmedShop = true;
                        } else {
                            System.out.println("Then why did you say you wanted your gear improved?");
                            hasConfirmedShop = true;
                        }
                    }
                }
            } else if (answer == 2) {
                System.out.println("so you want the good stuff huh...");
                Thread.sleep(1000);
                System.out.println("1: Grappling Hook: Extends range of your next attack at the cost of speed.");
                System.out.println("2. Regenerative Stone: Heal yourself for a small amount.");
                System.out.println("3. Soul Snatcher: Hits the opponent for a percentage of their max hp, ignoring defense. ");
                System.out.println("Any other number: Go back");
                playerResponse();
                if (answer == 1) {
                    System.out.println("This item is single use and costs 75 per hook. How many do you want?");
                    playerResponse();
                    tempVal = grapplingHook;
                    shopExchange();
                    System.out.println("You bought " + (grapplingHook - tempVal) + " grappling hooks.");
                } else if (answer == 2) {
                    System.out.println("This item is single use and costs 40 per stone. How many do you want?");
                    playerResponse();
                    tempVal = regenerativeStone;
                    shopExchange();
                    System.out.println("You bought " + (regenerativeStone - tempVal) + " stones.");
                } else if (answer >= 3) {
                    System.out.println("This item is single use and costs 120 per snatcher. How many do you want?");
                    playerResponse();
                    tempVal = soulSnatcher;
                    shopExchange();
                    System.out.println("You bought " + (soulSnatcher - tempVal) + " Soul Snatchers.");
                }
                else {
                    System.out.println("Just give me your money already!.");
                }
            } else if (answer == 3) {
                fastTravel();
            } else if (answer == 4) {
                exchangeRate = 70;
                improveRate = 150;
                System.out.println("It costs " + exchangeRate + " coins to get " + improveRate + " hp. Press 1 to confirm, or press another number to leave. ");
                playerResponse();
                if (answer == 1) {
                    if (coin >= 70) {
                        coin -= exchangeRate;
                        playerHP += improveRate;
                        if (playerHP > 250 + (50*level)) playerHP = 200 + (50*level);
                        System.out.println("Please continue throwing your health away, it brings me profit.");
                    } else System.out.println("You're too poor. You can stop bleeding on my carpet now.");
                    hasConfirmedShop = true;
                }
            } else if (answer >= 5) {
                System.out.println("Come again soon! We want your money!");
                playerLeft = true;
            }

        }
    }

    static void advancedShop () throws InterruptedException {
        hasConfirmedShop = false;
        playerLeft = false;
        System.out.println("Welcome to Wanderer's Source, the number 1 shop for all things with pointy ends or go boom.");
        while (!playerLeft) {
            exchangeRate = 0;
            improveRate = 0;
            System.out.println("Your coins: " + coin);
            System.out.println("Press 1 to upgrade your gear, press 2 to buy items, press 3 to fast travel, press 4 to get healed, or press 5 to leave.");
            playerResponse();
            if (answer == 1) {
                System.out.println("What would you like to upgrade?");
                Thread.sleep(500);
                System.out.println("1. Damage");
                System.out.println("2. Defense");
                System.out.println("3. Speed");
                playerResponse();
                while (!hasConfirmedShop) {
                    if (answer == 1) {
                        exchangeRate = 10;
                        improveRate = 1;
                        System.out.println("The rate is " + improveRate + " increase per " + exchangeRate + " coins.");
                        Thread.sleep(500);
                        System.out.println("Press 1 to confirm, or press another number to go back.");
                        playerResponse();
                        if (answer == 1) {
                            System.out.println("How much would you like to increase your damage?");
                            playerResponse();
                            tempVal2 = weapondmg;
                            shopExchange();
                            weapondmg += tempVal;
                            System.out.println("We improved your damage by " + (weapondmg - tempVal2) + ".");
                            hasConfirmedShop = true;
                        } else {
                            System.out.println("Alright, maybe there's something else you want.");
                            hasConfirmedShop = true;
                        }
                    } else if (answer == 2) {
                        improveRate = 2;
                        exchangeRate = 13;
                        Thread.sleep(500);
                        System.out.println("Press 1 to confirm, or press another number to go back.");
                        playerResponse();
                        if (answer == 1) {
                            System.out.println("How much would you like to increase your defense?");
                            hasConfirmed = false;
                            playerResponse();
                            tempVal = playerDefense;
                            shopExchange();
                            playerDefense += tempVal;
                            System.out.println("We improved your defense by " + (playerDefense - tempVal2) + ".");
                            hasConfirmedShop = true;
                        } else {
                            System.out.println("Alright, maybe there's something else you want.");
                            hasConfirmedShop = true;
                        }
                    } else if (answer == 3) {
                        exchangeRate = 9;
                        improveRate = 1;
                        System.out.println("The rate is 1 increase per 9 coins.");
                        Thread.sleep(500);
                        System.out.println("Press 1 to confirm, or press another number to go back.");
                        playerResponse();
                        if (answer == 1) {
                            System.out.println("How much would you like to increase your speed?");
                            playerResponse();
                            tempVal = playerSpeed;
                            shopExchange();
                            playerSpeed += tempVal;
                            System.out.println("We improved your speed by " + (playerSpeed - tempVal) + ".");
                            hasConfirmedShop = true;
                        } else {
                            System.out.println("Alright, maybe there's something else you want.");
                            hasConfirmedShop = true;
                        }
                    }
                }
            } else if (answer == 2) {
                System.out.println("Guess I gotta bust out the extra cabinet...");
                Thread.sleep(1000);
                System.out.println("1: Grappling Hook: Extends range of your next attack at the cost of speed.");
                System.out.println("2. Regenerative Stone: Heal yourself for a small amount.");
                System.out.println("3. Soul Snatcher: Hits the opponent for a percentage of their max hp, ignoring defense. ");
                System.out.println("Any other number: Go back");
                playerResponse();
                if (answer == 1) {
                    System.out.println("This item is single use and costs 75 per hook. How many do you want?");
                    playerResponse();
                    tempVal = grapplingHook;
                    shopExchange();
                    System.out.println("You bought " + (grapplingHook - tempVal) + " grappling hooks.");
                } else if (answer == 2) {
                    System.out.println("This item is single use and costs 40 per stone. How many do you want?");
                    playerResponse();
                    tempVal = regenerativeStone;
                    shopExchange();
                    System.out.println("You bought " + (regenerativeStone - tempVal) + " stones.");
                } else if (answer >= 3) {
                    System.out.println("This item is single use and costs 120 per snatcher. How many do you want?");
                    playerResponse();
                    tempVal = soulSnatcher;
                    shopExchange();
                    System.out.println("You bought " + (soulSnatcher - tempVal) + " Soul Snatchers.");
                }
                else {
                    System.out.println("I can't help but feel like you're just messing with me.");
                }
            } else if (answer == 3) {
                fastTravel();
            } else if (answer == 4) {
                exchangeRate = 70;
                improveRate = 150;
                System.out.println("It costs " + exchangeRate + " coins to get " + improveRate + " hp. Press 1 to confirm, or press another number to leave. ");
                playerResponse();
                if (answer == 1)  shopHeal();
                else System.out.println("I guess you don't mind bleeding.");
                    hasConfirmedShop = true;
                }
             else if (answer >= 5) {
                System.out.println("Come again soon! We want your money!");
                playerLeft = true;
            }

        }
    }
    static void fastTravel() throws InterruptedException {
        System.out.println("200 coins to fast travel. Press 1 if you don't mind us taking your money.");
        playerResponse();
        if (answer == 1) {
            int answer2 = 0;
            if (coin > 200)
                System.out.println("200 coins to fast travel. Type your x coordinate.");
            playerResponse();
            if (answer > 50) {
                System.out.println("You can't go there.");
            } else {
                System.out.println("Type your y coordinate.");
                hasConfirmed = false;
                while (!hasConfirmed) {
                    try {
                        answer2 = KB.nextInt();
                        if (answer2 >= 0) {
                            hasConfirmed = true;
                        } else {
                            System.out.println("Invalid response.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid response. Please enter a valid integer.");
                        KB.next();
                    }
                }
            }
            if (answer2 > 25) {
                System.out.println("You can't go there.");
            } else {
                System.out.println("See you later. Sorcerer powers GO!");
                tempVal = playerPosX;
                tempVal2 = playerPosY;
                playerPosX = answer;
                playerPosY = answer2;
                coin -= 200;
                hasConfirmedShop = true;
                playerLeft = true;
            }
        } else if (coin < 200) {
            System.out.println("Yeah... you're way too poor for that.");
            hasConfirmedShop = true;
        }
        else
        {
            System.out.println("Make up your mind...");
        }
    }

    static int playerResponse() throws InterruptedException {
        answer = 0;
        hasConfirmed = false;
        while (!hasConfirmed) {
            try {
                answer = KB.nextInt();
                if (answer >= 0 ) {
                    hasConfirmed = true;
                } else {
                    System.out.println("Invalid response.");
                }
            } catch (Exception e) {
                 System.out.println("Invalid response. Please enter a valid integer.");
                KB.next();
            }
        }
        return answer;
    }
    static void shopExchange() throws InterruptedException {
        tempVal = 0;
        shopCNT = 0;
        while (shopCNT < answer) {
            if (coin > exchangeRate) {
                tempVal += improveRate;
                coin -= exchangeRate;
                shopCNT += 1;
            } else {
                System.out.println("Sorry, but you don't have enough coins. We're not running a charity here...");
                shopCNT = answer;
            }
        }
    }

    static void shopHeal() throws InterruptedException {
        System.out.println("It costs " + exchangeRate + " coins to get " + improveRate + " hp. Press 1 to confirm, or press another number to leave. ");
        playerResponse();
        if (answer == 1) {
            if (coin >= 70) {
                coin -= 70;
                playerHP += 150;
                if (playerHP > 200 + (50*level)) playerHP = 200 + (50*level);
                System.out.println("Please continue throwing your health away, it brings me profit.");
            } else System.out.println("You're too poor. You can stop bleeding on my carpet now.");
            hasConfirmedShop = true;
        }
    }
    static void endOfDay() throws InterruptedException {
        playerexp += 15;
        rngValmin = 25;
        rngValmax = 25;
        genericRNG();
        coin += rngVal;
        requiredXP = 60*level;
        while (playerexp > requiredXP) {
            playerexp -= requiredXP;
            level += 1;
            skillPoints += 1;
            System.out.println("You are now level " + level + " ! Your strength grows!");
            tempVal2 = playerPower;
            tempVal = weapondmg;
            rngValmin = 45;
            rngValmax = 45;
            genericRNG();
            weapondmg = weapondmg + rngVal;
            System.out.println("Your damage has increased by " + Math.round((weapondmg - tempVal)));
            tempVal = playerDefense;
            genericRNG();
            playerDefense = playerDefense + rngVal;
            System.out.println("Your defense has increased by " + Math.round((playerDefense - tempVal)));
            tempVal = playerSpeed;
            genericRNG();
            playerSpeed = playerSpeed + rngVal;
            System.out.println("Your speed has increased by " + Math.round((playerSpeed - tempVal)));
            playerPower = (weapondmg + playerSpeed + playerDefense + playerHP)/4;
            System.out.println("Your overall power has increased by " + Math.round((playerPower - tempVal2)));
            Thread.sleep(5000);
        }
        if (playerPosX > 25 && !inMountains)
        {
            System.out.println("You are going to enter the mountains. The enemies are stronger, but the loot is better. Press 1 to confirm, or press 2 to return.");
            playerResponse();
            if (answer == 1)
            {
                inWildlands = false;
                inMountains = true;
                System.out.println("To the mountains!");
            }
            else
            {
                playerPosX = (int) tempVal;
                playerPosY = (int) tempVal2;
                System.out.println("You have returned to your previous location.");
            }
        }
        if (playerPosX < 25 && inMountains)
        {
            System.out.println("You are going to enter the wildlands. Press 1 to confirm, or press 2 to return.");
            playerResponse();
            if (answer == 1)
            {
                inWildlands = true;
                inMountains = false;
                System.out.println("To the wildlands!");
            }
            else {
                while (playerPosX > 25) {
                    playerPosX -= 1;
                }
            }
        }
        tempVal = playerHP/10;
        playerHP += tempVal;
        if (playerHP > 200 + (50*level))
        {
            playerHP = 200 + (50*level);
        }
        day += 1;
    }
    static void upgradeGear() throws InterruptedException {
        while ((day * level)/2 - decayImp <= 0) {
            decayImp -= 1;
            //Can't let decayImp make the increase 0 or less
        }
        rngValmin = 1;
        rngValmax = 6;
        genericRNG();
        tempVal = rngVal;
        if (tempVal == 1 || tempVal == 2) {
            tempVal = weapondmg;
            rngVal = 0;
            if (inWildlands) {
                rngValmin = (day * level)/2 - decayImp;
                rngValmax = 20 - decayImp;
            }
            else if (inMountains) {
                rngValmin = (int) (1.25*(day*level))/2 + 25 - decayImp;
                rngValmax = 35 - decayImp;
            }
            genericRNG();
            weapondmg += rngVal;

            System.out.println("Your damage has increased by " + Math.round((weapondmg - tempVal)));
        }
        if (tempVal == 3) {
            tempVal = playerSpeed;
            rngVal = 0;
            if (inWildlands) {
                rngValmin = (day * level)/2 - decayImp;
                rngValmax = 20 - decayImp;
            }
            else if (inMountains) {
                rngValmin = (int) (1.25*(day*level))/2 + 25 - decayImp;
                rngValmax = 35 - decayImp;
            }
            genericRNG();
            playerSpeed = playerSpeed + rngVal;
            System.out.println("Your speed has increased by " +  Math.round((playerSpeed - tempVal)));
        }
        if (tempVal == 4) {
            tempVal = playerDefense;
            rngVal = 0;
            if (inWildlands) {
                rngValmin = (day * level)/2 + 10 - decayImp;
                rngValmax = 20 - decayImp;
            }
            else if (inMountains) {
                rngValmin = (int) (1.25*(day*level))/2 + 25 - decayImp;
                rngValmax = 35 - decayImp;
            }
            genericRNG();
            playerDefense = playerDefense + rngVal;
            decayImp = decayImp + 1;

            System.out.println("Your defense has improved by " + Math.round((playerDefense - tempVal)));
        }
        if (tempVal == 5) {
            tempVal = coin;
            rngValmin = 60;
            rngValmax = 40;
            genericRNG();
            coin += rngVal;
            System.out.println("You didn't upgrade your armor, but you found " + Math.round((coin - tempVal)) + " coins lying around.");
        }
        if (tempVal == 6) {
            System.out.println("You couldn't upgrade your armor.");
        }
        decayImp += 5;
        spentDay = true;
    }

    static void exploreZenotopia() throws InterruptedException {
        String answerType;
        boolean hasConfirmedTraversal = false;
        while (!hasConfirmedTraversal) {
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
                        System.out.println("You almost fall off a cliff and die. That would've been funny.");
                        playerPosX = (int) tempVal;
                        hasConfirmed = true;
                    } else {
                        hasConfirmed = true;
                        hasConfirmedTraversal = true;
                    }
                }
               else if (answerType.contains("D") || answerType.contains("d")) {
                    playerPosX = playerPosX + 1;
                    hasConfirmed = true;
                    hasConfirmedTraversal = true;
                }
               else if (answerType.equals("W") || answerType.equals("w")) {
                    playerPosY += 1;
                    if (playerPosY > 25) {
                        System.out.println("You run into a wall and fall over. The king laughs at you.(can't exceed 25 units up)");
                        playerPosY = (int) tempVal2;
                        hasConfirmed = true;
                    } else {
                        hasConfirmed = true;
                        hasConfirmedTraversal = true;
                    }
                }
                else if (answerType.equals("S") || answerType.equals("s")) {
                    playerPosY -= 1;
                    if (playerPosY < 0) {
                        System.out.println("You run into a wall and fall over. The king laughs at you.(can't have negative y coordinate)");
                        playerPosY = (int) tempVal2;
                        hasConfirmed = true;
                    } else {
                        hasConfirmedTraversal = true;
                        hasConfirmed = true;
                    }
                }
                else {
                    System.out.println("You did not input a valid response.");
                    KB.next();
                }
            }
        }
        spentDay = true;
    }

    static void ending() throws InterruptedException {
        if (playerHP <= 0) {
            System.out.println("It was a worthy attempt.");
            Thread.sleep(1000);
            System.out.println("Your eyes struggle to make out the form of the one who has bested you.");
            Thread.sleep(1000);
            System.out.println("They raise their weapon.");
            Thread.sleep(1000);
            System.out.println("GAME OVER");
        } else if (enemyHP <= 0) {
            System.out.println("It was a worthy attempt.");
            Thread.sleep(1000);
            System.out.println("Their eyes struggle to make out the form of the one who has bested them.");
            Thread.sleep(1000);
            System.out.println("You raise their weapon.");
            Thread.sleep(1000);
            System.out.println("The crowd erupts in your victory. For you were a nobody,and now you have proven yourself.");
            Thread.sleep(1000);
            System.out.println("The king's guards bring you into the palace.");
            Thread.sleep(1000);
            System.out.println("For 1 night, you are a royal. You are treated to the finest bath, dinner, clothing and entertainment.");
            Thread.sleep(1000);
            System.out.println("Finally, you stand before the throne of the King, Zenopid.");
            Thread.sleep(1000);
            System.out.println(" 'As the rules dictate, I will grant you 1 wish of your choosing. Except the throne, any thing you desire shall be yours.");
            Thread.sleep(1000);
            System.out.println("1. All the money in the kingdom");
            System.out.println("2. Your daughter");
            System.out.println("3. All the land I can see");
            playerResponse();
            if (answer == 2) {
                System.out.println("You are banished from the kingdom.");
            } else {
                System.out.println("You are now the wealthiest man in Zenotopia.");
            }
        }
        System.out.println("Congratulations!");
        Thread.sleep(1000);
        playerPower = (weapondmg + playerDefense + playerSpeed + (level*250));
        System.out.println("You ended the game with a power level of " + playerPower + ".");
        Thread.sleep(1000);
        System.out.println("You had " + coin + " coins.");
        Thread.sleep(1000);
        System.out.println("You defeated + " +numOfEnemiesDefeated + " enemies.");
        Thread.sleep(1000);
        System.out.println("Made by Temi Awosiyan.");
        System.out.println("Sources:");
        Thread.sleep(1000);
        System.out.println("https://pixabay.com/sound-effects/search/medieval/");
        System.out.println("Block sfx: Sound Effect by <a href=\"https://pixabay.com/users/sectionsound-34536612/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=143940\">SectionSound</a> from <a href=\"https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=143940\">Pixabay</a>");
        System.out.println("Ki Charge: https://www.youtube.com/watch?v=VoYzS_E7Ijg&ab_channel=DavidDumaisAudio");
    }
    protected  static void playMusic(int trackNum) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Music.setFile(trackNum);
        Music.startClip();
        Music.loopClip();
        musicPlaying = true;
    }
    protected static void stopMusic() {
        Music.stopClip();
        musicPlaying = false;
    }
    protected static void soundEffect(int trackNum) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Music.setFile(trackNum);
        Music.startClip();
    }
    protected static void resetMusic() {
        Music.clip.setMicrosecondPosition(0);
    }


}

    //Combat will come soon...
//Combat is almost here...
//Combat is here(?)...
//COMBAT IS HERE (for axe.... and only against weakswordsman)
//COMBAT IS HERE (for wildlands and base weapon set)
//COMBAT IS HERE (Mountains included!)
//COMBAT IS HERE! but its broken rn cuz of stamina
//combat 2.0 is here!
