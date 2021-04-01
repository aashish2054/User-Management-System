package org.knoldus.model

sealed trait userType

object userType{

  case object Customer extends userType
  case object Admin extends userType
}


