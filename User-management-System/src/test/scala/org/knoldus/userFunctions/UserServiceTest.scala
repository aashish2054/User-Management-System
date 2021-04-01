package org.knoldus.userFunctions

import org.knoldus.model.{User, userType}
import org.knoldus.repository.UserDatabase
import org.knoldus.userFunction.UserService
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID


class UserServiceTest extends AnyFlatSpec{

  val mockedUserDB: UserDatabase = mock[UserDatabase]
  val userService = new UserService(mockedUserDB)


   //if UserID is not given
  val User1: User = User(None,"Sumit", userType.Customer)
  val User1ID: Option[UUID] = userService.createUser(User1)


   //if USERID is given
  val User2: User = User(Some(UUID.randomUUID()), "Ashish", userType.Customer)
  val User2ID: Option[UUID] = mockedUserDB.createUser(User2)

  "createUser method" should "create a user if the USERID is not provided" in {
    when(mockedUserDB.createUser(User1)) thenReturn Some(UUID.randomUUID())
    assert(User1ID.isDefined)

  }

  "getUserByID method" should "return the user only if the USERID matches" in {
    when(mockedUserDB.createUser(User1)) thenReturn Some(UUID.randomUUID())
    when(mockedUserDB.readUserbyID(User1ID)) thenReturn User1.copy(id = User1ID)
    val retrievedUser = userService.getUserByID(User1ID)
    assert(retrievedUser.id == User1ID)

  }

  it should "throw NoSuchElementException if the given USERID does not exist" in {
    val randomId = Some(UUID.randomUUID())
    when(mockedUserDB.readUserbyID(randomId)) thenThrow new NoSuchElementException()
    assertThrows[NoSuchElementException] {
      userService.getUserByID (randomId)
    }
  }

  "updateUser method" should "return true if it successfully updates the User details" in {
    when(mockedUserDB.createUser(User1)) thenReturn Some(UUID.randomUUID())
    val User1ID = userService.createUser(User1)
    when(mockedUserDB.readUserbyID(User1ID)) thenReturn User1.copy(id = User1ID)
    val updatedUser = userService.getUserByID (User1ID).copy(name = "Ashu")
    when(mockedUserDB.updateUser (User1ID, updatedUser)) thenReturn true
    val UserUpdated: Boolean = userService.updateUser(User1ID, updatedUser)
    assert(UserUpdated)

  }

  it should "throw a NoSuchElementException if the USERID does not exist" in {
    val randomId = Some (UUID.randomUUID())
    val updatedUser = User (Some(UUID.randomUUID()), "Ashu", userType.Admin)
    when(mockedUserDB.updateUser (randomId,updatedUser)) thenThrow new NoSuchElementException()
    assertThrows[NoSuchElementException] {
      userService.updateUser (randomId, updatedUser)

    }
  }

  "deleteUser method" should "return true if it successfully deletes the user" in {
    when(mockedUserDB.createUser(User1)) thenReturn Some(UUID.randomUUID())
    when(mockedUserDB.deleteUser(User1ID)) thenReturn true
    val isUserDeleted = userService.deleteUser(User1ID)
    assert(isUserDeleted)
  }

  it should "throw a NoSuchElementException if the given USERID does not exist" in {
    val randomId = Some(UUID.randomUUID())
    when(mockedUserDB.deleteUser(randomId)) thenThrow new NoSuchElementException()
    assertThrows[NoSuchElementException] {
      userService.deleteUser(randomId)
    }
  }

  "getAllUsers method" should "return all users in the database in a list " in {
    when(mockedUserDB.readAll) thenReturn
      List(User(Some(UUID.randomUUID()),"sumit", userType.Admin),User(Some(UUID.randomUUID()),"Ashish Chaudhary", userType.Customer))
    assert(userService.getAllUsers.nonEmpty)
  }

  it should "return an empty list if the database is empty" in{
    when(mockedUserDB.readAll) thenReturn
      List.empty
    assert(userService.getAllUsers.isEmpty)

  }

}
