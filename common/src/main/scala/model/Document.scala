/*
 * Copyright (C) 2015  Language Technology Group
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package model

import model.queryable.DocumentQueryable
import org.joda.time.LocalDateTime
import utils.{DBSettings, DBRegistry}

// scalastyle:off
import scalikejdbc._
// scalastyle:on

/**
 * Document representation.
 *
 * @param id       unique document identifier.
 * @param content  document body that contains raw text.
 * @param created  creation date and time of the document.
 */
case class Document(id: Long, content: String, created: LocalDateTime)

/**
 * Companion object for [[model.Document]] instances.
 */
object Document extends DocumentQueryable with DBSettings {

  def connector: NamedDB = DBRegistry.db()

  val rowParser = (rs: WrappedResultSet) =>
    Document(
      rs.int("id"),
      rs.string("content"),
      rs.jodaLocalDateTime("created")
    )

  override def getDocuments(): List[Document] = connector.readOnly { implicit session =>
    sql" SELECT * FROM document".map(rowParser).list.apply()
  }

  override def getDocumentIds(): List[Long] = connector.readOnly { implicit session =>
    sql" SELECT id FROM document".map(_.long("id")).list.apply()
  }

  override def getDocumentsByEntityId(id: Long): List[Document] = connector.readOnly { implicit session =>
    sql"""SELECT * FROM document d
          INNER JOIN documententity de ON d.id = de.docid
          WHERE de.entityid = ${id}
    """.map(rowParser).list.apply()
  }

  override def getDocumentsByEntityIds(e1: Long, e2: Long): List[Document] = connector.readOnly { implicit session =>
    sql"""SELECT * FROM document d
          INNER JOIN documententity de1 ON d.id = de1.docid
          INNER JOIN documententity de2 ON d.id = de2.docid
          WHERE de1.entityid = ${e1} AND de2.entityid =${e2}
    """.map(rowParser).list.apply()
  }

  override def getMetadataKeysAndTypes(): List[(String, String)] = connector.readOnly { implicit session =>
    sql"SELECT DISTINCT key, type FROM metadata".map(rs => (rs.string("key"), rs.string("type"))).list.apply()
  }

  override def getMetadataType(id: Long): List[(String, String)] = connector.readOnly { implicit session =>
    sql"SELECT DISTINCT key, type FROM metadata".map(rs => (rs.string("key"), rs.string("type"))).list.apply()
  }

  override def getMetadataValueByDocumentId(docId: Long, key: String): List[String] = connector.readOnly { implicit session =>
    sql"SELECT DISTINCT value FROM metadata WHERE docid = ${docId} AND key = ${key}".map(rs => rs.string("value")).list.apply()
  }

  override def getMetadataKeyValueByDocumentId(docId: Long): List[(String, String)] = connector.readOnly { implicit session =>
    sql"SELECT DISTINCT key, value FROM metadata WHERE docid = ${docId}".map(rs => (rs.string("key"), rs.string("value"))).list.apply()
  }
}