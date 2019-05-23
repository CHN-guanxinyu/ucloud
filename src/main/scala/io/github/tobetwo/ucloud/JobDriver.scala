package io.github.tobetwo.ucloud

trait JobDriver {
  @throws[Exception] def runJob(args: String*): String
}
