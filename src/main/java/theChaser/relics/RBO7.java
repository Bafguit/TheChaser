package theChaser.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MercuryHourglass;
import theChaser.actions.ChaserUtil;
import theChaser.util.TextureLoader;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class RBO7 extends CustomRelic {
    public static final String ID = makeID("R-B0-7");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("rb7.png"));

    public RBO7() {
        super(ID, IMG, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public void atTurnStart() {
        if(ChaserUtil.getAllDebuffMonsters().size() > 0) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            for (AbstractMonster m : ChaserUtil.getAllDebuffMonsters()) {
                addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, 5, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE, true));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new RBO7();
    }
}