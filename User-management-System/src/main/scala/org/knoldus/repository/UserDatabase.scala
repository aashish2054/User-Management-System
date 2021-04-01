package org.knoldus.repository

import org.knoldus.model.User

import java.util.UUID
import scala.collection.mutable.ListBuffer


class UserDatabase extends Dao[User] {


  private val inputList:ListBuffer[User]= ListBuffer.empty[User]

  override def createUser(user: User):Option[UUID]={

    val userID = UUID.randomUUID()
    user match {
      case User(None,_,_) => inputList.append(user.copy(id=Some(userID))); inputList.last.id
      case User(Some(_),_,_) => None

    }
  }

  override def readUserbyID(id:Option[UUID]):User = {

    val newList = inputList.filter(user => {user.id == id})
    val retrievdUser = newList.head
    retrievdUser

  }

  override def updateUser(id:Option[UUID], updatedUser: User):Boolean= {

    val indexOfUser = inputList.indexOf(readUserbyID(id))
    inputList.update(indexOfUser, updatedUser)
    true

  }

  override def deleteUser(id:Option[UUID]):Boolean= {

    val indexOfUser = inputList.indexOf(readUserbyID(id))
    inputList.remove(indexOfUser)
    true

  }

  override def readAll:List[User] = inputList.toList
}
