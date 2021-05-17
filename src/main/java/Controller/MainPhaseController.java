package Controller;

import Model.Card;
import Model.DuelModel;
import Model.Monster;
import View.DuelView;
import View.MainPhaseView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;

public class MainPhaseController extends DuelController {
    private static final MainPhaseController mainPhaseController = new MainPhaseController();
    private final DuelModel duelModel = duelController.duelModel;
    private final DuelView duelView = duelController.duelView;

    private MainPhaseController() {

    }

    public static MainPhaseController getInstance() {
        return mainPhaseController;
    }


    public String set() {
        ArrayList<ArrayList<Card>> selectedCards = duelModel.getSelectedCards();
        if (selectedCards.get(duelModel.turn) == null) {
            return "no card is selected yet";
        } else {
            if (!(duelModel.getHandCards().get(duelModel.turn)).contains((selectedCards.get(duelModel.turn)).get(0))) {
                return "you can’t set this card";
            } else if (duelModel.monsterSetOrSummonInThisTurn != null) {
                return "you already summoned/set on this turn";
            } else if ((selectedCards.get(duelModel.turn).get(0)).getCardType().equals("Monster")) {
                if (selectedCards.get(duelModel.turn).get(0).getLevel() > 5)
                    return "this card can not set";
                else
                    return this.setMonster();
            } else
                return setTrapOrSpell();
        }
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
        String response = "";
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        boolean existMonsterOnField = false;
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Monster")) {
            return "you can’t summon this card";
        } else {
            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);

            if (!detailsOfSelectedCard.equals("Hand")) {
                return "you can’t summon this card";
            } else if (card.getCardType().equals("Ritual") || card.isHasSpecialSummon()) {
                return "you can’t summon this card";
            } else if (duelModel.getMonsterSetOrSummonInThisTurn() != null) {
                return "you already summoned/set on this turn";
            } else {
                if (card.getLevel() <= 4) {
                    response = normalSummonMonsterOnField(card, "Attack");
                    return response;
                }
                if (card.getName().equals("Terratiger, the Empowered Warrior")) {
                    return normalSummonCardThatCanSummonAnotherCard(response);
                } else if (card.getLevel() == 5 || card.getLevel() == 6) {
                    if (getNumberOfMonstersInPlayerField() >= 1) {
                        existMonsterOnField = true;
                    }
                    if (!existMonsterOnField) {
                        return "there are not enough cards for tribute";
                    } else {
                        int address;
                        if (!isAi) {
                            address = mainPhaseView.getCardAddressForTribute();
                        } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                            address = mainPhaseView.getCardAddressForTribute();
                        } else {
                            address = getPlaceOfMonsterForAiTribute(1);
                        }
                        if (address > 5) {
                            return "there no monsters one this address";
                        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null) {
                            return "there no monsters one this address";
                        } else {
                            String stateOfCard;
                            if (!isAi) {
                                stateOfCard = mainPhaseView.getStateOfCardForSummon();
                            } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                                stateOfCard = mainPhaseView.getStateOfCardForSummon();
                            } else {
                                if (card.getAttackPower() >= card.getDefensePower()) {
                                    stateOfCard = "Attack";
                                } else {
                                    stateOfCard = "Defence";
                                }
                            }
                            if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                                return "please enter the appropriate state (Defence or Attack)";
                            } else {
                                duelModel.deleteMonster(duelModel.turn, address - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                        address));
                                return normalSummonMonsterOnField(card, stateOfCard);
                            }
                        }
                    }
                } else if (card.getName().equals("Beast King Barbaros")) {
                    return summonMonsterHasTwoMethods(card);
                } else if (card.getLevel() >= 7) {
                    int address;
                    int address1;
                    if (getNumberOfMonstersInPlayerField() < 2) {
                        return "there are not enough cards for tribute";
                    }
                    if (!isAi) {
                        address = mainPhaseView.getCardAddressForTribute();
                        address1 = mainPhaseView.getCardAddressForTribute();
                    } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                        address = mainPhaseView.getCardAddressForTribute();
                        address1 = mainPhaseView.getCardAddressForTribute();
                    } else {
                        address = getPlaceOfMonsterForAiTribute(1);
                        address1 = getPlaceOfMonsterForAiTribute(2);
                    }
                    if (address > 5 || address1 > 5 || address1 == address) {
                        return "there is no monster on one of these addresses";
                    } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null
                            || duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null) {
                        return "there is no monster on one of these addresses";
                    } else {
                        String stateOfCard;
                        if (!isAi) {
                            stateOfCard = mainPhaseView.getStateOfCardForSummon();
                        } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                            stateOfCard = mainPhaseView.getStateOfCardForSummon();
                        } else {
                            if (card.getAttackPower() >= card.getDefensePower()) {
                                stateOfCard = "Attack";
                            } else {
                                stateOfCard = "Defence";
                            }
                        }
                        if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                            return "please enter the appropriate state (Defence or Attack)";
                        } else {
                            duelModel.deleteMonster(duelModel.turn, address - 1);
                            duelModel.deleteMonster(duelModel.turn, address1 - 1);
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address));
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address1));
                            return normalSummonMonsterOnField(card, stateOfCard);
                        }
                    }
                }
            }
        }
        return "";
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

    public String normalSummonMonsterOnField(Card monster, String state) {
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
        if (monster.getName().equals("Command knight")) {
            effectOfCommandKnight();
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
                        if (monster.getName().equals("Command knight")) {
                            effectOfCommandKnight();
                        }
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
        int placeOfMonster;
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        if (!isAi) {
            placeOfMonster = mainPhaseView.scanPlaceOfMonsterForDestroyInManEaterFlipSummon();
        } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
            placeOfMonster = mainPhaseView.scanPlaceOfMonsterForDestroyInManEaterFlipSummon();
        } else {
            Card card = getBestMonsterOfOpponentForAi();
            placeOfMonster = duelModel.getMonstersInField().get(1 - duelModel.turn).indexOf(card) + 1;
        }
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
                int address;
                if (!isAi) {
                    address = mainPhaseView.getCardAddressForTribute();
                } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                    address = mainPhaseView.getCardAddressForTribute();
                } else {
                    address = 1;
                }
                if (address > duelModel.getHandCards().get(duelModel.turn).size()) {
                    return "there is no way you could special summon a monster";
                } else {
                    String stateOfCard;
                    if (!isAi) {
                        stateOfCard = mainPhaseView.getStateOfCardForSummon();
                    } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                        stateOfCard = mainPhaseView.getStateOfCardForSummon();
                    } else {
                        stateOfCard = "Attack";
                    }
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
                int address;
                int address1;
                int address2;
                if (!isAi) {
                    address = mainPhaseView.getCardAddressForTribute();
                    address1 = mainPhaseView.getCardAddressForTribute();
                    address2 = mainPhaseView.getCardAddressForTribute();
                } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                    address = mainPhaseView.getCardAddressForTribute();
                    address1 = mainPhaseView.getCardAddressForTribute();
                    address2 = mainPhaseView.getCardAddressForTribute();
                } else {
                    address = getPlaceOfMonsterForAiTribute(1);
                    address1 = getPlaceOfMonsterForAiTribute(2);
                    address2 = getPlaceOfMonsterForAiTribute(3);
                }
                if (address > 5 || address1 > 5 || address2 > 5 || address == address1 || address == address2
                        || address1 == address2) {
                    return "there is no way you could special summon a monster";
                } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null
                        || duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null ||
                        duelModel.getMonstersInField().get(duelModel.turn).get(address2 - 1) == null) {
                    return "there is no way you could special summon a monster";
                } else {
                    String stateOfCard;
                    if (!isAi) {
                        stateOfCard = mainPhaseView.getStateOfCardForSummon();
                    } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                        stateOfCard = mainPhaseView.getStateOfCardForSummon();
                    } else {
                        stateOfCard = "Attack";
                    }
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

    public String summonMonsterHasTwoMethods(Card monster) {
        // monsterName : Beast King Barbaros
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        String response;
        if (!isAi) {
            response = mainPhaseView.summonMonsterHasTwoMethods();
        } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
            response = mainPhaseView.summonMonsterHasTwoMethods();
        } else {
            if (getNumberOfMonstersInPlayerField() >= 3) {
                response = "YES";
            } else {
                response = "NO";
            }
        }
        if (!response.equals("NO") && !response.equals("YES")) {
            return "Please enter correct response";
        } else if (response.equals("NO")) {
            monster.setAttackPower(1900);
            duelModel.monsterFlipSummonOrNormalSummonForTrapHole = monster;
            return normalSummonMonsterOnField(monster, "Attack");
        } else {
            int address1;
            int address2;
            int address3;
            if (!isAi) {
                address1 = mainPhaseView.getCardAddressForTribute();
                address2 = mainPhaseView.getCardAddressForTribute();
                address3 = mainPhaseView.getCardAddressForTribute();
            } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                address1 = mainPhaseView.getCardAddressForTribute();
                address2 = mainPhaseView.getCardAddressForTribute();
                address3 = mainPhaseView.getCardAddressForTribute();
            } else {
                address1 = getPlaceOfMonsterForAiTribute(1);
                address2 = getPlaceOfMonsterForAiTribute(2);
                address3 = getPlaceOfMonsterForAiTribute(3);
            }
            if (address1 > 5 || address2 > 5 || address3 > 5 || address1 == address2 || address2 == address3
                    || address1 == address3) {
                return "there is no monster on one of these addresses";
            } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null
                    || duelModel.getMonstersInField().get(duelModel.turn).get(address2 - 1) == null ||
                    duelModel.getMonstersInField().get(duelModel.turn).get(address3 - 1) == null) {
                return "there is no monster on one of these addresses";
            } else {
                String stateOfCard;
                if (!isAi) {
                    stateOfCard = mainPhaseView.getStateOfCardForSummon();
                } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                    stateOfCard = mainPhaseView.getStateOfCardForSummon();
                } else {
                    stateOfCard = "Attack";
                }
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
        String response1;
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        if (response.equals("summoned successfully")) {
            if (!isAi) {
                response1 = mainPhaseView.normalSummonCardThatCanSummonAnotherCard();
            } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                response1 = mainPhaseView.normalSummonCardThatCanSummonAnotherCard();
            } else {
                response1 = "YES";
                ArrayList<Monster> monstersInHand = arrangeMonsterInHandWithLevelAndAttackPower(duelModel.getHandCards()
                        .get(duelModel.turn));
                for (Monster monster : monstersInHand) {
                    if (!monster.getCardType().equals("Ritual") && !monster.isHasSpecialSummon()) {
                        if (monster.getLevel() <= 4) {
                            int placeOfMonster = duelModel.getHandCards().get(duelModel.turn).indexOf(monster) + 1;
                            String aiCommand = "select --hand " + placeOfMonster;
                            duelController.selectHand(duelView.getCommandMatcher(aiCommand, "^select --hand (\\d+)$"));
                        }
                    }
                }
            }
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
            } else if (detailOfSelectedCard[0].equals("opponent"))
                return "you cant active your opponent field card";
            else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("O")) {
                return "you have already activated this card";
            } else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("H")) {
                return duelController.activateEffect(Integer.parseInt(detailOfSelectedCard[2]));
            }
            return "you cant active this card";
        }
    }

    public void aiMainPhaseController() {
        aiSetAndNormalSummon();
        aiFlipSummon();
        aiSpecialSummon();
        setAiSpellAndTrap();
        aiActiveEffect();
    }

    public void aiActiveEffect() {
        for (Card card : duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn)) {
            if (card != null) {
                if (card.getCategory().equals("Spell")) {
                    int placeOfSpellCard = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card) + 1;
                    String state = duelModel.getSpellAndTrapCondition(duelModel.turn, placeOfSpellCard).split("/")[0];
                    if (state.equals("H")) {
                        String aiCommand = "select --spell " + placeOfSpellCard;
                        duelController.selectSpellOrTrap(duelView.getCommandMatcher(aiCommand, "^select --spell (\\d+)$"));
                        if (card.getName().equals("Monster Reborn")) {
                            aiActiveMonsterReborn(placeOfSpellCard);
                        } else if (card.getName().equals("Terraforming")) {
                            aiActiveTerraforming(placeOfSpellCard);
                        } else if (card.getName().equals("Pot of Greed")) {
                            aiActivePotOfGreed(placeOfSpellCard);
                        } else if (card.getName().equals("Raigeki")) {
                            aiActiveRaigeki(placeOfSpellCard);
                        } else if (card.getName().equals("Change of Heart")) {
                            aiActiveChangeOfHeart(placeOfSpellCard);
                        } else if (card.getName().equals("Harpie’s Feather Duster")) {
                            aiActiveHarpiesFeatherDuster(placeOfSpellCard);
                        } else if (card.getName().equals("Swords of Revealing Light")) {
                            duelController.effectOfSwordsOfRevealingLight(placeOfSpellCard);
                        } else if (card.getName().equals("Dark Hole")) {
                            aiActiveDarkHole(placeOfSpellCard);
                        } else if (card.getName().equals("Supply Squad")) {
                            duelController.effectOfSupplySquad(placeOfSpellCard);
                        } else if (card.getName().equals("Spell Absorption")) {
                            duelController.effectOfSpellAbsorption(placeOfSpellCard);
                        } else if (card.getName().equals("Messenger of peace")) {
                            duelController.effectOfMessengerOfPeace(placeOfSpellCard);
                        } else if (card.getName().equals("Twin Twisters")) {
                            aiActiveTwinTwisters(placeOfSpellCard);
                        } else if (card.getName().equals("Mystical space typhoon")) {
                            aiActiveMysticalSpaceTyphoon(placeOfSpellCard);
                        } else if (card.getName().equals("Ring of Defense")) {
                            aiActiveRingOfDefense(placeOfSpellCard);
                        } else if (card.getName().equals("Advanced Ritual Art")) {
                            aiActiveAdvancedRitualArt(placeOfSpellCard);
                        } else if (card.getName().equals("MagnumShield")) {
                            duelController.effectOfMagnumShield(placeOfSpellCard);
                        } else if (card.getName().equals("UnitedWeStand")) {
                            duelController.effectOfMagnumShield(placeOfSpellCard);
                        } else if (card.getName().equals("BlackPendant")) {
                            duelController.effectOfBlackPendant(placeOfSpellCard);
                        } else if (card.getName().equals("SwordOfDarkstraction")) {
                            duelController.effectOfSwordOfDarkstraction(placeOfSpellCard);
                        }
                    }
                }
            }
        }
    }

    public void aiActiveMonsterReborn(int placeOfSpell) {
        if (hasMonsterInGraveyard(duelModel.turn) || hasMonsterInGraveyard(1 - duelModel.turn)) {
            if (!isMonsterZoneFull(duelModel.turn)) {
                duelController.effectOfMonsterReborn(placeOfSpell);
            }
        }
    }

    public void aiActiveTerraforming(int placeOfSpell) {
        ArrayList<Card> playersCardsInDeck = duelModel.getPlayersCards().get(duelModel.turn);
        for (Card card : playersCardsInDeck) {
            if (card.getCardType().equals("Field")) {
                duelController.effectOfTerraforming(placeOfSpell);
                break;
            }
        }
    }

    public void aiActivePotOfGreed(int placeOfSpell) {
        if (duelModel.getPlayersCards().get(duelModel.turn).size() >= 2) {
            duelController.effectOfPotOfGreed(placeOfSpell);
        }
    }

    public void aiActiveRaigeki(int placeOfSpell) {
        boolean hasOpponentAnyMonster = false;
        for (Card card : duelModel.getMonstersInField().get(1 - duelModel.turn)) {
            if (card != null) {
                hasOpponentAnyMonster = true;
                break;
            }
        }
        if (hasOpponentAnyMonster) {
            duelController.effectOfRaigeki(placeOfSpell);
        }
    }

    public void aiActiveChangeOfHeart(int placeOfSpell) {
        boolean hasOpponentAnyMonster = false;
        for (Card card : duelModel.getMonstersInField().get(1 - duelModel.turn)) {
            if (card != null) {
                hasOpponentAnyMonster = true;
                break;
            }
        }
        if (hasOpponentAnyMonster && !duelController.isMonsterZoneFull(duelModel.turn)) {
            duelController.effectOfChangeOfHeart(placeOfSpell);
        }
    }

    public void aiActiveHarpiesFeatherDuster(int placeOfSpell) {
        boolean hasOpponentAnySpellOrTrap = false;
        for (Card card : duelModel.getSpellsAndTrapsInFiled().get(1 - duelModel.turn)) {
            if (card != null) {
                hasOpponentAnySpellOrTrap = true;
                break;
            }
        }
        if (hasOpponentAnySpellOrTrap) {
            duelController.effectOfHarpiesFeatherDuster(placeOfSpell);
        }
    }

    public void aiActiveDarkHole(int placeOfSpell) {
        boolean hasOpponentAnyMonster = false;
        boolean hasAiAnyMonster = false;
        for (Card card : duelModel.getMonstersInField().get(1 - duelModel.turn)) {
            if (card != null) {
                hasOpponentAnyMonster = true;
                break;
            }
        }
        for (Card card : duelModel.getMonstersInField().get(duelModel.turn)) {
            if (card != null) {
                hasAiAnyMonster = true;
                break;
            }
        }
        if (hasAiAnyMonster || hasOpponentAnyMonster) {
            duelController.effectOfDarkHole(placeOfSpell);
        }
    }

    public void aiActiveTwinTwisters(int placeOfSpell) {
        if (getNumberOfSpellsAndTrapsInPlayerField(1 - duelModel.turn) >= 1 &&
                !duelController.hasSpellSetInThisTurn() && duelModel.getHandCards().get(duelModel.turn).size() >= 1) {
            duelController.effectOfTwinTwisters(placeOfSpell);
        }
    }

    public void aiActiveRingOfDefense(int placeOfSpell) {
        if (!duelController.hasSpellSetInThisTurn()) {
            duelController.effectOfRingOfDefense(placeOfSpell);
        }
    }

    public void aiActiveMysticalSpaceTyphoon(int placeOfSpell) {
        if (getNumberOfSpellsAndTrapsInPlayerField(1 - duelModel.turn) >= 1 &&
                !duelController.hasSpellSetInThisTurn()) {
            duelController.effectOfMysticalSpaceTyphoon(placeOfSpell);
        }
    }

    public void aiActiveBlackPendant(int placeOfSpell) {


    }

    public void aiActiveUnitedWeStand(int placeOfSpell) {


    }

    public void aiActiveMagnumShield(int placeOfSpell) {


    }

    public boolean isThisPlaceHasSpellOrTrap(int place) {
        return duelModel.getSpellsAndTrapsInFiled().get(place - 1) != null;
    }

    public String getResponseForAiForTwinTwistersAndMysticalSpaceTyphoon() {
        for (int i = 1; i <= 5; i++) {
            if (mainPhaseController.isThisPlaceHasSpellOrTrap(i)) {
                return "opponent " + i;
            }
        }
        return "";
    }

    public void aiActiveAdvancedRitualArt(int placeOfSpell) {
        if (!duelController.isMonsterZoneFull(duelModel.turn)) {
            for (Card card : duelModel.getHandCards().get(duelModel.turn)) {
                if (card.getName().equals("Crab Turtle")) {
                    if (hasNecessaryMonsterForRitualSummonCrabTurtle()) {
                        duelController.effectOfAdvancedRitualArt(placeOfSpell);
                    }
                } else if (card.getName().equals("Skull Guardian")) {
                    if (hasNecessaryMonsterForRitualSummonSkullGuardian()) {
                        duelController.effectOfAdvancedRitualArt(placeOfSpell);
                    }
                }
            }
        }
    }

    public String getResponseForRitualSummon(Card card) {
        String response;
        ArrayList<Card> allMonstersInDeck = new ArrayList<>();
        ArrayList<Card> monsterWithLevel4 = new ArrayList<>();
        ArrayList<Card> monsterWithLevel3 = new ArrayList<>();
        for (Card card1 : duelModel.getPlayersCards().get(duelModel.turn)) {
            if (card1.getCategory().equals("Monster")) {
                allMonstersInDeck.add(card1);
            }
        }

        for (Card card1 : allMonstersInDeck) {
            if (card1.getLevel() == 4) {
                monsterWithLevel4.add(card1);
            }
        }
        for (Card card1 : allMonstersInDeck) {
            if (card1.getLevel() == 3) {
                monsterWithLevel3.add(card1);
            }
        }
        if (card.getName().equals("Crab Turtle")) {
            int addressOfMonster1 = duelModel.getPlayersCards().get(duelModel.turn)
                    .indexOf(monsterWithLevel4.get(0)) + 1;
            int addressOfMonster2 = duelModel.getPlayersCards().get(duelModel.turn)
                    .indexOf(monsterWithLevel4.get(1)) + 1;
            response = addressOfMonster1 + " " + addressOfMonster2;
            return response;
        } else if (card.getName().equals("Skull Guardian")) {
            int addressOfMonster1 = duelModel.getPlayersCards().get(duelModel.turn)
                    .indexOf(monsterWithLevel4.get(0)) + 1;
            int addressOfMonster2 = duelModel.getPlayersCards().get(duelModel.turn)
                    .indexOf(monsterWithLevel3.get(0)) + 1;
            response = addressOfMonster1 + " " + addressOfMonster2;
            return response;
        }
        return "";
    }

    public boolean hasNecessaryMonsterForRitualSummonCrabTurtle() {
        int numberOfNecessaryMonster = 0;
        for (Card card : duelModel.getPlayersCards().get(duelModel.turn)) {
            if (card.getCategory().equals("Monster")) {
                if (card.getLevel() == 4) {
                    numberOfNecessaryMonster++;
                }
            }
        }
        return numberOfNecessaryMonster >= 2;
    }

    public boolean hasNecessaryMonsterForRitualSummonSkullGuardian() {
        int numberOfNecessaryMonsterWithLevel4 = 0;
        int numberOfNecessaryMonsterWithLevel3 = 0;
        for (Card card : duelModel.getPlayersCards().get(duelModel.turn)) {
            if (card.getCategory().equals("Monster")) {
                if (card.getLevel() == 4) {
                    numberOfNecessaryMonsterWithLevel4++;
                }
                if (card.getLevel() == 3) {
                    numberOfNecessaryMonsterWithLevel3++;
                }
            }
        }
        return numberOfNecessaryMonsterWithLevel4 >= 1 && numberOfNecessaryMonsterWithLevel3 >= 1;
    }

    public Integer getNumberOfSpellsAndTrapsInPlayerField(int turn) {
        int numberOfSpellsAndTraps = 0;
        for (Card card : duelModel.getSpellsAndTrapsInFiled().get(turn)) {
            if (card != null) {
                numberOfSpellsAndTraps++;
            }
        }
        return numberOfSpellsAndTraps;
    }

    public Boolean hasMonsterInGraveyard(int turn) {
        boolean hasMonsterInGraveyard = false;
        ArrayList<Card> cardsInGraveyard = duelModel.getGraveyard(turn);
        for (Card card : cardsInGraveyard) {
            if (card.getCategory().equals("Monster")) {
                hasMonsterInGraveyard = true;
                break;
            }
        }
        return hasMonsterInGraveyard;
    }

    public void aiSetAndNormalSummon() {
        ArrayList<Card> aiHandCards = duelModel.getHandCards().get(duelModel.turn);
        ArrayList<Monster> monstersInHand = arrangeMonsterInHandWithLevelAndAttackPower(aiHandCards);
        for (Monster monster : monstersInHand) {
            if (duelModel.getMonsterSetOrSummonInThisTurn() == null) {
                if (!duelController.isMonsterZoneFull(duelModel.turn)) {
                    if (!monster.getCardType().equals("Ritual") && !monster.isHasSpecialSummon()) {
                        if (monster.getLevel() <= 4) {
                            int placeOfMonsterCard = duelModel.getHandCards().get(duelModel.turn).indexOf(monster) + 1;
                            String aiCommand = "select --hand " + placeOfMonsterCard;
                            duelController.selectHand(duelView.getCommandMatcher(aiCommand, "^select --hand (\\d+)$"));
                            if (monster.getAttackPower() >= monster.getDefensePower()) {
                                summon();
                            } else {
                                setMonster();
                            }
                        } else if (monster.getLevel() == 5 || monster.getLevel() == 6) {
                            boolean isMonsterInFieldExists = false;
                            if (getNumberOfMonstersInPlayerField() >= 1) {
                                isMonsterInFieldExists = true;
                            }
                            int placeOfMonsterCard = duelModel.getHandCards().get(duelModel.turn).indexOf(monster) + 1;
                            String aiCommand = "select --hand " + placeOfMonsterCard;
                            duelController.selectHand(duelView.getCommandMatcher(aiCommand, "^select --hand (\\d+)$"));
                            if (isMonsterInFieldExists) {
                                summon();
                            }
                        } else if (monster.getLevel() >= 7) {
                            boolean hasEnoughMonsterInFieldForTribute = false;
                            if (getNumberOfMonstersInPlayerField() >= 2) {
                                hasEnoughMonsterInFieldForTribute = true;
                            }
                            int placeOfMonsterCard = duelModel.getHandCards().get(duelModel.turn).indexOf(monster) + 1;
                            String aiCommand = "select --hand " + placeOfMonsterCard;
                            duelController.selectHand(duelView.getCommandMatcher(aiCommand, "^select --hand (\\d+)$"));
                            if (hasEnoughMonsterInFieldForTribute) {
                                summon();
                            }
                        }
                    }
                }
            }
        }
    }

    public void aiFlipSummon() {
        ArrayList<Card> monstersInField = duelModel.getMonstersInField().get(duelModel.turn);
        int i = 0;
        for (Card card : monstersInField) {
            if (card != null) {
                String[] state = duelModel.getMonsterCondition(duelModel.turn, i + 1).split("/");
                if (state[0].equals("DH")) {
                    if (card != duelModel.monsterSetOrSummonInThisTurn) {
                        int placeOfMonsterCard = duelModel.getMonstersInField().get(duelModel.turn).indexOf(card) + 1;
                        String aiCommand = "select --monster " + placeOfMonsterCard;
                        duelController.selectMonster(duelView.getCommandMatcher(aiCommand, "^select --monster (\\d+)$"));
                        flipSummon();
                    }
                }
            }
            i++;
        }
    }

    public ArrayList<Monster> arrangeMonsterInHandWithLevelAndAttackPower(ArrayList<Card> handCards) {
        ArrayList<Monster> monstersInHand = new ArrayList<>();
        for (Card card : handCards) {
            if (card.getCategory().equals("Monster")) {
                Monster monster = (Monster) card;
                monstersInHand.add(monster);
            }
        }
        Comparator<Monster> compareForAiSummonAndSet = Comparator
                .comparing(Monster::getLevel, Comparator.reverseOrder())
                .thenComparing(Monster::getAttackPower, Comparator.reverseOrder())
                .thenComparing(Monster::getDefensePower, Comparator.reverseOrder());
        monstersInHand.sort(compareForAiSummonAndSet);
        return monstersInHand;
    }

    public ArrayList<Card> arrangeMonsterInFiledWitheLevelAndAttackPowerAscending() {
        ArrayList<Card> monstersInFieldForAiTribute = new ArrayList<>();
        for (Card card : duelModel.getMonstersInField().get(duelModel.turn)) {
            if (card != null) {
                monstersInFieldForAiTribute.add(card);
            }
        }
        Comparator<Card> compareForAiTribute = Comparator
                .comparing(Card::getLevel)
                .thenComparing(Card::getAttackPower)
                .thenComparing(Card::getDefensePower);
        monstersInFieldForAiTribute.sort(compareForAiTribute);
        return monstersInFieldForAiTribute;
    }

    public Integer getPlaceOfMonsterForAiTribute(int numberOfTributeCard) {
        ArrayList<Card> monstersInFieldForAiTribute = arrangeMonsterInFiledWitheLevelAndAttackPowerAscending();
        Card card = null;
        if (numberOfTributeCard == 1) {
            card = monstersInFieldForAiTribute.get(0);
        } else if (numberOfTributeCard == 2) {
            card = monstersInFieldForAiTribute.get(1);
        } else if (numberOfTributeCard == 3) {
            card = monstersInFieldForAiTribute.get(2);
        }
        return duelModel.getMonstersInField().get(duelModel.turn).indexOf(card) + 1;
    }

    public Integer getNumberOfMonstersInPlayerField() {
        ArrayList<Card> monstersInField = duelModel.getMonstersInField().get(duelModel.turn);
        int numberOfMonstersInPlayerFiled = 0;
        for (Card card : monstersInField) {
            if (card != null) {
                numberOfMonstersInPlayerFiled++;
            }
        }
        return numberOfMonstersInPlayerFiled;
    }

    public Card getBestMonsterOfOpponentForAi() {
        ArrayList<Card> monstersInField = new ArrayList<>();
        for (Card card : duelModel.getMonstersInField().get(1 - duelModel.turn)) {
            if (card != null) {
                monstersInField.add(card);
            }
        }
        Comparator<Card> compareForAi = Comparator
                .comparing(Card::getLevel, Comparator.reverseOrder())
                .thenComparing(Card::getAttackPower, Comparator.reverseOrder())
                .thenComparing(Card::getDefensePower, Comparator.reverseOrder());
        monstersInField.sort(compareForAi);
        return monstersInField.get(0);
    }

    public void aiSpecialSummon() {
        for (Card card : duelModel.getHandCards().get(duelModel.turn)) {
            if (card != null) {
                if (card.getName().equals("The Tricky")) {
                    if (duelModel.getHandCards().get(duelModel.turn).size() > 0) {
                        int placeOfMonsterCard = duelModel.getHandCards().get(duelModel.turn).indexOf(card) + 1;
                        String aiCommand = "select --hand " + placeOfMonsterCard;
                        duelController.selectHand(duelView.getCommandMatcher(aiCommand, "^select --hand (\\d+)$"));
                        specialSummon();
                    }
                } else if (card.getName().equals("Gate Guardian")) {
                    if (getNumberOfMonstersInPlayerField() >= 3) {
                        int placeOfMonsterCard = duelModel.getHandCards().get(duelModel.turn).indexOf(card) + 1;
                        String aiCommand = "select --hand " + placeOfMonsterCard;
                        duelController.selectHand(duelView.getCommandMatcher(aiCommand, "^select --hand (\\d+)$"));
                        specialSummon();
                    }
                }
            }
        }
    }

    public void setAiSpellAndTrap() {
        for (Card card : duelModel.getHandCards().get(duelModel.turn)) {
            if (card.getCategory().equals("Spell") || card.getCategory().equals("Trap")) {
                if (card.getCardType().equals("Field")) {
                    int placeOfSpellCard = duelModel.getHandCards().get(duelModel.turn).indexOf(card) + 1;
                    String aiCommand = "select --hand " + placeOfSpellCard;
                    duelController.selectHand(duelView.getCommandMatcher(aiCommand, "^select --hand (\\d+)$"));
                    duelController.activeZoneFromHand();
                } else if (!duelController.isSpellZoneFull(duelModel.turn)) {
                    int placeOfSpellCard = duelModel.getHandCards().get(duelModel.turn).indexOf(card) + 1;
                    String aiCommand = "select --hand " + placeOfSpellCard;
                    duelController.selectHand(duelView.getCommandMatcher(aiCommand, "^select --hand (\\d+)$"));
                    setTrapOrSpell();
                }
            }
        }
    }
}
