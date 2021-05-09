package theChaser.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theChaser.powers.HidePower;
import theChaser.util.TextureLoader;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class AcidSolution extends CustomRelic {
    public static final String ID = makeID("Acid Solution");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("acid.png"));
    private static final Texture IMG_OUT = TextureLoader.getTexture(makeRelicPath("outline/acid.png"));

    public AcidSolution() {
        super(ID, IMG, IMG_OUT, RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.owner.isPlayer && info.type == DamageInfo.DamageType.NORMAL && target != null) {
            if(AbstractDungeon.player.hasPower(WeakPower.POWER_ID) || (AbstractDungeon.player.hasPower(FrailPower.POWER_ID) && target.currentBlock > 0)) {
                this.flash();
                if (AbstractDungeon.player.hasPower(WeakPower.POWER_ID)) {
                    this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, 1, false), 1, true));
                }
                if (AbstractDungeon.player.hasPower(FrailPower.POWER_ID) && target.currentBlock > 0) {
                    this.addToBot(new RemoveAllBlockAction(target, AbstractDungeon.player));
                }
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }



    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new AcidSolution();
    }

}