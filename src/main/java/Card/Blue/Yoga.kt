package Card.Blue

import com.megacrit.cardcrawl.actions.common.ExhaustAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster


class Yoga : AbstractCard(DeepFreeze.ID, DeepFreeze.NAME, "1", DeepFreeze.COST, DeepFreeze.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF) {
    init {
        this.magicNumber = 2
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
        }
    }

    override fun use(p0: AbstractPlayer?, p1: AbstractMonster?) {
        this.addToBot(ExhaustAction(this.magicNumber, false, true, true))

        for(index in 1..magicNumber){
            val card = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy()
            card.setCostForTurn(-99)
            addToBot(MakeTempCardInHandAction(card, true))
            if (this.upgraded) {
                card.freeToPlayOnce = true
            }
        }


    }

    override fun makeCopy(): AbstractCard {
        return Yoga()
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