package Controller;

import Model.Card;
import Model.Monster;
import View.MainPhaseView;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainPhaseController extends DuelController {
    private static MainPhaseController mainPhaseController = new MainPhaseController();

    private MainPhaseController() {

    }

    public static MainPhaseController getInstance() {
        return mainPhaseController;
    }


    public String set() {
        ArrayList<ArrayList<Card>> selectedCards = this.duelModel.getSelectedCards();
        if (selectedCards.get(this.duelModel.turn) == null) {
            return "no card is selected yet";
        } else {
            if (!(duelModel.getHandCards().get(this.duelModel.turn)).contains((selectedCards.get(this.duelModel.turn)).get(0))) {
                return "you can’t set this card";
            } else if (this.duelModel.monsterSetOrSummonInThisTurn == null) {
                return "you already summoned/set on this turn";
            } else if ((selectedCards.get(this.duelModel.turn).get(0)).getCardType().equals("Monster")) {
                return this.setMonster();
            } else
                return setTrapOrSpell();
        }
    }

    public String setTrapOrSpell() {

        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (card.getCategory().equals("Field")) {
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
        Monster monster = (Monster) card;
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromHandToGame("DH/1", 0);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 1);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromHandToGame("DH/2", 1);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 2);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromHandToGame("DH/3", 2);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 3);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromHandToGame("DH/4", 3);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 4);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromHandToGame("DH/5", 4);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 5);
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
        String response = "";
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        boolean existMonsterOnField = false;
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Monster")) {
            return "you can’t summon this card";
        } else {
            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            ArrayList<Card> cardsInHandsOfPlayer = duelModel.getHandCards().get(duelModel.turn);
            String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);
            Monster monster = (Monster) card;
            if (!detailsOfSelectedCard.equals("Hand")) {
                return "you can’t summon this card";
            } else if (card.getCardType().equals("Ritual") || monster.isHasSpecialSummon()) {
                return "you can’t summon this card";
            } else if (duelModel.getMonsterSetOrSummonInThisTurn() != null) {
                return "you already summoned/set on this turn";
            } else {
                if (monster.getLevel() <= 4) {
                    response = normalSummonMonsterOnField(monster, "Attack");
                    return response;
                }
                if (monster.getName().equals("Terratiger, the Empowered Warrior")) {
                    return normalSummonCardThatCanSummonAnotherCard(response);
                } else if (monster.getLevel() == 5 || monster.getLevel() == 6) {
                    for (Card cardInHandOfPlayer : cardsInHandsOfPlayer) {
                        if (cardInHandOfPlayer.getCategory().equals("monster")) {
                            existMonsterOnField = true;
                            break;
                        }
                    }
                    if (!existMonsterOnField) {
                        return "there are not enough cards for tribute";
                    } else {
                        int address = mainPhaseView.getCardAddressForTribute();
                        if (address > 5) {
                            return "there no monsters one this address";
                        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null) {
                            return "there no monsters one this address";
                        } else {
                            String stateOfCard = mainPhaseView.getStateOfCardForSummon();
                            if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                                return "please enter the appropriate state (Defence or Attack)";
                            } else {
                                duelModel.deleteMonster(duelModel.turn, address - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                        address));
                                return normalSummonMonsterOnField(monster, stateOfCard);
                            }
                        }
                    }
                } else if (monster.getName().equals("Beast King Barbaros")) {
                    return summonMonsterHasTwoMethods(monster);
                } else if (monster.getLevel() >= 7) {
                    int numberOfPlayerMonsterOnField = 0;
                    for (Card cardInHandOfPlayer : cardsInHandsOfPlayer) {
                        if (cardInHandOfPlayer.getCategory().equals("monster")) {
                            numberOfPlayerMonsterOnField++;
                        }
                    }
                    if (numberOfPlayerMonsterOnField < 2) {
                        return "there are not enough cards for tribute";
                    }
                    int address = mainPhaseView.getCardAddressForTribute();
                    int address1 = mainPhaseView.getCardAddressForTribute();
                    if (address > 5 || address1 > 5 || address1 == address) {
                        return "there is no monster on one of these addresses";
                    } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null
                            || duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null) {
                        return "there is no monster on one of these addresses";
                    } else {
                        String stateOfCard = mainPhaseView.getStateOfCardForSummon();
                        if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                            return "please enter the appropriate state (Defence or Attack)";
                        } else {
                            duelModel.deleteMonster(duelModel.turn, address - 1);
                            duelModel.deleteMonster(duelModel.turn, address1 - 1);
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address));
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address1));
                            return normalSummonMonsterOnField(monster, stateOfCard);
                        }

                    }
                }
            }
        }
        return "";
    }

    public String normalSummonMonsterOnField(Monster monster, String state) {
        String stateOfCard = "OO";
        if (state.equals("Attack")) {
            stateOfCard = "OO";
        } else if (state.equals("Defence")) {
            stateOfCard = "DO";
        }
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/1", 0);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 1);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/2", 1);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 2);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/3", 2);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 3);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/4", 3);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 4);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/5", 4);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 5);
        } else {
            return "monster card zone is full";
        }
        if (monster.getLevel() <= 4) {
            if (monster.getAttackPower() >= 1000) {
                duelModel.monsterFlipSummonOrNormalSummonForTrapHole = monster;
            }
        }
        duelModel.monsterSummonForEffectOfSomeTraps = monster;
        return "summoned successfully";
    }

    public String specialSummonMonsterOnField(String state) {
        String stateOfCard = "OO";
        if (state.equals("Attack")) {
            stateOfCard = "OO";
        } else if (state.equals("Defence")) {
            stateOfCard = "DO";
        }
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/1", 0);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/2", 1);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/3", 2);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/4", 3);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/5", 4);
        } else {
            return "monster card zone is full";
        }
        duelModel.monsterSummonForEffectOfSomeTraps = card;
        return "summoned successfully";
    }

    public String flipSummon() {
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else {
            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            if (!card.getCategory().equals("Monster")) {
                return "you can’t flipSummon this card";
            }
            String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);
            String[] details = detailsOfSelectedCard.split("/");
            if (details[0].equals("Hand") || details[0].equals("Opponent")) {
                return "you can’t change this card position";
            } else if (!details[1].equals("DO") && !details[1].equals("DH") && !details[1].equals("OO")) {
                return "you can’t change this card position";
            } else if (!details[1].equals("DH")) {
                return "you can’t flip summon this card";
            } else {
                int placeOfSummonCard = Integer.parseInt(details[2]);
                if (duelModel.getMonsterSetOrSummonInThisTurn() == null) {
                    duelModel.flipSummon(placeOfSummonCard - 1);
                    return "flip summoned successfully";
                } else {
                    String detailsOfCardSummonedOrSetInThisTurn = duelModel.getMonsterCondition(duelModel.turn,
                            duelModel.thePlaceOfMonsterSetOrSummonInThisTurn - 1);
                    String[] details1 = detailsOfCardSummonedOrSetInThisTurn.split("/");
                    int placeOfCardSummonedOrSetInThisTurn = Integer.parseInt(details1[1]);
                    if (placeOfCardSummonedOrSetInThisTurn == placeOfSummonCard) {
                        return "you can’t flip summon this card";
                    } else {
                        Monster monster = (Monster) card;
                        duelModel.flipSummon(placeOfSummonCard - 1);
                        if (monster.getLevel() <= 4) {
                            if (monster.getAttackPower() >= 1000) {
                                duelModel.monsterFlipSummonOrNormalSummonForTrapHole = monster;
                            }
                        }
                        duelModel.monsterSummonForEffectOfSomeTraps = monster;
                        if (card.getName().equals("Man-Eater Bug")) {
                            return "flipSummon Man-Eater Bug";
                        }
                        return "flip summoned successfully";
                    }
                }
            }
        }
    }

    public String flipSummonManEaterBug() {
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        int placeOfMonster = mainPhaseView.scanPlaceOfMonsterForDestroyInManEaterFlipSummon();
        if (placeOfMonster > 5) {
            return "you must enter correct address";
        } else {
            Card card = duelModel.getMonster(1 - duelModel.turn, placeOfMonster);
            if (card == null) {
                return "this address has not monster";
            } else {
                duelModel.deleteMonster(1 - duelModel.turn, placeOfMonster - 1);
                duelModel.addCardToGraveyard(1 - duelModel.turn, card);
                return "monster with this address in opponent board destroyed";
            }
        }
    }

    public String specialSummon() {
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "there is no way you could special summon a monster";
        } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Monster")) {
            return "there is no way you could special summon a monster";
        } else {
            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);
            Monster monster = (Monster) card;
            if (!detailsOfSelectedCard.equals("Hand")) {
                return "there is no way you could special summon a monster";
            } else if (!monster.isHasSpecialSummon()) {
                return "there is no way you could special summon a monster";
            } else if (monster.getName().equals("The Tricky")) {
                int address = mainPhaseView.getCardAddressForTribute();
                if (address > duelModel.getHandCards().get(duelModel.turn).size()) {
                    return "there is no way you could special summon a monster";
                } else {
                    String stateOfCard = mainPhaseView.getStateOfCardForSummon();
                    if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                        return "please enter the appropriate state (Defence or Attack)";
                    } else {
                        duelModel.deleteCardFromHandWithIndex(address - 1);
                        duelModel.addCardToGraveyard(duelModel.turn, duelModel.getHandCards().
                                get(duelModel.turn).get(address - 1));
                        return specialSummonMonsterOnField(stateOfCard);
                    }
                }
            } else if (monster.getName().equals("Gate Guardian")) {
                int address = mainPhaseView.getCardAddressForTribute();
                int address1 = mainPhaseView.getCardAddressForTribute();
                int address2 = mainPhaseView.getCardAddressForTribute();
                if (address > 5 || address1 > 5 || address2 > 5 || address == address1 || address == address2
                        || address1 == address2) {
                    return "there is no way you could special summon a monster";
                } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null
                        || duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null ||
                        duelModel.getMonstersInField().get(duelModel.turn).get(address2 - 1) == null) {
                    return "there is no way you could special summon a monster";
                } else {
                    String stateOfCard = mainPhaseView.getStateOfCardForSummon();
                    if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                        return "please enter the appropriate state (Defence or Attack)";
                    } else {
                        duelModel.deleteMonster(duelModel.turn, address - 1);
                        duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                address));
                        duelModel.deleteMonster(duelModel.turn, address1 - 1);
                        duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                address1));
                        duelModel.deleteMonster(duelModel.turn, address2 - 1);
                        duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                address2));
                        return specialSummonMonsterOnField(stateOfCard);
                    }
                }
            }
        }
        return "";
    }

    public String summonMonsterHasTwoMethods(Monster monster) {
        // monsterName : Beast King Barbaros
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        String response = mainPhaseView.summonMonsterHasTwoMethods();
        if (!response.equals("NO") && !response.equals("YES")) {
            return "Please enter correct response";
        } else if (response.equals("NO")) {
            monster.setAttackPower(1900);
            duelModel.monsterFlipSummonOrNormalSummonForTrapHole = monster;
            return normalSummonMonsterOnField(monster, "Attack");
        } else {
            int address1 = mainPhaseView.getCardAddressForTribute();
            int address2 = mainPhaseView.getCardAddressForTribute();
            int address3 = mainPhaseView.getCardAddressForTribute();
            if (address1 > 5 || address2 > 5 || address3 > 5 || address1 == address2 || address2 == address3
                    || address1 == address3) {
                return "there is no monster on one of these addresses";
            } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null
                    || duelModel.getMonstersInField().get(duelModel.turn).get(address2 - 1) == null ||
                    duelModel.getMonstersInField().get(duelModel.turn).get(address3 - 1) == null) {
                return "there is no monster on one of these addresses";
            } else {
                String stateOfCard = mainPhaseView.getStateOfCardForSummon();
                if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                    return "please enter the appropriate state (Defence or Attack)";
                } else {
                    duelModel.deleteMonster(duelModel.turn, address1 - 1);
                    duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                            address1));
                    duelModel.deleteMonster(duelModel.turn, address2 - 1);
                    duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                            address2));
                    duelModel.deleteMonster(duelModel.turn, address3 - 1);
                    duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                            address3));
                    for (int i = 0; i < 5; i++) {
                        duelModel.deleteMonster(1 - duelModel.turn, i);
                        duelModel.deleteSpellAndTrap(1 - duelModel.turn, i);
                    }
                    return normalSummonMonsterOnField(monster, stateOfCard);
                }
            }
        }
    }

    public String normalSummonCardThatCanSummonAnotherCard(String response) {
        // monsterName : Terratiger, the Empowered Warrior
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        if (response.equals("summoned successfully")) {
            String response1 = mainPhaseView.normalSummonCardThatCanSummonAnotherCard();
            if (!response1.equals("NO") && !response1.equals("YES")) {
                return "Please enter correct response";
            } else if (response1.equals("YES")) {
                if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
                    return "no card is selected yet";
                } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Monster")) {
                    return "you can’t summon this card";
                } else {
                    Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);
                    Monster monster = (Monster) card;
                    if (!detailsOfSelectedCard.equals("Hand")) {
                        return "you can’t summon this card";
                    } else if (card.getCardType().equals("Ritual") || monster.isHasSpecialSummon()) {
                        return "you can’t summon this card";
                    } else if (monster.getLevel() > 4) {
                        return "you can’t summon this card";
                    } else {
                        return normalSummonMonsterOnField(monster, "Defence");
                    }
                }
            }
        }
        return "";
    }

    public String activateSpellEffectMainController() {
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Spell")) {
            return "activate effect is only for spell cards.";
        } else {
            String[] detailOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn)
                    .get(duelModel.getSelectedCards().get(duelModel.turn).get(0)).split("/");
            if (detailOfSelectedCard[0].equals("Hand")) {
                return duelController.activateEffect(-1);
            } else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("Field") && detailOfSelectedCard.equals("2"))
                activateEffect(-2);
            else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("Field") && detailOfSelectedCard.equals("1"))
                return "this zone is activated";
            else if (detailOfSelectedCard[0].equals("opponent"))
                return "you cant active your opponent field card";
            else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("Field") && detailOfSelectedCard.equals("1"))
                return "this card is activate";
            else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("O")) {
                return "you have already activated this card";
            } else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("H")) {
                return duelController.activateEffect(Integer.parseInt(detailOfSelectedCard[2]));
            }
            return "you cant active this card";
        }
    }
}
