package theChaser.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theChaser.powers.PenetrativePower;
import theChaser.powers.TargetPower;
import theChaser.util.TextureLoader;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class LetterOfRequest extends CustomRelic {
    public static final String ID = makeID("Letter Of Request");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("let.png"));

    public LetterOfRequest() {
        super(ID, IMG, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new TargetPower(m, 3), 3, true));
        }
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }



    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new LetterOfRequest();
    }

}