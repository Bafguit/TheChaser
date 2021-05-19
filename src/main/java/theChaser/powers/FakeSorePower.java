package theChaser.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.util.TextureLoader;

public class FakeSorePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheChaserMod.makeID("Fake Sore");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theChaserResources/images/powers/fake84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theChaserResources/images/powers/fake32.png");

    public FakeSorePower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * (1.0F + (0.05F * this.amount));
        } else {
            return damage;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + 5 * this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FakeSorePower(owner, amount);
    }
}
