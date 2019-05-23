package com.ct.ucloud.driver

import com.ct.ucloud.util.FileUtil
import com.ct.ucloud.{DeviceDriver, FileManager}

class Printer extends DeviceDriver {
  override def runJob(args: String*): String =
    FileManager.download(args.head)(FileUtil.print)
}
