package int09h.facebook.com.sbtexhere3

import int09h.facebook.com.sbtexhere3.api.Sberbank
import int09h.facebook.com.sbtexhere3.models.SbEntity
import int09h.facebook.com.sbtexhere3.models.Point
import int09h.facebook.com.sbtexhere3.models.Triangle

/**
 * Created by int09h on 28/02/2018.
 */
class FilialFactory(private val api: Sberbank) {
    fun getFilials(currentPos: Point, radius: Double): List<SbEntity>? {
        val geo = Triangle.createFromPointAndRadius(currentPos, radius)
        val atms = api.fetchFilials(geo)?.distinctBy { it.address }
        return atms?.map {
            val atmPos = Point(it.coordinates?.latitude!!, it.coordinates.longitude!!)
            SbEntity(it.address!!, it.type!!, it.name!!, currentPos.distanceTo(atmPos) )
        }?.sortedBy { it.distance }
    }
}