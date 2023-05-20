import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Combat extends Primary {

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

    //Armor checker,lets you still hit the opponent even if your move was slower or weaker.
    protected static boolean hasArmor = false;

    protected static boolean playerIsActionable = true;

    protected static boolean enemyIsActionable = true;

    //Boolean for whether Ki Charge has been used or not
    protected static boolean swordCharged = false;
    
    protected static double playerDamage;
    
    protected static double attackSpeed;
    
    protected static double attackDefense;

    //The reason why I use these variables instead of the ones in Primary is because of weaken. That way,
    //the player's stats are only weakened for the duration of the fight.


    //For grab type moves
    protected static boolean ignoresBlock = false;



    public static void fight() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (moveSet.containsValue("Swap")) shieldHand = "D";
        else shieldHand = "";
        playerDamage = weapondmg;
        attackSpeed = playerSpeed;
        attackDefense = playerDefense;
        enemyTypeSelector();
        startCombat();
        while (isFighting) {
            resetCombatVar();
            if (unactionableTurns >= 1) playerIsActionable = false;
            if (enemyUnactionableTurns >= 1) enemyIsActionable = false;
            hasConfirmedCombat = false;
            while (!hasConfirmedCombat) {
                if (playerIsActionable) {
                    turnLoop();
                    playerResponse();
                    if (answer == 1) {
                        if (weaponName.contains("Axe")) axeSelection();
                        if (weaponName.contains("Sword")) swordSelection();
                        if (weaponName.contains("Shield")) shieldSelection();
                    } else if (answer == 2) playerMove();
                    else if (answer == 3) playerBlock();
                    else if (answer == 4) utilityItem();
                    else if (answer >= 5) playerWait();
                }
                else {
                    hasConfirmedCombat = true;
                }
            }
            tempVal = enemyStamina;
            if (enemyIsActionable) {
                 if (enemyName.contains("Sword")) SwordsmanAI();
                 if (enemyName.contains("Shields")) shieldsmanAI();
                 if (enemyName.contains("Bow")) strongbowmanAI();
                 if (enemyName.contains("Bear")) bearAI();
              //  if (enemyName.equals("The Last")) BossAI();
                if (fightRange < 0) fightRange = 0;
                if (fightRange > 8) fightRange = 8;
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
                break;
            }
        }

    }
    static void startCombat() throws InterruptedException {
        if (enemyName.contains("Weak Swords-man")) System.out.println("A weak swordsman attacks you!");
        if (enemyName.contains("Strong Swords-man")) System.out.println("An experienced swordsman forces you into a duel...");
        if (enemyName.contains("Weak Shields-man")) System.out.println("A weak shieldsman attacks you!");
        if (enemyName.contains("Strong Shields-man")) System.out.println("An experienced shieldsman forces you into a duel...");
        if (enemyName.contains("Strong Bowman")) System.out.println("You get the strange feeling someone is aiming at you...");
        if (enemyName.contains("Bear")) System.out.println("A deep growl sends shivers down your spine...");
        Thread.sleep(1000);
        System.out.println("Your power: " + Math.round(playerPower));
        System.out.println("Enemy power: " + Math.round(enemyPower));
        Thread.sleep(250);
        System.out.println("BEGINNING COMBAT");
        System.out.println("-----------------------------------------------------------");
    }

    static void enemyTypeSelector() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
      if (inWildlands) {
          rngValmin = 1;
          rngValmax = 2;
          genericRNG();
          if (rngVal == 1) enemyName = "Weak Shields-man";
          else enemyName = "Weak Swords-man";
      }
          if (enemyName.contains("Weak Shields-man")) {
              enemyHP = 135;
              rngValmin = 55;
              rngValmax = (day * level);
              genericRNG(); // This is for dmg
              enemyDamage = rngVal;
              rngValmin = 80;
              rngValmax = (day * level);
              genericRNG(); // This is for speed
              enemySpeed = rngVal;
              rngValmin = 100;
              genericRNG(); // This is for defense
              enemyDefense = rngVal;
          }
          if (enemyName.contains("Weak Swords-man")) {
              enemyHP = 100;
              rngValmin = 90;
              rngValmax = (day * level);
              genericRNG(); // This is for dmg
              enemyDamage = rngVal;
              genericRNG(); // This is for speed
              enemySpeed = rngVal;
              rngValmin = 60;
              genericRNG(); // This is for defense
              enemyDefense = rngVal;
          } else if (inMountains) {
              rngValmin = 1;
              rngValmax = 7;
              genericRNG();
              if (rngVal == 2) enemyName = "Bear";
              else if (rngVal == 4 || rngVal == 5) enemyName = "Strong Bowman";
              else if (rngVal == 6) enemyName = "Strong Shields-man";
              else enemyName = "Strong Swords-man";
              if (enemyName.contains("Strong Swords-man")) {
                  enemyHP = 150;
                  rngValmin = 145;
                  rngValmax = (int) ((day * level) * 1.25);
                  genericRNG(); // This is for dmg
                  enemyDamage = rngVal;
                  rngValmin =
                          rngValmax = (int) ((day * level) * 1.25);
                  genericRNG(); // This is for speed
                  enemySpeed = rngVal;
                  rngValmin = 90;
                  genericRNG(); // This is for defense
                  enemyDefense = rngVal;
              }
              if (enemyName.contains("Bear")) {
                  enemyHP = 240;
                  rngValmin = 165;
                  rngValmax = (int) ((day * level) * 1.25);
                  genericRNG(); // This is for dmg
                  enemyDamage = rngVal;
                  rngValmin = 150;
                  rngValmax = (int) ((day * level) * 1.25);
                  genericRNG(); // This is for speed
                  enemySpeed = rngVal;
                  rngValmin = 105;
                  genericRNG(); // This is for defense
                  enemyDefense = rngVal;
              }
              if (enemyName.contains("Strong Bowman")) {
                  enemyHP = 185;
                  rngValmin = 120;
                  rngValmax = (day * level);
                  genericRNG(); // This is for dmg
                  enemyDamage = rngVal;
                  rngValmin = 75;
                  rngValmax = (day * level);
                  genericRNG(); // This is for speed
                  enemySpeed = rngVal;
                  rngValmin = 75;
                  genericRNG(); // This is for defense
                  enemyDefense = rngVal;
              }
              if (enemyName.contains("Strong Shields-man")) {
                  enemyHP = 225;
                  rngValmin = 65;
                  rngValmax = (day * level);
                  genericRNG(); // This is for dmg
                  enemyDamage = rngVal;
                  rngValmin = 110;
                  rngValmax = (day * level);
                  genericRNG(); // This is for speed
                  enemySpeed = rngVal;
                  rngValmin = 120;
                  genericRNG(); // This is for defense
                  enemyDefense = rngVal;
              }
          }
          //sound effects

          if (enemyName.contains("Sword")) Music.soundEffect(4);
          enemyPower = (enemyHP + enemyDamage + enemySpeed + enemyDefense) / 4;
      }
    static void turnLoop() throws InterruptedException {
        //This just prints all the data the player needs to make their decisions.
        System.out.println("Your health: " + Math.round(playerHP));
        System.out.println("Your stamina: " + playerStamina);
        if (weather != 3)/*weather 3  is duststorm */ {
            System.out.println("Enemy health: " + Math.round(enemyHP));
            System.out.println("Enemy stamina: " + Math.round(enemyStamina));
            System.out.println("Distance: " + fightRange);
        }
        else System.out.println("The storm blinds you, not allowing you to see your opponent...");
        System.out.println("Super: " + superMeter);
        System.out.println("Turn: " + turn);
        Thread.sleep(500);
        System.out.println("1: Select Attack ");
        System.out.println("2: Move ");
        System.out.println("3: Block ");
        System.out.println("4: Utility Items");
        System.out.println("5: Wait ");
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
        else
        {
            System.out.println("You did not confirm.");
        }
    }

    static void playerMove() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        //Repostion to set up a sweet spot or to get to a safe range to wait.
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
                fightRange += 1;
                {
                    if (fightRange > 8) {
                        System.out.println("You cannot move further back. Please re-input your movement option.");
                        fightRange -=1;
                    } else {
                        moveName = "Backpedal";
                        playerHasWhiffed = false;
                        playerIsAttacking = false;
                        playerIsBlocking = false;
                        hasConfirmedCombat = true;
                        hasConfirmedMove = true;
                        playerStamina += 5;
                        Music.soundEffect(11);
                    }
                }
            }
           else if (answerType.contains("D") || answerType.contains("d")) {
                fightRange -=1;
                if (fightRange == 0) {
                    System.out.println("You cannot move into your opponent. Please re-input your movement option.");
                    fightRange += 1;
                } else {
                    moveName = "Dash";
                    playerIsAttacking = false;
                    playerIsBlocking = false;
                    playerHasWhiffed = false;
                    hasConfirmedCombat = true;
                    hasConfirmedMove = true;
                    playerStamina += 10;
                    Music.soundEffect(11);
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
            moveSpeed = -1;
            hasConfirmedCombat = true;
        }
    }
    static void universalMoves() throws InterruptedException {
        //Right now it's only shove, but any move that's possible by any weapon will be listed here
        if (moveName.contains("Shove")) {
            moveRange = 1;
            moveDamage = (playerDamage * 0.25) * (100 / (100 + enemyDefense));
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
        playerIsBlocking = false;
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
            else  hasConfirmedAtk = true;
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
                System.out.println("Range: 2");
                System.out.println("Cost: " + staminaCost + " Stamina");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.75)));
                System.out.println("Damage: Medium");

                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    moveDamage = (playerDamage * 0.5) * (100 / (100 + enemyDefense));
                    moveRange = 2;
                    moveSpeed = (attackSpeed * 0.75);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Downward Slice")) {
                staminaCost = 30;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Slashes at your opponent's legs.");
                System.out.println("Range: 2");
                System.out.println("Cost: " + staminaCost + " Stamina");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.7)));
                System.out.println("Damage: Medium+");
System.out.println("Cost: " + staminaCost + " Stamina");
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    moveDamage = (playerDamage * 0.65) * (100 / (100 + enemyDefense));
                    moveRange = 2;
                    moveSpeed = (attackSpeed * 0.7);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
            playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Pommel")) {
                staminaCost = 45;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Quickly smashes the hilt of your sword against your opponent.");
                System.out.println("Range: 1");
                System.out.println("Cost: " + staminaCost + " Stamina");
                System.out.println("Speed: " + Math.round((attackSpeed)));
                System.out.println("Damage: Medium");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    moveDamage = (playerDamage * 0.35) * (100 / (100 + enemyDefense));
                    moveRange = 2;
                    moveSpeed = (attackSpeed);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
            playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Shove")) {
                System.out.println("Pushes the enemy backwards and creates some space.");
                System.out.println("Range:1");
                System.out.println("Cost 0 Stamina");
                System.out.println("Speed: " + Math.round((attackSpeed * 1.2)));
                System.out.println("Damage: Medium");
                    System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    universalMoves();
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
            playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Thrust")) {
                staminaCost = 30;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Dash forward and poke with the very edge of your sword.");
                System.out.println("Range: 4");
                System.out.println("Cost: " + staminaCost + " Stamina");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.4)));
                System.out.println("Damage: High");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    moveRange = 4;
                    moveDamage = (playerDamage * 1.1) * (100 / (100 + enemyDefense));
                    moveSpeed = (attackSpeed * 0.4);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
            playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Phantom Slash") && hasSuper) {
                System.out.println("Ultimate: Phantom SLash: Strike faster then the eye can see, ignoring defense.");
                System.out.println("Range: 3");
                System.out.println("Speed: " + Math.round((attackSpeed * 1.2)));
                System.out.println("Damage: Extremely High");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    moveRange = 3;
                    moveDamage = (playerDamage * 1.7);
                    if (tempMove.contains("2")) {
                        tempVal = 200 + (level*50);
                        moveDamage += 1.5*(tempVal - playerHP);
                    }
                    moveSpeed = (attackSpeed * 1.2);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    superMeter = 0;
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Phantom Slash") && !hasSuper) {
                System.out.println("You don't have your super yet.");
            }
            else if (tempMove.contains("Ki Charge")) {
                System.out.println("Ki Charge: Powers up the blade, giving you extra range, and bonus damage if spaced.");
                System.out.println("Range: N.A");
                System.out.println("Speed: N.A");
                System.out.println("Damage: N.A");
                System.out.println("Cost: 0 Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    playerIsAttacking = false;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    swordCharged = true;
                    Music.soundEffect(9);
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Whirlwind")) {
                staminaCost = 45;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Whirlwind: A giant swinging attack that deals massive stamina damage.");
                System.out.println("Range: 3");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.45)));
                System.out.println("Damage: High");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    staminaDamage = 70;
                    moveName = tempMove;
                    moveRange = 3;
                    moveDamage = (playerDamage * 0.9) * (100/ (100/enemyDefense));
                    if (tempMove.contains("2")) moveDamage += moveDamage;
                    moveSpeed = (attackSpeed * 0.5);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
            playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            }
            else if (tempMove.contains("Rising Upper")) {
                staminaCost = 40;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Rising Upper: Moves forward and slashes upwards, knocking the opponent back.");
                System.out.println("Range: 2");
                System.out.println("Cost: " + staminaCost + " Stamina");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.6)));
                System.out.println("Damage: Medium");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    moveRange = 2;
                    moveDamage = (playerDamage * 0.7) * (100/ (100/enemyDefense));
                    if (tempMove.contains("2")) CC += "Stun";
                    moveSpeed = (attackSpeed * 0.6);
                    CC += "Boop";
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
            playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            }
            else if (tempMove.contains("Vanishing Strike")) {
                staminaCost = 35;
               if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println(answer + ": Vanishing Strike: Teleport to a location and slash your opponent.");
                System.out.println("Range: 1");
                System.out.println("Cost: " + staminaCost + " Stamina");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.8)));
                System.out.println("Damage: Medium+");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
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
                    moveName = "Vanishing Strike";
                    moveRange = 1;
                    moveDamage = (playerDamage * 0.75) * (100/ (100/enemyDefense));
                    moveSpeed = (attackSpeed * 0.8);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
            playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            }
        }
        //Strike sfx
        //I check for these moves because they don't involve the blade of the sword, so no blade sfx.
        if (playerIsAttacking && !tempMove.contains("Shove") && !tempMove.contains("Ki Charge") && !tempMove.contains("Pommel")){
            rngValmin = 5;
            rngValmax = 2;
            genericRNG();
            Music.soundEffect(rngVal);
        }
    }

    static void shieldSelection() throws InterruptedException {
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
        if (!hasShield) System.out.println("You don't have your shield...");
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
            else if (answer == 0) hasConfirmedAtk = true;
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
                System.out.println("Dashes forward and slams against the opponent.");
                System.out.println("Range: 2");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.65)));
                System.out.println("Damage: Medium");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Bash";
                    moveDamage = (playerDamage * 0.7) * (100 / (100 + enemyDefense));
                    moveRange = 2;
                    moveSpeed = (attackSpeed * 0.65);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Uppercut")) {
                staminaCost = 20;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Knocks your opponent upwards and away.");
                System.out.println("Range: 2");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.65)));
                System.out.println("Damage: Medium");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Uppercut";
                    moveDamage = (playerDamage * 0.6) * (100 / (100 + enemyDefense));
                    moveRange = 1;
                    moveSpeed = (attackSpeed * 0.65);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    CC += "Boop";
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Slam")) {
                staminaCost = 35;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Jump up and smash your shield against the opponent. Does bonus damage if 2 spaces away.");
                System.out.println("Range: 2");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.7)));
                System.out.println("Damage: High");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Slam";
                    moveDamage = (playerDamage * 1.1) * (100 / (100 + enemyDefense));
                    moveRange = 2;
                    moveSpeed = (attackSpeed * 0.7);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Shove")) {
                System.out.println("Pushes the enemy backwards and creates some space.");
                System.out.println("Range:1");
                System.out.println("Speed: " + Math.round((attackSpeed * 1.2)));
                System.out.println("Damage: Medium");
                System.out.println("Cost: 0 Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Shove";
                    universalMoves();
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Shield Punch")) {
                staminaCost = 40;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Shield Punch: Sends a shockwave that knocks the opponent back.");
                System.out.println("Range: 2");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.6)));
                System.out.println("Damage: High");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    staminaDamage = 25;
                    moveName = "Shield Punch";
                    moveRange = 2;
                    moveDamage = (playerDamage * 0.9) * (100 / (100 + enemyDefense));
                    moveSpeed = (attackSpeed * 0.6);
                    playerIsAttacking = true;
                    CC += "Boop";
                    ccAmount = 2;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Shield Sweep")) {
                staminaCost = 35;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Shield Sweep: Spin around, striking your opponents ankles, lowering their stamina heavily.");
                System.out.println("Range: 2");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.4)));
                System.out.println("Damage: Medium");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    staminaDamage = 65;
                    moveName = "Shield Sweep";
                    moveRange = 2;
                    moveDamage = (playerDamage * 0.7) * (100 / (100 + enemyDefense));
                    moveSpeed = (attackSpeed * 0.4);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Earthquake") && hasSuper) {
                System.out.println("Ultimate: Earthquake: Slams the shield into the ground, stunning the enemy.");
                System.out.println("Range: 3");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.65)));
                System.out.println("Damage: Extremely High");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    staminaDamage = 85;
                    moveName = "Earthquake";
                    moveRange = 3;
                    moveDamage = (playerDamage * 1.2) * (100 / (100 + enemyDefense));
                    moveSpeed = (attackSpeed * 0.65);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    superMeter = 0;
                }
                else {
                    hasConfirmedAtk = true;
                }
            }
            else if (tempMove.contains("Earthquake") && !hasSuper)
            {
                System.out.println("You don't have your super yet.");
            }
            else if (tempMove.contains("")) {
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
                        System.out.println("Tai Otoshi: Grabs the opponent, throwing them down to the ground. Ignores block.");
                        System.out.println("Cost: " + staminaCost + " Stamina");
                        System.out.println("Press 1 to confirm, or press another to reselect.");
                        playerResponse();
                        if (answer == 1) {
                            moveName = "Tai Otoshi";
                            moveRange = 1;
                            moveSpeed = attackSpeed * 0.8;
                            moveDamage = (playerDamage * 0.8) * (100 / (100 + enemyDefense));
                            playerIsAttacking = true;
                            hasConfirmedAtk = true;
                            ignoresBlock = true;
                            hasConfirmedCombat = true;
                        } else {
                            hasConfirmedAtk = true;
                        }
                    } else if (shieldHand.contains("D")) {
                        staminaCost = 25;
                        if (staminaCost >= playerStamina ) {
                            System.out.println("Not enough stamina.");
                            break;
                        }
                        System.out.println("Retreating Strike: Dodge backwards, before kicking your opponent.");
                        System.out.println("Cost: " + staminaCost + " Stamina");
                        System.out.println("Press 1 to confirm, or press another to reselect.");
                        playerResponse();
                        if (answer == 1) {
                            fightRange += 1;
                            moveName = "Retreating Strike";
                            moveRange = 3;
                            moveSpeed = attackSpeed * 0.7;
                            moveDamage = (playerDamage * 0.6) * (100 / (100 + enemyDefense));
                            CC += "Boop";
                            ccAmount = 1;
                            playerIsAttacking = true;
                            hasConfirmedAtk = true;
                            hasConfirmedCombat = true;
                        }
                    }
                } else {
                    System.out.println("Off hand: Deals bonus damage. Dominant hand: Increased speed");
                    System.out.println("Range: N.A");
                    System.out.println("Speed: N.A");
                    System.out.println("Damage: N.A");
                    Thread.sleep(500);
                    System.out.println("Press 1 to confirm, or press another to reselect.");
                    playerResponse();
                    if (answer == 1) {
                        moveName = "";
                        if (shieldHand.contains("D")) shieldHand = "O";
                        else shieldHand = "D";
                        playerIsAttacking = false;
                        hasConfirmedAtk = true;
                        hasConfirmedCombat = true;
                    } else {
                        hasConfirmedAtk = true;
                    }
                }
            }
            else if (tempMove.contains("Deflect")) {
                staminaCost = 55;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Deflect: Parries the opponent's strike, stunning them");
                System.out.println("Range: 1");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.8)));
                System.out.println("Damage: N.A");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Deflect";
                    moveRange = 2;
                    moveSpeed = (attackSpeed * 0.8);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
        }
     else if (tempMove.contains("Shield Toss")) {
                staminaCost = 30;
                if (staminaCost >= playerStamina ) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Shield Toss: Spin around, striking your opponents ankles, lowering their stamina heavily.");
                System.out.println("Range: 5");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.6)) + "(dependant on distance)");
                System.out.println("Damage: Medium (dependant on spacing)");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Shield Toss";
                    moveRange = 5;
                    moveDamage = (playerDamage * 0.7) * (100 / (100 + enemyDefense) - (playerDamage/fightRange));
                    moveSpeed = (attackSpeed * 0.6)/fightRange;
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    hasShield = false;
                    playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            }
    }
    }

    static void axeSelection() throws InterruptedException {
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
            for (int count = 1; count < 6; count++) {
                System.out.println(count + ": " + moveSet.get("Move " + count) + ".");
            }
            System.out.println("Or press 0 to go back.");
            playerResponse();
            if (answer != 0) tempMove = moveSet.get("Move " + answer);
            else if (answer == 0) hasConfirmedAtk = true;
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
                System.out.println("Range: 2");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.7)));
                System.out.println("Damage: Medium");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = tempMove;
                    moveDamage = (playerDamage * 0.55) * (100 / (100 + enemyDefense));
                    moveRange = 2;
                    moveSpeed = (attackSpeed * 0.7);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Shatter")) {
                staminaCost = 35;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Plunges the blunt side of the axe into your opponent, doing major damage and knocking the enemy backwards.");
                System.out.println("Range: 2");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.6)));
                System.out.println("Damage: Extremely high");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Shatter";
                    moveDamage = (playerDamage * 1.35) * (100 / (100 + enemyDefense));
                    moveRange = 2;
                    moveSpeed = (attackSpeed * 0.6);
                    playerIsAttacking = true;
                    CC += "Boop";
                    ccAmount = 2;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Butcher")) {
                staminaCost = 55;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Uses the axe like a knife for quick slashes.");
                System.out.println("Range:1");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.8)));
                System.out.println("Damage: Medium");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Butcher";
                    moveRange = 1;
                    moveDamage = (playerDamage * 0.7) * (100 / (100 + enemyDefense));
                    moveSpeed = (attackSpeed * 0.8);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                }
                else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Shove")) {
                System.out.println(" Pushes the enemy backwards and creates some space.");
                System.out.println("Range:1");
                System.out.println("Speed: " + Math.round((attackSpeed * 1.2)));
                System.out.println("Damage: Medium");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Shove";
                    universalMoves();
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Skewer")) {
                staminaCost = 40;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Drags the opponent closer to you.");
                System.out.println("Range: 3");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.65)));
                System.out.println("Damage: Medium");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Skewer";
                    moveRange = 3;
                    moveDamage = (playerDamage * 0.4) * (100 / (100 + enemyDefense));
                    moveSpeed = (attackSpeed * 0.65);
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    CC += "Suck";
                    ccAmount = 1;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {

                    hasConfirmedAtk = true;
                }

            } else if (tempMove.contains("Eradicate") && hasSuper) {
                System.out.println("Ultimate: Eradicate: Gives you better ignite for 5 turns, giving bonus burn,armor, as well as stun on moves with cc");
                System.out.println("Range: N.A");
                System.out.println("Speed: N.A");
                System.out.println("Damage: N.A");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Eradicate";
                    playerIsAttacking = false;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    superMeter = 0;
                    eradicateTurns = 5;
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Ignite")) {
                System.out.println("The next strike does burn damage.");
                System.out.println("Range: N.A");
                System.out.println("Speed: N.A");
                System.out.println("Damage: N.A");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Ignite";
                    playerIsAttacking = false;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    isIgnited = true;
                    playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Overhead Cleave")) {
                staminaCost = 40;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println("Swings downward at your opponent, slowing the opponent on hit.");
                System.out.println("Range: 2");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.55)));
                System.out.println("Damage: Mid");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Overhead Cleave";
                    moveRange = 2;
                    moveDamage = (playerDamage * 0.8) * (100 / (100 + enemyDefense));
                    moveSpeed = (attackSpeed * 0.55);
                    CC += "Weaken Speed";
                    ccAmount = 2;
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Weaken")) {
                staminaCost = 35;
                if (staminaCost >= playerStamina) {
                    System.out.println("Not enough stamina.");
                    break;
                }
                System.out.println(" Bashes your opponent's head with the blunt side of the axe, lowering the opponent's damage.");
                System.out.println("Range: 2");
                System.out.println("Speed: " + Math.round((attackSpeed * 0.6)));
                System.out.println("Damage: Mid");
                System.out.println("Cost: " + staminaCost + " Stamina");
                Thread.sleep(500);
                System.out.println("Press 1 to confirm, or press another to reselect.");
                playerResponse();
                if (answer == 1) {
                    moveName = "Cripple";
                    moveRange = 2;
                    moveDamage = (playerDamage * 0.40) * (100 / (100 + enemyDefense));
                    moveSpeed = (attackSpeed * 0.6);
                    CC += "Weaken Damage";
                    ccAmount = 2;
                    playerIsAttacking = true;
                    hasConfirmedAtk = true;
                    hasConfirmedCombat = true;
                    playerStamina -= staminaCost;
                } else {
                    hasConfirmedAtk = true;
                }
            } else if (tempMove.contains("Pyroslash")) {
                staminaCost = 35;
            if (staminaCost >= playerStamina ) {
                System.out.println("Not enough stamina.");
                break;
            }
            System.out.println("A slow flame wave that ignites the opponent on hit.");
            System.out.println("Range: Infinite");
            System.out.println("Speed: " + Math.round((attackSpeed * 0.6)));
            System.out.println("Damage: Mid");
            System.out.println("Cost: " + staminaCost + " Stamina");
            Thread.sleep(500);
            System.out.println("Press 1 to confirm, or press another to reselect.");
            playerResponse();
            if (answer == 1) {
                moveName = "Pyroslash";
                moveSpeed = (attackSpeed * 0.6);
                playerIsAttacking = true;
                hasConfirmedAtk = true;
                hasConfirmedCombat = true;
                playerStamina -= staminaCost;
            }
            else {
                hasConfirmedAtk = true;
            }
        }
        }
    }

    static void utilityItem() throws InterruptedException {
        /*
        Right now the utility list is this:
        Grappling Hook: +1 range, -20% speed
        Regenerative Stone: 20% heal
        Soul Snatcher: ~15% of opponent's HP (needs to be looked at, seems broken)
         */
        System.out.println("Select your utility, or press 0 to leave.");
        boolean hasConfirmedUtil = false;
        while (!hasConfirmedUtil) {
            if (grapplingHook > 0) {
                System.out.println("1: Grappling Hook");
            }
            if (regenerativeStone > 0) {
                System.out.println("2: Regenerative Stone");
            }
            if (soulSnatcher > 0) {
                System.out.println("3: Soul Snatcher");
            }
            playerResponse();
            if (answer == 0)
            {
                hasConfirmedUtil = true;
            }
            if (answer == 1)
            {
                System.out.println("You are using grappling hook. Press 1 to confirm, or press another number to change.");
                playerResponse();
                if (answer == 1)
                {
                    grapplingHook -= 1;
                    hasGrapplingHook = true;
                    hasConfirmedCombat = true;
                    hasConfirmedUtil = true;
                    System.out.println("Grappling hook engaged!");
                }
            }
            if (answer == 2)
            {
                System.out.println("You are using a regenerative stone. Press 1 to confirm, or press another number to change.");
                playerResponse();
                if (answer == 1)
                {
                    regenerativeStone -= 1;
                    tempVal2 = playerHP;
                    tempVal = playerHP/5;
                    playerHP += tempVal;
                    System.out.println("You recovered " + (playerHP - tempVal2) + " hp!");
                    hasConfirmedCombat = true;
                    hasConfirmedUtil = true;
                }
            }
            if (answer == 3)
            {
                System.out.println("You are using a Soul Snatcher. Press 1 to confirm, or press another number to change.");
                playerResponse();
                if (answer == 1)
                {
                    soulSnatcher -= 1;
                    tempVal2 = enemyHP;
                    tempVal = enemyHP/6;
                    enemyHP -= tempVal;
                    System.out.println("You removed " + (tempVal2 - enemyHP) + " hp!");
                    hasConfirmedCombat = true;
                    hasConfirmedUtil = true;
                    //might rework this into a move... seems kinda broken LMAO
                }
            }
        }
    }

    static void SwordsmanAI() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        //The AI is RNG with odds that change depending on fight circumstances, like hp remaining, distance, or the player's weapon.
        rngValmin = 1;
        if (enemyName.contains("Weak Swords-man")) {
            rngValmax = 9;
        } else rngValmax = 10;
        enemyConfirm = false;
        while (!enemyConfirm) {
            genericRNG();
            rngbaddie = rngVal;
            //System.out.println("rngVal" + rngVal);
            if (fightRange <= 2) {
                if (rngbaddie == 1 || rngbaddie == 2) {
                    enemymoveName = "Wait";
                    enemyStamina += 20;
                }
                if (rngbaddie == 3 || rngbaddie == 4) {
                    if (enemyStamina > 20) {
                        enemyStamina -= 20;
                        enemyIsAttacking = true;
                        enemymoveName = "Forward Swipe";
                        enemymoveRange = 2;
                        enemymoveSpeed = (enemySpeed * 0.75);
                        enemymoveDamage = (enemyDamage * 0.7) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                }
                if (rngbaddie == 5) {
                    fightRange -= 1;
                    enemymoveName = "Walk Forward";
                    Music.soundEffect(11);
                }
                if (rngbaddie == 6) {
                    if (enemyStamina > 25) {
                        enemyStamina -= 25;
                        enemyIsAttacking = true;
                        enemyIsBlocking = false;
                        enemymoveName = "Stab";
                        enemymoveRange = 2;
                        enemymoveSpeed = (enemySpeed * 0.9);
                        enemymoveDamage = (enemyDamage * 0.65) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                }
                if (rngbaddie == 7) {
                    if (enemyStamina > 45) {
                        enemyStamina -= 45;
                        enemyStaminaDamage = 70;
                        enemyIsAttacking = true;
                        enemymoveName = "Whirlwind";
                        enemymoveRange = 3;
                        enemymoveSpeed = (enemySpeed * 0.3);
                        enemymoveDamage = (enemyDamage * 1.1) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                }
                if (rngbaddie == 8) {
                    fightRange += 1;
                    enemymoveName = "Walk Backwards";
                    enemyStamina += 5;
                }
                if (rngbaddie == 9 || rngbaddie == 10) {
                    enemymoveName = "Block";
                    enemyIsBlocking = true;
                }
                if (rngbaddie == 11 && !enemyName.contains("Weak Swords-man")) {
                    if (enemyStamina >= 40) {
                        enemyStamina -= 40;
                        enemyIsAttacking = true;
                        enemymoveName = "Ki Slash";
                        enemymoveRange = 3;
                        enemymoveSpeed = enemySpeed * (0.7 / fightRange);
                        enemymoveDamage = (enemyDamage * 0.6) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                }
            } else {
                rngValmin = 1;
                if (enemyName.contains("Weak Swords")) {
                    rngValmax = 5;
                } else rngValmax = 6;
                genericRNG();
                rngbaddie = rngVal;
                if (rngbaddie == 1) {
                    enemymoveName = "Walk Forward";
                    fightRange -= 1;
                    enemyStamina += 10;
                    Music.soundEffect(11);
                } else if (rngbaddie == 4 && !enemyName.contains("Weak") || rngbaddie == 5 && !enemyName.contains("Weak")) {
                    if (enemyStamina >= 40) {
                        enemyStamina -= 40;
                        enemyIsAttacking = true;
                        enemymoveName = "Ki Slash";
                        enemymoveRange = 3;
                        enemymoveSpeed = enemySpeed * (0.7 / fightRange);
                        enemymoveDamage = (enemyDamage * 0.6) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                } else if (rngbaddie == 2) {
                    if (enemyStamina >= 65) {
                        enemyStamina -= 65;
                        enemyStaminaDamage = 65;
                        enemyIsAttacking = true;
                        enemymoveName = "Whirlwind";
                        enemymoveRange = 3;
                        enemymoveSpeed = (enemySpeed * 0.3);
                        enemymoveDamage = (enemyDamage * 1.1) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                } else {
                    enemyIsAttacking = false;
                    enemymoveName = "Wait";
                    enemyStamina += 20;
                }
            }
        }
        if (enemyIsAttacking && !enemymoveName.contains("Shove")) {
            rngValmin = 5;
            rngValmax = 2;
            genericRNG();
            Music.soundEffect(rngVal);
        }
    }


    static void shieldsmanAI() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        //The AI is RNG with odds that change depending on fight circumstances, like hp remaining, distance, or the player's weapon.
        rngValmin = 1;
        if (enemyName.contains("Weak Shields-man")) {
            rngValmax = 9;
        } else  {
            rngValmax = 10;
        }
        genericRNG();
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
                        enemyIsAttacking = true;
                        enemymoveName = "Bash";
                        enemymoveRange = 2;
                        enemymoveSpeed = (enemySpeed * 0.6);
                        enemymoveDamage = (enemyDamage * 0.65) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                }
                if (rngbaddie == 5) {
                    fightRange -= 1;
                    enemymoveName = "Walk Forward";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyStamina += 10;
                    Music.soundEffect(11);
                    enemyConfirm = true;
                }
                if (rngbaddie == 6) {
                    if (enemyStamina >= 35) {
                        enemyStamina -= 35;
                        enemyIsAttacking = true;
                        enemymoveName = "Uppercut";
                        enemymoveRange = 1;
                        enemymoveSpeed = (enemySpeed * 0.75);
                        enemymoveDamage = (enemyDamage * 0.5) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                }
                if (rngbaddie == 7) {
                    if (enemyStamina >= 25) {
                        enemyStamina -= 25;
                        enemyStaminaDamage = 30;
                        enemyIsAttacking = true;
                        enemymoveName = "Side Kick";
                        enemymoveRange = 3;
                        enemymoveSpeed = (enemySpeed * 0.9);
                        enemymoveDamage = (enemyDamage * 0.4) * (100 / (100 + attackDefense));
                        enemyCC += "Boop";
                        enemyCCAmount = 2;
                        enemyConfirm = true;
                    }
                }
                if (rngbaddie == 8) {
                    fightRange -= 1;
                    enemymoveName = "Walk Backwards";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyStamina += 5;
                    enemyStamina += 5;
                    Music.soundEffect(11);
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
                        enemyStamina -= 45;
                        enemyStaminaDamage = 70;
                        enemyIsAttacking = true;
                        enemymoveName = "Grapple";
                        enemymoveRange = 1;
                        enemymoveSpeed = (enemySpeed * 1.1);
                        enemymoveDamage = (enemyDamage * 0.3) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                }
            } else {
                enemymoveName = "Walk Forward";
                enemyIsAttacking = false;
                enemyHasWhiffed = false;
                fightRange -= 1;
                enemyStamina += 10;
                enemyConfirm = true;
            }
        }
    }
    static void strongbowmanAI() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        //The AI is RNG with odds that change depending on fight circumstances, like hp remaining, distance, or the player's weapon.
        rngValmin = 1;
        rngValmax = 10;
        genericRNG();
        rngbaddie = rngVal;
        //System.out.println("rngVal" + rngVal);
        enemyConfirm = false;
        while (!enemyConfirm) {
            if (fightRange <= 2) {
                if (rngbaddie == 1) {
                    enemymoveName = "Wait";
                    enemyHasWhiffed = false;
                    enemyIsAttacking = false;
                    enemyIsBlocking = false;
                    enemyStamina += 20;
                }
                if (rngbaddie == 3) {
                    if (enemyStamina >= 20) {
                        enemyStaminaDamage = 25;
                        enemyIsAttacking = true;
                        enemymoveName = "Bow Swipe";
                        enemymoveRange = 2;
                        enemymoveSpeed = (enemySpeed * 0.55);
                        enemymoveDamage = (enemyDamage * 0.6) * (100 / (100 + attackDefense));
                    }
                }
                if (rngbaddie == 5) {
                    fightRange -= 1;
                    enemymoveName = "Walk Forward";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyStamina += 10;
                    Music.soundEffect(11);
                }
                if (rngbaddie == 6) {
                    if (enemyQuiver > 0) {
                        enemyStaminaDamage = 5;
                        enemyIsAttacking = true;
                        enemymoveName = "Quick Shot";
                        enemymoveRange = 2;
                        enemymoveSpeed = (enemySpeed * 0.65);
                        enemymoveDamage = (enemyDamage * 0.5) * (100 / (100 + attackDefense));
                        enemyQuiver -= 1;
                    } else {
                        System.out.println("The enemy is out of arrows!");
                        enemyHasWhiffed = true;
                    }
                }
                if (rngbaddie == 7) {
                    if (enemyStamina >= 25) {
                        enemyStamina -= 25;
                        enemyIsAttacking = true;
                        enemymoveName = "Side Kick";
                        enemymoveRange = 2;
                        enemymoveSpeed = (enemySpeed * 0.85);
                        enemymoveDamage = (enemyDamage * 0.35) * (100 / (100 + attackDefense));
                    }
                }
                if (rngbaddie == 8 || rngbaddie == 2 || rngbaddie == 3) {
                    fightRange -= 1;
                    enemymoveName = "Walk Backwards";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyStamina += 5;
                    Music.soundEffect(11);
                }
                if (rngbaddie == 9) {
                    enemymoveName = "Block";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyIsBlocking = true;
                }
                if (rngbaddie == 10) {
                    if (enemyQuiver < 5) {
                        enemyIsAttacking = false;
                        enemyIsBlocking = false;
                        enemyHasWhiffed = true;
                        enemymoveName = "Reload";
                        enemyQuiver = 5;
                    } else {
                        enemymoveName = "Wait";
                        enemyHasWhiffed = false;
                        enemyIsAttacking = false;
                        enemyIsBlocking = false;
                        enemyStamina += 20;
                    }
                }
            } else if (fightRange <= 5) {
                enemyIsAttacking = true;
                enemymoveName = "Straight Shot";
                enemymoveRange = 5;
                enemymoveSpeed = (enemySpeed * 0.65 / fightRange);
                enemymoveDamage = (enemyDamage * 0.3) * (100 / (100 + attackDefense));
                enemyQuiver -= 1;
            } else {
                enemymoveName = "Walk Forward";
                enemyIsAttacking = false;
                enemyHasWhiffed = false;
                fightRange -= 1;
                enemyStamina += 10;
                Music.soundEffect(11);
            }
        }
    }

    static void bearAI() throws InterruptedException {
        //The AI is RNG with odds that change depending on fight circumstances, like hp remaining, distance, or the player's weapon.
        rngValmin = 1;
        rngValmax = 10;
        genericRNG();
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
                    enemyConfirm = true;
                }
                if (rngbaddie == 3 || rngbaddie == 4) {
                    if (enemyStamina >= 15) {
                        enemyStamina -= 15;
                        enemyIsAttacking = true;
                        enemymoveName = "Claw Strike";
                        enemymoveRange = 2;
                        enemymoveSpeed = (enemySpeed * 0.75);
                        enemymoveDamage = (enemyDamage * 0.65) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                }
                if (rngbaddie == 5) {
                    fightRange -= 1;
                    enemymoveName = "Walk Forward";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyStamina += 10;
                }
                if (rngbaddie == 6) {
                    if (enemyStamina >= 40) {
                        enemyStamina -= 40;
                        enemyStaminaDamage = 45;
                        enemyIsAttacking = true;
                        enemymoveName = "Maul";
                        enemymoveRange = 1;
                        enemymoveSpeed = (enemySpeed * 0.8);
                        enemymoveDamage = (enemyDamage * 0.7) * (100 / (100 + attackDefense));
                        enemyConfirm = true;
                    }
                }
                if (rngbaddie == 7) {
                    if (enemyStamina >= 45) {
                        enemyStamina -= 45;
                        enemyIsAttacking = true;
                        enemymoveName = "Charge";
                        enemymoveRange = 3;
                        enemymoveSpeed = (enemySpeed * 0.3);
                        enemymoveDamage = (enemyDamage * 1.1) * (100 / (100 + attackDefense));
                        fightRange -= 1;
                        enemyConfirm = true;
                    }
                }
                if (rngbaddie == 8) {
                    fightRange -= 1;
                    enemymoveName = "Walk Backwards";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyStamina += 5;
                }
                if (rngbaddie == 9) {
                    enemymoveName = "Block";
                    enemyIsAttacking = false;
                    enemyHasWhiffed = false;
                    enemyIsBlocking = true;
                }
            } else {
                enemymoveName = "Walk Forwards";
                enemyIsAttacking = false;
                enemyHasWhiffed = false;
                fightRange -= 1;
                enemyStamina += 10;
            }
        }
    }
    static void combatRecap() throws InterruptedException {
        stopMusic();
        resetMusic();
        if (playerHP <= 0) {
            /* The concequence for losing isn't a game over because the game is getting longer
            as I work on it. Would suck if you just lost everything immediatly because you made
            a couple bad decisions. Instead, you lose 10% of your time bank, which adds up over time.
             */
            System.out.println("You lost. The hours turn into days as you regain your strength...");
            day = day + 10;
            playerexp = playerexp + 15;
            playerHP = 250 * level;
            isFighting = false;
        }
        if (enemyHP <= 0) {
            numOfEnemiesDefeated += 1;
            System.out.println("Victory!");
            tempVal = coin;
            rngValmin = (int) (enemyPower/1.5);
            rngValmax = (int) (enemyPower);
            genericRNG();
            coin += rngVal;
            System.out.println("You gained " + (coin - tempVal) + " coins!");
            tempVal = playerexp;
            //Getting exp based on the opponent's power may be too inconsistent of a growth right now...
            rngValmin = (int) (enemyPower/2);
            rngValmax = (int) (enemyPower/1.5);
            genericRNG();
            playerexp += rngVal;
            System.out.println("You gained " + (playerexp - tempVal) + " experience!");
            //enemy goes to the shadow realm (aka the negative spot I put everything)
            enemyPosX[(int) tempVal3] = -2;
            enemyPosY[(int) tempVal3] = -2;
            numofenemy -= 1;
        }
        System.out.println("-----------------------------------------------------------");
    }
    static void turnResult() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        boolean playerLandedCounterHit = false;
        boolean playerStruck = false;
        boolean enemyStruck = false;
        //Player stuff
        boolean hitTipper = false;
        //Move specfic stuff
        //Slam spacing
        if (moveName.contains("Slam")) {
            if (fightRange - 2 == 0) {
                moveDamage = moveDamage * 1.25;
                hitTipper = true;
            }
            fightRange -=2;
            if (fightRange < 0) {
                fightRange = 0;
            }
        }
        //Ki Charge
        if (swordCharged && playerIsAttacking) {
            moveRange += 1;
            if (moveName.contains("2"))
            {
                moveSpeed = moveSpeed*1.2;
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
                tempVal = enemyHP;
                moveDamage = (playerDamage*0.45) * (100/(100 + enemyDefense));
                enemyHP -= moveDamage;
                System.out.println("The opponent was hit for " + (tempVal - enemyHP) + "by the Pyroslash!");
            }
        }
        //Pyroslash spawner
        if (moveName.contains("Pyroslash") && !usedPyroslash) {
            pyroslashLocation = 1;
            usedPyroslash = true;
        }
        //Dominant hand buff (+20% speed)
        if (shieldHand.contains("D")) {
            moveSpeed = moveSpeed*1.2;
        }
        //Off hand buff (+15% damage)
        else if (shieldHand.contains("O")) {
            moveDamage = moveDamage*1.15;
        }
        //Axe stuff, installs do a lot
        if (eradicateTurns > 0) {
            isIgnited = true;
            if (!CC.isEmpty()) {
                CC += "Stun";
            }
            hasArmor = true;
        }
        if (playerIsActionable) System.out.println("You used " + moveName);
        else System.out.println("You are unable to move.");
        if (enemyIsActionable) System.out.println(enemyName + " used " + enemymoveName);
        else System.out.println(enemyName + " is unable to move!");
        Thread.sleep(750);
        if (hasGrapplingHook) {
            moveRange += 1;
            tempVal = moveSpeed / 5;
            moveSpeed -= tempVal;
        }
        if (moveRange < fightRange && playerIsAttacking) {
            {
                System.out.println("You whiffed!");
                playerHasWhiffed = true;
                playerWhiffedturns = moveRange;
            }
        } else if (enemymoveRange < fightRange && enemyIsAttacking) {
            System.out.println("The enemy whiffed!");
            enemyHasWhiffed = true;
            enemyWhiffedturns = enemymoveRange;
            // this giant chunk of if statements is a series of checks to decide who wins if both fighters strike in the same turn.
            //The order is Speed -> Damage -> Clash (no damage taken for either side)
        } else if (playerIsAttacking && enemyIsAttacking) {
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
            else {
                System.out.println("Clash! Both your speed and damage were identical!");
            }
        }
        //If you're attacking and the enemy's blocking, this happens
        if (enemyIsBlocking && !enemyIsAttacking && playerIsAttacking && !playerHasWhiffed) {
            if (!ignoresBlock) {
                moveDamage = moveDamage / 4;
                System.out.println("The enemy blocked your attack!");
                enemyStamina += staminaDamage;
                Music.soundEffect(3);
            }
            else System.out.println("You got past the enemy's block!");
            enemyStruck = true;
        }
        if (enemyHasWhiffed && playerIsAttacking && !playerHasWhiffed)
        //If your opponent whiffed, you do 35% more damage. it's not that complicated bro let me keep it in
        {
            moveDamage *= 1.35;
            System.out.println("Bonus damage because the opponent whiffed! ");
            enemyStruck = true;
            enemyWhiffedturns -= 1;
        }
        //Enemy stuff
        else if (!playerIsAttacking && enemyIsAttacking) {
            //Same thing for players, if they whiff and the opponent hits them they take 35% extra dmg
            if (playerHasWhiffed) {
                tempVal = playerHP;
                enemymoveDamage = (enemymoveDamage * 1.35);
                System.out.println("Bonus damage because you whiffed!");
                playerStruck = true;
            } else if (playerIsBlocking) {
                enemymoveDamage = (enemymoveDamage / 4);
                System.out.println("You blocked the enemy's attack!");
                rngValmin = 10;
                rngValmax = 20;
                genericRNG();
                playerStamina += rngVal;
                enemyStaminaDamage = enemyStaminaDamage/2;
                Music.soundEffect(3);
                playerStruck = true;
            }
            else {
                playerStruck = true;
            }
        } else if (playerIsBlocking && enemyIsBlocking) {
            System.out.println("Both fighters blocked! A tense standoff...");
        }
        if (playerIsAttacking && !enemyIsAttacking && !enemyHasWhiffed && !enemyIsBlocking && (moveRange >= fightRange)) {
            enemyStruck = true;
        }
        if (hasArmor && playerIsAttacking) {
            System.out.println("You armored through the opponent's attack!");
            enemyStruck = true;
        }
          
        if (enemyStruck && !playerHasWhiffed) {
            //Sword sfx
            if (weaponName.contains("Sword") && !moveName.contains("Shove")) {
               Music.soundEffect(8);
            }
            if (enemyIsAttacking) playerLandedCounterHit = true;
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
                shieldLocation = fightRange - (fightRange + 1);
            }
            if (playercounterHit && enemyIsActionable) enemyStamina -= 20;
            if (masteredWeapon && playerLandedCounterHit) isIgnited = true;
            if (isIgnited) CC += "DOT";
            if (enemyIsActionable) enemyStamina -= staminaDamage;
            if (moveName.contains("Vanishing Strike 2") && playerLandedCounterHit) moveDamage = moveDamage * 1.25;
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
            if (hitTipper) {
                //lets the player know that they landed the sweet spot
                System.out.println("Direct hit! Bonus damage!");
            }
            superMeter += Math.round(moveDamage / 3);
            //Super meter builds with damage
            if (superMeter >= 100) {
                hasSuper = true;
                superMeter = 100;
            } else hasSuper = false;
            // this is the Sword Mastery Perk (Sword charges give charge back on sweetspot hits)
            if (weaponName.contains("Sword") && masteredWeapon && hitTipper) swordCharged = true;
            if (moveName.contains("Cripple 2")) staminaRegenPaused = ccAmount;
        }
        if (playerStruck) {
            if (enemyName.contains("Sword")) {
                //hit sfx
                if (swordCharged) Music.soundEffect(10);
                else Music.soundEffect(8);
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
                if (playerIsActionable) playerStamina -= enemyStaminaDamage;
                superMeter += Math.round(enemymoveDamage / 5);
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
                        if (CC.contains("Weaken Damage")) playerDamage = playerDamage * (100 / (100 * ccAmount));
                        if (CC.contains("Weaken Speed")) attackSpeed = attackSpeed * (100 / (100 * ccAmount));
                    }
                }
            }
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
                enemyUnactionableTurns = 0;
                enemyStamina = 100;
            }
        }
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
        //can't go above 100, although maybe i'll add it for axe super?
        if (playerStamina > 100) playerStamina = 100;
        if (enemyStamina > 100) enemyStamina = 100;
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
        rngValmin = 1;
        rngValmax = 2;
        genericRNG();
        if (rngVal == 1)
        {
            bossIsSwordsman = true;
        }
        else if (rngVal == 2)
        {
            bossIsShieldsman = true;
        }
        else if (rngVal == 3){
            bossIsBowman = true;
            enemyQuiver = 7;
        }
        isBoss = true;
        enemyHP = 2500;
        rngValmin = 1750;
        rngValmax = 150;
        genericRNG();
        enemyDamage = rngVal;
        genericRNG();
        enemySpeed = rngVal;
        genericRNG();
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
        if (enemyHP <= 1750 && enemyHP >= 1000 && bossHasSwapped == 0|| enemyHP <= 1000 && bossHasSwapped == 1) {
            rngValmin = 1;
            rngValmax = 1;
            genericRNG();
            if (bossIsShieldsman) {
                if (rngVal == 1) {
                    bossIsShieldsman = false;
                    bossIsSwordsman = true;
                    System.out.println("The boss swaps to their sword!");
                } else if (rngVal == 2) {
                    bossIsShieldsman = false;
                    bossIsBowman = true;
                    System.out.println("The boss swaps to their bow!");
                }
            } else if (bossIsSwordsman) {
                if (rngVal == 1) {
                    bossIsSwordsman = false;
                    bossIsShieldsman = true;
                    System.out.println("The boss swaps to their shield!");
                } else if (rngVal == 2) {
                    bossIsSwordsman = false;
                    bossIsBowman = true;
                    System.out.println("The boss swaps to their bow!");
                }
            }
            else if (bossIsBowman) {
                if (rngVal == 1) {
                    bossIsBowman = false;
                    bossIsShieldsman = true;
                    System.out.println("The boss swaps to their shield!");
                } else if (rngVal == 2) {
                    bossIsBowman = false;
                    bossIsSwordsman = true;
                    System.out.println("The boss swaps to their bow!");
                }
            }
            bossHasSwapped += 1;
        }
        if (bossIsSwordsman) SwordsmanAI();
        else if (bossIsBowman) strongbowmanAI();
        else if (bossIsShieldsman) shieldsmanAI();
    }
}
