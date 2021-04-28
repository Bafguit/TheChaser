package theChaser.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import theChaser.powers.PuzzledPower;

public class ForteIsFrailtyPatch {
    public ForteIsFrailtyPatch() {
    }
/*
    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class, AbstractGameAction.AttackEffect.class}
    )

    public static class BuffToDebuffAction {
        public BuffToDebuffAction() {
        }

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.actions.common.ApplyPowerAction") && m.getMethodName().equals("setValues")) {
                        m.replace("$_ = $proceed($$); if(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.hasPower(theChaser.powers.ForteIsFrailtyPower.POWER_ID) && powerToApply.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF) { powerToApply.type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF; }");
                    }
                }
            };
        }
    }*/
}