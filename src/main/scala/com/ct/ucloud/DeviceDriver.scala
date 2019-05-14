package com.ct.ucloud

trait DeviceDriver {
  @throws[Exception] def runJob(args: String*): String
}
