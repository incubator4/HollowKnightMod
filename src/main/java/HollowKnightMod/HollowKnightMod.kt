package HollowKnightMod


import Boss.Hornet
import Card.Blue.DeepFreeze
import Card.Blue.MultipleProcess
import Card.Blue.Yoga
import basemod.AutoAdd
import basemod.BaseMod
import basemod.interfaces.EditCardsSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.Exordium
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.localization.MonsterStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


@SpireInitializer
class HollowKnightMod : EditCardsSubscriber, PostInitializeSubscriber, EditStringsSubscriber {
    init {
        BaseMod.subscribe(this)
    }


    override fun receiveEditStrings() {
        if (Settings.language === Settings.GameLanguage.ZHS) {
            BaseMod.loadCustomStringsFile(MonsterStrings::class.java, "localization/zhs/monsters.json")
            BaseMod.loadCustomStringsFile(PowerStrings::class.java, "localization/zhs/powers.json")
            BaseMod.loadCustomStringsFile(CardStrings::class.java, "localization/zhs/cards.json")
        } else {
            BaseMod.loadCustomStringsFile(MonsterStrings::class.java, "localization/zhs/monsters.json")
            BaseMod.loadCustomStringsFile(PowerStrings::class.java, "localization/zhs/powers.json")
        }

    }


    override fun receiveEditCards() {
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