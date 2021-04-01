package org.knoldus.bootstrap

import org.knoldus.repository.{Dao, UserDatabase}
import org.knoldus.model.{userType, User}
import org.knoldus.userFunction.UserService

object main {


  def main(args: Array[String]): Unit = {

    val userDB:Dao[User] = new UserDatabase()
    val userService = new UserService(userDB)


    val Administrator = User(None, "Ashish Chaudhary", userType.Admin)
    val user1 = User(None, "Sumit Malik", userType.Customer)
    val user2 = User(None, "Varun Attri", userType.Customer)
    val user3 = User(None, "Sachin", userType.Customer)


    val ADMINID = userService.createUser(Administrator)
    val user1ID = userService.createUser(user1)
    val user2ID = userService.createUser(user2)
    val user3ID = userService.createUser(user3)

    println("List of All Users => " + userService.getAllUsers)


    val updated_user2= userService.getUserByID(user1ID).copy(name = "Ashu")

    userService.updateUser(user1ID, updated_user2)

    println("Updated User => " + userService.getUserByID(user1ID))

    userService.deleteUser(user1ID)

    userService.getUserByID(user1ID)
  }

}