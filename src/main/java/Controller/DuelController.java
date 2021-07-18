package Controller;

import Model.*;
import View.DuelView;
import View.MainPhaseView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelController {
    protected static DuelController duelController = null;
    protected DuelModel duelModel;
    protected DuelView duelView;
    protected boolean isAi;
    int playerActiveCloseForest;
    int attackaddedForClosedForest = 0;

    protected DuelController() {

    }

    public static DuelController getInstance() {
        if (duelController == null)
            duelController = new DuelController();
        return duelController;
    }

    public void setDuelModel(DuelModel duelModel, DuelView duelView, DuelController duelController, boolean isAi) {
        this.isAi = isAi;
        this.duelModel = duelModel;
        this.duelView = duelView;
        DuelController.duelController = duelController;
    }

    public void selectFirstPlayer() {

    }

    //این تابع حین بازی صدا زده میشه تا کارت های ورودی شامل میدان شوند
    public void activeFieldInGame() {
        if (duelModel.getField().get(duelModel.turn).get(0) != null) {
            Card spell = duelModel.getField().get(duelModel.turn).get(0);
            if (spell.getName().equals("Yami"))
                effectOfYami(1);
            else if (spell.getName().equals("Forest"))
                effectOfForest(1);
            else if (spell.getName().equals("Closed Forest"))
                effectOfClosedForest(1);
            else if (spell.getName().equals("UMIIRUKA"))
                effectOfUmiiruka(1);
        } else if (duelModel.getField().get(1 - duelModel.turn).get(0) != null) {
            Card spell = duelModel.getField().get(1 - duelModel.turn).get(0);
            if (spell.getName().equals("Yami"))
                effectOfYami(1);
            else if (spell.getName().equals("Forest"))
                effectOfForest(1);
            else if (spell.getName().equals("Closed Forest"))
                effectOfClosedForest(1);
            else if (spell.getName().equals("UMIIRUKA"))
                effectOfUmiiruka(1);
        }
    }

    public String deselect() {
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else {
            duelModel.deSelectedCard();
            return "card deselected";
        }
    }

    public String activateEffect(int placeOfSpell, String cardName, String cardType) {
        if (cardName.equals("Monster Reborn"))
            return effectOfMonsterReborn(placeOfSpell);
        else if (cardName.equals("Terraforming"))
            return effectOfTerraforming(placeOfSpell);
        else if (cardName.equals("Pot of Greed"))
            return effectOfPotOfGreed(placeOfSpell);
        else if (cardName.equals("Raigeki"))
            return effectOfRaigeki(placeOfSpell);
        else if (cardName.equals("Change of Heart"))
            return effectOfChangeOfHeart(placeOfSpell);
        else if (cardName.equals("Harpie’s Feather Duster"))
            return effectOfHarpiesFeatherDuster(placeOfSpell);
        else if (cardName.equals("Swords of Revealing Light")) {
            return effectOfSwordsOfRevealingLight(placeOfSpell);
        } else if (cardName.equals("Dark Hole")) {
            return effectOfDarkHole(placeOfSpell);
        } else if (cardName.equals("Supply Squad")) {
            return effectOfSupplySquad(placeOfSpell);
        } else if (cardName.equals("Spell Absorption")) {
            return effectOfSpellAbsorption(placeOfSpell);
        } else if (cardName.equals("Messenger of peace")) {
            return effectOfMessengerOfPeace(placeOfSpell);
        } else if (cardName.equals("Twin Twisters")) {
            return effectOfTwinTwisters(placeOfSpell);
        } else if (cardName.equals("Mystical space typhoon")) {
            return effectOfMysticalSpaceTyphoon(placeOfSpell);
        } else if (cardName.equals("Ring of Defense")) {
            return effectOfRingOfDefense(placeOfSpell);
        } else if (cardName.equals("Advanced Ritual Art")) {
            return effectOfAdvancedRitualArt(placeOfSpell);
        } else if (cardType.equals("Field")) {
            if (placeOfSpell == -1)
                activeZoneFromHand();
            if (placeOfSpell == -2)
                activeSetZone();
        } else if (cardName.equals("SwordOfDarkDestruction"))
            return effectOfSwordOfDarkstraction(placeOfSpell);
        else if (cardName.equals("Black Pendant"))
            return effectOfBlackPendant(placeOfSpell);
        else if (cardName.equals("UnitedWeStand"))
            return effectOfUnitedWeStand(placeOfSpell);
        else if (cardName.equals("Magnum Shield"))
            return effectOfMagnumShield(placeOfSpell);
        return "";
    }

    public void deActiveOldField() {
        if (duelModel.getField().get(duelModel.turn).get(0) != null) {
            Card spell = duelModel.getField().get(duelModel.turn).get(0);
            if (spell.getName().equals("Yami"))
                effectOfYami(-1);
            else if (spell.getName().equals("Forest"))
                effectOfForest(-1);
            else if (spell.getName().equals("Closed Forest"))
                effectOfClosedForest(-1);
            else if (spell.getName().equals("UMIIRUKA"))
                effectOfUmiiruka(-1);
        } else if (duelModel.getField().get(1 - duelModel.turn).get(0) != null) {
            Card spell = duelModel.getField().get(1 - duelModel.turn).get(0);
            if (spell.getName().equals("Yami"))
                effectOfYami(-1);
            else if (spell.getName().equals("Forest"))
                effectOfForest(-1);
            else if (spell.getName().equals("Closed Forest"))
                effectOfClosedForest(-1);
            else if (spell.getName().equals("UMIIRUKA"))
                effectOfUmiiruka(-1);
        }

    }

    public void deActiveEquipSpell(Card card, String spellName) {
        if (spellName.equals("SwordOfDarkDestruction"))
            swordOfDarkstraction(card, -1);
        else if (spellName.equals("Black Pendant"))
            blackPendant(card, -1);
        else if (spellName.equals("UnitedWeStand"))
            unitedWeStand(card, -1);
        else if (spellName.equals("Magnum Shield"))
            deActiveMagnumShield(card);
    }

    public void deActiveMagnumShield(Card card) {
        int place = duelModel.getMonstersInField().get(duelModel.turn).indexOf(card);

        if (duelModel.getMonsterCondition(duelModel.turn, place).split("/")[0].startsWith("D")) {
            card.setAttackPower(card.getAttackPower() - Card.getCardByName(card.getName()).getDefensePower());
        } else {
            card.setDefensePower(card.getDefensePower() - Card.getCardByName(card.getName()).getAttackPower());
        }
    }

    public String activeSetZone() {
        deActiveOldField();
        duelModel.activeField(duelModel.getSelectedCards().get(duelModel.turn).get(0));
        Card spell = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        duelModel.getField().get(duelModel.turn).set(1, null);
        deselect();
        if (spell.getName().equals("Yami"))
            effectOfYami(1);
        else if (spell.getName().equals("Forest"))
            effectOfForest(1);
        else if (spell.getName().equals("Closed Forest")) {
            playerActiveCloseForest = duelModel.turn;
            effectOfClosedForest(1);
        } else if (spell.getName().equals("UMIIRUKA"))
            effectOfUmiiruka(1);
        return "spell zone activate";
    }

    public String activeZoneFromHand() {
        deActiveOldField();
        duelModel.activeField(duelModel.getSelectedCards().get(duelModel.turn).get(0));
        Card spell = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        duelModel.getHandCards().get(duelModel.turn).remove(spell);
        if (spell.getName().equals("Yami"))
            return effectOfYami(1);
        else if (spell.getName().equals("Forest"))
            return effectOfForest(1);
        else if (spell.getName().equals("Closed Forest")) {
            playerActiveCloseForest = duelModel.turn;
            return effectOfClosedForest(1);
        } else if (spell.getName().equals("UMIIRUKA"))
            return effectOfUmiiruka(1);
        return "spell zone activate";
    }


    public void isOpponentHasAnySpellOrTrapForActivate() {
        boolean hasAnySpellOrTrap = false;
        for (int i = 1; i <= 5; i++) {
            if (duelModel.getSpellAndTrap(1 - duelModel.turn, i) != null) {
                if (duelModel.getSpellAndTrap(1 - duelModel.turn, i).getCardType()
                        .equals("Quick-play") || duelModel.getSpellAndTrap(1 - duelModel.turn, i)
                        .getCategory().equals("Trap")) {
                    hasAnySpellOrTrap = true;
                    break;
                }
            }
        }
        duelView.opponentActiveEffect(hasAnySpellOrTrap);
    }

    public String opponentActiveSpellOrTrap() {
        Matcher matcher = duelView.scanCommandForActiveSpell();
        if (matcher.find()) {
            String response = selectSpellOrTrap(matcher);
            System.out.println(response);
            if (response.equals("card selected")) {
                if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Trap")) {
                    return opponentActiveTrap();
                } else {
                    if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCardType()
                            .equals("Quick-play")) {
                        return "you cant active this spell int this turn";
                    }
                    String[] detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).
                            get(duelModel.getSelectedCards().get(duelModel.turn).get(0)).split("/");
                    String stateOfSelectedCard = detailsOfSelectedCard[1];
                    if (stateOfSelectedCard.equals("O")) {
                        return "Card already Activated";
                    } else {
                        return opponentActiveSpell(Integer.parseInt(matcher.group(1)));
                    }
                }
            }
        }
        return "it’s not your turn to play this kind of moves";
    }

    public void aiOpponentActiveSpellOrTrap() {
        ArrayList<Card> spellsAndTraps = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn);
        for (int i = 0; i < 6; i++) {
            if (spellsAndTraps.get(i).getCategory().equals("Trap")) {
                if (duelModel.getSpellAndTrapCondition(duelModel.turn, i + 1).charAt(0) == 'H') {
                    Pattern pattern = Pattern.compile("^select --spell (\\d+)$");
                    Matcher matcher = pattern.matcher("select --spell " + (i + 1));
                    if (matcher.find()) {
                        selectSpellOrTrap(matcher);
                    }
                    opponentActiveTrap();
                    return;
                }
            } else if (spellsAndTraps.get(i).getCategory().equals("Spell") && spellsAndTraps.get(i).getCardType().equals("Quick-play")) {
                if (duelModel.getSpellAndTrapCondition(duelModel.turn, i + 1).charAt(0) == 'H') {
                    Pattern pattern = Pattern.compile("^select --spell (\\d+)$");
                    Matcher matcher = pattern.matcher("select --spell " + (i + 1));
                    if (matcher.find()) {
                        selectSpellOrTrap(matcher);
                    }
                    opponentActiveSpell(i + 1);
                }
            }
        }
    }

    public String opponentActiveSpell(int placeOfSpell) {
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (card.getName().equals("Twin Twisters")) {
            return effectOfTwinTwisters(placeOfSpell);
        } else if (card.getName().equals("Mystical space typhoon")) {
            return effectOfMysticalSpaceTyphoon(placeOfSpell);
        } else if (card.getName().equals("Ring of Defense")) {
            return effectOfRingOfDefense(placeOfSpell);
        }
        return "";
    }

    public String effectOfMonsterReborn(int placeOfSpell) {
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        String kindOfGraveyard = duelView.scanKindOfGraveyardForActiveEffect();
        int numberOfCard = duelView.scanNumberOfCardForActiveEffect();
        String state = mainPhaseView.getStateOfCardForSummon();

        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of MonsterReborn" + "/" + kindOfGraveyard + "/"
                    + "/" + numberOfCard + "/" + state + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public String effectOfTerraforming(int placeOfSpell) {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of Terraforming" + "/" + placeOfSpell + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public String effectOfPotOfGreed(int placeOfSpell) {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of PotOfGreed" + "/" + placeOfSpell + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public String effectOfRaigeki(int placeOfSpell) {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of Raigeki" + "/" + placeOfSpell + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public void deleteAllMonsters(int turn) {
        ArrayList<Card> monstersInFieldOfPlayer = duelModel.getMonstersInField().get(turn);
        int i = 0;
        for (Card card : monstersInFieldOfPlayer) {
            if (card != null) {
                duelModel.deleteMonster(turn, i);
                duelModel.addCardToGraveyard(turn, card);
            }
            i++;
        }
    }

    public String effectOfChangeOfHeart(int placeOfSpell) {
        int numberOfMonsterCard = duelView.scanNumberOfCardForActiveEffect();
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of ChangeOfHeart" + "/" + placeOfSpell + "/"
                    + numberOfMonsterCard + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public String effectOfHarpiesFeatherDuster(int placeOfSpell) {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of HarpiesFeatherDuster" + "/" + placeOfSpell + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public String effectOfSwordsOfRevealingLight(int placeOfSpell) {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of SwordsOfRevealingLight" + "/" + placeOfSpell + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public void changeStateOfMonsterWithSwordsCard(int turn) {
        ArrayList<Card> monstersInField = duelModel.getMonstersInField().get(turn);
        int i = 1;
        for (Card card : monstersInField) {
            if (card != null) {
                String[] detailsOfMonsterCard = duelModel.getMonsterCondition(turn, i)
                        .split("/");
                if (detailsOfMonsterCard[0].equals("DH")) {
                    duelModel.addMonsterCondition(turn, i - 1, "DO" + i);
                }
            }
            i++;
        }
    }

    public String effectOfDarkHole(int placeOfSpell) {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of DarkHole" + "/" + placeOfSpell + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public String effectOfSupplySquad(int placeOfSpell) {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of SupplySquad" + "/" + placeOfSpell + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public String effectOfSpellAbsorption(int placeOfSpell) {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of SpellAbsorption" + "/" + placeOfSpell + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public String effectOfMessengerOfPeace(int placeOfSpell) {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Effect Of MessengerOfPeace" + "/" + placeOfSpell + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return response;
    }

    public String effectOfTwinTwisters(int placeOfSpell) {
        MainPhaseController mainPhaseController = MainPhaseController.getInstance();
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (hasSpellSetInThisTurn()) {
            return "preparations of this spell are not done yet";
        }
        Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (placeOfSpell != -1) {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
        } else {
            activeSpellFromHand();
        }
        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card1, false);
        duelController.isOpponentHasAnySpellOrTrapForActivate();
        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card1)) {
            int placeOfCardInHand;
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card1);
            if (!isAi) {
                placeOfCardInHand = duelView.scanNumberOfCardForDeleteFromHand();
            } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                placeOfCardInHand = duelView.scanNumberOfCardForDeleteFromHand();
            } else {
                placeOfCardInHand = 1;
            }
            if (placeOfCardInHand > duelModel.getHandCards().get(duelModel.turn).size()) {
                return "you cant delete card with this address from your hand";
            } else {
                int numberOfCardsPlayerWantToDestroyed;
                Card card = duelModel.getHandCards().get(duelModel.turn).get(placeOfCardInHand - 1);
                duelModel.deleteCardFromHand(card);
                duelModel.addCardToGraveyard(duelModel.turn, card);
                if (!isAi) {
                    numberOfCardsPlayerWantToDestroyed = duelView.scanNumberOfCardThatWouldBeDelete();
                } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                    numberOfCardsPlayerWantToDestroyed = duelView.scanNumberOfCardThatWouldBeDelete();
                } else {
                    if (mainPhaseController.getNumberOfSpellsAndTrapsInPlayerField(1 - duelModel.turn) > 1) {
                        numberOfCardsPlayerWantToDestroyed = 2;
                    } else {
                        numberOfCardsPlayerWantToDestroyed = 1;
                    }
                }
                if (numberOfCardsPlayerWantToDestroyed == 0) {
                    if (placeOfSpell != -1) {
                        duelModel.effectOfSpellAbsorptionCards();
                        duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                        duelModel.addCardToGraveyard(duelModel.turn, card1);
                        return "spell activated";
                    } else {
                        duelModel.effectOfSpellAbsorptionCards();
                        int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card1);
                        duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                        duelModel.addCardToGraveyard(duelModel.turn, card1);
                        return "spell activated";
                    }
                } else if (numberOfCardsPlayerWantToDestroyed == 1) {
                    String response;
                    if (!isAi) {
                        response = duelView.scanPlaceOfCardWantToDestroyed();
                    } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                        response = duelView.scanPlaceOfCardWantToDestroyed();
                    } else {
                        response = mainPhaseController.getResponseForAiForTwinTwistersAndMysticalSpaceTyphoon();
                    }
                    String[] splitResponse = response.split(" ");
                    int placeOfSpellOrTrapCard = Integer.parseInt(splitResponse[1]);
                    if (splitResponse[0].equals("my")) {
                        return deleteASpellCardInActiveSpell(duelModel.turn, placeOfSpell,
                                placeOfSpellOrTrapCard);
                    } else if (splitResponse[0].equals("opponent")) {
                        return deleteASpellCardInActiveSpell(1 - duelModel.turn, placeOfSpell,
                                placeOfSpellOrTrapCard);
                    } else {
                        return "you must enter my/opponent";
                    }
                } else if (numberOfCardsPlayerWantToDestroyed == 2) {
                    String response1;
                    String response2;
                    if (!isAi) {
                        response1 = duelView.scanPlaceOfCardWantToDestroyed();
                        response2 = duelView.scanPlaceOfCardWantToDestroyed();
                    } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                        response1 = duelView.scanPlaceOfCardWantToDestroyed();
                        response2 = duelView.scanPlaceOfCardWantToDestroyed();
                    } else {
                        response1 = mainPhaseController.getResponseForAiForTwinTwistersAndMysticalSpaceTyphoon();
                        response2 = mainPhaseController.getResponseForAiForTwinTwistersAndMysticalSpaceTyphoon();
                    }
                    String[] splitResponse1 = response1.split(" ");
                    String[] splitResponse2 = response2.split(" ");
                    int placeOfSpellOrTrapCard1 = Integer.parseInt(splitResponse1[1]);
                    int placeOfSpellOrTrapCard2 = Integer.parseInt(splitResponse2[1]);
                    if (splitResponse1[0].equals("my") && splitResponse2[0].equals("my")) {
                        return deleteTwoSpellCardInActiveSpell(duelModel.turn, duelModel.turn, placeOfSpell,
                                placeOfSpellOrTrapCard1, placeOfSpellOrTrapCard2);
                    } else if (splitResponse1[0].equals("opponent") && splitResponse2[0].equals("opponent")) {
                        return deleteTwoSpellCardInActiveSpell(1 - duelModel.turn, 1 - duelModel.turn, placeOfSpell,
                                placeOfSpellOrTrapCard1, placeOfSpellOrTrapCard2);
                    } else if (splitResponse1[0].equals("my") && splitResponse2[0].equals("opponent")) {
                        return deleteTwoSpellCardInActiveSpell(duelModel.turn, 1 - duelModel.turn, placeOfSpell,
                                placeOfSpellOrTrapCard1, placeOfSpellOrTrapCard2);
                    } else if (splitResponse1[0].equals("opponent") && splitResponse2[0].equals("my")) {
                        return deleteTwoSpellCardInActiveSpell(1 - duelModel.turn, duelModel.turn, placeOfSpell,
                                placeOfSpellOrTrapCard1, placeOfSpellOrTrapCard2);
                    } else {
                        return "you must enter my/opponent";
                    }
                } else {
                    return "you cant destroy spell in this number";
                }
            }
        }
        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card1);
        return "";
    }

    public String effectOfMysticalSpaceTyphoon(int placeOfSpell) {
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (hasSpellSetInThisTurn()) {
            return "preparations of this spell are not done yet";
        }
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (placeOfSpell != -1) {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
        } else {
            activeSpellFromHand();
        }
        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
        duelController.isOpponentHasAnySpellOrTrapForActivate();
        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
            String response;
            if (!isAi) {
                response = duelView.scanPlaceOfCardWantToDestroyed();
            } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                response = duelView.scanPlaceOfCardWantToDestroyed();
            } else {
                MainPhaseController mainPhaseController = MainPhaseController.getInstance();
                response = mainPhaseController.getResponseForAiForTwinTwistersAndMysticalSpaceTyphoon();
            }
            String[] splitResponse = response.split(" ");
            int placeOfSpellOrTrapCard = Integer.parseInt(splitResponse[1]);
            if (splitResponse[0].equals("my")) {
                return deleteASpellCardInActiveSpell(duelModel.turn, placeOfSpell, placeOfSpellOrTrapCard);
            } else if (splitResponse[0].equals("opponent")) {
                return deleteASpellCardInActiveSpell(1 - duelModel.turn, placeOfSpell, placeOfSpellOrTrapCard);
            } else {
                return "you must enter my/opponent";
            }
        }
        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
        return "";
    }

    public String effectOfRingOfDefense(int placeOfSpell) {
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (hasSpellSetInThisTurn()) {
            return "preparations of this spell are not done yet";
        } else {
            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            } else {
                activeSpellFromHand();
            }
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
            duelController.isOpponentHasAnySpellOrTrapForActivate();
            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                duelModel.effectOfSpellAbsorptionCards();
                return "spell activated";
            }
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
            return "";
        }
    }


    public boolean hasSpellSetInThisTurn() {
        ArrayList<Card> spellsAndTrapsSetInThisTurn = duelModel.getSpellsAndTarpsSetInThisTurn()
                .get(duelModel.turn);
        for (Card card : spellsAndTrapsSetInThisTurn) {
            if (card.getCategory().equals("Spell")) {
                if (card == duelModel.getSelectedCards().get(duelModel.turn).get(0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String deleteTwoSpellCardInActiveSpell(int turn1, int turn2, int placeOfSpell, int placeOfSpellOrTrapCard1, int placeOfSpellOrTrapCard2) {
        if (placeOfSpellOrTrapCard1 > 5 || placeOfSpellOrTrapCard2 > 5) {
            return "you must enter correct address";
        } else {
            Card card1 = duelModel.getSpellsAndTrapsInFiled().get(turn1).get(placeOfSpellOrTrapCard1 - 1);
            Card card2 = duelModel.getSpellsAndTrapsInFiled().get(turn2).get(placeOfSpellOrTrapCard2 - 1);
            if (card1 == null || card2 == null) {
                return "you cant destroyed card with this address";
            } else {
                Card spellCard = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                if (placeOfSpell != -1) {
                    deleteASpell(turn1, placeOfSpellOrTrapCard1, card1);
                    deleteASpell(turn2, placeOfSpellOrTrapCard2, card2);
                    duelModel.effectOfSpellAbsorptionCards();
                    duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                    duelModel.addCardToGraveyard(duelModel.turn, spellCard);
                    return "spell activated";
                } else {
                    deleteASpell(turn1, placeOfSpellOrTrapCard1, card1);
                    deleteASpell(turn2, placeOfSpellOrTrapCard2, card2);
                    duelModel.effectOfSpellAbsorptionCards();
                    int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(spellCard);
                    duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                    duelModel.addCardToGraveyard(duelModel.turn, spellCard);
                    return "spell activated";
                }
            }
        }
    }

    public String deleteASpellCardInActiveSpell(int turn, int placeOfSpell, int placeOfSpellOrTrapCard) {
        if (placeOfSpellOrTrapCard > 5) {
            return "you must enter correct address";
        } else {
            Card card1 = duelModel.getSpellsAndTrapsInFiled().get(turn).get(placeOfSpellOrTrapCard - 1);
            if (card1 == null) {
                return "you cant destroyed card with this address";
            } else {
                Card spellCard = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                if (placeOfSpell != -1) {
                    deleteASpell(turn, placeOfSpellOrTrapCard, card1);
                    duelModel.effectOfSpellAbsorptionCards();
                    duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                    duelModel.addCardToGraveyard(duelModel.turn, spellCard);
                    return "spell activated";
                } else {
                    deleteASpell(turn, placeOfSpellOrTrapCard, card1);
                    duelModel.effectOfSpellAbsorptionCards();
                    int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(spellCard);
                    duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                    duelModel.addCardToGraveyard(duelModel.turn, spellCard);
                    return "spell activated";
                }
            }
        }
    }

    public void deleteASpell(int turn, int placeOfSpellOrTrapCard, Card card) {
        if (card.getName().equals("Swords of Revealing Light")) {
            duelModel.deleteSwordsCard(turn, card);
        } else if (card.getName().equals("Supply Squad")) {
            duelModel.deleteSupplySquadCard(turn, card);
        } else if (card.getName().equals("Spell Absorption")) {
            duelModel.deleteSpellAbsorptionCards(turn, card);
        } else if (card.getName().equals("Messenger of peace")) {
            duelModel.deleteMessengerOfPeaceCards(turn, card);
        } else {
            duelModel.deleteSpellAndTrap(turn, placeOfSpellOrTrapCard - 1);
            duelModel.addCardToGraveyard(turn, card);
        }
        for (Map.Entry<Card, Boolean> entry : duelModel.getSpellOrTrapActivated().get(turn).entrySet()) {
            Card spellCardActivated = entry.getKey();
            if (spellCardActivated == card) {
                duelModel.getSpellOrTrapActivated().get(turn).put(card, true);
            }
        }
    }

    public boolean isSpellZoneFull(int turn) {
        boolean spellZoneFull = true;
        ArrayList<Card> spellsAndTraps = duelModel.getSpellsAndTrapsInFiled().get(turn);
        for (Card card : spellsAndTraps) {
            if (card == null) {
                spellZoneFull = false;
                break;
            }
        }
        return spellZoneFull;
    }

    public String activeSpellFromHand() {
        if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(0) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/1", 0);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(1) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/2", 1);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(2) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/3", 2);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(3) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/4", 3);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(4) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/5", 4);
        }
        return "spell activated";
    }

    public String effectOfAdvancedRitualArt(int placeOfSpell) {
        boolean hasAnyRitualMonster = false;
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        ArrayList<Card> handCards = duelModel.getHandCards().get(duelModel.turn);
        ArrayList<Card> cardsInDeck = duelModel.getPlayersCards().get(duelModel.turn);
        for (Card card : handCards) {
            if (card.getCategory().equals("Monster")) {
                if (card.getCardType().equals("Ritual")) {
                    hasAnyRitualMonster = true;
                    break;
                }
            }
        }
        if (!hasAnyRitualMonster) {
            return "there is no way you could ritual summon a monster";
        } else {
            if (isMonsterZoneFull(duelModel.turn)) {
                return "there is no way you could ritual summon a monster";
            }
            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            } else {
                activeSpellFromHand();
            }
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
            duelController.isOpponentHasAnySpellOrTrapForActivate();
            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                int addressOfMonsterForRitualSummon = 0;
                if (!isAi) {
                    addressOfMonsterForRitualSummon = duelView.scanAddressOfCardForRitualSummon();
                } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                    addressOfMonsterForRitualSummon = duelView.scanAddressOfCardForRitualSummon();
                } else {
                    for (Card card1 : duelModel.getHandCards().get(duelModel.turn)) {
                        if (card1.getCardType().equals("Ritual")) {
                            addressOfMonsterForRitualSummon = duelModel.getHandCards().get(duelModel.turn)
                                    .indexOf(card1) + 1;
                        }
                    }
                }
                if (addressOfMonsterForRitualSummon > duelModel.getHandCards().get(duelModel.turn).size()) {
                    return "you didnt select correct address";
                }
                Card ritualMonster = duelModel.getHandCards().get(duelModel.turn).get(addressOfMonsterForRitualSummon - 1);
                if (!ritualMonster.getCategory().equals("Monster") || !ritualMonster.getCardType().equals("Ritual")) {
                    return "you didnt select ritual monster";
                }
                String[] addressOfCardForTribute;
                if (!isAi) {
                    addressOfCardForTribute = duelView.scanAddressForTributeForRitualSummon().split(" ");
                } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                    addressOfCardForTribute = duelView.scanAddressForTributeForRitualSummon().split(" ");
                } else {
                    MainPhaseController mainPhaseController = MainPhaseController.getInstance();
                    addressOfCardForTribute = mainPhaseController.getResponseForRitualSummon(ritualMonster).split(" ");
                }
                if (addressOfCardForTribute.length < 2) {
                    return "you must enter two address for tribute from your deck";
                } else {
                    int addressOfCard1 = Integer.parseInt(addressOfCardForTribute[0]);
                    int addressOfCard2 = Integer.parseInt(addressOfCardForTribute[1]);
                    if (addressOfCard1 > cardsInDeck.size() || addressOfCard2 > cardsInDeck.size()) {
                        return "you must enter correct address";
                    } else {
                        Card card1 = cardsInDeck.get(addressOfCard1 - 1);
                        Card card2 = cardsInDeck.get(addressOfCard2 - 1);
                        if (!card1.getCategory().equals("Monster") || !card2.getCategory().equals("Monster")) {
                            return "you must tribute monster";
                        } else {
                            if (card1.getLevel() + card2.getLevel() != ritualMonster.getLevel()) {
                                return "selected monsters levels don’t match with ritual monster";
                            } else {
                                String stateOfCardForSummon;
                                duelModel.deleteCardFromDeck(duelModel.turn, addressOfCard1 - 1, card1);
                                duelModel.deleteCardFromDeck(duelModel.turn, addressOfCard2 - 1, card2);
                                MainPhaseView mainPhaseView = MainPhaseView.getInstance();
                                if (!isAi) {
                                    stateOfCardForSummon = mainPhaseView.getStateOfCardForSummon();
                                } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                                    stateOfCardForSummon = mainPhaseView.getStateOfCardForSummon();
                                } else {
                                    if (ritualMonster.getAttackPower() >= ritualMonster.getDefensePower()) {
                                        stateOfCardForSummon = "Attack";
                                    } else {
                                        stateOfCardForSummon = "Defence";
                                    }
                                }
                                if (!stateOfCardForSummon.equals("Attack")
                                        && !stateOfCardForSummon.equals("Defence")) {
                                    return "you must Highlight the state of card for summon correctly";
                                }
                                ritualSummonMonsterOnField(stateOfCardForSummon, ritualMonster);
                                duelModel.effectOfSpellAbsorptionCards();
                                if (placeOfSpell != -1) {
                                    duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                                } else {
                                    int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card);
                                    duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                                }
                                duelModel.addCardToGraveyard(duelModel.turn, card);
                                return "spell activated";
                            }
                        }
                    }
                }
            }
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
            return "";
        }
    }


    public void ritualSummonMonsterOnField(String state, Card card) {
        String stateOfCard = "OO";
        if (state.equals("Attack")) {
            stateOfCard = "OO";
        } else if (state.equals("Defence")) {
            stateOfCard = "DO";
        }
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.ritualSummon(stateOfCard + "/1", 0, card);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.ritualSummon(stateOfCard + "/2", 1, card);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.ritualSummon(stateOfCard + "/3", 0, card);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.ritualSummon(stateOfCard + "/4", 0, card);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.ritualSummon(stateOfCard + "/5", 0, card);
        }
        duelModel.monsterSummonForEffectOfSomeTraps = card;
        activeFieldInGame();
    }

    public String effectOfMagnumShield(int placeOfSpellInField) {
        boolean isWarriorExist = false;
        ArrayList<Integer> placeOfWarriorCard = new ArrayList<>();
        Card cardForAi = null;
        if (isSpellZoneFull(duelModel.turn))
            return "spellZone full!";
        for (int i = 0; i < 5; i++) {
            Card card = duelModel.getMonstersInField().get(duelModel.turn).get(i);
            if (card != null)
                //check it
                if (card.getCategory().equals("Warrior")) {
                    if (duelModel.getMonsterCondition(duelModel.turn, i).split("/")[0].equals("DO") || duelModel.getMonsterCondition(duelModel.turn, i).split("/")[0].equals("OO"))
                        isWarriorExist = true;
                    if (cardForAi != null) {
                        if (card.getAttackPower() > cardForAi.getAttackPower())
                            cardForAi = card;
                    } else cardForAi = card;
                    placeOfWarriorCard.add(i);
                }
        }
        if (isWarriorExist) {
            int place;
            if (duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                place = duelModel.getMonstersInField().indexOf(cardForAi);
            } else {
                while (true) {
                    place = duelView.scanForChoseMonsterForEquip(placeOfWarriorCard);
                    if (placeOfWarriorCard.contains(place))
                        break;
                }
                Card monsterForEquip = duelModel.getMonster(duelModel.turn, place);
                Card thisSpell = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                if (placeOfSpellInField != -1) {
                    duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpellInField);
                } else {
                    activeSpellFromHand();
                }

                duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(thisSpell, false);
                isOpponentHasAnySpellOrTrapForActivate();
                if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(thisSpell)) {
                    if (duelModel.getMonstersInField().get(duelModel.turn).contains(monsterForEquip)) {
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(thisSpell);

                        Card monster = duelModel.getMonstersInField().get(duelModel.turn).get(place);
                        if (duelModel.getMonsterCondition(duelModel.turn, place).startsWith("D")) {
                            monster.setAttackPower(monster.getAttackPower() + monster.getDefensePower());

                            duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).set(placeOfSpellInField, duelModel.getSelectedCards().get(duelModel.turn).get(0));
                            //پر شود
                        } else {
                            monster.setDefensePower(monster.getAttackPower() + monster.getDefensePower());
                        }

                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(thisSpell);
                        return "spell activated";
                    }
                }
            }

        }
        return "you don't have any Warrior monster to equip ";
    }

    public String effectOfUnitedWeStand(int placeOfSpell) {
        boolean isMonster = false;
        ArrayList<Integer> placeOfMonsterCard = new ArrayList<>();
        Card cardForAi = null;
        if (isSpellZoneFull(duelModel.turn))
            return "spellZone full!";
        for (int i = 0; i < 5; i++) {
            Card card = duelModel.getMonstersInField().get(duelModel.turn).get(i);
            if (card != null) {
                //check it
                if (duelModel.getMonsterCondition(duelModel.turn, i).equals("DO") || duelModel.getMonsterCondition(duelModel.turn, i).equals("OO"))
                    isMonster = true;
                if (cardForAi != null) {
                    if (card.getAttackPower() > cardForAi.getAttackPower())
                        cardForAi = card;
                } else cardForAi = card;
                placeOfMonsterCard.add(i);
            }
        }
        if (isMonster) {
            int place;
            if (duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                place = placeOfMonsterCard.indexOf(cardForAi);
            } else {
                while (true) {
                    place = duelView.scanForChoseMonsterForEquip(placeOfMonsterCard);
                    if (placeOfMonsterCard.contains(place))
                        break;
                }
            }
            Card monsterForEquip = duelModel.getMonster(duelModel.turn, place);
            Card thisSpell = duelModel.getSelectedCards().get(duelModel.turn).get(0);

            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            } else {
                activeSpellFromHand();
            }
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(thisSpell, false);
            isOpponentHasAnySpellOrTrapForActivate();
            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(thisSpell)) {
                if (duelModel.getMonstersInField().get(duelModel.turn).contains(monsterForEquip)) {
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(thisSpell);

                    Card monster = duelModel.getMonstersInField().get(duelModel.turn).get(place);

                    unitedWeStand(monster, 1);
                    Card spell = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    duelModel.activeEquip(monster, spell);
                    return "spell activated";
                }

            }
        }
        return "you don't have any monster to equip";
    }

    public void unitedWeStand(Card monster, int activeOrDeactive) {
        monster.setDefensePower(monster.getDefensePower() + 800 * activeOrDeactive);
        monster.setAttackPower(monster.getAttackPower() + 800 * activeOrDeactive);
    }

    public String effectOfBlackPendant(int placeOfSpell) {
        boolean isMonster = false;
        ArrayList<Integer> placeOfMonsterCard = new ArrayList<>();
        Card cardForAi = null;
        if (isSpellZoneFull(duelModel.turn))
            return "spellZone full!";
        for (int i = 0; i < 5; i++) {
            Card card = duelModel.getMonstersInField().get(duelModel.turn).get(i);

            if (card != null) {
                //check it
                if (duelModel.getMonsterCondition(duelModel.turn, i).equals("DO") || duelModel.getMonsterCondition(duelModel.turn, i).equals("OO"))
                    isMonster = true;
                if (cardForAi != null) {
                    if (card.getAttackPower() > cardForAi.getAttackPower())
                        cardForAi = card;
                } else cardForAi = card;
                placeOfMonsterCard.add(i);
            }
        }

        if (isMonster) {
            int place;
            if (duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                place = placeOfMonsterCard.indexOf(cardForAi);
            } else {
                while (true) {
                    place = duelView.scanForChoseMonsterForEquip(placeOfMonsterCard);
                    if (placeOfMonsterCard.contains(place))
                        break;
                }
            }
            Card monsterForEquip = duelModel.getMonster(duelModel.turn, place);
            Card thisSpell = duelModel.getSelectedCards().get(duelModel.turn).get(0);

            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            } else {
                activeSpellFromHand();
            }
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(thisSpell, false);
            isOpponentHasAnySpellOrTrapForActivate();
            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(thisSpell)) {
                if (duelModel.getMonstersInField().get(duelModel.turn).contains(monsterForEquip)) {
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(thisSpell);

                    Card monster = duelModel.getMonstersInField().get(duelModel.turn).get(place);

                    blackPendant(monster, 1);
                    Card spell = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    duelModel.activeEquip(monster, spell);

                    return "spell activated";
                }
            }
        }
        return "you don't have any monster to equip";
    }

    public void blackPendant(Card monster, int activeOrdeActive) {
        monster.setAttackPower(monster.getAttackPower() + 500 * activeOrdeActive);
    }

    public String effectOfSwordOfDarkstraction(int placeOfSpell) {
        boolean isMonster = false;
        ArrayList<Integer> placeOfMonsterCard = new ArrayList<>();
        Card cardForAi = null;
        if (isSpellZoneFull(duelModel.turn))
            return "spellZone full!";
        for (int i = 0; i < 5; i++) {
            Card card = duelModel.getMonstersInField().get(duelModel.turn).get(i);
            if (card != null) {
                //check it
                if (duelModel.getMonsterCondition(duelModel.turn, i).equals("DO") || duelModel.getMonsterCondition(duelModel.turn, i).equals("OO"))
                    if (card.getCategory().equals("Fiend") || card.getCategory().equals("Spellcaster")) {
                        if (cardForAi != null) {
                            if (card.getAttackPower() > cardForAi.getAttackPower())
                                cardForAi = card;
                        } else cardForAi = card;
                        isMonster = true;
                        placeOfMonsterCard.add(i);
                    }
            }
        }
        if (isMonster) {
            int place;
            if (duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                place = placeOfMonsterCard.indexOf(cardForAi);
            } else {
                while (true) {
                    place = duelView.scanForChoseMonsterForEquip(placeOfMonsterCard);
                    if (placeOfMonsterCard.contains(place))
                        break;
                }
            }
            Card monsterForEquip = duelModel.getMonster(duelModel.turn, place);
            Card thisSpell = duelModel.getSelectedCards().get(duelModel.turn).get(0);

            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            } else {
                activeSpellFromHand();
            }
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(thisSpell, false);
            isOpponentHasAnySpellOrTrapForActivate();
            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(thisSpell)) {
                if (duelModel.getMonstersInField().get(duelModel.turn).contains(monsterForEquip)) {
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(thisSpell);
                    Card monster = duelModel.getMonstersInField().get(duelModel.turn).get(place);
                    swordOfDarkstraction(monster, 1);
                    duelModel.activeEquip(monster, thisSpell);
                    return "spell activated";
                }
            }
        }
        return "you don't have any monster to equip";
    }


    private void swordOfDarkstraction(Card monster, int activeOrdeactive) {
        monster.setAttackPower(monster.getAttackPower() + 400 * activeOrdeactive);
        monster.setDefensePower(monster.getDefensePower() - 200 * activeOrdeactive);
    }

    public String effectOfUmiiruka(int activeOrdeActive) {
        for (int i = 0; i < 5; i++) {
            Card monster = duelModel.getMonstersInField().get(duelModel.turn).get(i);
            Card monster1 = duelModel.getMonstersInField().get(duelModel.turn).get(i);
            if (monster != null)
                if (monster.getCardType().equals("Aqua"))
                    if (duelModel.getSpellZoneActivate().get(duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(duelModel.turn).add(i, true);
                        monster.setDefensePower(monster.getDefensePower() - 400 * activeOrdeActive);
                        monster.setAttackPower(monster.getAttackPower() + 500 * activeOrdeActive);
                    }
            if (monster1 != null)
                if (monster1.getCardType().equals("Aqua"))
                    if (!duelModel.getSpellZoneActivate().get(1 - duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(1 - duelModel.turn).add(i, true);
                        monster1.setDefensePower(monster1.getDefensePower() - 400 * activeOrdeActive);
                        monster1.setAttackPower(monster1.getAttackPower() + 500 * activeOrdeActive);
                    }
        }

        return "spellZone activated";
    }

    public String effectOfClosedForest(int activeOrdeActive) {

        for (int i = 0; i < 5; i++) {
            Card monster = duelModel.getMonstersInField().get(playerActiveCloseForest).get(i);
            if (monster != null)
                if (monster.getCardType().equals("Beast-Type")) {
                    if (!duelModel.getSpellZoneActivate().get(playerActiveCloseForest).get(i))
                        duelModel.getSpellZoneActivate().get(playerActiveCloseForest).add(i, true);
                    if (activeOrdeActive == 1)
                        monster.setAttackPower(monster.getAttackPower() + duelModel.getGraveyard(playerActiveCloseForest).size() * 100 - attackaddedForClosedForest);
                    else
                        monster.setAttackPower(monster.getAttackPower() - attackaddedForClosedForest);
                }
            attackaddedForClosedForest = duelModel.getGraveyard(playerActiveCloseForest).size() * 100;

        }
        return "spellZone activated";
    }

    public String effectOfForest(int activeOrdeActive) {

        for (int i = 0; i < 5; i++) {
            Card monster = duelModel.getMonstersInField().get(duelModel.turn).get(i);
            Card monster1 = duelModel.getMonstersInField().get(1 - duelModel.turn).get(i);
            if (monster != null)
                if (monster.getCardType().equals("Beast-Warrior") || monster.getCardType().equals("Beast") || monster.getCardType().equals("Insect")) {
                    forest(i, monster, activeOrdeActive);
                }
            if (monster1 != null)
                if (monster1.getCardType().equals("Beast-Warrior") || monster.getCardType().equals("Beast") || monster.getCardType().equals("Insect"))
                    forest(i, monster1, activeOrdeActive);
        }
        return "spellZone activated";
    }

    private void forest(int i, Card monster, int activeOrdeActive) {
        if (!duelModel.getSpellZoneActivate().get(duelModel.turn).get(i)) {
            duelModel.getSpellZoneActivate().get(duelModel.turn).add(i, true);
            Card monster2 = Card.getCardByName(monster.getName());
            monster.setAttackPower(monster2.getAttackPower() + 200 * activeOrdeActive);
            monster.setDefensePower(monster2.getDefensePower() + 200 * activeOrdeActive);
        }
    }

    public String effectOfYami(int activeOrdeActive) {
        for (int i = 0; i < 5; i++) {
            Card monster = duelModel.getMonstersInField().get(duelModel.turn).get(i);
            Card monster1 = duelModel.getMonstersInField().get(1 - duelModel.turn).get(i);
            if (monster != null) {
                if (monster.getCardType().equals("Fiend") || monster.getCardType().equals("Spellcaster")) {
                    forest(i, monster, activeOrdeActive);
                    forest(i, monster1, activeOrdeActive);
                } else if (monster.getCardType().equals("Fairy")) {
                    forest(i, monster, activeOrdeActive * -1);
                    forest(i, monster1, activeOrdeActive * -1);
                }
            }
            if (monster1 != null) {
                if (monster1.getCardType().equals("Fiend") || monster1.getCardType().equals("Spellcaster")) {
                    if (!duelModel.getSpellZoneActivate().get(1 - duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(1 - duelModel.turn).add(i, true);
                        monster1.setAttackPower(Card.getCardByName(monster1.getName()).getAttackPower() + 200);
                        monster1.setDefensePower(Card.getCardByName(monster1.getName()).getDefensePower() + 200);

                    }
                } else if (monster1.getCardType().equals("Fairy")) {
                    if (!duelModel.getSpellZoneActivate().get(1 - duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(1 - duelModel.turn).add(i, true);
                        Card monster2 = Card.getCardByName(monster.getName());
                        monster1.setAttackPower(monster2.getAttackPower() - 200);
                        monster1.setDefensePower(monster2.getDefensePower() - 200);
                    }
                }
            }
        }
        return "spellZoneActivate";
    }

    public boolean isMonsterZoneFull(int turn) {
        boolean isMonsterZoneFull = true;
        ArrayList<Card> monsterInFiledPlayer = duelModel.getMonstersInField().get(turn);
        for (Card card : monsterInFiledPlayer) {
            if (card == null) {
                isMonsterZoneFull = false;
                break;
            }
        }
        return isMonsterZoneFull;
    }


    public void specialSummonMonsterOnFieldFromGraveyard(int turn, String state, int indexOfCardOfGraveyard) {
        String stateOfCard = "OO";
        if (state.equals("Attack")) {
            stateOfCard = "OO";
        } else if (state.equals("Defence")) {
            stateOfCard = "DO";
        }
        Card card = duelModel.getGraveyard(duelModel.turn).get(indexOfCardOfGraveyard);
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/1", card, 0);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/2", card, 1);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/3", card, 2);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/4", card, 3);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/5", card, 4);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        }
        if (card.getName().equals("Command knight")) {
            MainPhaseController mainPhaseController = MainPhaseController.getInstance();
            mainPhaseController.effectOfCommandKnight();
        }
        duelController.activeFieldInGame();
        duelModel.monsterSummonForEffectOfSomeTraps = card;
    }

    public String opponentActiveTrap() {
        Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (!duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[1].equals("H")) {
            return "card has been activated before";
        } else {
            ArrayList<Card> monsters = duelModel.getMonstersInField().get(1 - duelModel.turn);
            for (int i = 0; i < 5; i++) {
                if (monsters.get(i) != null) {
                    if (monsters.get(i).getName().equals("Mirage Dragon") && duelModel.getMonsterCondition(1 - duelModel.turn,
                            i + 1).split("/")[0].charAt(1) == 'O') {
                        return "opponent has face up Mirage Dragon Monster so you can't active";
                    }
                }
            }
            if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Magic Cylinder")) {
                return activeOtherTraps();
            } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Mirror Force")) {
                return activeOtherTraps();
            } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Mind Crush")) {
                return activeTrapMindCrush();
            } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Trap Hole")) {
                return effectOfTrapHole();
            } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Torrential Tribute")) {
                return effectOfTorrentialTribute();
            } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Time Seal")) {
                return activeOtherTraps();
            } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Negate Attack")) {
                return activeOtherTraps();
            } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Call of the Haunted")) {
                return activeTrapCallOfTheHaunted();
            } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Magic Jammer")) {
                return MagicJammer();
            } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Solemn Warning")) {
                return effectOfSolemnWarning();
            }
            return "trap activated";
        }
    }

    public String activeOtherTraps() {
        Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        activeNormalTraps();
        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(trap, false);
        duelController.isOpponentHasAnySpellOrTrapForActivate();
        int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(trap)) {
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
            return "trap activated";
        } else {
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
            return "";
        }
    }

    public String effectOfSolemnWarning() {
        if (duelModel.monsterSummonForEffectOfSomeTraps == null) {
            return "no monster summon";
        } else {
            Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            activeNormalTraps();
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(trap, false);
            duelController.isOpponentHasAnySpellOrTrapForActivate();
            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(trap)) {
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
                Card card = duelModel.monsterSummonForEffectOfSomeTraps;
                int indexOfMonsterSummoned = duelModel.getMonstersInField().get(1 - duelModel.turn).indexOf(card);
                duelModel.decreaseLifePoint(2000, duelModel.turn);
                duelModel.deleteMonster(1 - duelModel.turn, indexOfMonsterSummoned);
                duelModel.addCardToGraveyard(1 - duelModel.turn, card);
                int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
                duelModel.deleteSpellAndTrap(duelModel.turn, place - 1);
                duelModel.addCardToGraveyard(duelModel.turn, trap);
                return "trap activated";
            }
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
            return "";
        }
    }

    public String effectOfTorrentialTribute() {
        if (duelModel.monsterSummonForEffectOfSomeTraps == null) {
            return "no monster summon";
        } else {
            boolean opponentHasAnyMonster = false;
            boolean youHaveAnyMonster = false;
            ArrayList<Card> monstersInFieldOfOpponent = duelModel.getMonstersInField().get(1 - duelModel.turn);
            ArrayList<Card> monstersInFieldOfYou = duelModel.getMonstersInField().get(duelModel.turn);
            for (Card card : monstersInFieldOfOpponent) {
                if (card != null) {
                    opponentHasAnyMonster = true;
                    break;
                }
            }
            for (Card card : monstersInFieldOfYou) {
                if (card != null) {
                    youHaveAnyMonster = true;
                    break;
                }
            }
            if (!youHaveAnyMonster && !opponentHasAnyMonster) {
                return "no monster exist in field";
            } else {
                Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                activeNormalTraps();
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(trap, false);
                duelController.isOpponentHasAnySpellOrTrapForActivate();
                if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(trap)) {
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
                    deleteAllMonsters(duelModel.turn);
                    deleteAllMonsters(1 - duelModel.turn);
                    int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
                    duelModel.deleteSpellAndTrap(duelModel.turn, place - 1);
                    duelModel.addCardToGraveyard(duelModel.turn, trap);
                    return "trap activated";
                }
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
                return "";
            }
        }
    }

    public String effectOfTrapHole() {
        // check...
        if (duelModel.monsterFlipSummonOrNormalSummonForTrapHole == null) {
            return "your opponent didnt summon or flipSummon card with property for destroy";
        } else {
            Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            activeNormalTraps();
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(trap, false);
            duelController.isOpponentHasAnySpellOrTrapForActivate();
            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(trap)) {
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
                Card card = duelModel.monsterFlipSummonOrNormalSummonForTrapHole;
                int indexOfMonsterSummonOrFlipSummon = duelModel.getMonstersInField().get(1 - duelModel.turn).indexOf(card);
                duelModel.deleteMonster(1 - duelModel.turn, indexOfMonsterSummonOrFlipSummon);
                duelModel.addCardToGraveyard(1 - duelModel.turn, card);
                int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
                duelModel.deleteSpellAndTrap(duelModel.turn, place - 1);
                duelModel.addCardToGraveyard(duelModel.turn, trap);
                return "trap activated";
            }
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
            return "";
        }
    }

    public String MagicJammer() {
        if (duelModel.getSpellOrTrapActivated().get(1 - duelModel.turn).size() == 0) {
            return "no card activated";
        } else {
            Card cardActivated = null;
            for (Map.Entry<Card, Boolean> entry : duelModel.getSpellOrTrapActivated().get(1 - duelModel.turn).entrySet()) {
                cardActivated = entry.getKey();
            }
            assert cardActivated != null;
            if (!cardActivated.getCategory().equals("Spell")) {
                return "no spell activated";
            } else {
                Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                activeNormalTraps();
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(trap, false);
                duelController.isOpponentHasAnySpellOrTrapForActivate();
                if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(trap)) {
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
                    int placeOfCardDeleteFromHand = duelView.scanNumberOfCardForDeleteFromHand();
                    if (placeOfCardDeleteFromHand > duelModel.getHandCards().get(duelModel.turn).size()) {
                        return "you must enter address that has card (correct address)";
                    } else {
                        Card card = duelModel.getHandCards().get(duelModel.turn).get(placeOfCardDeleteFromHand - 1);
                        duelModel.deleteCardFromHand(card);
                        duelModel.addCardToGraveyard(duelModel.turn, card);
                        int indexOfCardActivated = duelModel.getSpellsAndTrapsInFiled().get(1 - duelModel.turn).
                                indexOf(cardActivated);
                        deleteASpell(1 - duelModel.turn, indexOfCardActivated + 1, cardActivated);
                        int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
                        duelModel.deleteSpellAndTrap(duelModel.turn, place - 1);
                        duelModel.addCardToGraveyard(duelModel.turn, trap);
                        return "trap activated";
                    }
                }
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
                return "";
            }
        }
    }


    public void activeNormalTraps() {
        Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
        String condition = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap);
        duelModel.changeSpellAndTrapCondition(duelModel.turn, place, condition.replaceAll("H", "O"));
    }

    public String activeTrapMindCrush() {
        Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        activeNormalTraps();
        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(trap, false);
        duelController.isOpponentHasAnySpellOrTrapForActivate();
        int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(trap)) {
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
            duelModel.deleteSpellAndTrap(duelModel.turn, place - 1);
            duelModel.addCardToGraveyard(duelModel.turn, trap);
//        String condition = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap);
//        duelModel.changeSpellAndTrapCondition(duelModel.turn, place,condition.replaceAll("H","O"));
            String cardName = duelView.getCardNameForTrapMindCrush();
            ArrayList<ArrayList<Card>> cardsInHand = duelModel.getHandCards();
            boolean isCardExistInOpponentHand = false;
            for (Card card : cardsInHand.get(duelModel.turn - 1)) {
                if (card.getName().equals(cardName)) {
                    duelModel.deleteCardFromOpponentHand(card);
                    isCardExistInOpponentHand = true;
                }
            }
            if (!isCardExistInOpponentHand) {
                ArrayList<Integer> number = new ArrayList<>();
                for (int i = 0; i < duelModel.getHandCards().get(duelModel.turn).size(); i++) {
                    number.add(i + 1);
                }
                Collections.shuffle(number);
                Card card = duelModel.getHandCards().get(duelModel.turn).get(number.get(0));
                duelModel.deleteCardFromHand(card);
                duelModel.addCardToGraveyard(duelModel.turn, card);
            }
            return "trap activated";
        }
        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
        return "";
    }

    public String activeTrapCallOfTheHaunted() {
        Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        activeNormalTraps();
        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(trap, false);
        duelController.isOpponentHasAnySpellOrTrapForActivate();
        int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(trap)) {
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
            duelModel.deleteSpellAndTrap(duelModel.turn, place - 1);
            duelModel.addCardToGraveyard(duelModel.turn, trap);
            ArrayList<Card> graveyard = duelModel.getGraveyard(duelModel.turn);
            for (int i = 0; i < graveyard.size() - 1; i++) {
                Card card = graveyard.get(i);
                if (card.getCategory().equals("Monster")) {
                    ArrayList<Card> monsters = duelModel.getMonstersInField().get(duelModel.turn);
                    for (int j = 0; j < 5; j++) {
                        if (monsters.get(j) == null) {
                            duelModel.addCertainMonsterFromGraveyardToGame(duelModel.turn, "OO/" + j + 1, j, card);
                            duelModel.deleteCardFromGraveyard(duelModel.turn, i);
                            return "trap activated";
                        }
                    }
                }
            }
        }
        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(trap);
        return "";
    }

    public ArrayList<String> showGraveYard(int turn) {
        ArrayList<Card> graveyardCards = duelModel.getGraveyard(turn);
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < graveyardCards.size(); i++) {
            output.add(i + 1 + ". " + graveyardCards.get(i).getName() + ": " + graveyardCards.get(i).getDescription());
        }
        if (graveyardCards.size() == 0) {
            output.add("graveyard empty");
        }
        return output;
    }

    public ArrayList<String> checkCard(Matcher matcher) {
        ArrayList<String> output = new ArrayList<>();
        if (Card.getCardByName(matcher.group(1)) == null) {
            output.add("card with name " + matcher.group(1) + " does not exist");
        } else {
            Card card = Card.getCardByName(matcher.group(1));
            if (card.getCategory().equals("Monster")) {
                output = showMonster(matcher.group(1));
            } else if (card.getCategory().equals("Spell")) {
                output = showSpell(matcher.group(1));
            } else {
                output = showTrap(matcher.group(1));
            }
        }
        return output;
    }

    public ArrayList<String> checkSelectedCard(Matcher matcher) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<ArrayList<Card>> selectedCards = duelModel.getSelectedCards();
        ArrayList<HashMap<Card, String>> detailsOfSelectedCards = duelModel.getDetailOfSelectedCard();
        if (selectedCards.get(duelModel.turn).get(0) == null) {
            output.add("no card is selected yet");
        } else {
            Card card = selectedCards.get(duelModel.turn).get(0);
            if (detailsOfSelectedCards.get(duelModel.turn).get(card).equals("Opponent/H")) {
                output.add("card is not visible");
            } else {
                if (card.getCategory().equals("Monster")) {
                    output = showMonster(card.getName());
                } else if (card.getCategory().equals("Spell")) {
                    output = showSpell(card.getName());
                } else {
                    output = showTrap(card.getName());
                }
            }
        }
        return output;
    }

    private ArrayList<String> showMonster(String cardName) {
        Card monster = Card.getCardByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + monster.getName());
        output.add("Level: " + monster.getLevel());
        output.add("Type: " + monster.getCardType());
        output.add("ATK: " + monster.getAttackPower());
        output.add("DEF: " + monster.getDefensePower());
        output.add("Description: " + monster.getDescription());
        return output;
    }

    private ArrayList<String> showSpell(String cardName) {
        Card spell = Card.getCardByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + spell.getName());
        output.add("Spell");
        output.add("Type: " + spell.getCardType());
        output.add("Description: " + spell.getDescription());
        return output;
    }

    private ArrayList<String> showTrap(String cardName) {
        Card trap = Card.getCardByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + trap.getName());
        output.add("Trap");
        output.add("Type: " + trap.getCardType());
        output.add("Description: " + trap.getDescription());
        return output;
    }

    public String surrender() {
        return null;
    }

    public String selectMonster(Matcher matcher) {
        try {
            LoginController.dataOutputStream.writeUTF("Select Monster" + "/" + Integer.parseInt(matcher.group(1))
                    + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }


    public String selectOpponentMonster(Matcher matcher) {
        try {
            LoginController.dataOutputStream.writeUTF("Select Opponent Monster" + "/" + Integer.parseInt(matcher.group(1))
                    + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }


    public String selectSpellOrTrap(Matcher matcher) {
        try {
            LoginController.dataOutputStream.writeUTF("Select Spell Or Trap" + "/" + Integer.parseInt(matcher.group(1))
                    + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public String selectOpponentSpellOrTrap(Matcher matcher) {
        try {
            LoginController.dataOutputStream.writeUTF("Select Opponent Spell Or Trap" + "/" + Integer.parseInt(matcher.group(1))
                    + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public String selectFieldZone(int place) {
        try {
            LoginController.dataOutputStream.writeUTF("Select FieldZone" + "/" + place + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public String selectOpponentFieldZone(int place) {
        try {
            LoginController.dataOutputStream.writeUTF("Select Opponent FieldZone" + "/" + place + "/" +
                    LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }


    public String selectHand(Matcher matcher) {
        try {
            LoginController.dataOutputStream.writeUTF("Select Hand" + "/" + Integer.parseInt(matcher.group(1)) + "/" +
                    LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public void increaseLP(Matcher matcher) {
        duelModel.increaseLifePoint(Integer.parseInt(matcher.group(1)), duelModel.turn);
    }
}