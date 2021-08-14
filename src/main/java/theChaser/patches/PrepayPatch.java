package theChaser.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theChaser.actions.ChaserUtil;
import theChaser.actions.TargetAttackAction;
import theChaser.powers.FreeCardPower;

@SpirePatch(
        clz=AbstractCard.class,
        method="freeToPlay"
)
public class PrepayPatch
{

    public static SpireReturn<Boolean> Prefix(AbstractCard __instance)
    {
        if(AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.hasPower(FreeCardPower.POWER_ID) && __instance.type != AbstractCard.CardType.CURSE) {
            return SpireReturn.Return(true);
        } else {
            return SpireReturn.Continue();
        }
    }
}