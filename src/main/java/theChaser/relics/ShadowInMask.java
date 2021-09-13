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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theChaser.TheChaserMod;
import theChaser.actions.TargetAttackAction;
import theChaser.powers.TargetPower;
import theChaser.util.TextureLoader;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class ShadowInMask extends CustomRelic {
    public static final String ID = makeID("Shadow In Mask");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("mask.png"));
    private static final Texture IMG_OUT = TextureLoader.getTexture(makeRelicPath("outline/mask.png"));

    public ShadowInMask() {
        super(ID, IMG, IMG_OUT, RelicTier.BOSS, LandingSound.FLAT);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        String keywordName = TheChaserMod.localKeyword.Targeting;
        this.tips.add(new PowerTip(TheChaserMod.getKeywordInfo(keywordName).NAME, TheChaserMod.getKeywordInfo(keywordName).DESCRIPTION));
        if(TheChaserMod.getLanguage().equals("kor") || TheChaserMod.getLanguage().equals("jpn") || TheChaserMod.getLanguage().equals("zhs") || TheChaserMod.getLanguage().equals("zht")) {
            String keyName = TheChaserMod.localKeyword.Trigger;
            this.tips.add(new PowerTip(TheChaserMod.getKeywordInfo(keyName).NAME, TheChaserMod.getKeywordInfo(keyName).DESCRIPTION));
        }
        this.initializeTips();
    }

    @Override
    public void obtain() {
        // Replace the starter relic, or just give the relic if starter isn't found
        if (AbstractDungeon.player.hasRelic(ShadowInNecklace.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(ShadowInNecklace.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void atTurnStart() {
        this.flash();
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new TargetPower(m, 1), 1, true));
        }
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new TargetAttackAction());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(ShadowInNecklace.ID);
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new ShadowInMask();
    }

}
