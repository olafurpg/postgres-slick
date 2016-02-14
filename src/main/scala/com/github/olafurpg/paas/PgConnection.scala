package com.github.olafurpg.paas

import java.net.URI

import scala.util.Try

case class PgConnection(dbUrl: String, user: String, password: String) {
  val jdbcUrl = "jdbc:" + dbUrl
}

object PgConnection {

  /**
    * Get postgres connection parameters from PaaS environment variables.
    *
    * First tries PG{USER,PASSWORD}, then parses user/password from DATABASE_URL
    * and falls back to "" if nothing works (for example, in IntelliJ).
    */
  def create(): PgConnection = {
    val dbUrl = sys.env.getOrElse("DATABASE_URL", "")
    val pgUserPass = for {
      user <- sys.env.get("PGUSER")
      password <- sys.env.get("PGPASSWORD")
    } yield UserPass(user, password)
    val userPass = pgUserPass
      .orElse(parseDbUrl(dbUrl)) // Parse user from dbUrl.
      .getOrElse(UserPass("", "")) // Last resort.
    PgConnection(dbUrl, userPass.user, userPass.password)
  }

  private case class UserPass(user: String, password: String)

  private def parseDbUrl(dbUrl: String): Option[UserPass] = {
    val dbUri = new URI(dbUrl)
    (for {
      info <- Try(dbUri.getUserInfo)
      user <- Try {
        val split = info.split(":")
        UserPass(split(0), split(1))
      }
    } yield user).toOption
  }
}


