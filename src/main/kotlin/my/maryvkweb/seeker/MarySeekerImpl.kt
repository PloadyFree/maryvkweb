package my.maryvkweb.seeker

import my.maryvkweb.LoggerDelegate
import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationType
import my.maryvkweb.service.RelationService
import my.maryvkweb.service.VkService

class MarySeekerImpl(
        private val vk: VkService,
        override val userId: Int,
        private val relationType: RelationType,
        private val relationService: RelationService
) : MarySeeker {

    private val log by LoggerDelegate(MarySeekerImpl::class.java)

    override fun seek() {
        val curUsers = getCurUsers() ?: return
        val wasUsers = getWasUsers()

        val appeared = curUsers.filterNot(wasUsers::contains)
        val disappeared = wasUsers.filterNot(curUsers::contains)

        if (!appeared.isEmpty()) processAppeared(appeared)
        if (!disappeared.isEmpty()) processDisappeared(disappeared)
    }

    private fun getWasUsers() = relationService.findAllFor(userId, relationType)
    private fun getCurUsers() = vk.getConnectedIds(userId, relationType)

    private fun processAppeared(appeared: List<Int>) =
            appeared.map(this::createRelation).forEach {
                relationService.addRelation(it)
                log.info("New relation appeared: " + it)
            }

    private fun processDisappeared(disappeared: List<Int>) =
            disappeared.map(this::createRelation).forEach {
                relationService.removeRelation(it)
                log.info("Relation disappeared: " + it)
            }

    private fun createRelation(targetId: Int) =
            Relation(
                    ownerId = userId,
                    targetId = targetId,
                    relationType = relationType
            )
}