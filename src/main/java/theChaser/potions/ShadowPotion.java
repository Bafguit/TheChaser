package theChaser.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theChaser.TheChaserMod;
import theChaser.powers.BleedingPower;
import theChaser.powers.HidePower;

public class ShadowPotion extends AbstractPotion {

    public static final String ID = TheChaserMod.makeID("Shadow Potion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    private static final String NAME = potionStrings.NAME;
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ShadowPotion() {
        super(NAME, ID, PotionRarity.RARE,
                PotionSize.GHOST, PotionColor.NONE);
        this.isThrown = true;
        this.labOutlineColor = CardHelper.getColor(100, 100, 255).cpy();
    }

    @Override
    public void use(AbstractCreature target) {
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HidePower(AbstractDungeon.player, this.potency), this.potency));
    }

    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ShadowPotion();
    }
}
