package io.github.tobetwo.ucloud.driver

import io.github.tobetwo.ucloud.util.FileUtil
import io.github.tobetwo.ucloud.{FileManager, JobDriver}

class Printer extends JobDriver {
  override def runJob(args: String*): String =
    FileManager.download(args.head)(FileUtil.print)
}
