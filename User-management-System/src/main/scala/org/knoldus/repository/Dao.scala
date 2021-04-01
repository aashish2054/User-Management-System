package org.knoldus.repository

import java.util.UUID

trait Dao[T] {

  def createUser(entity: T) : Option[UUID]

  def readUserbyID(id: Option[UUID]): T

  def updateUser(id: Option[UUID], updatedEntity: T) : Boolean

  def deleteUser(id: Option[UUID]) : Boolean

  def readAll: List[T]

}
