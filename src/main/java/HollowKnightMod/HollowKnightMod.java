package HollowKnightMod;


import Boss.Hornet;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




@SpireInitializer
public class HollowKnightMod implements EditCardsSubscriber,PostInitializeSubscriber, EditStringsSubscriber {

    public static final String MODNAME = "HollowKnightMod";
    public static final Logger logger = LogManager.getLogger(HollowKnightMod.class.getName());

    public HollowKnightMod(){
        BaseMod.subscribe(this);
    }
    public static void initialize() {

        logger.info("========================= HollowKnightMod INIT =========================");
        @SuppressWarnings("unused")
        HollowKnightMod mod = new HollowKnightMod();
    }


    @Override
    public void receiveEditStrings() {
        /*if (Settings.language == Settings.GameLanguage.ZHS) {
            BaseMod.loadCustomStrings(MonsterStrings.class, Gdx.files.internal(
                    "localization/zhs/monsters.json").readString(String.valueOf(StandardCharsets.UTF_8)));
            BaseMod.loadCustomStrings(PowerStrings.class,Gdx.files.internal(
                    "localization/zhs/powers.json").readString(String.valueOf(StandardCharsets.UTF_8)));
        } else {
            BaseMod.loadCustomStrings(MonsterStrings.class, Gdx.files.internal(
                    "localization/zhs/monsters.json").readString(String.valueOf(StandardCharsets.UTF_8)));
            BaseMod.loadCustomStrings(PowerStrings.class,Gdx.files.internal(
                    "localization/zhs/powers.json").readString(String.valueOf(StandardCharsets.UTF_8)));
        }*/

    }


    public void receiveEditCards() {

    }


    public void receivePostInitialize() {


        BaseMod.addMonster(Hornet.ID,() -> new Hornet());
        BaseMod.addBoss(Exordium.ID,Hornet.ID,
                "images/ui/map/boss/Hornet.png",
                "images/ui/map/bossOutline/Hornet.png");
    }


}