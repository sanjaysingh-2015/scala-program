package utils

import akka.stream.scaladsl.FileIO
import daos.{DocumentTypeDao, KycDocumentTypeDao, KycDocumentTypeMapDao}
import models.{DocumentType, KycDocumentType, KycDocumentTypeMap}

import javax.inject.Inject
import com.opencsv.CSVReaderBuilder

import java.io.{File, FileReader}
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}
import scala.jdk.CollectionConverters.IteratorHasAsScala

class CsvProcessingService @Inject()(kycDocumentTypeDao: KycDocumentTypeDao,
                                     documentTypeDao: DocumentTypeDao,
                                     kycDocumentTypeMapDao: KycDocumentTypeMapDao,
                                     appUtility: AppUtility)
                                    (implicit executionContext: ExecutionContext) {

  def processCSV(file: File): Future[Unit] = {
    val source = FileIO.fromPath(file.toPath)
    val reader = new FileReader(file)
    val csvReader = new CSVReaderBuilder(reader)
      .withSkipLines(1) // Skip the header line
      .build()

    val records: java.util.List[Array[String]] = csvReader.readAll()

    val insertions = records.iterator().asScala.map { record =>
      val kycDocumentName = record(0)
      val documentName = record(1)
      val description = record(2)

      for {
        kycDocType <- kycDocumentTypeDao.getByCode(kycDocumentName).flatMap {
          case Some(kycDocType) => Future.successful(kycDocType)
          case None =>
            val newKYCDocumentType = KycDocumentType(Option.empty, kycDocumentName, kycDocumentName, "OPEN")
            kycDocumentTypeDao.create(newKYCDocumentType)
        }
        docType <- documentTypeDao.getByCode(documentName).flatMap {
          case Some(docType) => Future.successful(docType)
          case None =>
            val newDocumentType = DocumentType(Option.empty, documentName, documentName, description, "OPEN")
            documentTypeDao.create(newDocumentType)
        }
        _ <- kycDocumentTypeMapDao.create(KycDocumentTypeMap(Option.empty, kycDocType.id.get, docType.id.get, "OPEN"))
      } yield ()
    }

    // Combine all the insertions using Future.sequence
    val allInsertions = Future.sequence(insertions)

    // Return the combined Future
    allInsertions.map(_ => ())
  }
}
