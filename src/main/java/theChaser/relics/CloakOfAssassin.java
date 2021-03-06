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
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theChaser.TheChaserMod;
import theChaser.powers.HidePower;
import theChaser.powers.TargetPower;
import theChaser.util.TextureLoader;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class CloakOfAssassin extends CustomRelic {
    public static final String ID = makeID("Cloak Of Assassin");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("cloak.png"));
    private static final Texture IMG_OUT = TextureLoader.getTexture(makeRelicPath("outline/cloak.png"));

    public CloakOfAssassin() {
        super(ID, IMG, IMG_OUT, RelicTier.RARE, LandingSound.MAGICAL);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        String keywordName = TheChaserMod.localKeyword.Hide;
        this.tips.add(new PowerTip(TheChaserMod.getKeywordInfo(keywordName).NAME, TheChaserMod.getKeywordInfo(keywordName).DESCRIPTION));
        this.initializeTips();
    }

    @Override
    public void atBattleStartPreDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HidePower(AbstractDungeon.player, 2)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }



    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new CloakOfAssassin();
    }

}