package Controller;

import Model.Card;
import Model.DuelModel;
import View.DuelView;
import View.MainPhaseView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;

public class MainPhaseController extends DuelController {
    private static final MainPhaseController mainPhaseController = new MainPhaseController();
    DuelModel duelModel = duelController.duelModel;
    DuelView duelView = duelController.duelView;

    private MainPhaseController() {

    }

    public static MainPhaseController getInstance() {
        return mainPhaseController;
    }

    public String set() {
        try {
            LoginController.dataOutputStream.writeUTF("set/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "cant send massege to server";
//        ArrayList<ArrayList<Card>> selectedCards = duelModel.getSelectedCards();
//        if (selectedCards.get(duelModel.turn) == null) {
//            return "no card is selected yet";
//        } else {
//            if (!(duelModel.getHandCards().get(duelModel.turn)).contains((selectedCards.get(duelModel.turn)).get(0))) {
//                return "you can’t set this card";
//            } else if ((selectedCards.get(duelModel.turn).get(0)).getCategory().equals("Monster")) {
//                if (selectedCards.get(duelModel.turn).get(0).getLevel() > 4)
//                    return "this card cannot set normally";
//                if (duelModel.monsterSetOrSummonInThisTurn != null)
//                    return "you already summoned/set on this turn";
//                if (selectedCards.get(duelModel.turn).get(0).getLevel() > 5)
//                    return "this card can not set";
//                else
//                    return this.setMonster();
//
//            } else
//                return setTrapOrSpell();
//        }
    }

    public String setTrapOrSpell() {
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (card.getCardType().equals("Field")) {
            duelModel.setField(card);
        }
        if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(0) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/1", 0);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(1) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/2", 1);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(2) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/3", 2);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(3) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/4", 3);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(4) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/5", 4);
        else return "spell card zone is full";
        duelModel.setSpellsAndTrapsSetInThisTurn(duelModel.turn, card);
        return "set successfully";
    }

    public String setMonster() {
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromHandToGame("DH/1", 0);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 1);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromHandToGame("DH/2", 1);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 2);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromHandToGame("DH/3", 2);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 3);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromHandToGame("DH/4", 3);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 4);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromHandToGame("DH/5", 4);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 5);
        } else {
            return "monster card zone is full";
        }
        for (Card spellCard : duelModel.getSpellsAndTrapsInFiled().get(1 - duelModel.turn)) {
            if (spellCard != null) {
                if (spellCard.getName().equals("Swords of Revealing Light")) {
                    duelController.changeStateOfMonsterWithSwordsCard(duelModel.turn);
                    break;
                }
            }
        }
        return "set successfully";
    }

    public String setPosition(Matcher matcher) {
        String newPosition = matcher.group(1);
        int place = duelModel.getMonstersInField().get(duelModel.turn).indexOf(duelModel.getSelectedCards().get(duelModel.turn).get(0));
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) return "no card is selected yet";
        else if (!duelModel.getMonstersInField().get(duelModel.turn).contains(duelModel.getSelectedCards().get(duelModel.turn).get(0)))
            return "you can’t change this card position";
        else if (newPosition.equals("attack")) {
            if (duelModel.getMonsterCondition(duelModel.turn, place).equals("OO"))
                return "this card is already in the wanted position";
            else if (duelModel.setposition[place - 1])
                return "you already changed this card position in this turn";
            else {
                duelModel.changeAttackAndDefense(place);
                return "monster card position changed successfully";
            }

        } else {
            if (duelModel.getMonsterCondition(duelModel.turn, place).equals("DO"))
                return "this card is already in the wanted position";
            else if (duelModel.setposition[place - 1])
                return "you already changed this card position in this turn";
            else {
                duelModel.changeAttackAndDefense(place);
                return "monster card position changed successfully";
            }
        }

    }

    public String summon() {
        String result = "";
        String result1 = "";
        try {
            LoginController.dataOutputStream.writeUTF("summon" + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            result = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        String[] partsOfResult = result.split("/");
        if (partsOfResult.length >= 2) {
            result1 = partsOfResult[1];
        }
        if (result1.equals("summon Terratiger, the Empowered Warrior")) {
            return normalSummonCardThatCanSummonAnotherCard(partsOfResult[0]);
        } else if (result.equals("Normal Summon Card With Level 5 or 6")) {
            int address = mainPhaseView.getCardAddressForTribute();
            String stateOfCard = mainPhaseView.getStateOfCardForSummon();
            try {
                LoginController.dataOutputStream.writeUTF("Summon Monster With Level 5 Or 6" + "/" + address
                        + "/" + stateOfCard + "/" + LoginController.token);
                LoginController.dataOutputStream.flush();
                return LoginController.dataInputStream.readUTF();
            } catch (IOException x) {
                x.printStackTrace();
            }
        } else if (result.equals("Summon Beast King Barbaros")) {
            return summonMonsterHasTwoMethods();
        } else if (result.equals("Summon Monster With Level 7 Or More")) {
            int address = mainPhaseView.getCardAddressForTribute();
            int address1 = mainPhaseView.getCardAddressForTribute();
            String stateOfCard = mainPhaseView.getStateOfCardForSummon();
            try {
                LoginController.dataOutputStream.writeUTF("Summon Monster With Level 7 Or More" + "/" + address
                        + "/" + address1 + "/" + stateOfCard + "/" + LoginController.token);
                LoginController.dataOutputStream.flush();
                return LoginController.dataInputStream.readUTF();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
        return result;
    }


    public void effectOfCommandKnight() {
        for (Card card : duelModel.getMonstersInField().get(duelModel.turn)) {
            if (card != null) {
                card.setAttackPower(card.getAttackPower() + 400);
            }
        }
        for (Card card : duelModel.getMonstersInField().get(1 - duelModel.turn)) {
            if (card != null) {
                card.setAttackPower(card.getAttackPower() + 400);
            }
        }
    }

    public String flipSummon() {
        try {
            LoginController.dataOutputStream.writeUTF("flip Summon" + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public String flipSummonManEaterBug() {
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        int placeOfMonster = mainPhaseView.scanPlaceOfMonsterForDestroyInManEaterFlipSummon();
        try {
            LoginController.dataOutputStream.writeUTF("Flip Summon ManEaterBug" + "/" + placeOfMonster + "/" +
                    LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public String specialSummon() {
        String result = "";
        try {
            LoginController.dataOutputStream.writeUTF("special Summon" + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            result = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();

        if (result.equals("Special Summon The Tricky")) {
            int address = mainPhaseView.getCardAddressForTribute();
            String stateOfCard = mainPhaseView.getStateOfCardForSummon();
            try {
                LoginController.dataOutputStream.writeUTF("Special Summon The Tricky" + "/" + address
                        + "/" + stateOfCard + "/" + LoginController.token);
                LoginController.dataOutputStream.flush();
                return LoginController.dataInputStream.readUTF();
            } catch (IOException x) {
                x.printStackTrace();
            }
        } else if (result.equals("Special Summon Gate Guardian")) {
            int address = mainPhaseView.getCardAddressForTribute();
            int address1 = mainPhaseView.getCardAddressForTribute();
            int address2 = mainPhaseView.getCardAddressForTribute();
            String stateOfCard = mainPhaseView.getStateOfCardForSummon();
            try {
                LoginController.dataOutputStream.writeUTF("Special Summon Gate Guardian" + "/" + address
                        + "/" + address1 + "/" + address2 + "/" + stateOfCard + "/" + LoginController.token);
                LoginController.dataOutputStream.flush();
                return LoginController.dataInputStream.readUTF();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
        return result;
    }


    public String summonMonsterHasTwoMethods() {
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        String response = mainPhaseView.summonMonsterHasTwoMethods();
        int address1 = mainPhaseView.getCardAddressForTribute();
        int address2 = mainPhaseView.getCardAddressForTribute();
        int address3 = mainPhaseView.getCardAddressForTribute();
        String stateOfCard = mainPhaseView.getStateOfCardForSummon();
        try {
            LoginController.dataOutputStream.writeUTF("Summon Beast King Barbaros" + "/" + response + "/" + address1
                    + "/" + address2 + "/" + address3 + "/" + stateOfCard + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public String normalSummonCardThatCanSummonAnotherCard(String response) {
        String response1;
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        if (response.equals("summoned successfully")) {
            response1 = mainPhaseView.normalSummonCardThatCanSummonAnotherCard();
            try {
                LoginController.dataOutputStream.writeUTF("Summon Terratiger, the Empowered Warrior" + "/" + response1
                        + "/" + LoginController.token);
                LoginController.dataOutputStream.flush();
                return LoginController.dataInputStream.readUTF();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
        return "";
    }

    public String activateSpellEffectMainController() {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Activate Effect Main Controller" + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        String[] partsOfResponse = response.split("/");
        if (partsOfResponse.length > 2) {
            int placeOfSpell = Integer.parseInt(partsOfResponse[0]);
            String cardName = partsOfResponse[1];
            String cardType = partsOfResponse[2];
            return duelController.activateEffect(placeOfSpell, cardName, cardType);
        }
        return response;

    }

    public boolean anyoneWon() {
        if (duelModel.getLifePoint(0) <= 0 || duelModel.getLifePoint(1) <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
