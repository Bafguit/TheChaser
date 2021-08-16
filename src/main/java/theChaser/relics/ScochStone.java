package theChaser.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theChaser.TheChaserMod;
import theChaser.powers.PenetrativePower;
import theChaser.util.TextureLoader;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class ScochStone extends CustomRelic {
    public static final String ID = makeID("Scoch Stone");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ston.png"));
    private static final Texture IMG_OUT = TextureLoader.getTexture(makeRelicPath("outline/ston.png"));

    public ScochStone() {
        super(ID, IMG, IMG_OUT, RelicTier.COMMON, LandingSound.HEAVY);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        String keywordName = TheChaserMod.localKeyword.Targeting;
        this.tips.add(new PowerTip(TheChaserMod.getKeywordInfo(keywordName).NAME, TheChaserMod.getKeywordInfo(keywordName).DESCRIPTION));
        if(Settings.language.name().equals("KOR") || Settings.language.name().equals("JPN")) {
            String keyName = TheChaserMod.localKeyword.Trigger;
            this.tips.add(new PowerTip(TheChaserMod.getKeywordInfo(keyName).NAME, TheChaserMod.getKeywordInfo(keyName).DESCRIPTION));
        }
        this.initializeTips();
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PenetrativePower(AbstractDungeon.player, 1), 1));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }



    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new ScochStone();
    }

}