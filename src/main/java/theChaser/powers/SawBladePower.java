package theChaser.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theChaser.TheChaserMod;
import theChaser.util.TextureLoader;

public class SawBladePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheChaserMod.makeID("Saw Blade");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int strCount = 0;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theChaserResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theChaserResources/images/powers/placeholder_power32.png");

    public SawBladePower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK) {
            flashWithoutSound();
            addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount)));
            this.strCount++;
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.strCount)));
        this.strCount = 0;
    }

    @Override
    public AbstractPower makeCopy() {
        return new SawBladePower(owner, amount);
    }

}
