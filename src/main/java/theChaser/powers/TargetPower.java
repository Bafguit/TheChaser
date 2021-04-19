package theChaser.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.util.TextureLoader;

public class TargetPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheChaserMod.makeID("Target");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theChaserResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theChaserResources/images/powers/placeholder_power32.png");

    public TargetPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.loadRegion("accuracy");
        // We load those textures here.
        //this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        //this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(this.amount > 1) {
            this.amount--;
        } else if(this.amount == 1) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(!this.owner.isPlayer) {
            ChaserUtil.attack();
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + ChaserUtil.getTargetDamage() + DESCRIPTIONS[1] + DESCRIPTIONS[2] + amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new TargetPower(owner, amount);
    }
}
