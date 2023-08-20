package utils

import javax.inject.Inject
import com.typesafe.config.Config
import org.mongodb.scala._

class MongoClientProvider @Inject()(config: Config) {
  private val uri: String = config.getString("mongodb.uri")
  private val settings: MongoClientSettings = MongoClientSettings
    .builder()
    .applyConnectionString(new ConnectionString(uri))
    .build()

  lazy val client: MongoClient = MongoClient(settings)
  lazy val database: MongoDatabase = client.getDatabase(uri.split("/").last)
}
