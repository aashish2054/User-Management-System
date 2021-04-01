package org.knoldus.model

import java.util.UUID

case class User(id:Option[UUID], name:String, _type:userType)