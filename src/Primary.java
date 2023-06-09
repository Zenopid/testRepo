import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.sound.sampled.UnsupportedAudioFileException;


import java.util.HashMap;
public class Primary {

    static Scanner KB = new Scanner(System.in);

    protected static int decayImp = 2;
    //imp stands for improve, this is to decrease gear improvement if used too much
    protected static int level = 1;
    protected static int rngVal = 0;

    //Time and weather
    protected static int day = 1;
    protected static String weather = "";

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
    protected static int smokeBombs = 0;
    protected static int cycloneStraws = 0;
    protected static int magmaWhistle = 0;
    protected static int poisonDart = 0;
    protected static int insanityCurses = 0;
    protected static int enlargementFlask = 0;

    protected static double playerHP = 100 + (35 * level);
    protected static int coin = 0;
    //Money. Used to buy utility items as well as fast travel.

    //Combat
    static HashMap<String, String> moveSet = new HashMap<>();

    static HashMap<Integer, String> armorEnchantSet = new HashMap<>();

    static HashMap<Integer, String> weaponEnchantSet = new HashMap<>();

    static HashMap<String,Integer> enchantTier = new HashMap<>();

    protected static int numofmoves = 5;

    protected static String enemyName = "";

    protected static String enemyType = "";
    protected static boolean playerIsBlocking = false;

    protected static boolean enemyIsBlocking = false;
    protected static int ccAmount = 0;
    protected static boolean playercounterHit = false;
    protected static boolean enemycounterHit = false;
    protected static int playerStamina = 100;
    protected static int enemyStamina = 100;
    protected static int staminaDamage = 0;
    protected static int enemyStaminaDamage = 0;
    protected static int superMeter = 100;
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
    protected static String bossStance = "";
    protected static int bossHasSwapped = 0;

    //World interaction
    protected static boolean playerLeft = false;
    protected static boolean hasConfirmedShop = false;
    protected static boolean onShop;
    protected static boolean onDungeon = false;
    protected static int numofshops = 1;
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

    protected static String playerLocation = "Wildlands";

    protected static int difficultyTier = 0;
    protected static boolean inDungeon = false;

    //Assorted Variables (Not specfic to any one aspect of the game)
    protected static double tempVal = 0;
    protected static double tempVal2 = 0;

    protected static int tempInt = 0;

    protected static String tempString = "";

    /*
 generic doubles for all random number generator needs
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

    //Music
    protected static boolean musicPlaying = false;
    protected static int musicFile = -1;

    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
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
        enemyPosX[0] = 1;
        enemyPosY[0] = 1;
        shopPosX[0] = 2;
        shopPosY[0] = 2;
        dungeonPosX[0] = 3;
        dungeonPosY[0] = 3;
        chestPosX[0] = 1;
        chestPosY[0] = 0;
        System.out.println("The king of Zenotopia is holding a contest. Survive 100 days, and defeat the last person to win any 1 gift from the king.");
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
        else getAxe();
        //Day loop
        while (day <= 100) {
            System.out.println("Its day " + day);
            enemySpawner();
            chestSpawner();
            shopSpawner();
            dungeonSpawner();
            shelterSpawner();
            randomWeather();
            weatherEffects();
            if (playerHP <= 0) playerDied();
            spentDay = false;
            while (!spentDay) {
                if (!musicPlaying) playMusic(0);
                dayLoop();
                playerResponse();
                switch (answer) {
                    case 2 -> {
                        hasConfirmed = false;
                        tempVal = playerPosX;
                        tempVal2 = playerPosY;
                        exploreZenotopia();
                    }
                    case 3 -> skillTree();
                    case 4 -> playerLoadout();
                    case 5 -> {
                        stopMusic();
                        if (onShop) {
                            playMusic(12);
                            if (playerLocation.contains("Wildlands") && onShop) basicShop();
                            else if (playerLocation.contains("Mountains") && onShop) advancedShop();
                            else if (playerLocation.contains("Stronghold") && onShop)  enhancedShop();
                        }
                        else if (onDungeon) {
                            inDungeon = true;
                            Dungeon.undergroundExplore();
                        }
                    }
                    case 6 -> cheatCodes();
                    default -> upgradeGear();
                }
            }
            playerPosChecker();
            if (playerHP <= 0) playerDied();
            endOfDay();
        }
        stopMusic();
        isFighting = true;
        playerLocation = "";
        Combat.fight();
        ending();
    }

    // Mayowa.... why....
    //This was supposed to be less complicated than the 1st version...
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
        System.out.println("Vacuum: Brings your enemy closer.");
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

    static void selectWeapon() {
        System.out.println("Select your starting weapon: ");
        System.out.println("1: Sword: Balanced between offense and defense.");
        System.out.println("2: Shield: High defense and speed at the cost of offense.");
        System.out.println("3: Axe: High offense at the cost of defense and speed.");
    }
    static void genericRNG(int rngValmin, int rngValmax) {
        Random rand = new Random();
        rngVal = rand.nextInt(rngValmax) + rngValmin;
    }
    static void enemySpawner() {
        if (!isSnowing && day % 2 == 0) {
            genericRNG(2, 2);
            numofenemy += rngVal;
        }
        for (int z = 0; z < numofenemy; z++) {
            //This checks if an enemy is on a shop tile.
            while (enemyPosX[z] <= 0 && enemyPosY[z] <= 0 && enemyPosX[z] != shopPosX[z] && enemyPosY[z] != shopPosY[z])
            //Hopefully stops enemy locations from changing...
            {
                if (playerLocation.contains("Wildlands")) {
                    genericRNG(1, 24);
                    enemyPosX[z] = rngVal;
                    genericRNG(1, 24);
                    enemyPosY[z] = rngVal;
                } else if (playerLocation.contains("Mountains")) {
                    genericRNG(25, 25);
                    enemyPosX[z] = rngVal;
                    genericRNG(1, 25);
                    enemyPosY[z] = rngVal;
                }
                else if (playerLocation.contains("Stronghold")) {
                    genericRNG(25,25);
                    enemyPosX[z] = rngVal;
                    genericRNG(25,25);
                    enemyPosY[z] = rngVal;
                }
            }
            //Fills up the vacant spots where shelters should be
        }
    }

    static void dungeonSpawner() {
        if (day % 5 == 0)
        {
            genericRNG(10, 4);
            numofdungeons += rngVal;
        }
        for (int z = 0; z < numofdungeons; z++) {
            //This checks if an shelter is on a shop tile.
            while (dungeonPosX[z] <= 0 && dungeonPosY[z] <= 0 && dungeonPosX[z] != dungeonPosX[z] && dungeonPosY[z] != dungeonPosY[z])
            //Hopefully stops shelter locations from changing...
            {
                if (playerLocation.contains("Wildlands")) {
                    genericRNG(1, 24);
                    dungeonPosX[z] = rngVal;
                    genericRNG(1, 24);
                    dungeonPosY[z] = rngVal;
                } else if (playerLocation.contains("Mountains")) {
                    genericRNG(25, 25);
                    dungeonPosX[z] = rngVal;
                    genericRNG(1, 24);
                    dungeonPosY[z] = rngVal;
                }
                else if (playerLocation.contains("Stronghold")) {
                    genericRNG(25,25);
                    enemyPosX[z] = rngVal;
                    genericRNG(25,25);
                    enemyPosY[z] = rngVal;
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

    static void shelterSpawner() {
        genericRNG(0, 3);
        numofshelters += rngVal;
        for (int z = 0; z < numofshelters; z++) {
            //This checks if a shelter is on a shop tile.
            while (shelterPosX[z] <= 0 && shelterPosY[z] <= 0 && shelterPosX[z] != shopPosX[z] && shelterPosY[z] != shopPosY[z])
            //Hopefully stops shelter locations from changing...
            {
                if (playerLocation.contains("Wildlands")) {
                    genericRNG(1, 24);
                    shelterPosX[z] = rngVal;
                    genericRNG(1, 24);
                    shelterPosY[z] = rngVal;
                } else if (playerLocation.contains("Mountains")) {
                    genericRNG(25, 25);
                    shelterPosX[z] = rngVal;
                    genericRNG(1, 24);
                    shelterPosY[z] = rngVal;
                }
                else if (playerLocation.contains("Stronghold")) {
                    genericRNG(25,25);
                    enemyPosX[z] = rngVal;
                    genericRNG(25,25);
                    enemyPosY[z] = rngVal;
                }
            }
            //Fills up the vacant spots where shelters should be
        }
    }

    static void playerPosChecker() throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
        //Things to check for when the player is moving around
        for (int cnt3 = 0; cnt3 < shopPosX.length; cnt3++) {
            if (playerPosX == shopPosX[cnt3] && playerPosY == shopPosY[cnt3] && !isSnowing && !inDungeon) {
                onShop = true;
                underShelter = true;
                break;
            } else if (isSnowing) {
                onShop = false;
                underShelter = true;
                tempVal = 2;
            } else onShop = false;
        }
        for (int m = 0; m < enemyPosX.length; m++) {
            if (playerPosX == enemyPosX[m] && playerPosY == enemyPosY[m] && !inDungeon) {
                isFighting = true;
                stopMusic();
                Combat.fight();
            }
        }
        for (int cnt4 = 0; cnt4 < shelterPosX.length; cnt4++) {
            if (playerPosX == shelterPosX[cnt4] && playerPosY == shelterPosY[cnt4]) {
                underShelter = true;
                break;
            } else underShelter = false;
        }
        for (int m = 0; m < dungeonPosX.length; m++) {
            if (playerPosX == dungeonPosX[m] && playerPosY == dungeonPosY[m]) {
                onDungeon = true;
                break;
            } else onDungeon = false;
        }
        for (int c = 0; c < numofchest; c++) {
            if (playerPosX == chestPosX[c] && playerPosY == chestPosY[c]) {
                System.out.println("You've found a chest!");
                genericRNG(1, 3);
                tempVal = rngVal;
                genericRNG(50, (day * level) / 4 + 25);
                if (tempVal == 1) {
                    tempVal = weapondmg;
                    weapondmg += rngVal;
                    System.out.println("Your damage increased by " + (weapondmg - tempVal) + " !");
                } else if (tempVal == 2) {
                    tempVal = playerSpeed;
                    playerSpeed += rngVal;
                    System.out.println("Your speed increased by " + (playerSpeed - tempVal) + " !");
                } else if (tempVal == 3) {
                    tempVal = playerDefense;
                    playerDefense += rngVal;
                    System.out.println("Your defense increased by " + (playerDefense - tempVal) + " !");
                }
                chestPosX[c] = -3;
                chestPosY[c] = -3;
                numofchest -= 1;
            }
        }
    }

    static void getSwordRNG() {
        genericRNG(95, 35);
        weapondmg += rngVal;
        genericRNG(95, 35);
        playerDefense += rngVal;
        genericRNG(95, 35);
        playerSpeed += rngVal;
    }

    static void getSword() {
        System.out.println("Sword? You're really basic.");
        weaponName = "Sword";
        getSwordRNG();
        moveSet.put("Move 1", "Forward Swipe");
        moveSet.put("Move 2", "Downward Slice");
        moveSet.put("Move 3", "Pommel");
        moveSet.put("Move 4", "Shove");
        moveSet.put("Move 5", "Thrust");
        numofmoves = 5;
    }

    static void getShieldRNG() {
        genericRNG(65, 25);
        weapondmg += rngVal;
        genericRNG(150, 25);
        playerDefense += rngVal;
        genericRNG(120, 25);
        playerSpeed += rngVal;
    }

    static void getShield() {
        System.out.println("Shield? You must not be very confident.");
        weaponName = "Shield";
        getShieldRNG();
        moveSet.put("Move 1", "Bash");
        moveSet.put("Move 2", "Uppercut");
        moveSet.put("Move 3", "Slam");
        moveSet.put("Move 4", "Shove");
        moveSet.put("Move 5", "Shield Punch");
    }

    static void getAxeRNG() {
        genericRNG(125, 50);
        weapondmg += rngVal;
        genericRNG(65, 30);
        playerSpeed += rngVal;
        genericRNG(35, 35);
        playerDefense += rngVal;
    }

    static void getAxe() {
        System.out.println("Axe? Now we're talking...");
        weaponName = "Axe";
        getAxeRNG();
        moveSet.put("Move 1", "Forward Slash");
        moveSet.put("Move 2", "Shatter");
        moveSet.put("Move 3", "Butcher");
        moveSet.put("Move 4", "Shove");
        moveSet.put("Move 5", "Skewer");
    }

    protected static void skillTree() {
        answerType = "";
        hasConfirmed = false;
        while (!playerLeft) {
            System.out.println("Skill points: " + skillPoints);
            System.out.println("Press 0 to exit, or press another number to continue.");
            playerResponse();
            if (answer == 0) break;
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
                switch (answer) {
                    case 0 -> {
                        if (level > 1) confirmMove(1, "Phantom SLash");
                    }
                    case 1 -> {
                        if (level >= 3) confirmMove(1, "Ki Charge");
                    }
                    case 2 -> {
                        if (level >= 3) confirmMove(1, "Whirlwind");
                    }
                    case 3 -> {
                        if (level >= 3) confirmMove(1, "Rising Upper");
                    }
                    case 4 -> {
                        if (level >= 5) confirmMove(3, "Vanishing Strike");
                    }
                    case 5 -> {
                        if (level >= 8) confirmMove(2, "Ki Charge 2");
                    }
                    case 6 -> {
                        if (level >= 8) confirmMove(2, "Whirlwind 2");
                    }
                    case 7 -> {
                        if (level >= 8) confirmMove(2, "Rising Upper 2");

                    }
                    case 8 -> {
                        if (level >= 10) confirmMove(3, "Vanishing Strike 2");
                    }
                    case 9 -> {
                        if (level >= 12) confirmMove(3, "Sword Mastery");
                    }
                    case 10 -> {
                        if (level >= 15) confirmMove(4, "Phantom Slash 2");
                    }
                    default -> System.out.println("Invalid response.");
                }
            } else if (weaponName.contains("Shield")) {
                if (level > 1)
                    System.out.println("0: Ultimate: Earthquake: Slams the shield into the ground, stunning the enemy.");
                if (level >= 3) {
                    System.out.println("1: Shield Sweep: Spin around, striking your opponents ankles.");
                    System.out.println("2: Swap Hands: Off hand: Deals bonus damage. Dominant hand: Increased speed.");
                    System.out.println("3: Deflect: Parries the opponent's strike, stunning them.");
                }
                if (level >= 5) System.out.println("4: Shield Toss: Throw your shield like a projectile.");
                if (level >= 8) System.out.println("7: Deflect 2: Deals 25% of the opponent's damage as well.");
                if (level >= 10) {
                    System.out.println("8: Shield Toss 2: Get the shield back on successful hit, and do bonus damage with your next strike.");
                    System.out.println("8: Shield Toss 2: Get the shield back on successful hit, and do bonus damage with your next strike.");
                }
                if (level >= 12) System.out.println("9: Shield Mastery: Take reduced damage on moves that involve your shield.");
                if (level >= 15) System.out.println("10: Ultimate: Earthquake 2: The next attack deals bonus damage.");
                playerResponse();
                switch (answer) {
                    case 0:
                        if (level > 1) confirmMove(1, "Earthquake");
                        break;
                    case 1:
                        if (level >= 3) confirmMove(1, "Shield Sweep");
                        break;
                    case 2:
                        if (level >= 3) confirmMove(2, "Swap Hands");
                        break;
                    case 3:
                        if (level >= 3) confirmMove(1, "deflect");
                        break;
                    case 4:
                        if (level >= 5) confirmMove(2, "Shield Toss");
                        break;
                    case 5:
                        if (level >= 8) confirmMove(3, "Shield Toss 2");
                        break;
                    case 6:
                        if (level >= 8) confirmMove(2, "Swap Hands 2");
                        break;
                    case 7:
                        if (level >= 8) confirmMove(2, "Deflect 2");
                        break;
                    case 8:
                        if (level >= 10) confirmMove(2, "Shield Toss 2");
                        break;
                    case 9:
                        if (level >= 12) confirmMove(3, "Shield Mastery");
                        break;
                    case 10:
                        if (level >= 15) confirmMove(4, "Earthquake 2");
                        break;
                }
            } else if (weaponName.contains("Axe")) {
                if (level > 1)
                    System.out.println("0: Ultimate: Eradicate: Gives you better ignite for 5 turns, giving bonus burn,armor, and stun on moves with cc.");
                if (level >= 3) System.out.println("1: Ignite: The next strike does burn damage.");
                if (level >= 5) {
                    System.out.println("2: Overhead Cleave: Swings downward at your opponent, slowing the opponent on hit. ");
                    System.out.println("3: Cripple: Bashes your opponent's head with the blunt side of the axe, lowering the opponent's damage.");
                    System.out.println("4: Pyro-slash: A slow flame wave that ignites the opponent on hit.");
                }
                if (level >= 8) System.out.println("5: Ignite 2: Burn damage now heals you.");
                if (level >= 10) {
                    System.out.println("6: Overhead Cleave 2: Stuns on ignited opponents.");
                    System.out.println("7: Cripple 2: Prevents the opponent from regaining stamina.");
                    System.out.println("8: Pyro-slash 2: Deals impact damage.");
                }
                if (level >= 12)
                    System.out.println("9: Axe Mastery: All moves ignite on counter hit. If already ignited, stun.");
                if (level >= 15)
                    System.out.println("10: Ultimate: Eradicate 2: Ignite stacks, increasing the damage for every successful hit.");
                playerResponse();
                switch (answer) {
                    case 0:
                        if (level > 1) confirmMove(1, "Eradicate");
                        break;
                    case 1:
                        if (level >= 3) confirmMove(1, "Ignite");
                        break;
                    case 2:
                        if (level >= 3) {
                            answerType = "Overhead Cleave";
                            confirmMove(1, "Overhead Cleave");
                        }
                        break;
                    case 3:
                        if (level >= 3) confirmMove(1, "Cripple");
                        break;
                    case 4:
                        if (level >= 5) confirmMove(1, "Pyroslash");
                        break;
                    case 5:
                        if (level >= 8) confirmMove(2, "Ignite 2");
                        break;
                    case 6:
                        if (level >= 8) confirmMove(2, "Overhead Cleave 2");
                        break;
                    case 7:
                        if (level >= 8) confirmMove(2, "Cripple 2");
                        break;
                    case 8:
                        if (level >= 10) confirmMove(2, "Pyroslash 2");
                        break;
                    case 9:
                        if (level >= 12) confirmMove(3, "Axe Mastery");
                        break;
                    case 10:
                        if (level >= 15)  confirmMove(4, "Eradicate 2");
                        break;
                }
            }
        }
    }

    static void confirmMove(int cost, String requestedMove) {
        System.out.println("Cost: " + cost);
        System.out.println("Press 1 to confirm you want " + requestedMove);
        playerResponse();
        if (answer == 1 && skillPoints >= cost) {
                numofmoves += 1;
                moveSet.put("Move " + numofmoves, requestedMove);
                skillPoints -= cost;
                System.out.println("You have unlocked " + requestedMove);
        }
      else if(skillPoints<cost && answer == 1)System.out.println("Not enough points.");
        else System.out.println("You did not confirm.");
}

    static void playerLoadout() {
        boolean changingLoadout = true;
        System.out.println("Press 0 to exit, or press another number to continue.");
        playerResponse();
        if (answer == 0)  changingLoadout = false;
        while (changingLoadout) {
                System.out.println("Equipped: ");
                System.out.println("---------------------------------------------------------------");
            for (int count = 1; count < numofmoves + 1; count++) {
                System.out.println("Move " + count + ": " + moveSet.get("Move " + count) + ".");
                if (count == 5) System.out.println("---------------------------------------------------------------");
                    //When the 5th move is displayed, this line will also appear, showing that it's the last equipped move.
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
                System.out.println("Equipped: ");
                System.out.println("---------------------------------------------------------------");
            for (int count = 1; count < numofmoves + 1; count++) {
                System.out.println("Move " + count + ": " + moveSet.get("Move " + count) + ".");
                if (count == 5) System.out.println("---------------------------------------------------------------");
            }
            System.out.println("Press 0 to leave, or press another number to continue.");
            playerResponse();
            if (answer == 0) changingLoadout = false;
        }
    }

    static void chestSpawner() {
        genericRNG(1, 4);
        numofchest += rngVal;
        for (int b = 0; b < numofchest; b++) {
            // the if statement checks if the chest is on a enemy tile or a shop tile
            while (chestPosX[b] <= 0 && chestPosY[b] <= 0 && chestPosX[b] == shopPosX[b] && chestPosY[b] == shopPosY[b] && chestPosX[b] == enemyPosX[b] && chestPosY[b] == enemyPosY[b]) {
                if (playerLocation.contains("Wildlands")) {
                    genericRNG(1, 24);
                    chestPosX[b] = rngVal;
                    genericRNG(1, 24);
                    chestPosY[b] = rngVal;
                } else if (playerLocation.contains("Mountains")) {
                    genericRNG(25, 25);
                    chestPosX[b] = rngVal;
                    genericRNG(1, 24);
                    chestPosY[b] = rngVal;
                }
                else if (playerLocation.contains("Stronghold")) {
                    genericRNG(25,25);
                    enemyPosX[b] = rngVal;
                    genericRNG(25,25);
                    enemyPosY[b] = rngVal;
                }
            }
        }
        /*
        for (int h = 0; h < chestPosX.length; h++) {
            if (chestPosX[h] > 0 && chestPosY[h] > 0)
                System.out.println("Chest at (" + chestPosX[h] + "," + chestPosY[h] + ").");
        }
         */
    }

    static void shopSpawner() {
        if (day % 6 == 0) {
            genericRNG(1,2);
            numofshops += rngVal;
        }
        for (int b = 0; b < numofshops; b++) {
            while (shopPosX[b] <= 0 && shopPosY[b] <= 0) {
                genericRNG(1, 24);
                shopPosX[b] = rngVal;
                genericRNG(1, 24);
                shopPosY[b] = rngVal;
                for (int c = b + 1; c < numofshops; c++)
                    while (shopPosX[b] == enemyPosX[b] && shopPosY[b] == enemyPosY[b] || shopPosX[b] == dungeonPosX[b] && shopPosY[b] == dungeonPosY[b] || shopPosX[b] == shopPosY[c]) {
                        if (playerLocation.contains("Wildlands")) {
                            genericRNG(1, 24);
                            shopPosX[b] = rngVal;
                            genericRNG(1, 24);
                            shopPosY[b] = rngVal;
                        } else if (playerLocation.contains("Mountains")) {
                            genericRNG(25, 25);
                            shopPosX[b] = rngVal;
                            genericRNG(1, 24);
                            shopPosY[b] = rngVal;
                        }
                        else if (playerLocation.contains("Stronghold")) {
                            genericRNG(25,25);
                            enemyPosX[b] = rngVal;
                            genericRNG(25,25);
                            enemyPosY[b] = rngVal;
                        }
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

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void dayLoop() {
        clearConsole();
        //Prints out day info
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
        System.out.println("Your location: " + playerLocation);
        System.out.println("1: Upgrade Gear");
        System.out.println("2: Explore");
        System.out.println("3: Skill Tree");
        System.out.println("4: Change Loadout");
        System.out.println("6: Cheat Codes");
        if (onShop && !isSnowing) System.out.println("You found a shop! Press 5 to go in!");
        else if (onShop) System.out.println("You found a shop, but it looks like they're closed...");
        else if (onDungeon) System.out.println("You found a dungeon! Press 5 to go in! \nGreat treasures await, but at great risk...");
    }

    static void cheatCodes() {
        boolean doneCheating = false;
        while (!doneCheating) {
            System.out.println("Enter code:");
            playerResponse();
            switch (answer) {
                case 69420 -> {
                        int answer2 = 0;
                        System.out.println("Type your x coordinate.");
                        playerResponse();
                        if (answer > 50) System.out.println("You can't go there.");
                        else {
                            System.out.println("Type your y coordinate.");
                            hasConfirmed = false;
                            while (!hasConfirmed) {
                                try {
                                    answer2 = KB.nextInt();
                                    if (answer2 >= 0) hasConfirmed = true;
                                    else System.out.println("Invalid response.");
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid response. Please enter a valid integer.");
                                    KB.next();
                                }
                            }
                        }
                        if (answer2 > 25 && answer <= 24) System.out.println("You can't go there.");
                        else {
                            tempVal = playerPosX;
                            tempVal2 = playerPosY;
                            playerPosX = answer;
                            playerPosY = answer2;
                        }
                    }
                case 10001 -> {
                    day = 101;
                    playerDefense += 1250;
                    weapondmg += 1250;
                    playerSpeed += 1250;
                    level = 20;
                    skillPoints = 100;
                    playerHP = 200 + (35*level);
                    System.out.println("Activated omega mode and skipped to end.");
                }
                case 10002 -> {
                    for (int g = 0; g < shopPosX.length; g++) {
                        if (shopPosX[g] > 0 && shopPosY[g] > 0) {
                            playerPosX = shopPosX[g];
                            playerPosY = shopPosY[g];
                            System.out.println("Teleporting to shop...");
                            break;
                        }
                    }
                }
                case 10003 -> {
                    for (int g = 0; g < shopPosX.length; g++) {
                        if (dungeonPosX[g] > 0 && dungeonPosY[g] > 0) {
                            playerPosX = dungeonPosX[g];
                            playerPosY = dungeonPosY[g];
                            System.out.println("Teleporting to dungeon...");
                            break;
                        }
                    }
                }
                case 162506 -> {
                    if (playerPosX !=50) enemyPosX[20] = playerPosX +1;
                    else enemyPosX[20] = playerPosX -1;
                    enemyPosY[20] = playerPosX;
                    System.out.println("Added enemy directly in front of you.");
                }
                case 19890 -> {
                    coin += 100000;
                    System.out.println("You're now ballin.");
                }
                case 123456 -> {
                    playerPosX = 25;
                    playerPosY = 1;
                    System.out.println("Teleported to the Mountains.");
                }
                case 654321 -> {
                    playerPosX = 0;
                    playerPosY = 0;
                    System.out.println("Teleported to the Wildlands.");
                }
                case 362412 -> {
                    playerPosX = 25;
                    playerPosY = 25;
                    System.out.println("Teleported to the Stronghold.");
                }
                case 121212 -> {
                    playerHP += 10000000;
                    System.out.println("Activated immortality mode.");
                }
                case 101010 -> {
                    System.out.println("1: Sword");
                    System.out.println("2: Shield");
                    System.out.println("3: Axe");
                    playerResponse();
                    switch (answer) {
                        case 1 -> {
                            moveSet.clear();
                            getSword();
                        }
                        case 2 -> {
                            moveSet.clear();
                            getShield();
                        }
                        case 3 -> {
                            moveSet.clear();
                            getAxe();
                        }
                        default -> System.out.println("Bad response.");
                    }
                }
                default ->  System.out.println("Bad response.");
            }
            System.out.println("Press 0 to leave, or press another number to continue.");
            playerResponse();
            if (answer == 0) doneCheating = true;
        }
    }
    static void playerDied() {
        System.out.println("You were knocked out. The hours turn into days as you regain your strength...");
        day += 10;
        playerexp += 15;
        playerHP = 200 + (level*50);
    }

    static void randomWeather() {
        if (day % 7 == 0) {
            //Every 7 days, the weather will change
            genericRNG(1, 100);
            if (rngVal >= 1 && rngVal <= 14) weather = "Thunderstorm";
                //Has a chance of randomly destroying generated things like shops, enemies and dungeons
             else if (rngVal >= 15 && rngVal <= 23) weather = "Duststorm";
                //Duststorm
                //Makes it impossible to see, hiding fight distance
             else if (rngVal >= 24 && rngVal <= 40) weather = "Temporal Wind";
                //Randomly changes the day count
             else if (rngVal >= 40 && rngVal <= 47) weather = "Incendiary Rain";
                //Deals damage over time if you're not under shelter
             else if (rngVal >= 48 && rngVal <= 59) weather = "Snow";
                //Shops are closed, enemies hide under shelter.
            else weather = "Clear";
                //No effect
        }
    }

    static void weatherEffects() {
        System.out.println("Weather: ");
        if (!inDungeon) {
        switch (weather) {
                case "Thunderstorm" -> {
                    System.out.println("Thunderstorm: Lighting randomly strikes a tile, destroying anything on it.");
                    if (playerLocation.contains("Wildlands")) {
                        genericRNG(1,24);
                        boltPosX = rngVal;
                        genericRNG(1,24);
                        boltPosY = rngVal;
                    } else if (playerLocation.contains("Mountains")) {
                        genericRNG(25,25);
                        boltPosX = rngVal;
                        genericRNG(1,24);
                        boltPosY = rngVal;
                    }
                    else if (playerLocation.contains("Stronghold")) {
                        genericRNG(25,25);
                        boltPosX = rngVal;
                        genericRNG(25,25);
                        boltPosY = rngVal;
                    }
                    for (int count = 0; count < enemyPosX.length; count++) {
                        if (boltPosX == enemyPosX[count] && boltPosY == enemyPosY[count]){
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
                            System.out.println("You were struck by lighting! and lost " + Math.round(tempVal - playerHP) + " hp!");
                        }
                    }

                } case "Duststorm" -> System.out.println("Duststorm: Prevents you from seeing combat distance.");
                case "Temporal Wind" -> {
                    System.out.println("Temporal wind: Blows you into the future if touched.");
                    int[] temporalWindPosX = new int[3];
                    int[] temporalWindPosY = new int[3];
                    for (int x = 0; x < 3; x++) {
                        if (playerLocation.contains("Wildlands")) {
                            genericRNG(1,24);
                            temporalWindPosX[x] = rngVal;
                            genericRNG(1,24);
                            temporalWindPosY[x] = rngVal;
                        } else if (playerLocation.contains("Mountains")) {
                            genericRNG(25,25);
                            temporalWindPosX[x] = rngVal;
                            genericRNG(1,24);
                            temporalWindPosY[x] = rngVal;
                        }
                        else if (playerLocation.contains("Stronghold")) {
                            genericRNG(25,25);
                            boltPosX = rngVal;
                            genericRNG(25,25);
                            boltPosY = rngVal;
                        }
                    }
                    for (int count = 0; count < 3; count++) {
                        if (temporalWindPosX[count] == playerPosX && temporalWindPosY[count] == playerPosY) {
                            genericRNG(4,5);
                            tempVal = day;
                            day += rngVal;
                            System.out.println("You were blown " + (day - tempVal) + " days into the future!");
                        }
                    }

                } case "Incendiary Rain" -> {
                    int distanceX, distanceY;
                    System.out.println("Incendiary rain: Deals damage over time if not in shelter.");
                    for (int r = 0; r < shelterPosX.length; r++) {
                        distanceX = Math.abs(shelterPosX[r] - playerPosX);
                        distanceY = Math.abs(shelterPosY[r] - playerPosY);
                        if (distanceX + distanceY <= 3) System.out.println("Shelter at (" + shelterPosX[r] + "," + shelterPosY[r] + ")!");
                    }
                    if (!underShelter) {
                        playerHP -= (playerHP/10) + (25 * level);
                        //the tempVal is to cancel out the heal you get at the end of every day.
                        System.out.println("The fire rain burns your skin, causing you extreme pain...");
                    }
                } case "Snow" -> {
                    System.out.println("Snow: Enemy count doesn't increase, but shops are closed.");
                    isSnowing = true;
                }
                default -> System.out.println("Clear skies!");
            }
        }
    }

    static void basicShop() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        playerLeft = false;
        System.out.println("Welcome to Wanderer's Source, the number 1 shop for all things with pointy ends or go boom.");
        while (!playerLeft) {
            System.out.println("Your coins: " + coin);
            System.out.println("1: Buy Stats");
            System.out.println("2: Utility Items");
            System.out.println("3: Fast Travel");
            System.out.println("4: Quick Heal");
            System.out.println("5: Leave Shop");
            playerResponse();
            switch (answer) {
                case 1 -> {
                    System.out.println("What would you like to upgrade?");
                    Thread.sleep(500);
                    System.out.println("1. Damage");
                    System.out.println("2. Defense");
                    System.out.println("3. Speed");
                    playerResponse();
                    switch (answer) {
                        case 1 -> {
                            System.out.println("The rate is 10 increase per coin.");
                            Thread.sleep(500);
                            System.out.println("Press 1 to confirm, or press another number to go back.");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("How much would you like to increase your damage?");
                                playerResponse();
                                tempVal2 = weapondmg;
                                shopExchange(10,1);
                                weapondmg += tempVal;
                                soundEffect(14);
                                Thread.sleep(20);
                                soundEffect(18);
                                System.out.println("We improved your damage by " + (weapondmg - tempVal2) + ".");
                                hasConfirmedShop = true;
                            } else {
                                System.out.println("Then why did you say you wanted your gear improved?");
                                hasConfirmedShop = true;
                            }
                        } case 2 -> {
                            System.out.println("The rate is 2 increase per 13 coins.");
                            Thread.sleep(500);
                            System.out.println("Press 1 to confirm, or press another number to go back.");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("How much would you like to increase your defense?");
                                hasConfirmed = false;
                                playerResponse();
                                tempVal2 = playerDefense;
                                shopExchange(2,13);
                                playerDefense += tempVal;
                                soundEffect(14);
                                Thread.sleep(20);
                                soundEffect(18);
                                System.out.println("We improved your defense by " + (playerDefense - tempVal2) + ".");
                            } else {
                                System.out.println("Then why did you say you wanted your gear improved?");
                            }
                            hasConfirmedShop = true;
                        } default ->  {
                            System.out.println("The rate is 1 increase per 9 coins.");
                            Thread.sleep(500);
                            System.out.println("Press 1 to confirm, or press another number to go back.");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("How much would you like to increase your speed?");
                                playerResponse();
                                tempVal2 = playerSpeed;
                                shopExchange(9,1);
                                playerSpeed += tempVal;
                                soundEffect(14);
                                Thread.sleep(20);
                                soundEffect(18);
                                System.out.println("We improved your speed by " + (playerSpeed - tempVal2) + ".");
                                hasConfirmedShop = true;
                            } else {
                                System.out.println("Then why did you say you wanted your gear improved?");
                                hasConfirmedShop = true;
                            }
                        }
                    }
                } case 2 -> {
                    System.out.println("so you want the good stuff huh...");
                    Thread.sleep(1000);
                    System.out.println("1: Grappling Hook: Extends range of your next attack at the cost of speed.");
                    System.out.println("2. Regenerative Stone: Heal yourself for a small amount.");
                    System.out.println("3. Soul Snatcher: Hits the opponent for a percentage of their max hp, ignoring defense. ");
                    System.out.println("Any other number: Go back");
                    playerResponse();
                    switch (answer) {
                        case 1 -> {
                            System.out.println("This item is single use and costs 75 per hook. How many do you want?");
                            playerResponse();
                            tempVal2 = grapplingHook;
                            shopExchange(75,1);
                            soundEffect(18);
                            grapplingHook += tempVal;
                            System.out.println("You bought " + (grapplingHook - tempVal2) + " grappling hooks.");
                        } case 2 -> {
                            System.out.println("This item is single use and costs 40 per stone. How many do you want?");
                            playerResponse();
                            tempVal2 = regenerativeStone;
                            shopExchange(40,1);
                            soundEffect(18);
                            regenerativeStone += tempVal;
                            System.out.println("You bought " + (regenerativeStone - tempVal2) + " stones.");
                        } case 3 -> {
                            System.out.println("This item is single use and costs 120 per snatcher. How many do you want?");
                            playerResponse();
                            tempVal2 = soulSnatcher;
                            shopExchange(120,1);
                            soundEffect(18);
                            soulSnatcher += tempVal;
                            System.out.println("You bought " + (soulSnatcher - tempVal2) + " Soul Snatchers.");
                        } default -> System.out.println("Just give me your money already!.");
                    }
                } case 3 -> fastTravel();
                 case 4 -> {
                    System.out.println("It costs 70 coins to get 150 hp. Press 1 to confirm, or press another number to leave. ");
                    playerResponse();
                    if (answer == 1) {
                        if (coin >= 70) {
                            shopExchange(70,150);
                            playerHP += tempVal;
                            if (playerHP > (200 + (level * 50))) playerHP = 200 + (level * 50);
                            System.out.println("Your new hp is now " + playerHP);
                        } else System.out.println("You're too poor. You can stop bleeding on my carpet now.");
                        hasConfirmedShop = true;
                    }
                } default -> {
                    System.out.println("Come again soon! We want your money!");
                    stopMusic();
                    playerLeft = true;
                }
            }
        }
    }

    static void advancedShop () throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        playerLeft = false;
        System.out.println("Welcome to Spellbound Sundries, hope I can extend your lifespan at least a little bit.");
        while (!playerLeft) {
            System.out.println("Your coins: " + coin);
            System.out.println("1: Buy Stats");
            System.out.println("2: Utility Items");
            System.out.println("3: Fast Travel");
            System.out.println("4: Quick Heal");
            System.out.println("5: Leave Shop");
            playerResponse();
            switch (answer) {
                case 1 -> {
                    System.out.println("What would you like to upgrade?");
                    Thread.sleep(500);
                    System.out.println("1. Damage");
                    System.out.println("2. Defense");
                    System.out.println("3. Speed");
                    playerResponse();
                    switch (answer) {
                        case 1 -> {
                            System.out.println("The rate is 4 increase per 33 coins.");
                            Thread.sleep(500);
                            System.out.println("Press 1 to confirm, or press another number to go back.");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("How much would you like to increase your damage?");
                                playerResponse();
                                tempVal2 = weapondmg;
                                shopExchange(33,4);
                                weapondmg += tempVal;
                                soundEffect(14);
                                System.out.println("We improved your damage by " + (weapondmg - tempVal2) + ".");
                            } else System.out.println("Alright, maybe there's something else you want.");
                        }
                        case 2 -> {
                            System.out.println("The rate is 5 increase per 54 coins.");
                            Thread.sleep(500);
                            System.out.println("Press 1 to confirm, or press another number to go back.");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("How much would you like to increase your defense?");
                                hasConfirmed = false;
                                playerResponse();
                                tempVal2 = playerDefense;
                                shopExchange(5,54);
                                playerDefense += tempVal;
                                soundEffect(14);
                                System.out.println("We improved your defense by " + (playerDefense - tempVal2) + ".");
                            } else System.out.println("Alright, maybe there's something else you want.");

                        }
                        case 3 -> {
                            System.out.println("The rate is 7 increase per 41 coins.");
                            Thread.sleep(500);
                            System.out.println("Press 1 to confirm, or press another number to go back.");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("How much would you like to increase your speed?");
                                playerResponse();
                                tempVal = playerSpeed;
                                shopExchange(9,1);
                                playerSpeed += tempVal;
                                soundEffect(14);
                                System.out.println("We improved your speed by " + (playerSpeed - tempVal) + ".");
                            } else System.out.println("Alright, maybe there's something else you want.");

                        }
                    }

                }
                case 2 -> {
                    System.out.println("Guess I gotta bust out the extra cabinet...");
                    Thread.sleep(1000);
                    System.out.println("1: Grappling Hook: Extends range of your next attack at the cost of speed.");
                    System.out.println("2. Regenerative Stone: Heal yourself for a small amount.");
                    System.out.println("3. Soul Snatcher: Hits the opponent for a percentage of their max hp, ignoring defense. ");
                    System.out.println("4. Smoke Bomb: Choose your fight distance.");
                    System.out.println("5. Cyclone Straw: Instantly brings the opponent directly in front of you.");
                    System.out.println("6. Magma Whistle: Summons the hand of a fire giant, grabbing your opponent and slamming them far away.");
                    System.out.println("Any other number: Go back");
                    playerResponse();
                    switch (answer) {
                        case 1 -> {
                            System.out.println("This item is single use and costs 75 per hook. How many do you want?");
                            playerResponse();
                            tempVal2 = grapplingHook;
                            shopExchange(75,1);
                            grapplingHook += tempVal;
                            System.out.println("You bought " + (grapplingHook - tempVal2) + " grappling hooks.");
                        }
                        case 2 -> {
                            System.out.println("This item is single use and costs 40 per stone. How many do you want?");
                            playerResponse();
                            tempVal2 = regenerativeStone;
                            shopExchange(40,1);
                            regenerativeStone += tempVal;
                            System.out.println("You bought " + (regenerativeStone - tempVal2) + " stones.");
                        }
                        case 3 -> {
                            System.out.println("This item is single use and costs 120 per snatcher. How many do you want?");
                            playerResponse();
                            tempVal2 = soulSnatcher;
                            shopExchange(120,1);
                            soulSnatcher += tempVal;
                            System.out.println("You bought " + (soulSnatcher - tempVal2) + " Soul Snatchers.");
                        }
                        case 4 -> {
                            System.out.println("This item is single use and costs 90 per bomb. How many do you want?");
                            playerResponse();
                            tempVal2 = smokeBombs;
                            shopExchange(90,1);
                            smokeBombs += tempVal;
                            System.out.println("You bought " + (smokeBombs - tempVal2) + " smoke bombs.");
                        }
                        case 5 -> {
                            System.out.println("This item is single use and costs 90 per straw. How many do you want?");
                            playerResponse();
                            tempVal2 = cycloneStraws;
                            shopExchange(90,1);
                            cycloneStraws += tempVal;
                            System.out.println("You bought " + (cycloneStraws - tempVal2) + " cyclone straws.");
                        }
                        case 6 -> {
                            System.out.println("This item is single use and costs 160 per straw. How many do you want?");
                            playerResponse();
                            tempVal2 = magmaWhistle;
                            shopExchange(160,1);
                            magmaWhistle += tempVal;
                            System.out.println("You bought " + (magmaWhistle - tempVal2) + " magma whistles.");
                        }
                        default -> System.out.println("I can't help but feel like you're just messing with me.");

                    }
                }
                case 3 -> fastTravel();
                case 4 -> {
                    System.out.println("It costs 70 coins to get 150 hp. Press 1 to confirm, or press another number to leave. ");
                    playerResponse();
                    if (answer == 1) shopHeal(70,150);
                    else System.out.println("I guess you don't mind bleeding.");
                    hasConfirmedShop = true;
                }
                case 5 -> {
                    stopMusic();
                    System.out.println("Hope to see you around some time soon. You don't seem like the bloodthirsty type.");
                    playerLeft = true;
                }

            }
        }
    }
    static void enhancedShop() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        playerLeft = false;
        System.out.println("Arcane Artifacts, Arcane Artifacts, ARCANE ARTIFACTS!");
        while (!playerLeft) {
            System.out.println("Your coins: " + coin);
            System.out.println("1: Buy Stats");
            System.out.println("2: Utility Items");
            System.out.println("3: Fast Travel");
            System.out.println("4: Quick Heal");
            System.out.println("5: Enchants");
            System.out.println("6: Leave Shop");
            playerResponse();
            switch (answer) {
                case 1 -> {
                    System.out.println("What would you like to upgrade?");
                    Thread.sleep(500);
                    System.out.println("1. Damage");
                    System.out.println("2. Defense");
                    System.out.println("3. Speed");
                    playerResponse();
                    switch (answer) {
                        case 1 -> {
                            System.out.println("The rate is 11 increase per 105 coins.");
                            Thread.sleep(500);
                            System.out.println("Press 1 to confirm, or press another number to go back.");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("How much would you like to increase your damage?");
                                playerResponse();
                                tempVal2 = weapondmg;
                                shopExchange(105,11);
                                weapondmg += tempVal;
                                soundEffect(14);
                                System.out.println("We improved your damage by " + (weapondmg - tempVal2) + ".");
                            } else System.out.println("Why you lie! Lying bad! Me hate lies!");
                        }
                        case 2 -> {
                            System.out.println("The rate is 13 increase per 118 coins.");
                            Thread.sleep(500);
                            System.out.println("Press 1 to confirm, or press another number to go back.");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("How much would you like to increase your defense?");
                                hasConfirmed = false;
                                playerResponse();
                                tempVal2 = playerDefense;
                                shopExchange(118,13);
                                playerDefense += tempVal;
                                soundEffect(14);
                                System.out.println("We improved your defense by " + (playerDefense - tempVal2) + ".");
                            } else System.out.println("Why you lie! Lying bad! Me hate lies!");

                        }
                        case 3 -> {
                            System.out.println("The rate is 10 increase per 96 coins.");
                            Thread.sleep(500);
                            System.out.println("Press 1 to confirm, or press another number to go back.");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("How much would you like to increase your speed?");
                                playerResponse();
                                tempVal = playerSpeed;
                                shopExchange(96,10);
                                playerSpeed += tempVal;
                                soundEffect(14);
                                System.out.println("We improved your speed by " + (playerSpeed - tempVal) + ".");
                            } else System.out.println("Why you lie! Lying bad! Me hate lies!");

                        }
                    }

                }
                case 2 -> {
                    System.out.println("You want more boom?");
                    Thread.sleep(1000);
                    System.out.println("1: Grappling Hook: Extends range of your next attack at the cost of speed.");
                    System.out.println("2. Regenerative Stone: Heal yourself for a small amount.");
                    System.out.println("3. Soul Snatcher: Hits the opponent for a percentage of their max hp, ignoring defense. ");
                    System.out.println("4. Smoke Bomb: Choose your fight distance.");
                    System.out.println("5. Cyclone Straw: Instantly brings the opponent directly in front of you.");
                    System.out.println("6. Magma Whistle: Summons the hand of a fire giant, grabbing your opponent and slamming them far away.");
                    System.out.println("7. Poison Dart: Deal a small percentage of damage over time.");
                    System.out.println("8. Insanity Curse: Instantly cause your opponent to become angry.");
                    System.out.println("9. Enlargement Flask: Gain super armor on your next attack.");
                    System.out.println("Any other number: Go back");
                    playerResponse();
                    switch (answer) {
                        case 1 -> {
                            System.out.println("This item is single use and costs 75 per hook. How many do you want?");
                            playerResponse();
                            tempVal2 = grapplingHook;
                            shopExchange(75,1);
                            grapplingHook += tempVal;
                            System.out.println("You bought " + (grapplingHook - tempVal2) + " grappling hooks.");
                        }
                        case 2 -> {
                            System.out.println("This item is single use and costs 40 per stone. How many do you want?");
                            playerResponse();
                            tempVal2 = regenerativeStone;
                            shopExchange(40,1);
                            regenerativeStone += tempVal;
                            System.out.println("You bought " + (regenerativeStone - tempVal2) + " stones.");
                        }
                        case 3 -> {
                            System.out.println("This item is single use and costs 120 per snatcher. How many do you want?");
                            playerResponse();
                            tempVal2 = soulSnatcher;
                            shopExchange(120,1);
                            soulSnatcher += tempVal;
                            System.out.println("You bought " + (soulSnatcher - tempVal2) + " Soul Snatchers.");
                        }
                        case 4 -> {
                            System.out.println("This item is single use and costs 90 per bomb. How many do you want?");
                            playerResponse();
                            tempVal2 = smokeBombs;
                            shopExchange(90,1);
                            smokeBombs += tempVal;
                            System.out.println("You bought " + (smokeBombs - tempVal2) + " smoke bombs.");
                        }
                        case 5 -> {
                            System.out.println("This item is single use and costs 90 per straw. How many do you want?");
                            playerResponse();
                            tempVal2 = cycloneStraws;
                            shopExchange(90,1);
                            cycloneStraws += tempVal;
                            System.out.println("You bought " + (cycloneStraws - tempVal2) + " cyclone straws.");
                        }
                        case 6 -> {
                            System.out.println("This item is single use and costs 160 per straw. How many do you want?");
                            playerResponse();
                            tempVal2 = magmaWhistle;
                            shopExchange(160,1);
                            magmaWhistle += tempVal;
                            System.out.println("You bought " + (magmaWhistle - tempVal2) + " magma whistles.");
                        }
                        case 7 -> {
                            System.out.println("This item is single use and costs 175 per dart. How many do you want?");
                            playerResponse();
                            tempVal2 = poisonDart;
                            shopExchange(160,1);
                            poisonDart += tempVal;
                            System.out.println("You bought " + (poisonDart - tempVal2) + " poison darts.");
                        }
                        case 8 -> {
                            System.out.println("This item is single use and costs 195 per curse. How many do you want?");
                            playerResponse();
                            tempVal2 = insanityCurses;
                            shopExchange(160,1);
                            insanityCurses += tempVal;
                            System.out.println("You bought " + (insanityCurses - tempVal2) + " insanity curses.");
                        }
                        case 9 -> {
                            System.out.println("This item is single use and costs 215 per flask. How many do you want?");
                            playerResponse();
                            tempVal2 = enlargementFlask;
                            shopExchange(160,1);
                            enlargementFlask += tempVal;
                            System.out.println("You bought " + (enlargementFlask - tempVal2) + " enlargement flasks.");
                        }
                        default -> System.out.println("You speak trash, need teacher!");
                    }
                }
                case 3 -> fastTravel();
                case 4 -> {
                    System.out.println("It costs 115 coins to get 225 hp. Press 1 to confirm, or press another number to leave. ");
                    playerResponse();
                    if (answer == 1) shopHeal(115,225);
                    else System.out.println("I guess you don't mind bleeding.");
                    hasConfirmedShop = true;
                }
                case 5 -> {
                    System.out.println("You want shiny rocks?");
                    Thread.sleep(1000);
                    boolean wantsEnchants = true;
                    while (wantsEnchants) {
                        System.out.println("1: Weapon Enchants");
                        System.out.println("2: Armor Enchants");
                        System.out.println("3: Upgrade Enchants");
                        System.out.println("4: Leave");
                        if (answer == 1) {
                            System.out.println("----------------------------------------------------------------");
                            System.out.println("1: Combo Gem. Damage increases the more you are able to hit your opponent without being hit.");
                            System.out.println("2: Double Slash Gem. Deal bonus damage on every 2nd turn.");
                            System.out.println("3: Parry Gem. After blocking an attack, the next attack deals bonus damage.");
                        } else if (answer == 2) {
                            System.out.println("----------------------------------------------------------------");
                            System.out.println("1: Berserker Gem: Moving forward increases defense.");
                            System.out.println("2: Reflection Gem: Blocking attacks has a low chance of inflicting damage back.");
                            System.out.println("3: Absorption Gem: Blocking attacks boosts your stamina gain. ");
                        } else if (answer == 3) {
                            System.out.println("Select the type you want to upgrade:");
                            System.out.println("1: Armor");
                            System.out.println("2: Weapon");
                            playerResponse();
                            if (answer == 1) {
                                System.out.println("Armor:");
                                System.out.println("----------------------------------------------------------------");
                                for (int a = 0; a < armorEnchantSet.size(); a++) {
                                    System.out.println(a + ":" + armorEnchantSet.get(a));
                                }
                                System.out.println("Select the enchant you are looking to upgrade.");
                                do {
                                    playerResponse();
                                   System.out.println(armorEnchantSet.get(answer));
                                   tempString = armorEnchantSet.get(answer);
                                } while (armorEnchantSet.get(answer) == null);
                                System.out.println("Tier: " + enchantTier.get(tempString));
                                int cost = enchantTier.get(tempString)*500;
                                System.out.println("Cost: " + cost);
                                System.out.println("Press 1 to confirm, or press another number to go back.");
                                if (answer == 1 && coin >= cost) {
                                   cost = enchantTier.get(tempString);
                                   cost += 1;
                                   enchantTier.replace(tempString, cost);
                                   coin -= cost;
                                   System.out.println("Ooo, you have shiny rock too...");
                                }
                                else if (coin < cost && answer == 1) System.out.println("Where is shiny rock... (too poor)");
                                else System.out.println("*slumps into chair");
                            }
                             System.out.println("----------------------------------------------------------------");
                             System.out.println("Weapons:");
                             System.out.println("----------------------------------------------------------------");
                             for (int b = 0; b < weaponEnchantSet.size(); b++) {
                                 System.out.println(weaponEnchantSet.get(b));
                             }


                        }
                        else if (answer == 4) {
                            System.out.println("Hmm. No like shiny rocks?");
                            wantsEnchants = false;
                        }
                    }
                }
                case 6 -> {
                    stopMusic();
                    System.out.println("Goodbye funny person! See again soon!");
                    System.out.println("----------------------------------------------------------------");
                    playerLeft = true;
                }

            }
        }
    }
    static void fastTravel()  {
        System.out.println("200 coins to fast travel. Press 1 if you don't mind us taking your money.");
        playerResponse();
        if (answer == 1) {
            int answer2 = 0;
            if (coin > 200) System.out.println("200 coins to fast travel. Type your x coordinate.");
            playerResponse();
            if (answer > 50) System.out.println("You can't go there.");
            else {
                System.out.println("Type your y coordinate.");
                hasConfirmed = false;
                while (!hasConfirmed) {
                    try {
                        answer2 = KB.nextInt();
                        if (answer2 >= 0) hasConfirmed = true;
                        else System.out.println("Invalid response.");
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid response. Please enter a valid integer.");
                        KB.next();
                    }
                }
            }
            if (answer2 > 25) System.out.println("You can't go there.");
             else
             {
                if (playerLocation.contains("Wildlands")) System.out.println("See you later. Sorcerer powers GO!");
                else if (playerLocation.contains("Mountains")) System.out.println("Apologies if I'm a bit off. Been a while...");
                else if (playerLocation.contains("Stronghold")) System.out.println("Let's see if you die! *chuckles*");
                tempVal = playerPosX;
                tempVal2 = playerPosY;
                playerPosX = answer;
                playerPosY = answer2;
                coin -= 200;
                hasConfirmedShop = true;
                playerLeft = true;
            }
        } else if (coin < 200) {
            if (playerLocation.contains("Wildlands")) System.out.println("Yeah... you're way too poor for that.");
            else if (playerLocation.contains("Mountains")) System.out.println("Not much I can do if you don't have the money, I'm afraid.");
            else if (playerLocation.contains("Stronghold")) System.out.println("No money? But I need money for more comically large objects of mass destruction!");
        }
        else {
            if (playerLocation.contains("Wildlands")) System.out.println("Make up your mind...");
            else if (playerLocation.contains("Mountains")) System.out.println("Alright, if you say so.");
            else if (playerLocation.contains("Stronghold")) System.out.println("But, but, but, you said you wanna... now I'm sad you lied to me...");
        }
    }

    static void playerResponse() {
        hasConfirmed = false;
        while (!hasConfirmed) {
            try {
                answer = KB.nextInt();
                if (answer >= 0 ) hasConfirmed = true;
                 else System.out.println("Invalid response.");
            } catch (Exception e) {
                System.out.println("Invalid response. Please enter a valid integer.");
                KB.next();
            }
        }
    }
    static void shopExchange(int exchangeRate, int improveRate) {
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

    static void shopHeal(int exchangeRate, int improveRate) {
        System.out.println("It costs " + exchangeRate + " coins to get " + improveRate + " hp. Press 1 to confirm, or press another number to leave. ");
        playerResponse();
        if (answer == 1) {
            if (coin >= exchangeRate) {
                coin -= exchangeRate;
                playerHP += improveRate;
                if (playerHP > 75 + (35*level)) playerHP = 75 + (35*level);
               if (playerLocation.contains("Wildlands")) System.out.println("Please continue throwing your life away, it brings me profit.");
               else if (playerLocation.contains("Mountains")) System.out.println("Let's hope you last a bit longer next time.");
               else if (playerLocation.contains("Stronghold")) System.out.println("Wow! You're alive! That went way better then last time!");
            } else if (playerLocation.contains("Wildlands")) System.out.println("You're too poor. You can stop bleeding on my carpet now.");
            else if (playerLocation.contains("Mountains")) System.out.println("I can't help you if you can't help me.");
            else if (playerLocation.contains("Stronghold")) System.out.println("Where's money?! Don't see money in my hand...");
            hasConfirmedShop = true;
        }
    }
    static void endOfDay() throws InterruptedException {
        playerexp += 15;
        genericRNG(25,25*level);
        coin += rngVal;
        requiredXP = 80*level;
        while (playerexp > requiredXP) {
            requiredXP = 80*level;
            playerexp -= requiredXP;
            level += 1;
            skillPoints += 1;
            System.out.println("You are now level " + level + "! Your strength grows!");
            tempVal2 = playerPower;
            tempVal = weapondmg;
            genericRNG((int) weapondmg/20, (int) weapondmg/10);
            weapondmg += rngVal;
            System.out.println("Your damage has increased by " + Math.round((weapondmg - tempVal)));
            tempVal = playerDefense;
            genericRNG((int) playerDefense/20, (int) playerDefense/10);
            playerDefense += rngVal;
            System.out.println("Your defense has increased by " + Math.round((playerDefense - tempVal)));
            tempVal = playerSpeed;
            genericRNG((int) playerSpeed/20, (int) playerSpeed/10);
            playerSpeed += rngVal;
            System.out.println("Your speed has increased by " + Math.round((playerSpeed - tempVal)));
            playerPower = (weapondmg + playerSpeed + playerDefense + (100 + (level*35)))/4;
            System.out.println("Your overall power has increased by " + Math.round((playerPower - tempVal2)));
            Thread.sleep(5000);
        }
        if (playerPosX > 25 && playerPosY <25 && !playerLocation.contains("Mountains"))
        {
            tempVal = 25;
            System.out.println("You are going to enter the mountains.");
            if (difficultyTier > tempVal) System.out.print(" The enemies are stronger, but the loot is better.");
            System.out.println("Press 1 to confirm, or press 2 to return.");
            playerResponse();
            if (answer == 1)
            {
                playerLocation = "Mountains";
                difficultyTier = Math.toIntExact(Math.round(tempVal));
                System.out.println("To the mountains!");
            }
            else
            {
                playerPosX = Math.toIntExact(Math.round(tempVal));
                playerPosY = Math.toIntExact(Math.round(tempVal2));
                System.out.println("You have returned to your previous location.");
            }
        }
        if (playerPosX < 25 && playerPosY < 25 && playerLocation.contains("Mountains"))
        {
            System.out.println("You are going to enter the wildlands. Press 1 to confirm, or press 2 to return.");
            playerResponse();
            if (answer == 1)
            {
                difficultyTier = 20;
                playerLocation = "Wildlands";
                System.out.println("To the wildlands!");
            }
            else {
                playerPosX = Math.toIntExact(Math.round(tempVal));
                playerPosY = Math.toIntExact(Math.round(tempVal2));
                System.out.println("You have returned to your previous location.");
            }
        }
        if (playerPosX > 25 && playerPosY > 25 && !playerLocation.contains("Stronghold")) {
            tempVal = 30;
            System.out.println("You are going to enter the stronghold.");
            if (difficultyTier > tempVal) System.out.print(" The enemies are stronger, but the loot is better.");
            System.out.println("Press 1 to confirm, or press 2 to return.");
            playerResponse();
            if (answer == 1)
            {
                playerLocation = "Stronghold";
                System.out.println("To the stronghold!");
            }
            else {
                playerPosX = Math.toIntExact(Math.round(tempVal));
                playerPosY = Math.toIntExact(Math.round(tempVal2));
                System.out.println("You have returned to your previous location.");
            }
        }
        playerHP += (playerHP/10);
        if (playerHP > 100 + (35*level)) playerHP = 100 + (35*level);
        day += 1;
    }
    static void upgradeGear() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        while ((day * level) / 2 - decayImp <= 0) decayImp -= 1;
            //Can't let decayImp make the increase 0 or less
        genericRNG(1,6);
        int x = rngVal;
        genericRNG(-(decayImp), (difficultyTier*level) + (day));
        rngVal = Math.max(5, rngVal);
        if (Math.max(5,rngVal) == 5) System.out.println("Your hand grows tired fom the constant work...");
        //prevents stats from decreasing
        switch (x) {
            case 1:
            case 2: {
                tempVal = weapondmg;
                weapondmg += rngVal;
                System.out.println("Your damage has increased by " + Math.round((weapondmg - tempVal)));
                break;
            }
            case 3: {
                tempVal = playerSpeed;
                playerSpeed += rngVal;
                System.out.println("Your speed has increased by " + Math.round((playerSpeed - tempVal)));
                break;
            }
            case 4: {
                tempVal = playerDefense;
                playerDefense += rngVal;
                System.out.println("Your defense has improved by " + Math.round((playerDefense - tempVal)));
                break;
            }
            case 5: {
                tempVal = coin;
                coin += rngVal;
                System.out.println("You didn't upgrade your armor, but you found " + Math.round((coin - tempVal)) + " coins lying around.");
            }
            default: System.out.println("You couldn't upgrade your armor.");
        }
        if (x != 6) soundEffect(14);
        decayImp += (decayImp)*1.15;
            spentDay = true;
        }
    static void exploreZenotopia() {
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
            switch (answerType) {
                case "a":
                case "A": {
                      playerPosX -= 1;
                    if (playerPosX < 0) {
                        System.out.println("You almost fall off a cliff and die. That would've been funny.");
                        playerPosX = (int) tempVal;
                    }
                    hasConfirmedTraversal = true;
                    break;
                }
            case "d":
            case "D":
                 {
                    playerPosX += 1;
                     if (playerPosX > 50) {
                         System.out.println("You almost fall off a cliff and die. That would've been funny.");
                         playerPosX = (int) tempVal;
                     }
                     hasConfirmedTraversal = true;
                    break;
                }
                case "w":
                case "W": {
                    playerPosY += 1;
                    if (playerPosY > 25) {
                        System.out.println("You run into a wall and fall over. The king laughs at you.(can't exceed 25 units up)");
                        playerPosY = (int) tempVal2;
                    }
                    hasConfirmedTraversal = true;
                    break;
                }
                case "s":
                case "S": {
                    playerPosY -= 1;
                    if (playerPosY < 0) {
                        System.out.println("You run into a wall and fall over. The king laughs at you.(can't have negative y coordinate)");
                        playerPosY = (int) tempVal2;
                        hasConfirmed = true;
                    }
                    hasConfirmedTraversal = true;
                    break;
                }
                default: {
                    System.out.println("You did not input a valid response.");
                    hasConfirmedTraversal = true;
                    hasConfirmed = true;
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
            System.out.println("The crowd erupts in your victory. For you were a nobody, and now you have proven yourself.");
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
            if (answer == 2) System.out.println("You are banished from the kingdom.");
            else System.out.println("You are now the wealthiest man in Zenotopia.");
            System.out.println("Congratulations!");
        }
            Thread.sleep(1000);
            playerPower = (weapondmg + playerDefense + playerSpeed + 200 + (level*50));
            System.out.println("You ended the game with a power level of " + Math.round(playerPower) + ".");
            Thread.sleep(1000);
            System.out.println("You had " + coin + " coins.");
            Thread.sleep(1000);
            System.out.println("You defeated " + numOfEnemiesDefeated + " enemies.");
            Thread.sleep(1000);
            System.out.println("Made by Temi Awosiyan.");
            System.out.println("Sources:");
            Thread.sleep(1000);
            System.out.println("https://pixabay.com/sound-effects/search/medieval/");
            System.out.println("Block sfx: Sound Effect by <a href=\"https://pixabay.com/users/sectionsound-34536612/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=143940\">SectionSound</a> from <a href=\"https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=143940\">Pixabay</a>");
            System.out.println("Ki Charge: https://www.youtube.com/watch?v=VoYzS_E7Ijg&ab_channel=DavidDumaisAudio");
            System.out.println("Gear improve sfx: https://www.youtube.com/watch?v=xcDVBwaI-V0&ab_channel=SoundEffectsArchive");
            System.out.println("Fire sfx: https://mixkit.co/free-sound-effects/fire/");
            System.out.println("All music made by me in Garageband using Apple Royality Free Music.");
    }

    /*
    The following methods are all music.
     */
    protected static void playMusic(int trackNum) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Music audio = new Music();
        Music.setFile(trackNum);
        Music.startClip();
        Music.loopClip();
        musicPlaying = true;
    }
    protected static void stopMusic() throws LineUnavailableException, IOException {
        Music.stopClip();
        musicPlaying = false;
    }
    protected static void soundEffect(int trackNum) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Music audio = new Music();
        Music.setFile(trackNum);
        Music.startClip();
    }
    protected static void resetMusic() {
        Music.dayMusic.setMicrosecondPosition(0);
    }


}
