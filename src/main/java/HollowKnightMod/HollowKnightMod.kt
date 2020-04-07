package HollowKnightMod


import Card.Blue.DeepFreeze
import basemod.AutoAdd
import basemod.BaseMod
import basemod.interfaces.EditCardsSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.Gdx
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.localization.*
import org.apache.logging.log4j.LogManager
import java.lang.reflect.Type
import java.util.HashMap
import java.nio.charset.StandardCharsets


@SpireInitializer
class HollowKnightMod : EditCardsSubscriber, PostInitializeSubscriber, EditStringsSubscriber {
    init {
        BaseMod.subscribe(this)
    }


    override fun receiveEditStrings() {
        var jsonArray = HashMap<Type, String>()
        jsonArray.put(MonsterStrings::class.java,"")
        jsonArray.put(PowerStrings::class.java,"")
        jsonArray.put(EventStrings::class.java,"")
        jsonArray.put(PotionStrings::class.java,"")
        jsonArray.put(RelicStrings::class.java,"")

        var monsterJsonPath: String = ""
        var powersJsonPath: String = ""
        var cardJsonPath: String = ""
        when (Settings.language) {
            Settings.GameLanguage.ZHS -> {
                monsterJsonPath = "localization/zhs/monsters.json"
                powersJsonPath = "localization/zhs/powers.json"
                cardJsonPath = "localization/zhs/cards.json"
            }
            else -> {
                monsterJsonPath = "localization/zhs/monsters.json"
                powersJsonPath = "localization/zhs/powers.json"
                cardJsonPath = "localization/zhs/cards.json"
            }
        }
        BaseMod.loadCustomStrings(MonsterStrings::class.java, Gdx.files.internal(monsterJsonPath)
                .readString(StandardCharsets.UTF_8.toString()))
        BaseMod.loadCustomStrings(CardStrings::class.java, Gdx.files.internal(cardJsonPath)
                .readString(StandardCharsets.UTF_8.toString()))
    }


    override fun receiveEditCards() {
        BaseMod.addCard(DeepFreeze())
        AutoAdd("HollowKnightMod").packageFilter(AbstractCard::class.java)
                .setDefaultSeen(true).cards()
    }


    override fun receivePostInitialize() {


        //        BaseMod.addMonster(Hornet.ID,() -> new Hornet());
        //        BaseMod.addBoss(Exordium.ID,Hornet.ID,
        //                "images/ui/map/boss/Hornet.png",
        //                "images/ui/map/bossOutline/Hornet.png");
    }

    companion object {

        val MODNAME = "HollowKnightMod"
        val logger = LogManager.getLogger(HollowKnightMod::class.java.name)

        @JvmStatic
        fun initialize() {
            logger.info("========================= HollowKnightMod INIT =========================")
            val mod = HollowKnightMod()
        }
    }


}