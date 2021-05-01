package theChaser.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theChaser.actions.ChaserUtil;
import theChaser.actions.TargetAttackAction;
import theChaser.patches.interfaces.OnAfterTargetAttackSubscriber;
import theChaser.util.TextureLoader;

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class BoxCutter extends CustomRelic implements OnAfterTargetAttackSubscriber {
    public static final String ID = makeID("Box Cutter");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("cutter.png"));

    public BoxCutter() {
        super(ID, IMG, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public void atTurnStart() {
        this.beginPulse();
        this.pulse = true;
    }

    @Override
    public void onVictory() {
        this.stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new BoxCutter();
    }

    @Override
    public void onAfterTargetAttack(ArrayList<AbstractMonster> mo, int damage) {
        if(this.pulse) {
            this.flash();
            this.stopPulse();
            this.addToBot(new TargetAttackAction());
            this.addToBot(new TargetAttackAction());
        }
    }
}