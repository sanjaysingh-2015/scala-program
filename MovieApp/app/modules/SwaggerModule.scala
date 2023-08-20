package modules

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import play.api.inject._
import play.api.{Configuration, Environment}

class SwaggerModule extends SimpleModule((env: Environment, conf: Configuration) =>
  Seq(
    bind[OpenAPI].toInstance {
      val openAPI = new OpenAPI()
      openAPI.info(new Info().title("Your API Title").version("1.0"))
      // Add additional configurations if needed
      openAPI
    }
  )
)
