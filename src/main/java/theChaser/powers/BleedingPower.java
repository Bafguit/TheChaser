package theChaser.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.BurningBlood;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theChaser.TheChaserMod;
import theChaser.actions.BleedAction;
import theChaser.actions.InstantBleedingDamageAction;
import theChaser.util.TextureLoader;

import java.util.ArrayList;

public class BleedingPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheChaserMod.makeID("Bleeding");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theChaserResources/images/powers/Bleeding84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theChaserResources/images/powers/Bleeding32.png");

    public BleedingPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        this.updateDescription();
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL) {
            this.addToTop(new InstantBleedingDamageAction(this.owner, 1));
        }
        return damageAmount;
    }

    public void getBleedingDamage(int amount) {
        for(int i = 0; i < amount; i++) {
            this.flashWithoutSound();
            this.owner.damage(new DamageInfo(null, this.amount, DamageInfo.DamageType.HP_LOSS));
            if (AbstractDungeon.player.hasPower(OpenSorePower.POWER_ID)) {
                AbstractDungeon.player.getPower(OpenSorePower.POWER_ID).flashWithoutSound();
            } else if (this.amount > 1) {
                this.amount--;
            } else if (this.amount == 1) {
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
            if(amount > 1) {
                this.addToTop(new WaitAction(0.3F));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BleedingPower(owner, source, amount);
    }

}
