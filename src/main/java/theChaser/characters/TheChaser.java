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

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DefaultMod-character-Strings.json in the resources

public class TheChaser extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(TheChaserMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

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

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 74;
    public static final int MAX_HP = 74;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("The Chaser");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

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

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================\

    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        sr.setPremultipliedAlpha(false);
        super.renderPlayerImage(sb);
        sr.setPremultipliedAlpha(true);
    }

    public TheChaser(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theChaserResources/images/char/theChaser/orb/vfx.png", null,
                new SpineAnimation(
                        THE_DEFAULT_SKELETON_ATLAS, THE_DEFAULT_SKELETON_JSON, 1.0F));

        // =============== TEXTURES, ENERGY, LOADOUT =================  

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_DEFAULT_SHOULDER_2, // campfire pose
                THE_DEFAULT_SHOULDER_1, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), -20.0F, -24.0F, 240.0F, 240.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================
        this.loadAnimation(THE_DEFAULT_SKELETON_ATLAS, THE_DEFAULT_SKELETON_JSON, 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
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

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(ShadowInNecklace.ID);
        UnlockTracker.markRelicAsSeen(ShadowInNecklace.ID);

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("BLOCK_BREAK", MathUtils.random(-0.2F, 0.2F)); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT,
                true); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "BLOCK_BREAK";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_CHASER;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return DEFAULT_NAVY;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    public String getLeaderboardCharacterName() {
        return "CHASER";
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new FlawedStealth();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheChaser(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return DEFAULT_NAVY;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return DEFAULT_NAVY;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
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
        panels.add(new CutscenePanel("theChaserResources/images/scene/chaser2.png", "TURN_EFFECT"));
        panels.add(new CutscenePanel("theChaserResources/images/scene/chaser3.png", "CEILING_DUST_3"));
        return panels;
    }

    @Override
    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > currentBlock) {
            AnimationState.TrackEntry e = state.setAnimation(0, "Hit", false);
            state.addAnimation(0,"Idle", true, 0.0f);
            e.setTimeScale(1f);
        }

        super.damage(info);
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
