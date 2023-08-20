package services

import daos.AddressTypeDao
import models.AddressType
import payloads.common.StatusUpdatePayload
import payloads.{AddressTypeRequest, AddressTypeResponse}
import utils.AppUtility

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AddressTypeService @Inject() (addressTypeDao: AddressTypeDao, appUtility: AppUtility)
                                   (implicit executionContext: ExecutionContext){
  def getList(): Future[Seq[AddressTypeResponse]] = {
    val response = addressTypeDao.getAll.map { addressTypes =>
      addressTypes.map { case (addressType) =>
        AddressTypeResponse(addressType.id.get, addressType.code, addressType.name)
      }
    }
    response
  }
  def getById(id: Long): Future[Option[AddressTypeResponse]] = {
    val response = addressTypeDao.getById(id).map { addressType =>
      addressType.map { case(addType) =>
        AddressTypeResponse(addressType.get.id.get, addressType.get.code, addressType.get.name)
      }
    }
    response
  }

  def getByCode(code: String): Future[Option[AddressTypeResponse]] = {
    val response = addressTypeDao.getByCode(code).map { addressType =>
      addressType.map { case (addType) =>
        AddressTypeResponse(addressType.get.id.get, addressType.get.code, addressType.get.name)
      }
    }
    response
  }
  def getByName(name: String): Future[Option[AddressTypeResponse]] = {
    val response = addressTypeDao.getByName(name).map { addressType =>
      addressType.map { case (addType) =>
        AddressTypeResponse(addressType.get.id.get, addressType.get.code, addressType.get.name)
      }
    }
    response
  }

  def create(request: AddressTypeRequest): Future[AddressTypeResponse] = {
    val code = "ADT-"+appUtility.generateRandomInt(4)
    val addressType = AddressType(Option.empty, code, request.name)
    val result = addressTypeDao.create(addressType)
    result.map(addressType => AddressTypeResponse(addressType.id.get, addressType.code, addressType.name))
  }

  def update(id: Long, request: AddressTypeRequest): Future[AddressTypeResponse] = {
    val res = for {
      updatedRequest <- addressTypeDao.getById(id)
      result <- addressTypeDao.update(id, AddressType(updatedRequest.get.id, updatedRequest.get.code, request.name))
    } yield result
    res.map(addressType => AddressTypeResponse(addressType.get.id.get, addressType.get.code, addressType.get.name))
  }

  def delete(id: Long): Future[Boolean] = addressTypeDao.delete(id)
}
