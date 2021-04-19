package theChaser.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theChaser.TheChaserMod;
import theChaser.util.TextureLoader;

import java.util.ArrayList;

public class ForteIsFrailtyPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheChaserMod.makeID("Forte Is Frailty");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theChaserResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theChaserResources/images/powers/placeholder_power32.png");

    public ForteIsFrailtyPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();

        changeBuff();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.type == PowerType.BUFF && target != null && !target.isPlayer) {
            changeBuff();
        }
    }

    private void changeBuff() {
        for(int j = 0; j < AbstractDungeon.getCurrRoom().monsters.monsters.size(); j++) {
            for(int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.get(j).powers.size(); i++) {
                if(AbstractDungeon.getCurrRoom().monsters.monsters.get(j).powers.get(i).type == PowerType.BUFF) {
                    AbstractDungeon.getCurrRoom().monsters.monsters.get(j).powers.get(i).type = PowerType.DEBUFF;
                }
            }
        }

    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ForteIsFrailtyPower(owner);
    }
}
