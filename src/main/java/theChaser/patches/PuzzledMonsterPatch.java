//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import theChaser.powers.PuzzledPower;

public class PuzzledMonsterPatch {
    public PuzzledMonsterPatch() {
    }

    @SpirePatch(
        clz = AbstractMonster.class,
        method = "rollMove"
    )
    public static class RollMove {
        public RollMove() {
        }

        public static SpireReturn<Void> Prefix(AbstractMonster __instance) {
            return __instance.hasPower(PuzzledPower.POWER_ID) ? SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }

    @SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
    )
    public static class GetNextAction {
        public GetNextAction() {
        }

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.monsters.AbstractMonster") && m.getMethodName().equals("takeTurn")) {
                        m.replace("if (!m.hasPower(theChaser.powers.PuzzledPower.POWER_ID)) {$_ = $proceed($$); if (m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK || m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_BUFF || m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF || m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND) {m.addToBot(new theChaser.actions.TargetAttackAction());}} ");
                    }

                }
            };
        }
    }
}
