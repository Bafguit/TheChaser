package theChaser.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theChaser.actions.ChaserUtil;
import theChaser.actions.TargetAttackAction;

@SpirePatch(
        clz= AbstractPower.class,
        method="update"
)
public class AutoUpdateDescriptionPowerPatch
{

    public static void Postfix(AbstractPower __instance)
    {
        __instance.updateDescription();
    }
}