import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

class Combat extends Primary {

     //static combatUI CombatUI = new combatUI();


     private static Music music;

    public Combat(Music Music) {
         music = Music;
     }

    // public Combat() {
     //     CombatUI = new combatUI();
    // }

     protected static int staminaRegenPaused = 0;
    protected static int eradicateTurns = 0;
    protected static boolean isIgnited = false;
    protected static int shieldLocation = 0;

    private static boolean isPrimal = false;
    protected static int pyroslashLocation = 0;

    protected static boolean usedPyroslash = false;

    protected static boolean hasShield = true;
    protected static String enemyCC = "";

   protected static String shieldHand = "D";

    protected static int enemyCCAmount = 0;
    protected static boolean enemyConfirm = false;

    protected static int unactionableTurns = 0;

    protected static int enemyUnactionableTurns = 0;

    protected static int staminaCost = 0;

    protected static String tempMove = "";
  
    protected static String CC = "";
    
    protected static String moveDescription = "";

    //Armor checker,lets you still hit the opponent even if your move was slower or weaker.
    protected static boolean hasArmor = false;

    protected static int armorDuration = 0;

    protected static boolean playerIsActionable = true;

    protected static boolean enemyIsActionable = true;

    //Boolean for whether Ki Charge has been used or not
    protected static boolean swordCharged = false;
    
    protected static double attackDamage = weapondmg;
    
    protected static double attackSpeed = playerSpeed;
    
    protected static double attackDefense = playerDefense;

    //The reason why I use these variables instead of the ones in Primary is because of weaken. That way,
    //the player's stats are only weakened for the duration of the fight.


    //For grab type moves
    protected static boolean ignoresBlock = false;

    //Enemy AI behaviour
    protected static String enemyBehaviour = "";

    protected static int enemyEgo = 0;

    protected static int enemyFear = 0;

    protected static int enemyRage = 0;

    protected static int moveDecider = 0;

    protected static boolean enemyWillAttack = false, enemyWillMove = false, enemyWillDefend = false;

    protected static int  eSmokeBomb = 2;

    protected static int ePoisonDart = 2;

    protected static int enemyDOTDuration = 0;

    protected static int DOTDuration = 0;

    protected static double DOTDamage = 0;

    protected static double enemyDOTDamage = 0;

    protected static boolean enemyTakesDOT = false;

    protected static boolean playerTakesDOT = false;
     public static void fight() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
         if (moveSet.containsValue("Swap")) shieldHand = "D";
        else shieldHand = "";
       if (day <= 99 ) {
           genericRNG(1, 2);
           combatMusic(rngVal);
       }
       else combatMusic(2);
        clearConsole();
        decayImp -= 5;
        enemyTypeSelector();
       if (day <= 100) startCombat();
       else finalBattle();
       enemyPlaystyle();
        while (isFighting) {
            resetCombatVar();
            enemyEmotion();
            if (unactionableTurns >= 1) playerIsActionable = false;
            if (enemyUnactionableTurns >= 1) enemyIsActionable = false;
            hasConfirmedCombat = false;
            while (!hasConfirmedCombat) {
                if (playerIsActionable) {
                    turnLoop();
                    playerResponse();
                    switch (answer) {
                        case 1:
                        if (weaponName.contains("Axe")) axeSelection();
                        if (weaponName.contains("Sword")) swordSelection();
                        if (weaponName.contains("Shield")) shieldSelection();
                        break;
                        case 2:
                        playerMove();
                        break;
                        case 3:
                        playerBlock();
                        break;
                        case 4:
                        utilityItem();
                        break;
                        case 6:
                        winButton();
                        break;
                        default:
                        playerWait();
                        break;
                    }
                }
                else hasConfirmedCombat = true;
            }
            tempVal = enemyStamina;
            if (enemyIsActionable) {
                switch (enemyName) {
                    case "Weak Swords-man", "Strong Swords-man", "Ninja Swords-man" -> swordsmanAI();
                    case "Strong Shields-man", "Weak Shields-man", "Ninja Shields-man" -> shieldsmanAI();
                    case "Strong Bowman", "Ninja Bowman" -> bowmanAI();
                    case "Bear", "Attack Dog" -> beastAI();
                    case "Oni" -> oniAI();
                    case "The Last" -> BossAI();
                }
                if (fightRange < 0) fightRange = 0;
                else if (fightRange > 8) fightRange = 8;
            }
            else
            {
                System.out.println("The enemy is unable to move!");
            }
            if (staminaRegenPaused > 0) {
                enemyStamina = Math.toIntExact(Math.round(tempVal));
                staminaRegenPaused -= 1;
            }
            turnResult();
            turn += 1;
            if (!hasShield && shieldLocation - fightRange == 0) {
                System.out.println("You picked up your shield!");
                hasShield = true;
            }
            if (fightRange < 0) fightRange = 0;
            if (fightRange > 8) fightRange = 8;
            if (playerHP <= 0 || enemyHP <= 0) {
                combatRecap();
                combatStopMusic();
                isFighting = false;
            }
        }
        combatStopMusic();
    }
    static void startCombat() throws InterruptedException {
        switch (enemyName) {
            case "Weak Swords-man" -> System.out.println("A weak swordsman attacks you!");
            case "Strong Swords-man" -> System.out.println("An experienced swordsman forces you into a duel...");
            case "Ninja Swords-man" -> System.out.println("You sense a swordsman flowing with power...");
            case "Weak Shields-man" -> System.out.println("A weak shieldsman attacks you!");
            case "Strong Shields-man" -> System.out.println("An experienced shieldsman forces you into a duel...");
            case "Ninja Shields-man" -> System.out.println("You sense a shieldsman flowing with power...");
            case "Strong Bowman" -> System.out.println("You get the strange feeling someone is aiming at you...");
            case "Bear" -> System.out.println("A deep growl sends shivers down your spine...");
            default -> System.out.println("The smell of smoke surrounds you...");
        }
                Thread.sleep(1000);
                System.out.println("Your power: " + Math.round(playerPower));
                System.out.println("Enemy power: " + Math.round(enemyPower));
                Thread.sleep(250);
                System.out.println("BEGINNING COMBAT");
                System.out.println("-----------------------------------------------------------");
        }
static void enemyStatSelector(int dmg, int dmg2, int hp, int spd, int spd2, int def, int def2) {
         genericRNG(dmg, dmg2);
         enemyDamage = rngVal;
         genericRNG(spd, spd2);
         enemySpeed = rngVal;
         genericRNG(def,def2);
         enemyDefense = rngVal;
    enemyHP = hp;
}
    static void enemyTypeSelector() {
        if (playerLocation.contains("Wildlands")) {
            genericRNG(1, 2);
            if (rngVal == 1) enemyName = "Weak Shields-man";
            else enemyName = "Weak Swords-man";
            switch (enemyName) {
                case "Weak Shields-man":
                    enemyStatSelector(55, (day * level) + 15, 135, 80, (day * level) + 20, 100, (day * level) + 20);
                    break;
                case "Weak Swords-man":
                    tempInt = (day * level) + 30;
                    enemyStatSelector(65, tempInt, 115, 65, tempInt, 60, tempInt);
                    break;
            }
        }
            else if (playerLocation.contains("Mountains")) {
                genericRNG(1,7);
            tempInt = Math.toIntExact(Math.round((day * level) * 1.25));
                switch (rngVal) {
                    case 2:
                        enemyName = "Bear";
                        enemyStatSelector(165, tempInt, 250, 150, tempInt, 105, tempInt);
                        break;
                    case 4:
                    case 5:
                        enemyName = "Strong Bowman";
                        enemyStatSelector(120, tempInt, 185, 75, tempInt, 105, tempInt);
                        break;
                    case 6:
                        enemyName = "Strong Shields-man";
                        enemyStatSelector(225, tempInt, 225, 130, tempInt, 145, tempInt);
                        break;
                    default: {
                        enemyName = "Strong Swords-man";
                        enemyStatSelector(145, tempInt, 150, 110, tempInt, 90, tempInt);
                    }
                }
            }
            else if (playerLocation.contains("Stronghold")) {
                genericRNG(1,9);
            tempInt = Math.toIntExact(Math.round((day*level)*1.5));
            switch (rngVal) {
                case 1:
                case 2:
                    enemyName = "Attack Dog";
                    enemyStatSelector(190, tempInt, 285, 180, tempInt, 135, tempInt);
                    break;
                case 4:
                case 5:
                    enemyName = "Ninja Archer";
                    enemyStatSelector(240, tempInt, 215, 155, tempInt, 130, tempInt);
                    break;
                case 6:
                case 7: {
                    enemyName = "Ninja Shields-man";
                    enemyStatSelector(210, tempInt, 300, 190, tempInt, 175, tempInt);
                    break;
                }
                case 8:
                    enemyName = "Oni";
                    enemyStatSelector(300, tempInt, 350, 215, tempInt, 190, tempInt);
                    break;
                default: {
                    enemyName = "Ninja Swords-man";
                    enemyStatSelector(265, tempInt, 240, 165, tempInt, 145, tempInt);
                }
            }
            /*
               case "Strong Swords-man":
                    tempVal = ((day * level) * 1.25);
                    enemyStatSelector(145, (int) tempVal, 150, 130, (int) tempVal, 90, (int) tempVal);
                      //  (int dmg, int dmg2, int hp, int spd, int spd2, int def, int def2)
                    break;
                    case "Bear":
                    enemyStatSelector(165, (int) ((day*level)*1.25), 250, 150, (int) ((day*level)*1.25), 105,  (int) ((day*level)*1.25));
                    break;
                    case "Strong Bowman": {
                        enemyStatSelector(120, (int) (day*level*1.25), 185, 75, (int) (day*level*1.25), 105, (int) (day*level*1.25));
                        break;
                    }
                    case "Strong Shields-man":
                    enemyStatSelector(225, (int) (day*level*1.25), 225, 110, (int) (day*level*1.25), 145, (int) (day*level*1.25));
                    break;
                }
             */
        }

              if (inDungeon) {
                  if (enemyName.contains("Sword")) dungeonStrengthen(15,3,3,3);
                  else if (enemyName.contains("Shield")) dungeonStrengthen(12,10,3,2);
                  else if (enemyName.contains("Bow")) dungeonStrengthen(20,2,4,4);
                  else if (enemyName.contains("Bear")) dungeonStrengthen(17,3,4,5);
                  else if (enemyName.contains("Ninja")) dungeonStrengthen(16,6.666,2.5,5);
              }
            //sound effects

            if (enemyName.contains("Sword")) combatSoundEffect(4);
            enemyPower = (enemyHP + enemyDamage + enemySpeed + enemyDefense) / 4;
            if (enemyName.contains("Sword") || enemyName.contains("Shield")) enemyType = "Melee Human";
            else if (enemyName.contains("Bow")) enemyType = "Range Human";
            else if (enemyName.contains("Bear")) enemyType = "Beast";
    }

    static void setEmotion(String emotion, int rage, int ego, int fear) {
         enemyBehaviour = emotion;
         enemyRage = rage;
         enemyEgo = ego;
         enemyFear = fear;
    }
    static void dungeonStrengthen(double hp, double dmg, double spd, double def) {
        enemyHP += (double) Dungeon.dungeonPower/hp;
        enemyDamage += (double) Dungeon.dungeonPower/dmg;
        enemySpeed += (double) Dungeon.dungeonPower/spd;
        enemyDefense += (double) Dungeon.dungeonPower/def;
    }
    static void enemyPlaystyle() {
        if (enemyType.contains("Melee Human")) {
            if (enemyName.contains("Weak")) genericRNG(1, 2);
            else if (enemyName.contains("Strong")) genericRNG(1, 4);
            else if (enemyName.contains("Ninja")) genericRNG(1, 5);
            switch (rngVal) {
                case 1 -> setEmotion("Angry", 80, 10,10);
                case 2 -> setEmotion("Fearful", 10, 10, 80);
                case 4 -> setEmotion("Overconfident", 10, 80, 10);
                case 5 -> setEmotion("Cautious", 20, 30, 50);
                default -> setEmotion("Focused", 33,33,33);
            }
        }
        else if (enemyType.contains("Beast")) {
            if (enemyName.contains("Bear")) genericRNG(1,2);
            else genericRNG(1,3);
                if (rngVal == 1) setEmotion("Cautious", 20, 30, 50);
                else if (rngVal == 4) setEmotion("Focused", 33,33,33);
                else setEmotion("Angry", 80, 10,10);
            }
        }
     static void turnLoop() throws InterruptedException {
        //This just prints all the data the player needs to make their decisions.
        System.out.println("Your health: " + Math.round(playerHP));
        System.out.println("Your stamina: " + playerStamina + "%");
        if (!weather.contains("Duststorm"))/*weather 3  is duststorm, which blinds you */ {
            System.out.println("Enemy health: " + Math.round(enemyHP));
            System.out.println("Enemy stamina: " + enemyStamina + "%");
            System.out.println("Distance: " + fightRange);
        }
        else System.out.println("The storm blinds you, not allowing you to see your opponent...");
         System.out.println("Enemy is " + enemyBehaviour);
        System.out.println("Super: " + superMeter + "%");
        System.out.println("Turn: " + turn);
        Thread.sleep(500);
        System.out.println("1: Select Attack");
        System.out.println("2: Move ");
        System.out.println("3: Block ");
        System.out.println("4: Utility Items");
        System.out.println("5: Wait");
        System.out.println("6: Win Button");
        playerHasWhiffed = false;
        enemyHasWhiffed = false;
        playerIsBlocking = false;
        enemyIsBlocking = false;
    }
    static void playerWait() {
        //Good for getting some stamina back fast.
        System.out.println("Press 1 to confirm you are waiting, or press another number to change your action.");
        playerResponse();
        if (answer == 1)
        {
            playerStamina += 20;
            playerIsBlocking = false;
            playerIsAttacking = false;
            playerHasWhiffed = false;
            moveName = "Wait";
            hasConfirmedCombat = true;
        }
        else System.out.println("You did not confirm.");
    }

    static void playerUniversalOptions(char option) {
        playerIsAttacking = false;
        playerIsBlocking = false;
        playerHasWhiffed = false;
        switch (option) {
            case 'F' -> {
                moveName = "Walk Forward";
                playerStamina += 10;
            }
            case 'B' -> {
                moveName = "Walk Backwards";
                playerStamina += 5;
            }
            case 'G' -> {
                playerIsBlocking = true;
                moveName = "Block";
            }
            case 'W' -> {
                enemymoveName = "Wait";
                playerStamina += 20;
                if (playerStamina > 100) playerStamina = 100;
            }
        }
        hasConfirmedCombat = true;
    }

    static void playerMove() throws InterruptedException {
        //Reposition to set up a sweet spot or to get to a safe range to wait.
        playerIsBlocking = false;
        playerIsAttacking = false;
        hasConfirmedMove = false;
        while (!hasConfirmedMove) {
            System.out.println("Press A to move backwards, press D to move forwards, or press 0 to go back.");
            answerType = KB.next();
            while (answerType.length() > 1) {
                System.out.println("Too many characters. Try again.");
                answerType = KB.next();
            }
            if (answerType.contains("0")) hasConfirmedMove = true;
            if (answerType.contains("A") || answerType.contains("a")) {
                {
                    if ((fightRange + 1) > 8) {
                        System.out.println("You cannot move further back. Please re-input your movement option.");
                        fightRange -=1;
                    } else playerUniversalOptions('B');
                }
            }
           else if (answerType.contains("D") || answerType.contains("d")) {
                if ((fightRange - 1) < 0) {
                    System.out.println("You cannot move into your opponent. Please re-input your movement option.");
                } else playerUniversalOptions('F');
            }
            else {
                System.out.println("You did not confirm.");
                Thread.sleep(500);
            }
        }
    }

    static void playerBlock() {
        //Alternative to wait to get some stamina back at the cost of hp.
        System.out.println("Press 1 to confirm block, or 0 to go back.");
        playerResponse();
        if (answer != 0) playerUniversalOptions('G');
    }
    static void universalMoves() {
        //Right now it's only shove, but any move that's possible by any weapon will be listed here
        if (tempMove.contains("Shove")) {
            moveName = tempMove;
            moveRange = 1;
            moveDamage = (attackDamage * 0.25) * (100 / (100 + enemyDefense));
            moveSpeed = (attackSpeed * 1.2);
            playerIsAttacking = true;
            CC += "Boop";
            ccAmount = 1;
            hasConfirmedAtk = true;
            hasConfirmedCombat = true;
    playerStamina -= staminaCost;
        }
    }
    static void getMove() {
        System.out.println("Select your move using the corresponding number: ");
        for (int count = 1; count < 6; count++) {
            System.out.println(count + ": " + moveSet.get("Move " + count) + ".");
        }
        System.out.println("Or press 0 to go back.");
        playerResponse();
        if (answer != 0) tempMove = moveSet.get("Move " + answer);
        else hasConfirmedAtk = true;
        while (tempMove == null || answer > 5) {
            System.out.println("Invalid response. Please try again.");
            playerResponse();
            tempMove = moveSet.get("Move " + answer);
        }
    }
    
    static void enemyUniversalOptions(char option) {
        enemyIsAttacking = false;
        enemyHasWhiffed = false;
        switch (option) {
            case 'F' ->  {
                enemymoveName = "Walk Forward";
                enemyStamina += 10;
                combatSoundEffect(11);
            }
            case 'B' -> {
                enemymoveName = "Walk Backwards";
                enemyStamina += 5;
                combatSoundEffect(11);
            }
            case 'W' -> {
                enemymoveName = "Wait";
                enemyStamina += 20;
            }
            case 'G' -> {
                enemymoveName = "Block";
                enemyIsBlocking = true;
            }
        }
        enemyConfirm = true;
    }

    static void swordSelection() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        /* Sword Movelist:
        Forward Swipe
        Downward Slice
        Pommel
        Shove (Universal)
        Thrust
        Phantom Slash (Super)
        Whirlwind
        Rising Upper
        Vanishing Strike
        Ki Charge
         */
        hasConfirmedAtk = false;
        while (!hasConfirmedAtk) {
            if (swordCharged) System.out.println("Your sword is charged!");
           getMove();
            if (tempMove.contains("Forward Swipe")) {
                staminaCost = 20;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Swings the sword forward, hitting anyone caught in the way.");
                moveDescription = "Swings the sword forward, hitting anyone caught in the way.";
                printMoveInfo(2,0.75, "Medium");
                playerResponse();
                if (answer == 1) executeMove(0.75,2,0.5,"",0,0,0);
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Downward Slice")) {
                staminaCost = 30;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Slashes at your opponent's legs.");
                moveDescription = "Slashes at your opponent's legs.";
                printMoveInfo( 2, 0.7, "Medium + ");
                playerResponse();
                if (answer == 1) executeMove(0.7,2,0.65,"",0,0,0);
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Pommel")) {
                staminaCost = 45;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Quickly smashes the hilt of your sword against your opponent.");
                moveDescription = "Quickly smashes the hilt of your sword against your opponent.";
                //(int range, double speed, String damageType,
                printMoveInfo(1, 1, "Low");
                playerResponse();
                if (answer == 1) executeMove(1,2,0.35,"",0,0,0);
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Shove")) {
                System.out.println("Pushes the enemy backwards and creates some space.");
                moveDescription = "Pushes the enemy backwards and creates some space.";
                //(int range, double speed, String damageType,
                printMoveInfo(1, 1.2, "Very Low");
                playerResponse();
                if (answer == 1) universalMoves();
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Thrust")) {
                staminaCost = 30;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Dash forward and poke with the very edge of your sword.");
                moveDescription = "Dash forward and poke with the very edge of your sword.";
                printMoveInfo(4, 0.4, "High");
                playerResponse();
                if (answer == 1) executeMove(0.4,4,1.1,"",0,0,0);
                 else hasConfirmedAtk = true;
            } else if (tempMove.contains("Phantom Slash") && hasSuper) {
                printMoveInfo(3, 1.2, "Extremely High");
                playerResponse();
                if (answer == 1) {
                    tempVal2 = (attackDamage * 1.7);
                    if (tempMove.contains("2")) {
                        tempVal = 200 + (level*50);
                        tempVal2 += 1.5*(tempVal - playerHP);
                    }
                    superMeter = 0;
                    executeMove(1.2,3,tempVal2,"",0,0,0);
                } else hasConfirmedAtk = true;

            } else if (tempMove.contains("Phantom Slash") && !hasSuper) System.out.println("You don't have your super yet.");
            else if (tempMove.contains("Ki Charge")) {
                staminaCost = 0;
                System.out.println("Powers up the blade, giving you extra range, and bonus damage if spaced.");
                moveDescription = "Powers up the blade, giving you extra range, and bonus damage if spaced.";
                printMoveInfo(0,0, "N.A");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    playerIsAttacking = false;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    swordCharged = true;
                    combatSoundEffect(9);
                } else hasConfirmedAtk = true;
            } else if (tempMove.contains("Whirlwind")) {
                staminaCost = 45;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("A giant swinging attack that deals massive stamina damage.");
                printMoveInfo(3, 0.45, "High");
                playerResponse();
                if (answer == 1) executeMove(0.5,3,0.9,"",0,0,0);
                else hasConfirmedAtk = true;
            }
            else if (tempMove.contains("Rising Upper")) {
                staminaCost = 40;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Moves forward and slashes upwards, knocking the opponent back.");
                moveDescription = "Moves forward and slashes upwards, knocking the opponent back.";
                printMoveInfo(2, 0.6, "Medium");
                playerResponse();
                if (answer == 1) {
            if (tempMove.contains("2")) CC = "Boop, Stun";
            else CC = "Boop";
            executeMove(0.6,2,0.7,answerType,0,0, 0);
                } else hasConfirmedAtk = true;
            }
            else if (tempMove.contains("Vanishing Strike")) {
                staminaCost = 35;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Teleport to a location and slash your opponent.");
                moveDescription = "Teleport to a location and slash your opponent.";
                printMoveInfo(1,0.8, "Medium +");
                playerResponse();
                if (answer == 1) {
                    System.out.println("Input new distance.");
                    boolean tempConfirm = false;
                    while (!tempConfirm)
                    {
                        playerResponse();
                        if (answer >= 8 || answer < 0) System.out.println("Must be between 8 and 0.");
                        else tempConfirm = true;
                    }
                    fightRange = answer;
                    executeMove(0.8,1,0.6,"",0,0,0);
                } else hasConfirmedAtk = true;

            }
        }
        //Strike sfx
        //I check for these moves because they don't involve the blade of the sword, so no blade sfx.
        if (playerIsAttacking && !tempMove.contains("Shove") && !tempMove.contains("Ki Charge") && !tempMove.contains("Pommel")){
            genericRNG(5, 2);
            combatSoundEffect(rngVal);
        }
    }

    static void shieldSelection() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        /* Shield Movelist
        Bash
        Uppercut
        Slam
        Shove (Universal)
        Shield Punch
        Shield Sweep
        Earthquake
        Swap Hands
          - Tai Otoshi (Off Hand)
          - Retreating Strike (Dominant Hand)
         Deflect
         Shield Toss
         */
        if (!hasShield) System.out.println("You don't have your shield.");
        playerIsBlocking = false;
        hasConfirmedAtk = false;
        while (!hasConfirmedAtk) {
            getMove();
            if (!hasShield) {
                if (tempMove.contains("Bash") || tempMove.contains("Earthquake") || tempMove.contains("Deflect") || tempMove.contains("Swap")) {
                    System.out.println("You don't have your shield, so you can't use " + tempMove);
                    break;
                }
            }
            if (tempMove.contains("Bash")) {
                staminaCost = 15;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Dash forward and tackle the opponent with your shield.");
                moveDescription = "Dash forward and tackle the opponent with your shield.";
                printMoveInfo(2, 0.65, "Medium");
                playerResponse();
                if (answer == 1) executeMove(0.65,2,0.55,"",0,0,0);
                 else hasConfirmedAtk = true;
            } else if (tempMove.contains("Uppercut")) {
                staminaCost = 20;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Dash up and launch your opponent into the air with a powerful punch to the jaw.");
                moveDescription = "Dash up and launch your opponent into the air with a powerful punch to the jaw.";
                printMoveInfo(2, 0.65, "Medium");
                playerResponse();
                if (answer == 1) executeMove(0.65,1,0.6,"Boop",0,0,0);
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Slam")) {
                staminaCost = 35;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Jump up and smash your shield against the ground. Bonus damage if 2 spaces away.");
                moveDescription = "Jump up and smash your shield against the ground. Bonus damage if 2 spaces away.";
                printMoveInfo(2,0.7, "High");
                playerResponse();
                if (answer == 1) executeMove(0.7,2,1.1,"",0,0,0);
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Shove")) {
                staminaCost = 0;
                System.out.println("Pushes the enemy back and creates some space.");
                moveDescription = "Pushes the enemy back and creates some space.";
                printMoveInfo(1, 1.2, "Very Low");
                playerResponse();
                if (answer == 1) {
                    universalMoves();
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                }
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Shield Punch")) {
                staminaCost = 40;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Punch the back of your shield, sending a shockwave that knocks enemies backwards and lowers their stamina.");
                moveDescription = "Punch the back of your shield, sending a shockwave that knocks enemies backwards and lowers their stamina.";
                printMoveInfo(2, 0.6, "High");
                playerResponse();
                if (answer == 1) executeMove(0.6,2,0.9,"Boop",0,25,0);
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Shield Sweep")) {
                staminaCost = 35;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Spin around, striking your opponents ankles, lowering their stamina heavily.");
                moveDescription = "Spin around, striking your opponents ankles, lowering their stamina heavily.";
                printMoveInfo(2, 0.4, "Medium+");
                playerResponse();
                if (answer == 1) executeMove(0.4,2,0.7,"",0,65,0);
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Earthquake") && hasSuper) {
                System.out.println("Ultimate: Slams the shield into the ground, stunning the enemy.");
                moveDescription = "Ultimate: Slams the shield into the ground, stunning the enemy.";
                printMoveInfo(3, 0.65, "Extremely High");
                playerResponse();
                if (answer == 1) {
                    executeMove(0.65,3,1.6,"",0,85,0);
                    superMeter = 0;
                }
                else hasConfirmedAtk = true;
            }
            else if (tempMove.contains("Earthquake") && !hasSuper) System.out.println("You don't have your super yet.");
            else if (tempMove.contains("Swap Hands")) {
                if (shieldHand.contains("O")) System.out.println("Current: Off hand.");
                else if (shieldHand.contains("D")) System.out.println("Current: Dominant hand.");
                Thread.sleep(500);
                System.out.println("1: Stance Change");
                if (tempMove.endsWith("2")) System.out.println("2: Stance Move");
                playerResponse();
                if (answer == 2 && tempMove.endsWith("2")) {
                    if (shieldHand.contains("O")) {
                        staminaCost = 25;
                        if (staminaCost >= playerStamina ) {
                            System.out.println("Not enough stamina.");
                            break;
                        }
                        System.out.println("Grabs the opponent, throwing them down to the ground. Ignores block.");
                        moveDescription = "Grabs the opponent, throwing them down to the ground. Ignores block.";
                        moveName = "Tai Otoshi";
                        printMoveInfo(1, 0.8, "High");
                        playerResponse();
                        if (answer == 1) executeMove(0.8,1,0.8,"",0,0,0);
                        else hasConfirmedAtk = true;
                    } else if (shieldHand.contains("D")) {
                        staminaCost = 25;
                        if (staminaCost >= playerStamina ) {
                            System.out.println("Not enough stamina.");
                            break;
                        }
                        System.out.println("Dodge backwards, before kicking your opponent.");
                        moveDescription = "Dodge backwards, before kicking your opponent.";
                        moveName = "Retreating Strike";
                        printMoveInfo(3, 0.7, "Medium");
                        playerResponse();
                        if (answer == 1) executeMove(0.7,3,0.6,"",0,0,0);
                    }
                } else if (answer == 2) System.out.println("You do not have the required move.");
                else {
                    System.out.println("Off hand: Deals bonus damage. Dominant hand: Increased speed.");
                    printMoveInfo(0,0, "N.A");
                    playerResponse();
                    if (answer == 1) {
                        moveName = "";
                        if (shieldHand.contains("D")) shieldHand = "O";
                        else shieldHand = "D";
                        playerIsAttacking = false;
                        hasConfirmedAtk = true;
                        hasConfirmedCombat = true;
                    } else hasConfirmedAtk = true;
                }
            }
            else if (tempMove.contains("Deflect")) {
                staminaCost = 55;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Parries the opponent's strike, stunning them.");
                moveDescription = "Parries the opponent's strike, stunning them.";
                printMoveInfo(0,0,"N.A");
                playerResponse();
                if (answer == 1) executeMove(0.8,2,0,"",0,0,0);
                else hasConfirmedAtk = true;
        }
     else if (tempMove.contains("Shield Toss")) {
                staminaCost = 30;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Spin around, striking your opponents ankles, lowering their stamina heavily.");
                moveDescription = "Spin around, striking your opponents ankles, lowering their stamina heavily.";
                printMoveInfo(5, 0.9/fightRange, "Medium (depends on distance)");
                playerResponse();
                if (answer == 1) {
                    hasShield = false;
                    executeMove(0.9/fightRange,5,0.7 - (attackDamage /fightRange), "",0,0,0);
                } else hasConfirmedAtk = true;
            }
    }
    }

    static void axeSelection() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        /* Axe Movelist
        Forward Slash
        Shatter
        Butcher
        Shove (Universal)
        Skewer
        Eradicate (Universal) (Install)
        Ignite
        Weaken
        Pyroslash
        Overhead Cleave
         */
        playerIsBlocking = false;
        hasConfirmedAtk = false;
        while (!hasConfirmedAtk) {
          getMove();
            if (tempMove.contains("Forward Slash")) {
                staminaCost = 20;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Swings the axe forward, hitting anyone caught in the way.");
                moveDescription = "Swings the axe forward, hitting anyone caught in the way.";
                printMoveInfo(2, 0.7, "Medium");
                playerResponse();
                if (answer == 1) executeMove(0.7,2,0.55,"",0,0,0);
                 else hasConfirmedAtk = true;
            } else if (tempMove.contains("Shatter")) {
                staminaCost = 35;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Plunges the blunt side of the axe into your opponent, doing major damage and knocking the enemy backwards.");
                moveDescription = "Plunges the blunt side of the axe into your opponent, doing major damage and knocking the enemy backwards.";
                printMoveInfo(2, 0.6, "High");
                playerResponse();
                if (answer == 1) executeMove(0.35,2,1.1,"Boop",2,0,0);
                 else hasConfirmedAtk = true;
            } else if (tempMove.contains("Butcher")) {
                staminaCost = 55;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Uses the axe like a knife for quick slashes.");
                moveDescription = "Uses the axe like a knife for quick slashes.";
                printMoveInfo(1,0.8, "Medium");
                playerResponse();
                if (answer == 1) executeMove(0.8,1,0.7,"",0,0,0);
                 else hasConfirmedAtk = true;

            } else if (tempMove.contains("Shove")) {
                System.out.println("Pushes the enemy backwards and creates some space.");
                moveDescription = "Pushes the enemy backwards and creates some space.";
                printMoveInfo(1,1.2, "Very Low");
                playerResponse();
                if (answer == 1) universalMoves();
                 else hasConfirmedAtk = true;
            } else if (tempMove.contains("Skewer")) {
                staminaCost = 40;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Drags the opponent closer to you.");
                printMoveInfo(3, 0.65, "Low+");
                playerResponse();
                if (answer == 1) executeMove(0.65,3,0.4,"Suck", 1,0,0);
                 else hasConfirmedAtk = true;
            } else if (tempMove.contains("Eradicate") && hasSuper) {
                System.out.println("Ultimate: Gives you better ignite for 5 turns, giving bonus burn,armor, as well as stun on moves with cc.");
                moveDescription = "Ultimate: Gives you better ignite for 5 turns, giving bonus burn,armor, as well as stun on moves with cc.";
                printMoveInfo(0,0, "N.A");
                playerResponse();
                if (answer == 1) {
                    moveName = "Eradicate";
                    playerIsAttacking = false;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    superMeter = 0;
                    eradicateTurns = 5;
                } else hasConfirmedAtk = true;
            } else if (tempMove.contains("Ignite")) {
                System.out.println("The next strike does burn damage.");
                moveDescription = "The next strike does burn damage.";
                printMoveInfo(0,0, "N.A");
                playerResponse();
                if (answer == 1) {
                    moveName = "Ignite";
                    playerIsAttacking = false;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    isIgnited = true;
                    playerStamina -= staminaCost;
                    soundEffect(17);
                } else hasConfirmedAtk = true;
            } else if (tempMove.contains("Overhead Cleave")) {
                staminaCost = 40;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Swings downward at your opponent, slowing the opponent on hit.");
                moveDescription = "Swings downward at your opponent, slowing the opponent on hit.";
                printMoveInfo(2, 0.55, "High");
                playerResponse();
                if (answer == 1) executeMove(0.55,2,0.8,"Weaken Speed", 2, 0, 0);
                else hasConfirmedAtk = true;
            } else if (tempMove.contains("Weaken")) {
                staminaCost = 35;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Bashes your opponent's head with the blunt side of the axe, lowering the opponent's damage.");
                moveDescription = "Bashes your opponent's head with the blunt side of the axe, lowering the opponent's damage.";
                printMoveInfo(2, 0.6, "Medium");
                playerResponse();
                if (answer == 1) executeMove(0.6,2,0.4,"Weaken Damage",2,0,0);
                 else hasConfirmedAtk = true;
            } else if (tempMove.contains("Pyroslash")) {
                staminaCost = 35;
            if (staminaCost >= playerStamina ) {
                System.out.println("Not enough stamina.");
                break;
            }
            System.out.println("A slow flame wave that ignites the opponent on hit.");
            moveDescription = "A slow flame wave that ignites the opponent on hit.";
            Thread.sleep(500);
            System.out.println("Press 1 to confirm, or press another to reselect.");
            printMoveInfo(8, 0.6, "Medium");
            playerResponse();
            if (answer == 1) {
                moveName = "Pyroslash";
                moveSpeed = (attackSpeed * 0.6);
                playerIsAttacking = true;
                hasConfirmedAtk = true;
                hasConfirmedCombat = true;
                playerStamina -= staminaCost;
            }
            else hasConfirmedAtk = true;
        }
        }
    }

     protected static void printMoveInfo(int range, double speed, String damageType) throws InterruptedException {
         System.out.println(tempMove);
         System.out.println("Range: " + range);
         System.out.println("Speed: " + Math.round(attackSpeed*speed));
         System.out.println("Damage: " + damageType);
         System.out.println("Cost: " + staminaCost + "% Stamina");
         Thread.sleep(500);
         System.out.println("Press 1 to confirm, or press another to reselect.");
     }

     protected static void executeMove(double speed, int range, double damage, String crowdControl, int ccNum, int stamDMG, int playSFX) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        moveName = tempMove;
        moveSpeed = speed;
        moveRange = range;
        moveDamage = (attackDamage*damage) *(100/(100 + attackDefense));
        CC += crowdControl;
        ccAmount = ccNum;
        playerStamina -= staminaCost;
        staminaDamage = stamDMG;
        if (playSFX !=0) combatSoundEffect(playSFX);
        playerIsAttacking = true;
        playerIsBlocking = false;
        hasConfirmedAtk = true;
        hasConfirmedCombat = true;
    }

    protected static void winButton() {
         moveSpeed = 100000;
         moveRange = 20;
         moveDamage = enemyHP*3;
         playerIsAttacking = true;
         hasConfirmedCombat = true;
         moveName = "Dev Powers";
    }

    protected static void executeMoveEnemy(String enemyAction, double speed, int range, double damage, String crowdControl, int ccNum, int stamDMG, int playSFX, int stamCost) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
         enemymoveName = enemyAction;
         enemymoveSpeed = enemySpeed*speed;
        enemymoveRange = range;
        enemymoveDamage = enemyDamage*damage * (100/(100 + playerDefense));
        enemyCC += crowdControl;
        enemyCCAmount = ccNum;
        enemyStamina -= staminaCost;
        enemyStaminaDamage = stamDMG;
        if (playSFX !=0) combatSoundEffect(playSFX);
        enemyIsAttacking = true;
        enemyStamina -= stamCost;
        enemyConfirm = true;
    }


    static void utilityItem() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        /*
        Right now the utility list is this:
        Grappling Hook: +1 range, -20% speed
        Regenerative Stone: 20% heal
        Soul Snatcher: ~15% of opponent's HP (needs to be looked at, seems broken)
         */
        System.out.println("Select your utility, or press 0 to leave.");
            if (grapplingHook > 0) System.out.println("1: Grappling Hook");
            if (regenerativeStone > 0) System.out.println("2: Regenerative Stone");
            if (soulSnatcher > 0) System.out.println("3: Soul Snatcher");
            if (smokeBombs > 0) System.out.println("4: Smoke Bomb");
            if (cycloneStraws > 0) System.out.println("5: Cyclone Straw");
            if (magmaWhistle > 0) System.out.println("6: Magma Whistle");
            /*
                System.out.println("7. Poison Dart: Deal a small percentage of damage over time.");
                    System.out.println("8. Insanity Curse: Instantly cause your opponent to become angry.");
                    System.out.println("9. Enlargement Flask: Gain super armor on your next attack.");
             */
            if (poisonDart > 0) System.out.println("7. Poison Dart");
            if (insanityCurses > 0) System.out.println("8. Insanity Curse");
            if (enlargementFlask > 0) System.out.println("9. Enlargement Flask");
            playerResponse();
            switch (answer) {
                case 1 ->{
                    System.out.println("You are using grappling hook. Press 1 to confirm, or press another number to change.");
                    playerResponse();
                    if (answer == 1) {
                        moveName = "Grappling Hook";
                        grapplingHook -= 1;
                        hasGrapplingHook = true;
                        hasConfirmedCombat = true;
                    }
                    else System.out.println("You did not confirm.");
                }
                case 2 -> {
                    System.out.println("You are using a regenerative stone. Press 1 to confirm, or press another number to change.");
                    playerResponse();
                    if (answer == 1) {
                        moveName = "Regenerative Stone";
                        regenerativeStone -= 1;
                        tempVal2 = playerHP;
                        tempVal = playerHP / 5;
                        playerHP += tempVal;
                        System.out.println("You recovered " + (playerHP - tempVal2) + " hp!");
                        hasConfirmedCombat = true;
                    }
                    else System.out.println("You did not confirm.");
                }
                case 3 -> {
                    System.out.println("You are using a Soul Snatcher. Press 1 to confirm, or press another number to change.");
                    playerResponse();
                    if (answer == 1) {
                        moveName = "Soul Snatcher";
                        soulSnatcher -= 1;
                        tempVal2 = enemyHP;
                        tempVal = enemyHP / 6;
                        enemyHP -= tempVal;
                        System.out.println("You removed " + (tempVal2 - enemyHP) + " hp!");
                        hasConfirmedCombat = true;
                        //might rework this into a move... seems kinda broken LMAO
                    }
                    else System.out.println("You did not confirm.");
                }
                case 4 -> {
                    System.out.println("You are using a smoke bomb. Press 1 to confirm, or press another number to change.");
                    playerResponse();
                    if (answer == 1) {
                        System.out.println("Input new distance.");
                        boolean tempConfirm = false;
                        playerResponse();
                        while (!tempConfirm) {
                            if (answer < 0 || answer > 8) System.out.println("Invalid response. Please enter a number between 1 and 8.");
                            else {
                                fightRange = answer;
                                tempConfirm = true;
                                smokeBombs -= 1;
                                hasConfirmedCombat = true;
                            }
                        }
                    }
                    else System.out.println("You did not confirm.");
                }
                case 5 -> {
                    System.out.println("You are using a cyclone straw. Press 1 to confirm, or press another number to change.");
                    playerResponse();
                    if (answer == 1) {
                        moveName = "Cyclone Straw";
                        enemyHP -= 2*fightRange;
                        fightRange = 0;
                        cycloneStraws -= 1;
                        hasConfirmedCombat = true;
                    }
                    else System.out.println("You did not confirm.");
                }
                case 6 -> {
                    System.out.println("You are using a magma whistle. Press 1 to confirm, or press another number to change.");
                    playerResponse();
                    if (answer == 1) {
                        tempMove = "Magma Whistle";
                        magmaWhistle -= 1;
                        executeMove(1.8,4,0.1,"Boop",4,0,0);
                    }
                    else System.out.println("You did not confirm.");
                }
                case 7 -> {
                      /*
                             System.out.println("7. Poison Dart: Deal a small percentage of damage over time.");
                    System.out.println("8. Insanity Curse: Instantly cause your opponent to become angry.");
                    System.out.println("9. Enlargement Flask: Gain super armor on your next attack.");
                             */

                    System.out.println("You are using a poison dart. Press 1 to confirm, or press another number to change.");
                    playerResponse();
                    if (answer == 1) {
                        tempMove = "Poison Dart";
                        poisonDart -= 1;
                        DOTDuration = 3;
                        DOTDamage = attackDamage*0.05;
                        executeMove(1.7, 5,10, "DOT", 0,20,0);
                    }
                    else System.out.println("You did not confirm.");
                }
                case 8 -> {
                    System.out.println("You are using a insanity curse. Press 1 to confirm, or press another number to change.");
                    playerResponse();
                    if (answer == 1) {
                        tempMove = "Insanity Curse";
                        insanityCurses -= 1;
                        enemyRage = 100;
                        hasConfirmedCombat = true;
                    }
                    else System.out.println("You did not confirm.");
                }
                case 9 -> {
                    System.out.println("You are using an enlargement flask. Press 1 to confirm, or press another number to change.");
                    if (answer == 1) {
                        tempMove = "Enlargement Flask";
                        enlargementFlask -= 1;
                        hasArmor = true;
                        armorDuration = 2;
                        hasConfirmedCombat = true;
                    }
                }
                default -> {System.out.println("You did not confirm.");}
            }
    }

    static void enemyEmotion() {
         if (enemyEgo > (enemyRage + enemyFear)) {
             enemyBehaviour = "Overconfident";
             enemyEgo = 80;
             enemyFear = 10;
             enemyRage = 10;
         }
         else if (enemyRage > (enemyEgo + enemyFear)) {
             enemyBehaviour = "Angry";
             enemyRage = 80;
             enemyEgo = 10;
             enemyFear = 10;
         }
         else if (enemyFear > (enemyEgo + enemyEgo)) {
             enemyBehaviour = "Fearful";
             enemyFear = 80;
             enemyEgo = 10;
             enemyRage = 10;
         }
         else if (enemyFear + enemyRage + enemyEgo % 3 <= 2) {
             enemyBehaviour = "Focused";
             enemyEgo = 33;
             enemyRage = 33;
             enemyFear = 33;
         }
         else if (enemyFear - (enemyRage + enemyEgo) >= -3) {
             enemyBehaviour = "Cautious";
             enemyFear = 50;
             enemyEgo = 30;
             enemyRage = 20;
         }
    }

    static void humanEmotion() {
        genericRNG(1,99);
        switch (enemyBehaviour) {
            case "Angry" -> {
                if (rngVal >= 73 && rngVal <= 87) moveDecider = 2;
                else if (rngVal >= 88) moveDecider = 3;
                else moveDecider = 1;
            }
            case "Fearful" -> {
                if (rngVal >= 64 && rngVal <= 87) moveDecider = 3;
                else if (rngVal >= 88) moveDecider = 1;
                else moveDecider = 3;
            }
            case "Overconfident" -> {
                if (rngVal >= 69 && rngVal <= 83) moveDecider = 1;
                else if (rngVal >= 84) moveDecider = 2;
                else moveDecider = 3;
            }
            case "Cautious" -> {
                if (rngVal >= 34 && rngVal <= 69) moveDecider = 2;
                else if (rngVal >= 70) moveDecider = 3;
                else moveDecider = 1;
            }
            case "Focused" -> {
                genericRNG(1,2);
                moveDecider = rngVal;
            }
        }
        if (enemyWillAttack) moveDecider = 1;
        else if (enemyWillDefend) moveDecider = 2;
        else if (enemyWillMove) moveDecider = 3;
    }

    static void beastEmotion() {
        switch (enemyBehaviour) {
            case "Angry" -> {
                if (rngVal >= 73 && rngVal <= 87) moveDecider = 2;
                else if (rngVal >= 88) moveDecider = 3;
                else moveDecider = 1;
            }
            case "Cautious" -> {
                if (rngVal >= 34 && rngVal <= 69) moveDecider = 2;
                else if (rngVal >= 70) moveDecider = 3;
                else moveDecider = 1;
            }
        }
    }

    static void swordsmanAI() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
         /*
         here's how AI works:
         first, we get emotion
         the emotion influences the odds of either attack, move, or defend
         1 = attack
         2 = defend
         3 = move
          then it does a 2nd rng to determine which specific move to do.
          */
        humanEmotion();
        while (!enemyConfirm) {
            switch (moveDecider) {
                case 1 -> {
                    if (enemyName.contains("Weak Swords-man")) genericRNG(1, 4);
                    else if (enemyName.contains("Strong")) genericRNG(1, 5);
                    else genericRNG(1, 6);
                    rngbaddie = rngVal;
                    if (fightRange >= 2) {
                        if (rngbaddie == 1 || rngbaddie == 2) {
                            if (enemyStamina > 20) executeMoveEnemy("Forward Swipe",0.75, 2, 0.7, "", 0, 0, 0, 20);
                        }
                       else if (rngbaddie == 3) enemyUniversalOptions('F');
                       else if (rngbaddie == 4) {
                            if (enemyStamina > 25) executeMoveEnemy("Stab", 0.9, 2, 0.65, "", 0, 0, 0, 25);
                        }
                       else if (rngbaddie == 5) {
                            if (enemyStamina > 45) executeMoveEnemy("Whirlwind", 0.4, 3, 1.1, "", 0, 70, 0, 45);
                        }
                       else if (rngbaddie == 6) {
                            if (enemyStamina >= 40) executeMoveEnemy("Ki Slash", 0.7, 3, 0.6, "", 0, 0, 9, 40);
                        }
                       else if (rngbaddie == 7 && eSmokeBomb > 0) {
                           tempVal = 0;
                           enemymoveName = "Smoke Bomb";
                           do {
                               genericRNG(1,7);
                               tempVal++;
                           } while (rngVal <= fightRange || tempVal < 3);
                            fightRange = rngVal;
                           eSmokeBomb -= 1;
                           enemyConfirm = true;
                        }
                    } else {
                        if (enemyName.contains("Weak Swords")) genericRNG(1, 1);
                        else genericRNG(1, 4);
                        rngbaddie = rngVal;
                        if (rngbaddie == 1) enemyUniversalOptions('F');
                            else if (rngbaddie == 2) {
                            if (enemyStamina >= 45) executeMoveEnemy("Whirlwind",0.4, 3, 1.1, "", 0, 70, 0, 45);
                        }
                        else if (rngbaddie == 3 && !enemyName.contains("Weak") || rngbaddie == 4 && !enemyName.contains("Weak")) {
                            if (enemyStamina >= 40) executeMoveEnemy("Ki Slash", 0.7, 3, 0.6, "", 0, 0, 9, 40);
                        }
                        else if (rngbaddie == 5 && ePoisonDart > 0) {
                            tempVal = 0;
                            enemymoveName = "Poison Dart";
                            ePoisonDart -= 1;
                            enemyDOTDuration = 3;
                            enemyDOTDamage = enemyDamage*0.05;
                            executeMoveEnemy("Poison Dart", 1.7, 5,10, "DOT", 0,20,0, 0);
                        }
                    }
                }
                case 2 -> {
                    genericRNG(1,2);
                    rngbaddie = rngVal;
                    if (rngbaddie == 1) enemyUniversalOptions('B');
                    else enemyUniversalOptions('G');
                }
                case 3 -> {
                    genericRNG(1,3);
                    rngbaddie = rngVal;
                    if (rngbaddie == 1) enemyUniversalOptions('F');
                    else if (rngbaddie == 2) enemyUniversalOptions('B');
                    else enemyUniversalOptions('W');
                }
            }
        }
        if (enemyIsAttacking && !enemymoveName.contains("Shove") && !enemymoveName.contains("Ki Slash")) {
            //energy sfx
            genericRNG(5,2);
            combatSoundEffect(rngVal);
        }
    }


    static void shieldsmanAI() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        humanEmotion();
        while (!enemyConfirm) {
            switch (moveDecider) {
                case 1 -> {
                  if (enemyName.contains("Weak"))  genericRNG(1, 3);
                  else if (enemyName.contains("Strong")) genericRNG(1,4);
                  else if (enemyName.contains("Ninja")) genericRNG(1,6);
                    rngbaddie = rngVal;
                    if (rngbaddie == 1 || rngbaddie == 2) {
                        if (enemyStamina >= 15) executeMoveEnemy("Bash", 0.6, 2, 0.65, "", 0, 0, 0, 15);
                    }
                    else if (rngbaddie == 3) {
                        if (enemyStamina >= 35) executeMoveEnemy("Uppercut", 0.75, 1, 0.5, "", 0, 0, 0, 35);
                    }
                    else if (rngbaddie == 4) enemyUniversalOptions('F');
                    else if (rngbaddie == 5) {
                        if (enemyStamina >= 45) executeMoveEnemy("Grapple", 1.1, 1, 0.3, "", 0, 0, 0, 45);
                    }
                    else if (rngbaddie == 6 && eSmokeBomb > 0) {
                        tempVal = 0;
                        enemymoveName = "Smoke Bomb";
                        do {
                            genericRNG(1,7);
                            tempVal++;
                        } while (rngVal >= fightRange || tempVal < 5);
                        fightRange = rngVal;
                        eSmokeBomb -= 1;
                       if (tempVal < 5) enemyConfirm = true;
                    }
                    else if (rngbaddie == 7 && ePoisonDart > 0) {
                        tempVal = 0;
                        enemymoveName = "Poison Dart";
                        ePoisonDart -= 1;
                        enemyDOTDuration = 3;
                        enemyDOTDamage = enemyDamage*0.05;
                        executeMoveEnemy("Poison Dart", 1.7, 5,10, "DOT", 0,20,0, 0);
                    }
                }
                case 2 -> {
                    genericRNG(1, 3);
                    rngbaddie = rngVal;
                    if (rngbaddie == 1) enemyUniversalOptions('B');
                    if (rngbaddie == 2) enemyUniversalOptions('G');
                    else if (rngbaddie == 3) {
                        if (enemyStamina >= 25) executeMoveEnemy("Side Kick", 0.9, 3, 0.4, "Boop", 2, 30, 0, 25);
                    }
                    else if (rngbaddie == 4) {
                        tempVal = 0;
                        enemymoveName = "Smoke Bomb";
                        do {
                            genericRNG(1,7);
                            tempVal++;
                        } while (rngVal <= fightRange || tempVal < 5);
                        fightRange = rngVal;
                        eSmokeBomb -= 1;
                        if (tempVal < 3) enemyConfirm = true;
                    }
                }
                case 3 -> {
                    genericRNG(1, 3);
                    rngbaddie = rngVal;
                    if (rngbaddie == 1) enemyUniversalOptions('B');
                    if (rngbaddie == 2) enemyUniversalOptions('F');
                    if (rngbaddie == 3 || rngbaddie == 4) enemyUniversalOptions('W');
                }
            }
        }
    }
    static void bowmanAI() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
       humanEmotion();
       while (!enemyConfirm) {
        switch (moveDecider) {
         case 1 -> {
          if (enemyName.contains("Ninja")) genericRNG(1, 3);
          else genericRNG(1,2);
          rngbaddie = rngVal;
           if (rngbaddie == 1) {
           if (enemyStamina >= 20) executeMoveEnemy("Bow Swipe", 0.55, 2, 0.6, "", 0, 0, 0, 20);
             } else if (rngbaddie == 2) {
             if (enemyQuiver > 0) {
              enemyQuiver -= 1;
               executeMoveEnemy("Quick Shot", 0.65, 2, 0.5, "", 0, 5, 0, 0);
                } else {
                   System.out.println("The enemy is out of arrows!");
                   enemyHasWhiffed = true;
                   enemyWhiffedturns = 2;
                   }
                   } else if (rngbaddie == 3) {
                   if (enemyStamina >= 25) executeMoveEnemy("Side Kick", 0.85, 2, 0.35, "", 0, 0, 0, 25);
                   }
           else if (rngbaddie == 4) {
               DOTDuration = 1;
               DOTDamage = enemyDamage*0.15;
               if (enemyStamina >= 30) executeMoveEnemy("Arrow Stab", 0.6, 1,0.5,"DOT", 0, 35,0,30);
           }
                    }
                    case 2 -> {
                        if (fightRange >= 4) {
                                if (enemyQuiver > 0) {
                                    enemyQuiver -= 1;
                                    executeMoveEnemy("Straight Shot", 0.65, 5, 0.3, "", 0, 5, 0, 10);
                                }
                                else {
                                    System.out.println("The enemy is out of arrows!");
                                    enemyHasWhiffed = true;
                                    enemyWhiffedturns = 2;
                                }
                            }
                        else {
                            if (enemyName.contains("Ninja")) genericRNG(1,4);
                            else genericRNG(1, 3);
                            rngbaddie = rngVal;
                            if (rngbaddie == 1 || rngbaddie == 2 || rngbaddie == 3) enemyUniversalOptions('B');
                            else if (rngbaddie == 4) {
                                if (enemyQuiver > 0 && enemyStamina >= 40) {
                                    enemyQuiver -= 1;
                                    executeMoveEnemy("Retreating Shot", 0.45, 4, 0.25, "", 0, 0, 0, 40);
                                }
                            }
                            else enemyUniversalOptions('G');
                        }
                    }
                    case 3 -> {
                        genericRNG(1,4);
                        rngbaddie = rngVal;
                        if (rngbaddie == 1) enemyUniversalOptions('F');
                        else if (rngbaddie >= 2 && rngbaddie <= 4) enemyUniversalOptions('B');
                        else enemyUniversalOptions('W');
                    }
                }
            }
        }
    static void beastAI() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        beastEmotion();
        while (!enemyConfirm) {
            switch (moveDecider) {
                case 1 -> {
                    if (enemyName.contains("Bear")) genericRNG(1, 7);
                    else genericRNG(1, 8);
                    rngbaddie = rngVal;
                    if (fightRange <= 2) {
                        if (rngbaddie == 1 || rngbaddie == 2) {
                            if (enemyStamina >= 15) executeMoveEnemy("Claw Strike", 0.75, 2, 0.65, "", 0, 0, 0, 15);
                        }
                        if (rngbaddie == 3) enemyUniversalOptions('F');
                        if (rngbaddie == 4) {
                            if (enemyStamina >= 40) executeMoveEnemy("Maul", 0.8, 1, 0.7, "", 0, 45, 0, 40);
                        }
                        if (rngbaddie == 5) {
                            if (enemyStamina >= 45) executeMoveEnemy("Charge", 0.3, 3, 1.1, "", 0, 0, 0, 45);
                        } else if (rngbaddie == 9 && !isPrimal) {
                            enemymoveName = "Primal";
                            enemyIsAttacking = true;
                            enemyIsBlocking = false;
                            isPrimal = true;
                        } else enemyUniversalOptions('F');
                    }
                }
                    case 2 -> {
                        genericRNG(1, 2);
                        rngbaddie = rngVal;
                        //I want the bear to hold its ground more even if it rolls a defensive turn.
                        if (rngbaddie == 1) enemyUniversalOptions('W');
                        else enemyUniversalOptions('G');
                    }
                    case 3 -> {
                        genericRNG(1, 3);
                        if (rngbaddie == 1) enemyUniversalOptions('D');
                        else if (rngbaddie == 2) enemyUniversalOptions('F');
                        else enemyUniversalOptions('W');
                    }
                }
            }
        }
        static void oniAI() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
            humanEmotion();
            while (!enemyConfirm) {
                switch (moveDecider) {
                    case 1 -> {
                        genericRNG(1,7);
                        rngbaddie = rngVal;
                        if (fightRange >= 2) {
                            if (rngbaddie == 1 || rngbaddie == 2) {
                                if (enemyStamina > 35) executeMoveEnemy("Spirit Slash",0.8, 3, 0.75, "", 0, 0, 0, 35);
                            }
                            else if (rngbaddie == 3) enemyUniversalOptions('F');
                            else if (rngbaddie == 4) {
                                if (enemyStamina > 25) executeMoveEnemy("Lacerate", 0.6, 2, 0.65, "", 0, 0, 0, 25);
                            }
                            else if (rngbaddie == 5) {
                                if (enemyStamina > 45) executeMoveEnemy("Soul Snatch", 0.1, 4, 0.4, "DOT, Suck", 0, 70, 0, 45);
                            }
                            else if (rngbaddie == 6) {
                                if (enemyStamina > 60) executeMoveEnemy("Ki Stab", 0.9, 1, 0.85, "", 0, 0, 9, 60);
                            }
                            else if (rngbaddie == 7 && eSmokeBomb > 0) {
                                tempVal = 0;
                                enemymoveName = "Smoke Bomb";
                                do {
                                    genericRNG(1,7);
                                    tempVal++;
                                } while (rngVal >= fightRange || tempVal < 5);
                                fightRange = rngVal;
                                eSmokeBomb -= 1;
                                if (tempVal < 5) enemyConfirm = true;
                            }
                        } else {
                             genericRNG(1, 4);
                            rngbaddie = rngVal;
                            if (rngbaddie == 1) enemyUniversalOptions('F');
                            else if (rngbaddie == 2) {
                                if (enemyStamina > 45) executeMoveEnemy("Soul Snatch",0.4, 3, 1.1, "", 0, 70, 0, 45);
                            }
                            else if (rngbaddie == 3 && !enemyName.contains("Weak") || rngbaddie == 4 && !enemyName.contains("Weak")) {
                                if (enemyStamina > 40) executeMoveEnemy("Ki Slash", 0.7, 3, 0.6, "", 0, 0, 9, 40);
                            }
                            else if (rngbaddie == 5 && eSmokeBomb > 0) {
                                tempVal = 0;
                                enemymoveName = "Smoke Bomb";
                                do {
                                    genericRNG(1,7);
                                    tempVal++;
                                } while (rngVal <= fightRange || tempVal < 5);
                                fightRange = rngVal;
                                eSmokeBomb -= 1;
                               if (tempVal < 5) enemyConfirm = true;
                            }
                        }
                    }
                    case 2 -> {
                        genericRNG(1,3);
                        rngbaddie = rngVal;
                        if (rngbaddie == 1) enemyUniversalOptions('B');
                        else if (rngbaddie == 2 || rngbaddie == 3) {
                                if (enemyStamina > 10) executeMoveEnemy("Shuriken",0.7/fightRange, 6,0.1,"",0,0,0,10);
                            }
                        else enemyUniversalOptions('G');
                    }
                    case 3 -> {
                        genericRNG(1,3);
                        rngbaddie = rngVal;
                        if (rngbaddie == 1) enemyUniversalOptions('F');
                        else if (rngbaddie == 2) enemyUniversalOptions('B');
                        else enemyUniversalOptions('W');
                    }
                }
            }
            if (enemyIsAttacking && !enemymoveName.contains("Shove") && !enemymoveName.contains("Ki Slash")) {
                //energy sfx
                genericRNG(5,2);
                combatSoundEffect(rngVal);
            }
        }
    protected static void combatRecap() {
        if (playerHP <= 0) {
            System.out.println("You were defeated.");
            isFighting = false;
        }
        if (enemyHP <= 0) {
            numOfEnemiesDefeated += 1;
            System.out.println("Victory!");
            tempVal = coin;
            genericRNG((int) (enemyPower), (int) (enemyPower*1.2));
            coin += rngVal;
            System.out.println("You gained " + (coin - tempVal) + " coins!");
            tempVal = playerexp;
            //Getting exp based on the opponent's power may be too inconsistent of a growth right now...
            genericRNG((int) (enemyPower/2), (int) (enemyPower));
            playerexp += rngVal;
            System.out.println("You gained " + (playerexp - tempVal) + " experience!");
            //enemy goes to the shadow realm (aka the negative spots I put everything)
            for (int m = 0; m < enemyPosX.length; m++) {
                if (playerPosX == enemyPosX[m] && playerPosY == enemyPosY[m]) {
                    enemyPosX[m] = -30;
                    enemyPosY[m] = -30;
                }
            }
            numofenemy -= 1;
        }
        System.out.println("-----------------------------------------------------------");
    }
    static void turnResult() throws InterruptedException {
        boolean playerStruck = false;
        boolean enemyStruck = false;
        //Player stuff
        boolean hitTipper = false;
        //Move specific stuff
        //Slam spacing
        if (moveName.contains("Slam")) {
            if (fightRange - 2 == 0) {
                moveDamage = moveDamage * 1.25;
                hitTipper = true;
            }
            fightRange -= 2;
            if (fightRange < 0) fightRange = 0;
        }
        //Ki Charge
        if (swordCharged && playerIsAttacking) {
            moveRange += 1;
            if (moveName.contains("2")) {
                moveSpeed = moveSpeed * 1.2;
                CC += "Boop";
                ccAmount = 1;
            }
            if (fightRange - moveRange == 0) {
                moveDamage = moveDamage * 1.3;
                hitTipper = true;
            }
        }
        //Pyroslash projectile
        if (usedPyroslash) {
            pyroslashLocation += 1;
            if (pyroslashLocation >= fightRange) {
                if (moveSet.containsValue("2")) {
                    tempVal = enemyHP;
                    moveDamage = (attackDamage * 0.45) * (100 / (100 + enemyDefense));
                    if (enemyIsBlocking) moveDamage = moveDamage / 4;
                    enemyHP -= moveDamage;
                    System.out.println("The opponent was hit for " + (tempVal - enemyHP) + "by the Pyroslash!");
                }
                CC += "DOT";
                DOTDamage = Math.toIntExact(Math.round((attackDamage*0.25) * (100/(100 + enemyDefense))));
                DOTDuration = 2;
                enemyStruck = true;
            }
        }
        //Pyroslash spawner
        if (moveName.contains("Pyroslash") && !usedPyroslash) {
            pyroslashLocation = 1;
            usedPyroslash = true;
        }
        //Dominant hand buff (+20% speed)
        if (shieldHand.contains("D")) moveSpeed *= 1.2;

        //Off hand buff (+15% damage)
        else if (shieldHand.contains("O")) moveDamage *= 1.15;
        //Axe stuff, installs do a lot
        if (eradicateTurns > 0) {
            isIgnited = true;
            DOTDamage = attackDamage*0.15;
            //If your move already has DOT, you also get a stun. This might be broken tho............. but at the same time i haven't actually used a super as of May 25, 2023 lol.
            if (CC.contains("DOT")) CC += "Stun";
            hasArmor = true;
            armorDuration = 2;
        }
        //Attack dog buff (makes next move have bleed/DOT effect)
        if (isPrimal){
            enemyCC += "DOT";
            enemyDOTDamage += attackDamage*0.07;
            enemyDOTDuration = 2;
        }
        //Movement options
        //If your option moves you, it should be underneath
        switch (moveName) {
            case "Walk Backwards", "Retreating Strike" -> fightRange += 1;
            case "Walk Forward" -> fightRange -= 1;
            default -> {}
        }
        switch (enemymoveName) {
            case "Walk Backwards", "Retreating Shot" -> fightRange += 1;
            case "Walk Forward", "Charge" -> fightRange -= 1;
            default -> {}
        }
        if (playerIsActionable) System.out.println("You used " + moveName);
            /*if you're stunned */
        else System.out.println("You are unable to move.");
        if (enemyIsActionable) System.out.println(enemyName + " used " + enemymoveName);
        else System.out.println(enemyName + " is unable to move!");
        Thread.sleep(750);
        //Grappling hook gives you bonus range at the cost of 20% of your move's speed.
        //The if statement is to check if the flag is true.
        if (hasGrapplingHook) {
            moveRange += 1;
            moveSpeed -= moveSpeed/5;
        }
        if (moveRange < fightRange && playerIsAttacking) {
                System.out.println("You whiffed!");
                playerHasWhiffed = true;
                playerWhiffedturns = moveRange;
                playerIsAttacking = false;
        }
        if (enemymoveRange < fightRange && enemyIsAttacking) {
            System.out.println("The enemy whiffed!");
            enemyHasWhiffed = true;
            enemyWhiffedturns = enemymoveRange;
            enemyIsAttacking = false;
            //If the opponent whiffs, I want them to either play more defensively or double down
            genericRNG(5,10);
            tempVal = rngVal;
            genericRNG(1,1);
            if (rngVal == 1) enemyRage += tempVal;
            else enemyFear += tempVal;
            //gotta keep all the numbers as a total of 100 if possible
            enemyEgo -= tempVal;
            // this giant chunk of if statements is a series of checks to decide who wins if both fighters strike in the same turn.
            //The order is Speed -> Damage -> Clash (no damage taken for either side)
        }
        if (playerIsAttacking && enemyIsAttacking) {
            //Speed comparison first
            //Checks if ur move is faster than opponent's
            if (moveSpeed > enemymoveSpeed) {
                System.out.println("You struck the opponent because you were faster!");
                enemyStruck = true;
            }
            //Then checks if your move was slower
            else if (moveSpeed < enemymoveSpeed) {
                System.out.println("The opponent struck you because they were faster!");
                playerStruck = true;
                //If speed was tied, then we check for dmg to see which move out prioritizes the other
            } else if (moveDamage > enemymoveDamage) {
                System.out.println("You struck the opponent because your attack was stronger!");
                enemyStruck = true;
                //Then checks if your move is weaker
            } else if (moveDamage < enemymoveDamage) {
                System.out.println("The opponent struck you because your attack was weaker!");
                playerStruck = true;
            }
            //if both speed and damage are somehow identical... I have no idea how that would ever happen but then no damage.
            /* June 2nd Temi here, I've been playing for over 4 weeks and I've never seen the strength comparison, let alone this clash.  */
            else System.out.println("Clash! Both your speed and damage were identical!");
        }
        /*
        now we move to player stuff.
        if the player is the only one attacking, we check in this order:
        Whiffed -> Blocking
        Cuz i want the multiplier to happen before the dmg reduction
        in case the enemy whiffed and wants to block
         */
        if (playerIsAttacking && !playerHasWhiffed && !enemyIsAttacking && moveSpeed > enemymoveSpeed) {
            if (enemyHasWhiffed)
            //Player stuff
            {
                moveDamage *= 1.35;
                System.out.println("Bonus damage because the opponent whiffed! ");
                enemyWhiffedturns -= 1;
            }  if (enemyIsBlocking) {
                if (!ignoresBlock) {
                    moveDamage = moveDamage / 4;
                    System.out.println("The enemy blocked your attack!");
                    enemyStamina += staminaDamage;
                    combatSoundEffect(3);
                } else System.out.println("You got past the enemy's block!");
            }
            enemyStruck = true;
        }
        //Enemy stuff
        if (enemyIsAttacking && !enemyHasWhiffed && !playerIsAttacking && enemymoveSpeed > moveSpeed) {
            //Same thing for players, if they whiff and the opponent hits them they take 35% extra dmg
            if (playerHasWhiffed) {
                tempVal = playerHP;
                enemymoveDamage = (enemymoveDamage * 1.35);
                System.out.println("Bonus damage because you whiffed!");
            } else if (playerIsBlocking) {
                enemymoveDamage = (enemymoveDamage / 4);
                System.out.println("You blocked the enemy's attack!");
                genericRNG(10,20);
                playerStamina += rngVal;
                enemyStaminaDamage = enemyStaminaDamage / 2;
                combatSoundEffect(3);
            }
            playerStruck = true;
        } else if (playerIsBlocking && enemyIsBlocking) System.out.println("Both fighters blocked! A tense standoff...");

        //Code for if the other combatant doesn't do anything, just to make sure the move connects.
        if (playerIsAttacking && !enemyIsAttacking && !enemyHasWhiffed && !enemyIsBlocking && (moveRange >= fightRange)) enemyStruck = true;
        if (enemyIsAttacking && (enemymoveRange >= fightRange) && !playerIsBlocking && !enemyHasWhiffed) playerStruck = true;

        //  if (enemyStruck) playerStruck = false;

        if (playerStruck && hasArmor && armorDuration > 0 && !playerHasWhiffed && playerIsAttacking) enemyStruck = true;
        if (enemyStruck && !playerHasWhiffed) {
            if (enemyName.contains("Weak")) {
                genericRNG(1,2);
                if (rngVal == 1) {
                    genericRNG(10, 30);
                    enemyRage += rngVal;
                    tempVal = rngVal;
                    genericRNG(1, 1);
                    if (rngVal == 1) enemyFear -= rngVal;
                    else enemyEgo -= rngVal;
                } else {
                    genericRNG(10, 30);
                    enemyFear += rngVal;
                    genericRNG(1, 1);
                    if (rngVal == 1) enemyEgo -= rngVal;
                    else enemyRage -= rngVal;
                }
            }
            //Sword sfx
            if (weaponName.contains("Sword") && !moveName.contains("Shove")) combatSoundEffect(8);
            if (weaponName.contains("Axe") && !moveName.contains("Shove")) combatSoundEffect(15);
            if (enemyIsAttacking) playercounterHit = true;
            /*
            Quick recap
            If the opponent's attack misses, and you hit the opponent: bonus 35% damage
            if you and the opponent attack, but your move connects: counter-hit: -20% stamina
            Knock-back = increases fight range
            Vacuum = decreases fight range
            Stun = opponent cannot pick an option for x amount of turns
            Weaken = opponent does less damage
             */
            if (weaponName.contains("Shield") && moveName.contains("Shield Toss")) {
                hasShield = false;
                shieldLocation = fightRange - (fightRange - 1);
            }
            if (playercounterHit && enemyIsActionable) enemyStamina -= 20;
            if (masteredWeapon && playercounterHit) isIgnited = true;
            if (isIgnited) CC += "DOT";
            if (enemyIsActionable) enemyStamina -= staminaDamage;
            if (moveName.contains("Vanishing Strike 2") && playercounterHit) moveDamage *= 1.25;
            tempVal = enemyHP;
            enemyHP -= moveDamage;
            System.out.println("You did " + Math.round((tempVal - enemyHP)) + " damage!");
            //CC checks
            if (CC.contains("Boop")) {
                System.out.println("You sent your enemy flying!");
                fightRange += ccAmount;
            }
            if (CC.contains("Suck")) {
                System.out.println("You drew your enemy closer!");
                fightRange -= ccAmount;
            } else if (CC.contains("Stun")) {
                if (enemyIsActionable) {
                    System.out.println("You stunned your opponent for " + ccAmount + " turns!");
                    enemyIsActionable = false;
                    enemyUnactionableTurns = ccAmount;
                } else System.out.println("You cannot re-stun the opponent.");
            } else if (CC.contains("Weaken Damage") || CC.contains("Weaken Speed")) {
                System.out.println("You weakened the the opponent!");
                if (CC.contains("Weaken Damage")) enemyDamage *= ((double) 100 / (100 * ccAmount));
                if (CC.contains("Weaken Speed")) enemySpeed *= ((double) 100 / (100 * ccAmount));
            }
            else if (CC.contains("DOT")) enemyTakesDOT = true;
            if (DOTDuration > 0 && enemyTakesDOT) {
                tempVal2 = enemyHP;
                enemyHP -= DOTDamage;
                System.out.println("You did a bonus " + (tempVal2 - enemyHP) + " damage!");
                if (moveSet.containsValue("Ignite 2") && isIgnited) {
                    tempVal = playerHP;
                    playerHP += (tempVal2 - enemyHP);
                    System.out.println("You were healed for " + (playerHP - tempVal) + " HP!");
                }
            }
            if (hitTipper) System.out.println("Direct hit! Bonus damage!");
                //lets the player know that they landed the sweet spot
            superMeter += Math.round(moveDamage / 2.5);
            //Super meter builds with damage
            hasSuper = superMeter >= 100;
            // this is the Sword Mastery Perk (Sword charges gives charge on sweetspot hits)
            if (weaponName.contains("Sword") && masteredWeapon && hitTipper) swordCharged = true;
            if (moveName.contains("Cripple 2")) staminaRegenPaused = ccAmount;
        }
        if (playerStruck) {
            genericRNG(10, 20);
            enemyEgo += rngVal;
            genericRNG(1, 1);
            if (rngVal == 1) enemyRage -= rngVal;
            else enemyFear -= rngVal;
            if (enemyName.contains("Sword") && !enemymoveName.contains("Block")) {
                //hit sfx
                if (swordCharged) combatSoundEffect(10);
                else combatSoundEffect(8);
            }
            //Deflect is a counter, so we check if they got hit 
            if (moveName.contains("Deflect")) {
                enemyStamina = 0;
                if (moveName.contains("2")) {
                    moveDamage = enemymoveDamage * 0.25 * (100 / (100 + enemyDefense));
                    enemyHP -= moveDamage;
                }
                System.out.println("You reflected the opponent's attack!");
            } else {
                if (playerIsAttacking) enemycounterHit = true;
                //same for enemies
                if (enemycounterHit) playerStamina -= 20;
                if (playerIsActionable && enemyStaminaDamage > 0) playerStamina -= enemyStaminaDamage;
                superMeter += Math.round(enemymoveDamage / 4);
                tempVal = playerHP;
                playerHP -= enemymoveDamage;
                System.out.println(enemyName + " did " + Math.round((tempVal - playerHP)) + " damage!");
                if (enemyCC.contains("Boop")) {
                    System.out.println("The enemy sent you flying!");
                    fightRange += enemyCCAmount;
                }
                if (enemyCC.contains("Suck")) {
                    System.out.println("The enemy drew you closer!");
                    fightRange -= enemyCCAmount;
                }
                if (enemyCC.contains("Stun")) {
                    if (playerIsActionable) {
                        System.out.println("The opponent stunned you for " + ccAmount + " turns!");
                        playerIsActionable = false;
                        unactionableTurns = ccAmount;
                    }
                }
                if (enemyCC.contains("Weaken Damage") || enemyCC.contains("Weaken Speed")) {
                    System.out.println("The enemy weakened you!");
                    if (CC.contains("Weaken Damage")) attackDamage = attackDamage * ((double) 100 / (100 * ccAmount));
                    if (CC.contains("Weaken Speed")) attackSpeed = attackSpeed * ((double) 100 / (100 * ccAmount));
                }
                if (enemyCC.contains("DOT")) playerTakesDOT = true;
                if (enemyDOTDuration > 0 && playerTakesDOT) {
                    tempVal2 = playerHP;
                    playerHP -= enemyDOTDamage;
                    System.out.println("You took a bonus " + (tempVal2 - playerHP) + " damage!");
                }
            }
        }
        /*
        if either the player or the enemy ran out of stamina, they should
        be stunned for a turn.
         */
        if (enemyStamina <= 0) {
            System.out.println("The enemy's stamina has been depleted!");
            enemyStamina = 0;
            enemyIsActionable = false;
            enemyUnactionableTurns += 2;
        }
        if (playerStamina <= 0 && playerIsActionable) {
            System.out.println("Your stamina has been depleted!");
            playerStamina = 0;
            playerIsActionable = false;
            unactionableTurns += 2;
        }
        playerStamina += 10;
        for (int u = enemyStamina; u == 100; u++) {
            enemyFear += 1;
        }
        enemyStamina += 10;
        if (playerIsAttacking && swordCharged) swordCharged = false;
        }
    static void resetCombatVar() {
        clearConsole();
        //for stamina
        armorDuration -= 1;
        DOTDuration -= 1;
        if (enemyEgo <= 0) enemyEgo = 0;
        if (enemyFear <= 0) enemyFear = 0;
        if (enemyRage <= 0) enemyRage = 0;
        if ((enemyRage + enemyFear + enemySpeed) > 100) enemyEmotion();
            //This is to reset it to a preset number in case it goes past 100.
        unactionableTurns -= 1;
        enemyUnactionableTurns -= 1;
        if (unactionableTurns <= 0) {
            unactionableTurns = 0;
            if (!playerIsActionable) {
                playerIsActionable = true;
                playerStamina = 100;
            }
        }
        if (enemyUnactionableTurns <= 0) {
            enemyUnactionableTurns = 0;
            if (!enemyIsActionable) {
                enemyIsActionable = true;
                enemyStamina = 100;
            }
        }
        //for whiffs
        enemyWhiffedturns -= 1;
        playerWhiffedturns -= 1;
        if (enemyWhiffedturns <= 0) {
            enemyWhiffedturns = 0;
            enemyHasWhiffed = false;
        }
        if (playerWhiffedturns <= 0) {
            playerWhiffedturns = 0;
            playerHasWhiffed = false;
        }
        enemymoveName = "";
        enemymoveDamage = 0;
        enemymoveSpeed = 0;
        enemymoveRange = 0;
        moveName = "";
        moveDamage = 0;
        moveSpeed = 0;
        if (!hasGrapplingHook) moveRange = 0;
        //can't go above 100 since its supposed to be a percentage
        if (playerStamina > 100) playerStamina = 100;
        if (enemyStamina > 100) enemyStamina = 100;
        if (superMeter > 100) superMeter = 100;
        staminaCost = 0;
        playerIsBlocking = false;
        enemyIsBlocking = false;
        playerIsAttacking = false;
        enemyIsAttacking = false;
        hasConfirmedAtk = false;
        hasConfirmedCombat = false;
        hasConfirmedMove = false;
        playercounterHit = false;
        enemycounterHit = false;
        hasConfirmed = false;
        ignoresBlock = false;
        enemyStaminaDamage = 0;
        staminaDamage = 0;
        answer = 0;
        answerType = "";
        CC = "";
        ccAmount = 0;
        enemyCC = "";
        enemyCCAmount = 0;
    }
    static void finalBattle() throws InterruptedException {
        System.out.println("100 days are up...");
        Thread.sleep(1000);
        System.out.println("Thousands of people entered... only 2 remain.");
        Thread.sleep(1000);
        //Determines the boss's starting stance.
        genericRNG(1,2);
        if (rngVal == 1) {
            bossStance = "sword";
            enemyType = "Melee Human";
        }
        else if (rngVal == 2) {
            bossStance = "shield";
            enemyType = "Melee Human";
        }
        else {
            enemyType = "Range Human";
            bossStance = "bow";
            enemyQuiver = 5;
        }
        System.out.println("The enemy is using their " + bossStance + "!");
        isBoss = true;
        enemyStatSelector(120,150,1000,950,100,1050,100);
        enemyPower = (enemyHP + enemyDamage + enemySpeed + enemyDefense)/4;
        enemyName = "The Last";
        System.out.println("Your power: " + Math.round(playerPower));
        System.out.println("Enemy power: " + Math.round(enemyPower));
        Thread.sleep(250);
        System.out.println("CLAIM YOUR PRIZE");
        System.out.println("-----------------------------------------------------------");
    }
    static void BossAI() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        if (enemyHP <= 750 && enemyHP >= 500 && bossHasSwapped == 0|| enemyHP <= 300 && bossHasSwapped == 1) {
            answerType = bossStance;
            while (answerType.equals(bossStance)) {
                genericRNG(1, 2);
                if (rngVal == 1) bossStance = "sword";
                else if (rngVal == 2) bossStance = "shield";
                else bossStance = "bow";
            }
            System.out.println("The boss swaps to their " + bossStance + "!");
            bossHasSwapped += 1;
        }
        if (bossStance.contains("sword")) swordsmanAI();
        else if (bossStance.contains("bow")) bowmanAI();
        else shieldsmanAI();
    }

    protected static void combatMusic(int trackNum) {
        music.combatSetFile(trackNum);
        music.combatStartClip();
        music.combatLoopClip();
        musicPlaying = true;
    }

    protected static void combatStopMusic() {
        if (Music.dayMusic != null) {
            music.combatStopClip();
            musicPlaying = false;
        } else System.out.println("Clip is already stopped or not initialized.");
    }
    protected static void combatSoundEffect(int trackNum) {
        music.setFile(trackNum);
        music.startClip();
    }
}
