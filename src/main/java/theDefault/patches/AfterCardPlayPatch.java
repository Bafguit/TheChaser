package theDefault.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theDefault.actions.TargetAttackAction;

@SpirePatch(
        clz=AbstractPlayer.class,
        method="useCard"
)
public class AfterCardPlayPatch
{

    public static void Postfix(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse)
    {
        System.out.println("AfterCardPlayPatcher");
        if(c.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(new TargetAttackAction(new DamageInfo(__instance, 2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }
}