package theChaser.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theChaser.patches.interfaces.OnAfterTargetAttackSubscriber;
import theChaser.util.TextureLoader;

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class PocketKnife extends CustomRelic implements OnAfterTargetAttackSubscriber {
    public static final String ID = makeID("Pocket Knife");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("knf.png"));
    private static final Texture IMG_OUT = TextureLoader.getTexture(makeRelicPath("outline/knf.png"));

    public PocketKnife() {
        super(ID, IMG, IMG_OUT, RelicTier.BOSS, LandingSound.FLAT);
        this.isDone = false;
    }

    public void atTurnStart() {
        this.isDone = false;
        this.counter = 2;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new PocketKnife();
    }

    public void onVictory() {
        this.isDone = false;
        this.counter = -1;
    }

    @Override
    public void onAfterTargetAttack(ArrayList<AbstractMonster> mo, int damage) {
        if(this.counter > 0) {
            this.counter--;
            if (this.counter == 0) {
                this.counter = -1;
                this.flash();
                this.addToBot(new GainEnergyAction(1));
                this.isDone = true;
            }
        }
    }
}