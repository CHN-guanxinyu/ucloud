package com.ct.ucloud.driver

import com.ct.ucloud.util.FileUtil
import com.ct.ucloud.{FileManager, JobDriver}

class Printer extends JobDriver {
  override def runJob(args: String*): String =
    FileManager.download(args.head)(FileUtil.print)
}
