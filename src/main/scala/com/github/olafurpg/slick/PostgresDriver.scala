package com.github.olafurpg.slick


import com.github.tminglei.slickpg._

trait PostgresDriver extends ExPostgresDriver with PgArraySupport {
  // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"
  def pgjson = "jsonb"

  override val api = MyAPI

  object MyAPI extends API with ArrayImplicits {

    implicit val strListTypeMapper =
      new SimpleArrayJdbcType[String]("text").to(_.toList)
  }
}

object PostgresDriver extends PostgresDriver
