import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

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

     public static void fight() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
         if (moveSet.containsValue("Swap")) shieldHand = "D";
        else shieldHand = "";
       if (day <= 99 ) {
           genericRNG(1, 2);
           tempVal = Math.toIntExact(Math.round(rngVal));
           combatMusic((int) tempVal);
       }
       else combatMusic(2);
        clearConsole();
        decayImp -= 5;
        enemyTypeSelector();
       if (day <= 100) startCombat();
       else finalBattle();
        while (isFighting) {
            resetCombatVar();
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
                    case "Weak Swords-man", "Strong Swords-man" -> SwordsmanAI();
                    case "Strong Shields-man", "Weak Shields-man" -> shieldsmanAI();
                    case "Strong Bowman" -> bowmanAI();
                    case "Bear" -> bearAI(); 
                    case "The Last" -> BossAI();
                    //  if (enemyName.equals("The Last")) BossAI();
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
            case "Weak Shields-man" -> System.out.println("A weak shieldsman attacks you!");
            case "Strong Shields-man" -> System.out.println("An experienced shieldsman forces you into a duel...");
            case "Strong Bowman" -> System.out.println("You get the strange feeling someone is aiming at you...");
            case "Bear" -> System.out.println("A deep growl sends shivers down your spine...");
            default -> {
                System.out.println("The smell of smoke surrounds you...");
                Thread.sleep(1000);
                System.out.println("Your power: " + Math.round(playerPower));
                System.out.println("Enemy power: " + Math.round(enemyPower));
                Thread.sleep(250);
                System.out.println("BEGINNING COMBAT");
                System.out.println("-----------------------------------------------------------");
            }
        }
    }

    static void enemyTypeSelector() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (inWildlands) {

            genericRNG(1,2);
            if (rngVal == 1) enemyName = "Weak Shields-man";
            else enemyName = "Weak Swords-man";
        }
        switch (enemyName) {
            case "Weak Shields-man":
                enemyHP = 135;
                genericRNG(55, (day*level) + 15); // This is for dmg
                enemyDamage = rngVal;
                genericRNG(80, (day*level) + 20 ); // This is for speed
                enemySpeed = rngVal;
                genericRNG(100, (day*level)+ 20); // This is for defense
                enemyDefense = rngVal;
                break;
            case "Weak Swords-man":
                enemyHP = 115;
                tempVal = (day * level) + 30;
                genericRNG(65, (int) (tempVal)); // This is for dmg
                enemyDamage = rngVal;
                genericRNG(65, (int) (tempVal)); // This is for speed
                enemySpeed = rngVal;
                genericRNG(60, (int) (tempVal)); // This is for defense
                enemyDefense = rngVal;
                break;
        }
              if (inMountains) {
                genericRNG(1,7);
                switch (rngVal) {
                    case 2:
                        enemyName = "Bear";
                        break;
                    case 4:
                    case 5:
                    enemyName = "Strong Bowman";
                    break;
                    case 6:
                        enemyName = "Strong Shields-man";
                        break;
                    default: enemyName = "Strong Swords-man";
                }
                switch (enemyName) {
                    case "Strong Swords-man":
                    enemyHP = 150;
                    tempVal = ((day * level) * 1.25);
                    genericRNG(145,(int) tempVal); // This is for dmg
                    enemyDamage = rngVal;
                    genericRNG(130, (int) tempVal); // This is for speed
                    enemySpeed = rngVal;
                    genericRNG(90, (int) tempVal); // This is for defense
                    enemyDefense = rngVal;
                    break;
                    case "Bear":
                    enemyHP = 240;
                    genericRNG(165, (int) ((day*level)*1.25)); // This is for dmg
                    enemyDamage = rngVal;
                    genericRNG(150, (int) ((day*level)*1.25)); // This is for speed
                    enemySpeed = rngVal;
                    genericRNG(105, (int) ((day*level)*1.25)); // This is for defense
                    enemyDefense = rngVal;
                    break;
                    case "Strong Bowman": {
                    enemyHP = 185;
                    genericRNG(120, (day*level)); // This is for dmg
                    enemyDamage = rngVal;
                    genericRNG(75, (day*level)); // This is for speed
                    enemySpeed = rngVal;
                    genericRNG(75,(day*level)); // This is for defense
                    enemyDefense = rngVal;
                }
                    case "Strong Shields-man":
                    enemyHP = 225;
                    genericRNG(225, (day*level)); // This is for dmg
                    enemyDamage = rngVal;
                    genericRNG(110,(day*level)); // This is for speed
                    enemySpeed = rngVal;
                    genericRNG(145, (day*level)); // This is for defense
                    enemyDefense = rngVal;
                    break;
                }
            }

              if (inDungeon) {
                  if (enemyName.contains("Sword")) {
                      enemyHP += (double) Dungeon.dungeonPower/15;
                      enemyDamage += (double) Dungeon.dungeonPower/3;
                      enemySpeed += (double) Dungeon.dungeonPower/3;
                      enemyDefense += (double) Dungeon.dungeonPower/3;
                  }
                  else if (enemyName.contains("Shield")) {
                      enemyHP += (double) Dungeon.dungeonPower/12;
                      enemyDamage += (double) Dungeon.dungeonPower/10;
                      enemySpeed += (double) Dungeon.dungeonPower/3;
                      enemyDefense += (double) Dungeon.dungeonPower/2;
                  }
                  else if (enemyName.contains("Bow")) {
                      enemyHP += (double) Dungeon.dungeonPower/20;
                      enemyDamage += (double) Dungeon.dungeonPower/2;
                      enemySpeed += (double) Dungeon.dungeonPower/4;
                      enemyDefense += (double) Dungeon.dungeonPower/4;
                  }
                  else if (enemyName.contains("Bear")) {
                      enemyHP += (double) Dungeon.dungeonPower/17;
                      enemyDamage += (double) Dungeon.dungeonPower/3;
                      enemySpeed += (double) Dungeon.dungeonPower/4;
                      enemyDefense += (double) Dungeon.dungeonPower/5;
                  }
              }
            //sound effects

            if (enemyName.contains("Sword")) combatSoundEffect(4);
            enemyPower = (enemyHP + enemyDamage + enemySpeed + enemyDefense) / 4;
    }

     static void turnLoop() throws InterruptedException {
        //This just prints all the data the player needs to make their decisions.
        System.out.println("Your health: " + Math.round(playerHP));
        System.out.println("Your stamina: " + playerStamina + "%");
        if (weather != 3)/*weather 3  is duststorm, which blinds you */ {
            System.out.println("Enemy health: " + Math.round(enemyHP));
            System.out.println("Enemy stamina: " + Math.round(enemyStamina) + "%");
            System.out.println("Distance: " + fightRange);
        }
        else System.out.println("The storm blinds you, not allowing you to see your opponent...");
        System.out.println("Super: " + superMeter + "%");
        System.out.println("Turn: " + turn);
        Thread.sleep(500);
        System.out.println("1: Select Attack ");
        System.out.println("2: Move ");
        System.out.println("3: Block ");
        System.out.println("4: Utility Items");
        System.out.println("5: Wait ");
        System.out.println("6: Win Button");
        playerHasWhiffed = false;
        enemyHasWhiffed = false;
        playerIsBlocking = false;
        enemyIsBlocking = false;
    }
    static void playerWait() throws InterruptedException {
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

    static void playerMove() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
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
                    } else {
                        moveName = "Walk Backwards";
                        playerHasWhiffed = false;
                        playerIsAttacking = false;
                        playerIsBlocking = false;
                        hasConfirmedCombat = true;
                        hasConfirmedMove = true;
                        playerStamina += 5;
                    }
                }
            }
           else if (answerType.contains("D") || answerType.contains("d")) {
                if ((fightRange - 1) < 0) {
                    System.out.println("You cannot move into your opponent. Please re-input your movement option.");
                } else {
                    moveName = "Walk Forwards";
                    playerIsAttacking = false;
                    playerIsBlocking = false;
                    playerHasWhiffed = false;
                    hasConfirmedCombat = true;
                    hasConfirmedMove = true;
                    playerStamina += 10;
                }
            }
            else {
                System.out.println("You did not confirm.");
                Thread.sleep(500);
            }
        }
    }

    static void playerBlock() throws InterruptedException {
        //Alternative to wait to get some stamina back at the cost of hp.
        System.out.println("Press 1 to confirm block, or 0 to go back.");
        playerResponse();
        if (answer != 0) {
            playerIsBlocking = true;
            playerIsAttacking = false;
            moveName = "Block";
            moveDamage = 0;
            moveRange = 0;
            hasConfirmedCombat = true;
        }
    }
    static void universalMoves() throws InterruptedException {
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
            System.out.println("Select your move using the corresponding number: ");
            for (int count = 1; count < 6; count++) {
                System.out.println(count + ": " + moveSet.get("Move " + count) + ".");
            }
            System.out.println("Or press 0 to go back.");
            playerResponse();
            if (answer != 0) tempMove = moveSet.get("Move " + answer);
            else hasConfirmedAtk = true;
            while (tempMove == null || answer > 5) {
                System.out.println("Invalid response.");
                playerResponse();
                tempMove = moveSet.get("Move " + answer);
            }
            if (!hasShield) {
                if (tempMove.contains("Bash") || tempMove.contains("Earthquake ") || tempMove.contains("Deflect") || tempMove.contains("Swap")) {
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
                System.out.println("Jump up and smash your shield against the ground,. Bonus damage if 2 spaces away.");
                moveDescription = "Jump up and smash your shield against the ground,. Bonus damage if 2 spaces away.";
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
                } else {
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
                printMoveInfo(5, 0.6, "Medium (depends on distance)");
                playerResponse();
                if (answer == 1) {
                    hasShield = false;
                    executeMove(0.6/fightRange,5,0.7 - (attackDamage /fightRange), "",0,0,0);
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
            System.out.println("Select your move using the corresponding number: ");
            for (int count = 1; count < 6; count++) System.out.println(count + ": " + moveSet.get("Move " + count) + ".");
            System.out.println("Or press 0 to go back.");
            playerResponse();
            if (answer != 0) tempMove = moveSet.get("Move " + answer);
            else hasConfirmedAtk = true;
            while (tempMove == null || answer > 5) {
                System.out.println("Invalid response.");
                playerResponse();
                tempMove = moveSet.get("Move " + answer);
            }
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

    protected static void executeMoveEnemy(double speed, int range, double damage, String crowdControl, int ccNum, int stamDMG, int playSFX, int stamCost) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
         enemymoveName = tempMove;
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


    static void utilityItem() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
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
            if (magmaWhistle > 0) System.out.println("Magma Whistle");
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
                default -> {}
            }
    }

    static void SwordsmanAI() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        //The AI is RNG with odds that change depending on fight circumstances, like hp remaining, distance, or the player's weapon.
        enemyConfirm = false;
        while (!enemyConfirm) {
           if (enemyName.contains("Weak Swords-man")) genericRNG(1,9);
           else genericRNG(1,10);
            rngbaddie = rngVal;
            //System.out.println("rngVal" + rngVal);
            if (fightRange <= 2) {
                if (rngbaddie == 1 || rngbaddie == 2) {
                    enemymoveName = "Wait";
                    enemyStamina += 20;
                }
                if (rngbaddie == 3 || rngbaddie == 4) {
                    if (enemyStamina > 20) {
                        tempMove = "Forward Swipe";
                        executeMoveEnemy(0.75,2,0.7,"",0,0,0,20);
                    }
                }
                if (rngbaddie == 5) {
                    enemymoveName = "Walk Forward";
                    enemyStamina += 10;
                }
                if (rngbaddie == 6) {
                    if (enemyStamina > 25) {
                        tempMove = "Stab";
                        executeMoveEnemy(0.9,2,0.65,"",0,0,0,25);
                    }
                }
                if (rngbaddie == 7) {
                    if (enemyStamina > 45) {
                        tempMove = "Whirlwind";
                        executeMoveEnemy(0.4,3,1.1, "",0,70,0,45);
                    }
                }
                if (rngbaddie == 8) {
                    enemymoveName = "Walk Backwards";
                    enemyStamina += 5;
                }
                if (rngbaddie == 9 || rngbaddie == 10) {
                    enemymoveName = "Block";
                    enemyIsBlocking = true;
                }
                if (rngbaddie == 11 && !enemyName.contains("Weak Swords-man")) {
                    if (enemyStamina >= 40) {
                        tempMove = "Ki Slash";
                        executeMoveEnemy(0.7,3,0.6,"",0,0,9,40);
                    }
                }
            } else {
                if (enemyName.contains("Weak Swords")) genericRNG(1,5);
                else genericRNG(1,6);
                rngbaddie = rngVal;
                if (rngbaddie == 1) {
                    enemymoveName = "Walk Forward";
                    enemyStamina += 10;
                    combatSoundEffect(11);
                } else if (rngbaddie == 4 && !enemyName.contains("Weak") || rngbaddie == 5 && !enemyName.contains("Weak")) {
                    if (enemyStamina >= 40) {
                        tempMove = "Ki Slash";
                        executeMoveEnemy(0.7,3,0.6,"",0,0,9,40);
                    }
                } else if (rngbaddie == 2) {
                    if (enemyStamina >= 45) {
                        tempMove = "Whirlwind";
                        executeMoveEnemy(0.4,3,1.1, "",0,70,0,45);
                    }
                } else {
                    enemyIsAttacking = false;
                    enemymoveName = "Wait";
                    enemyStamina += 20;
                }
            }
        }
        if (enemyIsAttacking && !enemymoveName.contains("Shove") && !enemymoveName.contains("Ki Slash")) {
            genericRNG(5,2);
            combatSoundEffect(rngVal);
        }
    }


    static void shieldsmanAI() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        //The AI is RNG with odds that change depending on fight circumstances, like hp remaining, distance, or the player's weapon.
        if (enemyName.contains("Weak Shields-man")) genericRNG(1,9);
        else genericRNG(1,10);
        rngbaddie = rngVal;
        //System.out.println("rngVal" + rngVal);
        enemyConfirm = false;
        while (!enemyConfirm) {
            if (fightRange <= 2) {
                if (rngbaddie == 1 || rngbaddie == 2) {
                    enemymoveName = "Wait";
                    enemyHasWhiffed = false;
                    enemyIsAttacking = false;
                    enemyIsBlocking = false;
                    enemyStamina += 20;
                    enemyConfirm = true;
                }
                if (rngbaddie == 3 || rngbaddie == 4) {
                    if (enemyStamina >= 15) {
                        tempMove = "Bash";
                        executeMoveEnemy(0.6,2,0.65,"",0,0,0,15);
                    }
                }
                if (rngbaddie == 5) {
                    enemymoveName = "Walk Forward";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyStamina += 10;
                    combatSoundEffect(11);
                    enemyConfirm = true;
                }
                if (rngbaddie == 6) {
                    if (enemyStamina >= 35) {
                        tempMove = "Uppercut";
                        executeMoveEnemy(0.75,1,0.5,"",0,0, 0,35);
                    }
                }
                if (rngbaddie == 7) {
                    if (enemyStamina >= 25) {
                        tempMove = "Side Kick";
                        executeMoveEnemy(0.9,3,0.4,"Boop",2,30,0,25);
                    }
                }
                if (rngbaddie == 8) {
                    enemymoveName = "Walk Backwards";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyStamina += 5;
                    enemyConfirm = true;
                }
                if (rngbaddie == 9) {
                    enemymoveName = "Block";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyIsBlocking = true;
                    enemyConfirm = true;
                }
                if (rngbaddie == 10) {
                    if (enemyStamina >= 45) {
                        tempMove = "Grapple";
                        executeMoveEnemy(1.1,1,0.3,"",0,0,0,45);
                    }
                }
            } else {
                enemymoveName = "Walk Forward";
                enemyIsAttacking = false;
                enemyHasWhiffed = false;
                enemyStamina += 10;
                enemyConfirm = true;
            }
        }
    }
    static void bowmanAI() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        //The AI is RNG with odds that change depending on fight circumstances, like hp remaining, distance, or the player's weapon.
        genericRNG(1,10);
        rngbaddie = rngVal;
        enemyConfirm = false;
        while (!enemyConfirm) {
            switch (rngbaddie) {
                 case 2 -> {
                         enemymoveName = "Wait";
                         enemyStamina += 20;
                         enemyConfirm = true;
                 }
                    case 3 -> {
                        if (enemyStamina >= 20) {
                            tempMove = "Bow Swipe";
                            executeMoveEnemy(0.55, 2, 0.6, "", 0, 0, 0, 20);
                        }
                    }
                    case 5 -> {
                        enemymoveName = "Walk Forward";
                        enemyIsAttacking = false;
                        enemyHasWhiffed = false;
                        enemyStamina += 10;
                        combatSoundEffect(11);
                    }
                    case 6 -> {
                        if (enemyQuiver > 0) {
                            tempMove = "Quick Shot";
                            enemyQuiver -= 1;
                            executeMoveEnemy(0.65, 2, 0.5, "", 0, 5, 0, 10);
                        } else {
                            System.out.println("The enemy is out of arrows!");
                            enemyHasWhiffed = true;
                            enemyWhiffedturns = 2;
                        }
                    }
                    case 7 -> {
                        if (enemyStamina >= 25) {
                            tempMove = "Side Kick";
                            executeMoveEnemy(0.85, 2, 0.35, "", 0, 0, 0, 25);
                        }
                    }
                    case 9 -> {
                        enemymoveName = "Block";
                        enemyIsBlocking = true;
                        enemyConfirm = true;
                    }
                    case 10 -> {
                        if (enemyQuiver < 5) {
                            enemyHasWhiffed = true;
                            enemymoveName = "Reload";
                            enemyQuiver = 5;
                            enemyConfirm = true;
                        } else {
                            enemymoveName = "Wait";
                            enemyStamina += 20;
                            enemyConfirm = true;
                        }
                    }
                default ->  {
                    enemymoveName = "Walk Backwards";
                    enemyStamina += 5;
                    enemyConfirm = true;
                } } if (fightRange <= 5) {
                    enemyQuiver -= 1;
                    executeMoveEnemy(0.65, 5, 0.3, "", 0, 5, 0, 10);
                } else {
                    enemymoveName = "Walk Forward";
                    enemyStamina += 10;
                    combatSoundEffect(11);
                    enemyConfirm = true;
                }
            }
        }
    static void bearAI() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        //The AI is RNG with odds that change depending on fight circumstances, like hp remaining, distance, or the player's weapon.
        genericRNG(1,10);
        rngbaddie = rngVal;
        //System.out.println("rngVal" + rngVal);
        enemyConfirm = false;
        while (!enemyConfirm) {
            if (fightRange <= 2) {
                if (rngbaddie == 1 || rngbaddie == 2) {
                    enemymoveName = "Wait";
                    enemyConfirm = true;
                }
                if (rngbaddie == 3 || rngbaddie == 4) {
                    if (enemyStamina >= 15) {
                        tempMove = "Claw Strike";
                        executeMoveEnemy(0.75,2,0.65,"",0,0,0,15);
                    }
                }
                if (rngbaddie == 5) {
                    enemymoveName = "Walk Forward";
                    enemyStamina += 10;
                    enemyConfirm = true;
                }
                if (rngbaddie == 6) {
                    if (enemyStamina >= 40) {
                        tempMove = "Maul";
                        executeMoveEnemy(0.8,1,0.7,"",0,45,0,40);
                    }
                }
                if (rngbaddie == 7) {
                    if (enemyStamina >= 45) {
                        tempMove = "Charge";
                        executeMoveEnemy(0.3,3,1.1,"",0,0,0,45);
                    }
                }
                if (rngbaddie == 8) {
                    enemymoveName = "Walk Backwards";
                    enemyStamina += 5;
                    enemyConfirm = true;
                }
                if (rngbaddie == 9) {
                    enemymoveName = "Block";
                    enemyIsBlocking = true;
                    enemyConfirm = true;
                }
            } else {
                enemymoveName = "Walk Forward";
                enemyStamina += 10;
                enemyConfirm = true;
            }
        }
    }
    protected static void combatRecap() throws InterruptedException, LineUnavailableException, IOException {
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
            genericRNG((int) (enemyPower/1.5), (int) (enemyPower));
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
    static void turnResult() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
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
                    enemyHP -= moveDamage;
                    System.out.println("The opponent was hit for " + (tempVal - enemyHP) + "by the Pyroslash!");
                }
                CC += "DOT";
                ccAmount += 1;
                enemyStruck = true;
            }
        }
        //Pyroslash spawner
        if (moveName.contains("Pyroslash") && !usedPyroslash) {
            pyroslashLocation = 1;
            usedPyroslash = true;
        }
        //Dominant hand buff (+20% speed)
        if (shieldHand.contains("D")) moveSpeed = moveSpeed * 1.2;

        //Off hand buff (+15% damage)
        else if (shieldHand.contains("O")) moveDamage = moveDamage * 1.15;
        //Axe stuff, installs do a lot
        if (eradicateTurns > 0) {
            isIgnited = true;
            //If your move already has DOT, you also get a stun. This might be broken tho............. but at the same time i haven't actually used a super as of May 25, 2023 lol.
            if (CC.contains("DOT")) CC += "Stun";
            hasArmor = true;
        }
        //Movement options
        //If your option moves you, it should be underneath
        switch (moveName) {
            case "Walk Backwards", "Retreating Strike" -> fightRange += 1;
            case "Walk Forwards" -> fightRange -= 1;
            default -> {}
        }
        switch (enemymoveName) {
            case "Walk Backwards" -> fightRange += 1;
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
            tempVal = moveSpeed / 5;
            moveSpeed -= tempVal;
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

        if (enemyStruck && !playerHasWhiffed) {
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
            if (moveName.contains("Vanishing Strike 2") && playercounterHit) moveDamage = moveDamage * 1.25;
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
                if (CC.contains("Weaken Damage")) enemyDamage = enemyDamage * (100 / (100 * ccAmount));
                if (CC.contains("Weaken Speed")) enemySpeed = enemySpeed * (100 / (100 * ccAmount));
            }
            if (CC.contains("DOT" /* stands for Damage over Time */)) {
                tempVal2 = enemyHP;
                tempVal = moveDamage / 4;
                enemyHP -= tempVal;
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
            if (superMeter >= 100) hasSuper = true;
            else hasSuper = false;
            // this is the Sword Mastery Perk (Sword charges gives charge on sweetspot hits)
            if (weaponName.contains("Sword") && masteredWeapon && hitTipper) swordCharged = true;
            if (moveName.contains("Cripple 2")) staminaRegenPaused = ccAmount;
        }
        if (playerStruck) {
            if (enemyName.contains("Sword") && !enemymoveName.contains("Block")) {
                //hit sfx
                if (swordCharged) combatSoundEffect(10);
                else combatSoundEffect(8);
            }
            //Deflect is a counter, so we check if they got hit 
            if (moveName.contains("Deflect"))
            {
                enemyStamina = 0;
                if (moveName.contains("2")) {
                    moveDamage = enemymoveDamage*0.25 * (100 /(100 + enemyDefense));
                    enemyHP -= moveDamage;
                }
                System.out.println("You reflected the opponent's attack!");
            }
            else {
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
                }
            }
        /*
        if either the player or the enemy ran out of stamina, they should
        be stunned for a turn.
         */
        if (enemyStamina <= 0)
        {
            System.out.println("The enemy's stamina has been depleted!");
            enemyStamina = 0;
            enemyIsActionable = false;
            enemyUnactionableTurns += 2;
        }
        if (playerStamina <= 0 && playerIsActionable)
        {
            System.out.println("Your stamina has been depleted!");
            playerStamina = 0;
            playerIsActionable = false;
            unactionableTurns += 2;
        }
        playerStamina += 10;
        enemyStamina += 10;
        if (playerIsAttacking && swordCharged) swordCharged = false;
        }
    static void resetCombatVar() throws InterruptedException {
        clearConsole();
        //for stamina
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
        if (enemyWhiffedturns <= 0)
        {
            enemyWhiffedturns = 0;
            enemyHasWhiffed = false;
        }
        if (playerWhiffedturns <= 0)
        {
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
        if (!hasGrapplingHook) {
            moveRange = 0;
        }
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
        usedPyroslash = false;
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
        genericRNG(1,3);
        if (rngVal == 1) bossStance = "sword";
        else if (rngVal == 2) bossStance = "shield";
        else {
            bossStance = "bow";
            enemyQuiver = 7;
        }
        System.out.println("The enemy is using their " + bossStance + "!");
        isBoss = true;
        enemyHP = 1000;
        genericRNG(1250,150);
        enemyDamage = rngVal;
        genericRNG(950,100);
        enemySpeed = rngVal;
        genericRNG(1050,100);
        enemyDefense = rngVal;
        enemyPower = (enemyHP + enemyDamage + enemySpeed + enemyDefense)/4;
        enemyName = "The Last";
        System.out.println("Your power: " + Math.round(playerPower));
        System.out.println("Enemy power: " + Math.round(enemyPower));
        Thread.sleep(250);
        System.out.println("CLAIM YOUR PRIZE");
        System.out.println("-----------------------------------------------------------");
    }
    static void BossAI() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
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
        if (bossStance.contains("sword")) SwordsmanAI();
        else if (bossStance.contains("bow")) bowmanAI();
        else shieldsmanAI();
    }

    protected static void combatMusic(int trackNum) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        music.combatSetFile(trackNum);
        music.combatStartClip();
        music.combatLoopClip();
        musicPlaying = true;
    }

    protected static void combatStopMusic() throws LineUnavailableException, IOException {
        if (Music.dayMusic != null) {
            music.combatStopClip();
            musicPlaying = false;
        } else System.out.println("Clip is already stopped or not initialized.");
    }
    protected static void combatSoundEffect(int trackNum) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        music.setFile(trackNum);
        music.startClip();
    }
}
