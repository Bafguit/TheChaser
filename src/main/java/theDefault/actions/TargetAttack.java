package theDefault.actions;

import basemod.interfaces.*;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theDefault.powers.TargetPower;

import java.util.ArrayList;

public class TargetAttack implements OnCardUseSubscriber, PostEnergyRechargeSubscriber, PostBattleSubscriber, PostCreateStartingRelicsSubscriber {

    public static int TarAtkCnt = 0;
    public static int TarAtkCntPerTurn = 0;
    public static int TarAtkDmg = 2;
    public static int BaseTarAtkDmg = 2;

    public static int getTargetCount() {
        return getTarget().size();
    }

    public static boolean canAttack() {
        return getTargetCount() > 0;
    }

    public static int getTargetDamage() {
        return TarAtkDmg;
    }

    public static int getTargetAttackCount() {
        return TarAtkCnt;
    }

    public static int getTargetAttackCountPerTurn() {
        return TarAtkCntPerTurn;
    }

    public static void resetVars() {
        TarAtkDmg = BaseTarAtkDmg;
        TarAtkCnt = 0;
        TarAtkCntPerTurn = 0;
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

    public static void attack() {
        AbstractDungeon.actionManager.addToBottom(new DamageAllTargetAction(getTargetDamage(), getTarget()));
        TarAtkCnt++;
        TarAtkCntPerTurn++;
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(abstractCard.type == AbstractCard.CardType.ATTACK && canAttack()) {
            attack();
        }
    }

    @Override
    public void receivePostEnergyRecharge() {
        TarAtkCntPerTurn = 0;
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
