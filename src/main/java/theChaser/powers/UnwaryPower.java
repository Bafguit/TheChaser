package theChaser.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.patches.interfaces.OnAfterTargetAttackSubscriber;
import theChaser.util.TextureLoader;

import java.util.ArrayList;

public class UnwaryPower extends AbstractPower implements CloneablePowerInterface, OnAfterTargetAttackSubscriber {
    public AbstractCreature source;

    public static final String POWER_ID = TheChaserMod.makeID("Unwary");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theChaserResources/images/powers/unw84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theChaserResources/images/powers/unw32.png");

    public UnwaryPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onAfterTargetAttack(ArrayList<AbstractMonster> mo, int damage) {
        flash();
        for(AbstractMonster m : mo) {
            addToTop(new ApplyPowerAction(m, this.owner, new UnfortifiedPower(m, this.owner, 1), 1, true));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new UnwaryPower(owner);
    }
}
