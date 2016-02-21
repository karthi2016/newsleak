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

package model.queryable

import model.Document

trait DocumentQueryable {

  /**
   * Returns a list of all [[model.Document]] available in the collection.
   * @return List[Document]
   */
  def getDocuments(): List[Document]

  /**
   * Returns all [[model.Document]] Ids available in the collection.
   * @return  List[Long]
   */
  def getDocumentIds(): List[Long]

  /**
   * Returns [[model.Document]]s that contain the given [[model.Entity]] id.
   * @param id the [[model.Entity]] id
   * @return List[model.Document]
   */
  def getDocumentsByEntityId(id: Long): List[Document]

  /**
   * Returns [[model.Document]]s that contain the given pair of [[model.Entity]] ids.
   * @param e1 first [[model.Entity]]
   * @param e2 second [[model.Entity]]
   * @return List[Document]
   */
  def getDocumentsByEntityIds(e1: Long, e2: Long): List[Document]

  /**
   * Returns a list of tuple, where each tuple (key, type) represents
   * the available metadata key associated with its type in the <b>whole</b> collection.
   * Example: List(("Subject", "TEXT", "Position":"GEO", ...)
   *
   * @return List[(String, String)]
   */
  def getMetadataKeysAndTypes(): List[(String, String)]

  /**
   * Get list of metadata key and their types that is available for the given document.
   * Example: List((SignedBy,Text), (Header,Text))
   *
   * @param id: the id of the [[model.Document]].
   * @return List of metadata keys with their types present for the document.
   */
  def getMetadataType(id: Long): List[(String, String)]

  /**
   * Returns metadata values for a given metadata key for a given document.
   * Example: [`IR`, `MARR`, `MASS`] for the key `Tags`.
   *
   * @param docId  document id.
   * @param key name of the metadata key to search for e.g `Tags`.
   * @return List of metatadata values for the given key and document id.
   */
  def getMetadataValueByDocumentId(docId: Long, key: String): List[String]

  /**
   * Returns a list of tuple (key, value) for a given document id.
   * Example: List(SignedBy,HECK), (Tags,IR), (Tags,MASS), ...)
   *
   * @param docId document id to get list of metadata key, value tuple for.
   * @return  List[(String, String)].
   */
  def getMetadataKeyValueByDocumentId(docId: Long): List[(String, String)]
}