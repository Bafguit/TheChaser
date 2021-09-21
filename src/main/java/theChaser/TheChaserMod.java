package theChaser;

import basemod.*;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theChaser.actions.ChaserUtil;
import theChaser.cards.common.*;
import theChaser.cards.rare.*;
import theChaser.cards.starter.ChaserDefend;
import theChaser.cards.starter.ChaserStrike;
import theChaser.cards.starter.ReactiveShot;
import theChaser.cards.starter.FlawedStealth;
import theChaser.cards.temp.*;
import theChaser.cards.uncommon.*;
import theChaser.characters.TheChaser;
import theChaser.potions.AcidPotion;
import theChaser.potions.ShadowPotion;
import theChaser.potions.TargetPotion;
import theChaser.powers.*;
import theChaser.relics.*;
import theChaser.util.IDCheckDontTouchPls;
import theChaser.util.LocalKeyword;
import theChaser.util.LocalKeywordInfo;
import theChaser.util.TextureLoader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

@SpireInitializer
public class TheChaserMod implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber,
        EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber{

    public static final Logger logger = LogManager.getLogger(TheChaserMod.class.getName());
    private static String modID;

    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    private static final String MODNAME = "TheChaser";
    private static final String AUTHOR = "FastCat";
    private static final String DESCRIPTION = "A character mod adds The Chaser.";

    public static final Color DEFAULT_NAVY = CardHelper.getColor(77, 77, 255).cpy();

    private static final String ATTACK_DEFAULT_GRAY = "theChaserResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "theChaserResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "theChaserResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "theChaserResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theChaserResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theChaserResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theChaserResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theChaserResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theChaserResources/images/1024/card_default_gray_orb.png";

    private static final String THE_DEFAULT_BUTTON = "theChaserResources/images/charSelect/ChaserButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "theChaserResources/images/charSelect/ChaserBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theChaserResources/images/char/theChaser/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theChaserResources/images/char/theChaser/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theChaserResources/images/char/theChaser/corpse.png";

    public static final String BADGE_IMAGE = "theChaserResources/images/Badge.png";

    public static final String THE_DEFAULT_SKELETON_ATLAS = "theChaserResources/images/char/theChaser/TheChaser/TheChaser.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theChaserResources/images/char/theChaser/TheChaser/TheChaser.json";

    public static LocalKeyword localKeyword = new LocalKeyword();
    public static ArrayList<LocalKeywordInfo> localKeywordInfo = new ArrayList<LocalKeywordInfo>();

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public TheChaserMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        BaseMod.subscribe(new ChaserUtil());

        setModID("theChaser");

        logger.info("Done subscribing");
        
        logger.info("Creating the color " + TheChaser.Enums.COLOR_CHASER.toString());
        
        BaseMod.addColor(TheChaser.Enums.COLOR_CHASER, DEFAULT_NAVY, DEFAULT_NAVY, DEFAULT_NAVY,
                DEFAULT_NAVY, DEFAULT_NAVY, DEFAULT_NAVY, DEFAULT_NAVY,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");

        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("theChaser", "theChaserConfig", theDefaultDefaultSettings);
            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    public static void setModID(String ID) {
        modID = ID;
    }
    
    public static String getModID() { // NO
        return modID;
    }
    
    public static void initialize() {
        TheChaserMod theChasermod = new TheChaserMod();
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheChaser.Enums.THE_CHASER.toString());
        
        BaseMod.addCharacter(new TheChaser(CardCrawlGame.playerName, TheChaser.Enums.THE_CHASER),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheChaser.Enums.THE_CHASER);
        
        receiveEditPotions();
        logger.info("Added " + TheChaser.Enums.THE_CHASER.toString());
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                enablePlaceholder, settingsPanel, (label) -> {}, (button) -> {
            enablePlaceholder = button.enabled;
            try {
                SpireConfig config = new SpireConfig("theChaser", "theChaserConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton);
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        Color maroon = new Color(0.4F, 0, 0, 1).cpy();
        Color dark = new Color(50, 50, 255, 255).cpy();
        BaseMod.addPotion(AcidPotion.class, maroon, maroon, Color.YELLOW, AcidPotion.ID, TheChaser.Enums.THE_CHASER);
        BaseMod.addPotion(ShadowPotion.class, dark, null, null, ShadowPotion.ID, TheChaser.Enums.THE_CHASER);
        BaseMod.addPotion(TargetPotion.class, Color.WHITE, Color.RED, null, TargetPotion.ID, TheChaser.Enums.THE_CHASER);

        BaseMod.addPower(AccelerationPower.class, AccelerationPower.POWER_ID);
        BaseMod.addPower(BigPicturePower.class, BigPicturePower.POWER_ID);
        BaseMod.addPower(BleedingPower.class, BleedingPower.POWER_ID);
        BaseMod.addPower(DipladePower.class, DipladePower.POWER_ID);
        BaseMod.addPower(DualWieldingPower.class, DualWieldingPower.POWER_ID);
        BaseMod.addPower(FakeSorePower.class, FakeSorePower.POWER_ID);
        BaseMod.addPower(ForteIsFrailtyPower.class, ForteIsFrailtyPower.POWER_ID);
        BaseMod.addPower(HidePower.class, OpenSorePower.POWER_ID);
        BaseMod.addPower(PenetrativePower.class, PenetrativePower.POWER_ID);
        BaseMod.addPower(PreemptiveAttackPower.class, PreemptiveAttackPower.POWER_ID);
        BaseMod.addPower(ReplicaDaggerPower.class, ReplicaDaggerPower.POWER_ID);
        BaseMod.addPower(SawBladePower.class, SawBladePower.POWER_ID);
        BaseMod.addPower(ShadowFormPower.class, ShadowFormPower.POWER_ID);
        BaseMod.addPower(SpaceOutPower.class, SpaceOutPower.POWER_ID);
        BaseMod.addPower(TargetPower.class, TargetPower.POWER_ID);
        BaseMod.addPower(UnfortifiedPower.class, UnfortifiedPower.POWER_ID);
        BaseMod.addPower(UnwaryPower.class, UnwaryPower.POWER_ID);

        logger.info("Done loading badge Image and mod options");
    }

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        logger.info("Done editing potions");
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        BaseMod.addRelicToCustomPool(new ShadowInNecklace(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new CloakOfAssassin(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new RBO7(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new ShadowInMask(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new LetterOfRequest(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new BrokenTicker(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new ScochStone(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new PocketKnife(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new BoxCutter(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new AcidSolution(), TheChaser.Enums.COLOR_CHASER);
        BaseMod.addRelicToCustomPool(new RustyHelmet(), TheChaser.Enums.COLOR_CHASER);
        logger.info("Done adding relics!");
    }
    
    @Override
    public void receiveEditCards() {

        logger.info("Adding cards");
        //BASIC
        BaseMod.addCard(new ChaserStrike());
        BaseMod.addCard(new ChaserDefend());
        BaseMod.addCard(new ReactiveShot());
        BaseMod.addCard(new FlawedStealth());
        //COMMON
        BaseMod.addCard(new Alert());
        BaseMod.addCard(new Blitzkrieg());
        BaseMod.addCard(new Brass());
        BaseMod.addCard(new Chase());
        BaseMod.addCard(new ContinuousSlash());
        BaseMod.addCard(new DelusiveStrike());
        BaseMod.addCard(new FatalBlitz());
        BaseMod.addCard(new Geck());
        BaseMod.addCard(new InertialStrike());
        BaseMod.addCard(new LeaveTraces());
        BaseMod.addCard(new Linger());
        BaseMod.addCard(new Makeready());
        BaseMod.addCard(new Passing());
        BaseMod.addCard(new RandomThrow());
        BaseMod.addCard(new Stalling());
        BaseMod.addCard(new Thornmail());
        BaseMod.addCard(new Watch());
        BaseMod.addCard(new Wound());
        //UNCOMMON
        BaseMod.addCard(new Aggravate());
        BaseMod.addCard(new AttackFirst());
        BaseMod.addCard(new BigPicture());
        BaseMod.addCard(new BreakArmour());
        BaseMod.addCard(new Bulwark());
        BaseMod.addCard(new Chance());
        BaseMod.addCard(new Conquest());
        BaseMod.addCard(new Diplade());
        BaseMod.addCard(new Disguise());
        BaseMod.addCard(new FallTechnique());
        BaseMod.addCard(new FeebleDefend());
        BaseMod.addCard(new FeebleStrike());
        BaseMod.addCard(new Flexibility());
        BaseMod.addCard(new GaleStrike());
        BaseMod.addCard(new Hack());
        BaseMod.addCard(new HiddenDagger());
        BaseMod.addCard(new InternalHemorrhage());
        BaseMod.addCard(new OpenSore());
        BaseMod.addCard(new Paralyze());
        BaseMod.addCard(new Penetrate());
        BaseMod.addCard(new Penetrative());
        BaseMod.addCard(new PluckOutDaggers());
        BaseMod.addCard(new Ragewind());
        BaseMod.addCard(new ReadyToCounter());
        BaseMod.addCard(new ReplicaDagger());
        BaseMod.addCard(new ScarStab());
        BaseMod.addCard(new SecretCard());
        BaseMod.addCard(new Sequence());
        BaseMod.addCard(new SpaceOut());
        BaseMod.addCard(new Testing());
        BaseMod.addCard(new Throes());
        BaseMod.addCard(new UnstableBlock());
        BaseMod.addCard(new WeaknessStrike());
        BaseMod.addCard(new WinTheExchange());
        BaseMod.addCard(new Wither());
        //RARE
        BaseMod.addCard(new AbsoluteAdvantage());
        BaseMod.addCard(new Acceleration());
        BaseMod.addCard(new ApproachRun());
        BaseMod.addCard(new DualWielding());
        BaseMod.addCard(new ForteIsFrailty());
        BaseMod.addCard(new Fraudulence());
        BaseMod.addCard(new Hide());
        BaseMod.addCard(new IncisiveBlade());
        BaseMod.addCard(new Phrenitis());
        BaseMod.addCard(new PreemptiveAttack());
        BaseMod.addCard(new SawBlade());
        BaseMod.addCard(new ShadowForm());
        BaseMod.addCard(new Stealage());
        BaseMod.addCard(new Prepay());
        BaseMod.addCard(new Unwary());
        BaseMod.addCard(new Vitality());
        BaseMod.addCard(new Walkout());
        BaseMod.addCard(new Wile());
        //TEMP
        BaseMod.addCard(new BloodyWind());
        BaseMod.addCard(new Camouflage());
        BaseMod.addCard(new FollowupSlash());
        BaseMod.addCard(new KnifeThrow());
        BaseMod.addCard(new SequenceFlow());
        BaseMod.addCard(new ThrowingKnife());
/*
        logger.info("Auto Adding cards (Disabled)");
        new AutoAdd("TheChaser").packageFilter(ChaserCard.class).setDefaultSeen(true).cards();
*/
        logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/" + getLanguage() + "/theChaserCards.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/" + getLanguage() + "/theChaserPowers.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/" + getLanguage() + "/theChaserRelics.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/" + getLanguage() + "/theChaserPotions.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/" + getLanguage() + "/theChaserCharacter.json");

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/" + getLanguage() + "/theChaserKeywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            int var7 = keywords.length;
            for(int var8 = 0; var8 < var7; ++var8) {
                Keyword keyword = keywords[var8];
                LocalKeywordInfo locKeyInfo = new LocalKeywordInfo();
                locKeyInfo.NAME = keyword.PROPER_NAME;
                locKeyInfo.DESCRIPTION = keyword.DESCRIPTION;
                localKeywordInfo.add(locKeyInfo);

            }
        }

        FileHandle fileHandle = Gdx.files.internal(getModID() + "Resources/localization/" + getLanguage() + "/localizedKeyword.json");
        String f = fileHandle.readString(String.valueOf(StandardCharsets.UTF_8));
        JsonObject locKey = (new JsonParser()).parse(f).getAsJsonObject();
        localKeyword.Targeting = locKey.get("Targeting").getAsString();
        localKeyword.Trigger = locKey.get("Trigger").getAsString();
        localKeyword.Blindsided = locKey.get("Blindsided").getAsString();
        localKeyword.Bleeding = locKey.get("Bleeding").getAsString();
        localKeyword.Hide = locKey.get("Hide").getAsString();
        localKeyword.Swift = locKey.get("Swift").getAsString();
        localKeyword.Quickstep = locKey.get("Quickstep").getAsString();
        localKeyword.Proxy = locKey.get("Proxy").getAsString();

        logger.info("Done edittting strings");
    }

    public static LocalKeywordInfo getKeywordInfo(String name) {
        for(LocalKeywordInfo info : localKeywordInfo) {
            if(info.NAME.equals(name)) {
                return info;
            }
        }

        LocalKeywordInfo temp = new LocalKeywordInfo();
        temp.NAME = "[MISSING NAME]";
        temp.DESCRIPTION = "[MISSING DESCRIPTION]";
        return temp;
    }

    public static String getLanguage() {
        switch (Settings.language.name()) {
            case "KOR":
                return "kor";
            case "JPN":
                return "jpn";
            case "SPA":
                return "spa";
            case "ZHS":
                return "zhs";
            case "ZHT":
                return "zht";
            default:
                return "eng";
        }
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/" + getLanguage() + "/theChaserKeywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            int var7 = keywords.length;
            for(int var8 = 0; var8 < var7; ++var8) {
                Keyword keyword = keywords[var8];
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);

            }
        }
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
