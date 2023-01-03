package repositories

import com.typesafe.config.ConfigFactory
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.jdbczio.Quill
import io.getquill.{PostgresZioJdbcContext, SnakeCase}
import zio.*

import javax.sql.DataSource
import scala.jdk.CollectionConverters.MapHasAsJava

object QuillContext extends PostgresZioJdbcContext(SnakeCase) {

  val dataSourceLayer: ZLayer[Any, Nothing, DataSource] =
    ZLayer {
      for {
        _ <- ZIO.succeed(1)
      } yield Quill.DataSource.fromPrefix("testPostgresDB").orDie
    }.flatten
}