package theChaser.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theChaser.TheChaserMod;
import theChaser.cards.starter.ReactiveShot;
import theChaser.cards.starter.ChaserDefend;
import theChaser.cards.starter.ChaserStrike;
import theChaser.cards.starter.FlawedStealth;
import theChaser.relics.ShadowInNecklace;
import theChaser.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;

import static theChaser.TheChaserMod.*;
import static theChaser.characters.TheChaser.Enums.COLOR_CHASER;

public class TheChaser extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(TheChaserMod.class.getName());

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_CHASER;
        @SpireEnum
        public static AbstractCard.CardColor COLOR_CHASER;
    }

    public static class LibraryEnum {
        @SpireEnum
        public static CardLibrary.LibraryType COLOR_CHASER;
    }

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 74;
    public static final int MAX_HP = 74;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    private static final String ID = makeID("The Chaser");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public static final String[] orbTextures = {
            "theChaserResources/images/char/theChaser/orb/layer1.png",
            "theChaserResources/images/char/theChaser/orb/layer2.png",
            "theChaserResources/images/char/theChaser/orb/layer3.png",
            "theChaserResources/images/char/theChaser/orb/layer4.png",
            "theChaserResources/images/char/theChaser/orb/layer5.png",
            "theChaserResources/images/char/theChaser/orb/layer6.png",
            "theChaserResources/images/char/theChaser/orb/layer1d.png",
            "theChaserResources/images/char/theChaser/orb/layer2d.png",
            "theChaserResources/images/char/theChaser/orb/layer3d.png",
            "theChaserResources/images/char/theChaser/orb/layer4d.png",
            "theChaserResources/images/char/theChaser/orb/layer5d.png",};

    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        sr.setPremultipliedAlpha(false);
        super.renderPlayerImage(sb);
        sr.setPremultipliedAlpha(true);
    }

    public TheChaser(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theChaserResources/images/char/theChaser/orb/vfx.png", null,
                new SpineAnimation(THE_DEFAULT_SKELETON_ATLAS, THE_DEFAULT_SKELETON_JSON, 1.0F));

        initializeClass(null, THE_DEFAULT_SHOULDER_2, THE_DEFAULT_SHOULDER_1, THE_DEFAULT_CORPSE, getLoadout(),
                -20.0F, -24.0F, 240.0F, 240.0F, new EnergyManager(ENERGY_PER_TURN));

        this.loadAnimation(THE_DEFAULT_SKELETON_ATLAS, THE_DEFAULT_SKELETON_JSON, 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(ChaserStrike.ID);
        retVal.add(ChaserStrike.ID);
        retVal.add(ChaserStrike.ID);
        retVal.add(ChaserStrike.ID);
        retVal.add(ReactiveShot.ID);
        retVal.add(ChaserDefend.ID);
        retVal.add(ChaserDefend.ID);
        retVal.add(ChaserDefend.ID);
        retVal.add(ChaserDefend.ID);
        retVal.add(FlawedStealth.ID);

        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(ShadowInNecklace.ID);
        UnlockTracker.markRelicAsSeen(ShadowInNecklace.ID);

        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("BLOCK_BREAK", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "BLOCK_BREAK";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_CHASER;
    }

    @Override
    public Color getCardTrailColor() {
        return DEFAULT_NAVY;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new FlawedStealth();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheChaser(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return DEFAULT_NAVY;
    }

    @Override
    public Color getSlashAttackColor() {
        return DEFAULT_NAVY;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY};
    }

    public Texture getCutsceneBg() {
        return TextureLoader.getTexture("theChaserResources/images/scene/navyBg.jpg");
    }

    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("theChaserResources/images/scene/chaser1.png", "ATTACK_DAGGER_5"));
        panels.add(new CutscenePanel("theChaserResources/images/scene/chaser2.png"));
        panels.add(new CutscenePanel("theChaserResources/images/scene/chaser3.png"));
        return panels;
    }

    @Override
    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            AnimationState.TrackEntry e = state.setAnimation(0, "Hit", false);
            state.addAnimation(0,"Idle", true, 0.0f);
            e.setTimeScale(1f);
        }

        super.damage(info);
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

}
