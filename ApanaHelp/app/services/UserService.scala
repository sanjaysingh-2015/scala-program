package services

import daos.UserDao
import models.User
import payloads.common.StatusUpdatePayload
import payloads.{UserRequest, UserResponse}

import java.time.LocalDate
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userDao: UserDao)
                           (implicit executionContext: ExecutionContext) {
  def create(request: UserRequest): Future[UserResponse] = {
    val user = User(Option.empty, request.userName, request.firstName, request.middleName, request.lastName,
      request.email, request.mobileNo, request.alternateMobileNo, LocalDate.now(), "ADMIN", "OPEN")
    userDao.create(user).map { createdUser =>
      UserResponse(createdUser.id.get, createdUser.userName, createdUser.firstName, createdUser.middleName, createdUser.lastName,
        createdUser.email, createdUser.mobileNo, createdUser.alternateMobileNo, createdUser.createdOn, createdUser.createdBy, createdUser.status)
    }
  }

  def update(id: Long, request: UserRequest): Future[UserResponse] = {
    val response = for {
      updatedUser <- userDao.getById(id)
      result <- userDao.update(id, User(updatedUser.get.id, updatedUser.get.userName, request.firstName,
        request.middleName, request.lastName, request.email, request.mobileNo,
        request.alternateMobileNo, updatedUser.get.createdOn, updatedUser.get.createdBy, updatedUser.get.status))
    } yield result
    response.map(user => UserResponse(user.get.id.get, user.get.userName, user.get.firstName, user.get.middleName, user.get.lastName,
      user.get.email, user.get.mobileNo, user.get.alternateMobileNo, user.get.createdOn, user.get.createdBy, user.get.status))
  }

  def statusUpdate(id: Long, request: StatusUpdatePayload): Future[UserResponse] = {
    val response = for {
      updatedUser <- userDao.getById(id)
      result <- userDao.update(id, User(updatedUser.get.id, updatedUser.get.userName, updatedUser.get.firstName,
        updatedUser.get.middleName, updatedUser.get.lastName, updatedUser.get.email, updatedUser.get.mobileNo,
        updatedUser.get.alternateMobileNo, updatedUser.get.createdOn, updatedUser.get.createdBy, request.status))
    } yield result
    response.map(user => UserResponse(user.get.id.get, user.get.userName, user.get.firstName, user.get.middleName, user.get.lastName,
      user.get.email, user.get.mobileNo, user.get.alternateMobileNo, user.get.createdOn, user.get.createdBy, user.get.status))
  }

  def delete(id: Long): Future[Boolean] = userDao.delete(id)

  def getById(id: Long): Future[Option[UserResponse]] = {
    val response = userDao.getById(id).map { users =>
      users.map { case (user) =>
        UserResponse(user.id.get, user.userName, user.firstName, user.middleName, user.lastName,
          user.email, user.mobileNo, user.alternateMobileNo, user.createdOn, user.createdBy, user.status)
      }
    }
    response
  }

  def getByUserName(userName: String): Future[Option[UserResponse]] = {
    val response = userDao.getByUserName(userName).map { users =>
      users.map { case (user) =>
        UserResponse(user.id.get, user.userName, user.firstName, user.middleName, user.lastName,
          user.email, user.mobileNo, user.alternateMobileNo, user.createdOn, user.createdBy, user.status)
      }
    }
    response
  }

  def getByName(name: String): Future[Seq[UserResponse]] = {
    val response = userDao.getByName(name).map { users =>
      users.map { case (user) =>
        UserResponse(user.id.get, user.userName, user.firstName, user.middleName, user.lastName,
          user.email, user.mobileNo, user.alternateMobileNo, user.createdOn, user.createdBy, user.status)
      }
    }
    response
  }

  def getByEmail(email: String): Future[Seq[UserResponse]] = {
    val response = userDao.getByEmail(email).map { users =>
      users.map { case (user) =>
        UserResponse(user.id.get, user.userName, user.firstName, user.middleName, user.lastName,
          user.email, user.mobileNo, user.alternateMobileNo, user.createdOn, user.createdBy, user.status)
      }
    }
    response
  }

  def getByPhoneNo(phoneNo: String): Future[Seq[UserResponse]] = {
    val response = userDao.getByPhoneNo(phoneNo).map { users =>
      users.map { case (user) =>
        UserResponse(user.id.get, user.userName, user.firstName, user.middleName, user.lastName,
          user.email, user.mobileNo, user.alternateMobileNo, user.createdOn, user.createdBy, user.status)
      }
    }
    response
  }

  def getAll(): Future[Seq[UserResponse]] = {
    val response = userDao.getAll.map { users =>
      users.map { case (user) =>
        UserResponse(user.id.get, user.userName, user.firstName, user.middleName, user.lastName,
          user.email, user.mobileNo, user.alternateMobileNo, user.createdOn, user.createdBy, user.status)
      }
    }
    response
  }
}
