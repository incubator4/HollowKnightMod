package Card.Blue

import Action.DeepFreezeAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower

class DeepFreeze : AbstractCard(ID, NAME, "1", COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF) {
    init {
        this.baseMagicNumber = 3
        this.magicNumber = baseMagicNumber
    }

    override fun use(player: AbstractPlayer, monster: AbstractMonster) {
        val focus: AbstractPower =  AbstractDungeon.player.getPower("Focus")
        this.addToBot(DeepFreezeAction(monster, DamageInfo(player, focus.amount+2, DamageInfo.DamageType.NORMAL)))
        this.isUsed = true
    }

    override fun atTurnStart() {
        this.isUsed = false
        super.atTurnStart()
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return this.isUsed
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(1)
        }
    }
    //todo 生成球时更新费用

    override fun updateCost(amt: Int) {
        super.updateCost(amt)
    }

    override fun makeCopy(): AbstractCard {
        return DeepFreeze()
    }

    companion object {
        const val ID = "DeepFreeze"
        private val cardStrings: CardStrings
        val NAME: String
        val DESCRIPTION: String
        const val COST = 0

        init {

            cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
            NAME = cardStrings.NAME
            DESCRIPTION = cardStrings.DESCRIPTION
        }
    }

}
