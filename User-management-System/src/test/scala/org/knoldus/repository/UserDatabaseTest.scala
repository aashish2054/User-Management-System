package org.knoldus.repository

import org.knoldus.model.{User, userType}
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID


class UserDatabaseTest extends AnyFlatSpec {

  val userDB = new UserDatabase

  //if USERID is not given
  val User1: User = User(None,"Sumit", userType.Customer)
  val User1ID: Option[UUID] = userDB.createUser(User1)

  //if USERID is given
  val User2: User = User(Some(UUID.randomUUID()), "Varun", userType.Customer)
  val User2ID: Option[UUID] = userDB.createUser(User2)

  //testing createUser method
  "createUser method" should "create a user if the USERID is not provided" in {
    assert(User1ID.isDefined)
  }

   //and
  it should "not create a user if the USERID is provided" in {
    assert(User2ID.isEmpty)
  }

  //testing readUserbyID method
  "readUserbyID method" should "return the user only if the USERID matches" in {
    val UserEntryfound = userDB.readUserbyID(User1ID)
    assert(UserEntryfound.id == User1ID)
  }

  it should "throw NoSuchElementException if the given USERID does not exist" in {
    assertThrows[NoSuchElementException] {
      userDB.readUserbyID(Some(UUID.randomUUID()))
    }
  }

  // testing updateUser method
  "updateUser method" should "return TRUE if it successfully updates the User details" in {
    val updatedUser= userDB.readUserbyID(User1ID).copy(name = "Varun")
    val UserUpdated = userDB.updateUser(User1ID,updatedUser)
    assert(UserUpdated)
  }

  it should "throw a NoSuchElementException if the given USERID does not exist" in {

    val updatedUser: User = userDB.readUserbyID(User1ID).copy(name = "Varun")

    assertThrows[NoSuchElementException]{

      userDB.updateUser(Some(UUID.randomUUID()), updatedUser)

    }
  }

  //testing deleteUser method
  "deleteUser method" should "return true id it successfully deletes the user" in {
    val isUserDeleted = userDB.deleteUser(User1ID)
    assert(isUserDeleted)

  }

  it should "throw a NoSuchElementException if the given user ID does not exist" in {
    assertThrows[NoSuchElementException] {
      userDB.deleteUser(Some(UUID.randomUUID()))
    }
  }

  it should "return an empty list if the database is empty" in {
    assert(userDB.readAll.isEmpty)
  }

}
