package services

import daos.SkillDao
import models.Skill
import payloads.common.StatusUpdatePayload
import payloads.{SkillRequest, SkillResponse}
import utils.AppUtility

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SkillService @Inject()(skillDao: SkillDao, appUtility: AppUtility)
                            (implicit executionContext: ExecutionContext){
  def create(request: SkillRequest): Future[SkillResponse] = {
    val code = "SKL-"+appUtility.generateRandomInt(4)
    val skill: Skill = Skill(Option.empty, code, request.name)
    val result = skillDao.create(skill)
    result.map(skill => SkillResponse(skill.id.get, skill.code, skill.name))
  }

  def update(id: Long, request: SkillRequest): Future[SkillResponse] = {
    val skill = for {
      updatedSkillRequest <- skillDao.getById(id)
      result <- skillDao.update(id, Skill(updatedSkillRequest.get.id, updatedSkillRequest.get.code, request.name))
    } yield result
    skill.map(skill => SkillResponse(skill.get.id.get, skill.get.code, skill.get.name))
  }

  def getAll: Future[Seq[SkillResponse]] = {
    skillDao.getAll.map { skills =>
      skills.map(skill => SkillResponse(skill.id.get, skill.code, skill.name))
    }
  }

  def getById(id: Long): Future[Option[SkillResponse]] = {
    val response = skillDao.getById(id).map { skill =>
      skill.map { case(skill) =>
        SkillResponse(skill.id.get, skill.code, skill.name)
      }
    }
    response
  }

  def getByCode(code: String): Future[Option[SkillResponse]] = {
    val response = skillDao.getByCode(code).map { skill =>
      skill.map { case (skill) =>
        SkillResponse(skill.id.get, skill.code, skill.name)
      }
    }
    response
  }

  def getByName(name: String): Future[Option[SkillResponse]] = {
    val response = skillDao.getByName(name).map { skill =>
      skill.map { case (skill) =>
        SkillResponse(skill.id.get, skill.code, skill.name)
      }
    }
    response
  }

  def delete(id: Long): Future[Boolean] = skillDao.delete(id)
}
