package my.maryvkweb.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class RelationChange (
        @Id @GeneratedValue
        var id: Long? = null,

        var time: LocalDateTime? = null,
        var connectedId: Int? = null,
        var targetId: Int? = null,

        var relationType: RelationType? = null,
        var isAppeared: Boolean? = null
)