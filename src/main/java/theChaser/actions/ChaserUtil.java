package theChaser.actions;

import basemod.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theChaser.powers.PenetrativePower;
import theChaser.powers.TargetPower;

import java.util.ArrayList;

public class ChaserUtil implements OnCardUseSubscriber, PostEnergyRechargeSubscriber, PostBattleSubscriber, PostCreateStartingRelicsSubscriber {

    private static int TarAtkCnt = 0;
    private static int TarAtkCntPerTurn = 0;
    private static int TarAtkDmg = 2;
    private static int BaseTarAtkDmg = 2;

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
        isFirstCard = true;
    }

    public static ArrayList<AbstractMonster> getTarget() {
        ArrayList<AbstractMonster> temp = new ArrayList<>();
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(m.hasPower(TargetPower.POWER_ID)) {
                temp.add(m);
            }
        }

        return temp;
    }

    public static boolean isFirstCardPerTurn() {
        return isFirstCard;
    }

    public static void attack() {
        if(canAttack()) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllTargetAction(getTargetDamage(), getTarget()));
            TarAtkCnt++;
            TarAtkCntPerTurn++;
        }
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(isFirstCardPerTurn()) {
            isFirstCard = false;
        }
        if(abstractCard.type == AbstractCard.CardType.ATTACK) {
            attack();
        }
    }

    @Override
    public void receivePostEnergyRecharge() {
        TarAtkCntPerTurn = 0;
        isFirstCard = true;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        resetVars();
    }

    @Override
    public void receivePostCreateStartingRelics(AbstractPlayer.PlayerClass playerClass, ArrayList<String> arrayList) {
        resetVars();
    }
}
