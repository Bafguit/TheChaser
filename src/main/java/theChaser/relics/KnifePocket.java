package theChaser.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theChaser.actions.ChaserUtil;
import theChaser.patches.interfaces.OnAfterTargetAttackSubscriber;
import theChaser.util.TextureLoader;

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeID;
import static theChaser.TheChaserMod.makeRelicPath;

public class KnifePocket extends CustomRelic implements OnAfterTargetAttackSubscriber {
    public static final String ID = makeID("Knife Pocket");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));

    public KnifePocket() {
        super(ID, IMG, RelicTier.BOSS, LandingSound.FLAT);
        this.counter = 2;
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
        return new KnifePocket();
    }

    public void onVictory() {
        this.isDone = false;
        this.counter = -1;
    }

    @Override
    public void onAfterTargetAttack(ArrayList<AbstractMonster> mo, int damage) {
        if(!this.isDone) {
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