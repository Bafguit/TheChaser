package theChaser.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.actions.TargetAttackAction;

@SpirePatch(
        clz=AbstractMonster.class,
        method="rollMove"
)
public class AfterMonsterAttackPatch
{

    public static void Prefix(AbstractMonster __instance)
    {
        System.out.println("AfterMonsterAttackPatcher");
        if(__instance.intent == AbstractMonster.Intent.ATTACK || __instance.intent == AbstractMonster.Intent.ATTACK_BUFF || __instance.intent == AbstractMonster.Intent.ATTACK_DEBUFF || __instance.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
            AbstractDungeon.actionManager.addToBottom(new TargetAttackAction());
        }
    }
}