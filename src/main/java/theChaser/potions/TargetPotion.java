package theChaser.potions;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PoisonPotion;
import theChaser.TheChaserMod;
import theChaser.actions.TargetAttackAction;
import theChaser.powers.BleedingPower;

public class TargetPotion extends AbstractPotion {

    public static final String ID = TheChaserMod.makeID("Target Potion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    private static final String NAME = potionStrings.NAME;
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public TargetPotion() {
        super(NAME, ID, PotionRarity.UNCOMMON,
                PotionSize.SPIKY, PotionColor.NONE);
        this.isThrown = true;
        this.labOutlineColor = CardHelper.getColor(100, 100, 255).cpy();
    }

    @Override
    public void use(AbstractCreature target) {
        for(int i = 0; i < this.potency; i++) {
            this.addToBot(new TargetAttackAction());
        }
    }

    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        String keywordName = TheChaserMod.localKeyword.Targeting;
        this.tips.add(new PowerTip(TheChaserMod.getKeywordInfo(keywordName).NAME, TheChaserMod.getKeywordInfo(keywordName).DESCRIPTION));
        if(TheChaserMod.getLanguage().equals("kor") || TheChaserMod.getLanguage().equals("jpn") || TheChaserMod.getLanguage().equals("zhs")) {
            String keyName = TheChaserMod.localKeyword.Trigger;
            this.tips.add(new PowerTip(TheChaserMod.getKeywordInfo(keyName).NAME, TheChaserMod.getKeywordInfo(keyName).DESCRIPTION));
        }
    }

    @Override
    public int getPotency(int i) {
        return 5;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new TargetPotion();
    }
}
