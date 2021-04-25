package theChaser.actions;

import basemod.interfaces.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import theChaser.powers.DualWieldingPower;
import theChaser.powers.PenetrativePower;
import theChaser.powers.PuzzledPower;
import theChaser.powers.TargetPower;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;

public class ChaserUtil implements OnCardUseSubscriber, PostEnergyRechargeSubscriber, PostBattleSubscriber, PostCreateStartingRelicsSubscriber {

    private static int TarAtkCnt = 0;
    private static int TarAtkCntPerTurn = 0;
    private static int TarAtkDmg = 2;
    private static int BaseTarAtkDmg = 2;
    private static int CardCnt = 0;
    private static int CardCntPerTurn = 0;
    private static AbstractCard lastCard = null;
    private static Color BLEEDING_COLOR = new Color(100, 0, 0);

    @SpireEnum
    public static AbstractPower.PowerType NONE;

    private static void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    private static boolean isFirstCard = true;

    public static int getTargetCount() {
        return getTarget().size();
    }

    public static boolean canAttack() {
        return getTargetCount() > 0;
    }

    public static int getTargetDamage() {
        return TarAtkDmg + (AbstractDungeon.player.hasPower(PenetrativePower.POWER_ID) ? AbstractDungeon.player.getPower(PenetrativePower.POWER_ID).amount : 0);
    }

    public static int getTargetAttackCount() {
        return TarAtkCnt;
    }

    public static int getTargetAttackCountPerTurn() {
        return TarAtkCntPerTurn;
    }

    public static boolean isTargetAttackPerTurn() {
        return TarAtkCntPerTurn > 0;
    }

    public static void addTargetDamagePerBattle(int amount) {
        TarAtkDmg += amount;
    }

    public static void addTargetDamage(int amount) {
        BaseTarAtkDmg += amount;
        TarAtkDmg += amount;
    }

    public static void resetVars() {
        TarAtkDmg = BaseTarAtkDmg;
        TarAtkCnt = 0;
        TarAtkCntPerTurn = 0;
        CardCnt = 0;
        CardCntPerTurn = 0;
        lastCard = null;
        isFirstCard = true;
    }

    public static void applyPuzzled() {
        for(int i = 0; i < getCurrRoom().monsters.monsters.size(); i++) {
            if(!getCurrRoom().monsters.monsters.get(i).isDeadOrEscaped()) {
                getCurrRoom().monsters.monsters.get(i).powers.add(new PuzzledPower(getCurrRoom().monsters.monsters.get(i)));
                Collections.sort(getCurrRoom().monsters.monsters.get(i).powers);
                getCurrRoom().monsters.monsters.get(i).getPower(PuzzledPower.POWER_ID).onInitialApplication();
            }
        }
    }

    public static void removePuzzled() {
        for(int i = 0; i < getCurrRoom().monsters.monsters.size(); i++) {
            if(!getCurrRoom().monsters.monsters.get(i).isDeadOrEscaped() && getCurrRoom().monsters.monsters.get(i).hasPower(PuzzledPower.POWER_ID)) {
                addToBot(new RemoveSpecificPowerAction(getCurrRoom().monsters.monsters.get(i), AbstractDungeon.player, PuzzledPower.POWER_ID));
            }
        }
    }

    public static void reloadDeck() {
        for(int i = 0; i < AbstractDungeon.player.masterDeck.size(); i++) {
            AbstractDungeon.player.masterDeck.group.get(i).initializeDescription();
        }
    }

    public static boolean isAccel() {
        return getCardCountPerTurn() >= 4;
    }

    public static void addCardCountPerTurn(int amount) {
        CardCntPerTurn += amount;
    }

    public static int getCardCountPerTurn() {
        return CardCntPerTurn;
    }

    public static int getCardCount() {
        return CardCnt;
    }

    public static int getAllDebuffAmount() {
        int temp = 0;
        for(AbstractMonster m : getCurrRoom().monsters.monsters) {
            if(!m.isDeadOrEscaped()) {
                for (AbstractPower p : m.powers) {
                    if (p.type == AbstractPower.PowerType.DEBUFF) {
                        temp++;
                    }
                }
            }
        }

        return temp;
    }

    public static ArrayList<AbstractMonster> getAllDebuffMonsters() {
        ArrayList<AbstractMonster> mo = new ArrayList<>();

        for(AbstractMonster m : getCurrRoom().monsters.monsters) {
            for(AbstractPower p : m.powers) {
                if(p.type == AbstractPower.PowerType.DEBUFF) {
                    mo.add(m);
                    break;
                }
            }
        }

        return mo;
    }

    public static boolean hasDebuff() {
        for(AbstractMonster m : getCurrRoom().monsters.monsters) {
            if(!m.isDeadOrEscaped()) {
                for (AbstractPower p : m.powers) {
                    if (p.type == AbstractPower.PowerType.DEBUFF) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static ArrayList<AbstractMonster> getTarget() {
        ArrayList<AbstractMonster> temp = new ArrayList<>();
        for(AbstractMonster m : getCurrRoom().monsters.monsters) {
            if(!m.isDeadOrEscaped() && m.hasPower(TargetPower.POWER_ID)) {
                temp.add(m);
            }
        }

        return temp;
    }

    public static ArrayList<AbstractPower> getPlayerDebuff() {
        ArrayList<AbstractPower> ps = new ArrayList<>();

        for(AbstractPower p : AbstractDungeon.player.powers) {
            if(p.type == AbstractPower.PowerType.DEBUFF) {
                ps.add(p);
            }
        }

        return ps;
    }

    public static int getPlayerDebuffAmount() {
        return getPlayerDebuff().size();
    }

    public static void setFirstCardIsDone() {
        isFirstCard = false;
    }

    public static boolean isFirstCardPerTurn() {
        return isFirstCard;
    }

    public static void attack() {
        if(canAttack()) {
            if(AbstractDungeon.player.hasPower(DualWieldingPower.POWER_ID)) {
                AbstractDungeon.player.getPower(DualWieldingPower.POWER_ID).flash();
                addToBot(new VFXAction(new DaggerTargetEffect(AbstractDungeon.getMonsters().shouldFlipVfx(), getTargetCount()), 0.0F));
                addToBot(new DamageAllTargetAction(getTargetDamage(), getTarget()));
                TarAtkCnt++;
                TarAtkCntPerTurn++;
            }
            addToBot(new VFXAction(new DaggerTargetEffect(AbstractDungeon.getMonsters().shouldFlipVfx(), getTargetCount()), 0.0F));
            addToBot(new DamageAllTargetAction(getTargetDamage(), getTarget()));
            TarAtkCnt++;
            TarAtkCntPerTurn++;
        }
    }

    public static void cardIsUsed(AbstractCard card) {
        lastCard = card;
        CardCntPerTurn++;
        CardCnt++;
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
    }

    @Override
    public void receivePostEnergyRecharge() {
        TarAtkCntPerTurn = 0;
        CardCntPerTurn = 0;
        isFirstCard = true;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        resetVars();
        reloadDeck();
    }

    @Override
    public void receivePostCreateStartingRelics(AbstractPlayer.PlayerClass playerClass, ArrayList<String> arrayList) {
        resetVars();
    }
}
