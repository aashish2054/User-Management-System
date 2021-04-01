package org.knoldus.userFunction

import org.knoldus.model.User
import org.knoldus.repository.Dao

import java.util.UUID


class UserService(userDB: Dao[User]) {


  def createUser(user: User): Option[UUID]= {

    val userId = userDB.createUser(user)

    if(userId.isDefined){
      userId
    }
    else {
      None
    }
  }


  def getUserByID(id:Option[UUID]):User = {
    userDB.readUserbyID(id)
  }


  def updateUser(id:Option[UUID], updatedUser: User): Boolean = {

    userDB.updateUser(id,updatedUser)
  }


  def deleteUser(id:Option[UUID]): Boolean = {
    userDB.deleteUser(id)
  }


  def getAllUsers:List[User] = {

    val retrievedUsersList = userDB.readAll
    if(retrievedUsersList.nonEmpty){
      retrievedUsersList
    }
    else {
      List.empty
    }
  }
}
