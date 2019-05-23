package com.ct.ucloud

trait JobDriver {
  @throws[Exception] def runJob(args: String*): String
}
