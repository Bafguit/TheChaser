package theChaser.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theChaser.powers.TargetPower;
import theChaser.util.TextureLoader;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class ShadowInNecklace extends CustomRelic {
    public static final String ID = makeID("Shadow In Necklace");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("neck.png"));

    public ShadowInNecklace() {
        super(ID, IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
        AbstractPower p = new TargetPower(m, 2);
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, p));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }



    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new ShadowInNecklace();
    }

}